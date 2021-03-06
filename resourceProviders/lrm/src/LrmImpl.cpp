#include <iostream>
#include <fstream>
#include <string>
#include <exception>

extern "C" {
//FIXME OBS: Needed to deal with g++ 2.95. Go figure.
#ifndef __need_timespec
#define __need_timespec
#endif
#ifndef __USE_POSIX199309
#define __USE_POSIX199309
#endif
#include <time.h>
#include <unistd.h>
#include <fcntl.h>
#include <pthread.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <sys/times.h>
#include <sys/utsname.h>
#include <sys/wait.h>
#include <dirent.h>
#include <lua.h>
#include <lualib.h>
#include <lauxlib.h>
}

#include "NodeStaticInformation.hpp"
#include "DynamicInformationMonitor.hpp"
#include "LrmImpl.hpp"
#include "LrmSkeleton.hpp"

#include "LinuxProcess.hpp"
#include "LinuxSpecifics.hpp"
#include "GrmStub.hpp"
#include "AgentHandlerStub.hpp"

#include "utils/c++/LuaUtils.hpp"
#include "utils/c++/Config.hpp"
#include "utils/c++/OrbUtils.hpp"
#include "utils/c++/NoSuchConfigException.hpp"
#include "utils/c++/StringUtils.hpp"

#include "ExecutionSpecs.hpp"

/** Uncomment the following line if debugging information should be displayed */
//#define LRM_DEBUG

/** Directory for saving checkpoints in the local filesystem */
#define LOCAL_FS_DIR "/tmp/"

//---------------------------------------------------------------------
LrmImpl * LrmImpl::singleInstance_ = NULL;

//---------------------------------------------------------------------
LrmImpl & LrmImpl::init(const Config & config){

    if(LrmImpl::singleInstance_ == NULL) {
        LrmImpl::singleInstance_ = new LrmImpl(config);
        LrmImpl::singleInstance_->configureProxies();
        LrmImpl::singleInstance_->configureServer(config);
    }
      
    return *LrmImpl::singleInstance_;
}


//---------------------------------------------------------------------
void LrmImpl::removePreviousExecutionDirectories(){

    // TODO: This is not a nice way to remove old execution directories
    system("rm -rf [0-9]*:[0-9]*:[0-9]*:[0-9]*");  
    
}

//---------------------------------------------------------------------

#ifdef SECURITY
bool LrmImpl::isSecure(){
    return secureAppRepos;
}
#endif
    
//---------------------------------------------------------------------
void LrmImpl::updateLoop(){

    NodeStaticInformation nodeStaticInformation;
    ostringstream binaryNameStream;
    binaryNameStream << nodeStaticInformation.getOsName() << "_" << nodeStaticInformation.getProcessorName();
    binaryName = binaryNameStream.str();
        
    pthread_mutex_lock(&grmStubLock);        
    grmStub->registerLrm(LrmSkeleton::singleInstance().lrmIor(), nodeStaticInformation);
    pthread_mutex_unlock(&grmStubLock);

    DynamicInformationMonitor dynamicInformationMonitor(nodeStaticInformation, threshold);
    int timeSinceLastUpdate = 0;

    //FIXME: Lacks treatment for remote call errors.
    while(true){
        if(dynamicInformationMonitor.hadSignificantChange() || timeSinceLastUpdate >= keepAliveInterval.value()) {
	        pthread_mutex_lock(&grmStubLock);
	        grmStub->updateLrmInformation(LrmSkeleton::singleInstance().lrmIor(), dynamicInformationMonitor);
	        pthread_mutex_unlock(&grmStubLock);
            timeSinceLastUpdate = 0;
        }
        else {
            timeSinceLastUpdate += sampleInterval.value();
        }
        sleep(sampleInterval.value());
    }
}

//---------------------------------------------------------------------
LrmImpl::LrmImpl(const Config & config):appId(1){

	useExecManagerFlag = true;
      
    sampleInterval.set(StringUtils::string2int(config.getConf("sampleInterval")));
    keepAliveInterval.set(StringUtils::string2int(config.getConf("keepAliveInterval")));
    threshold = StringUtils::string2float(config.getConf("threshold"));
    lupaEnabled = StringUtils::string2int(config.getConf("enableLupa"));

    orbPath = config.getConf("orbPath");
    serverNameLocation = config.getConf("serverNameRef");
    CosNamingIdlPath = config.getConf("cosNamingIdlPath");   
 
    ahIorFile = "";
    ahIor = "";
    try{
      char hname[255], *tok = NULL;

      gethostname (hname, 254);

      tok = strtok (hname, ".");

      if (tok != NULL) {
        strcpy (hname, tok);
      }



      ahIorFile = config.getConf("ahIorDir") + "/" + hname + ".ior";


    }
    catch(const NoSuchConfigException & nsce){
     ahIor = config.getConf("ahIor");
    }




    appReposIdlPath = config.getConf("appReposIdlPath");
    resourceManagementIdlPath = config.getConf("resourceManagementIdlPath");
    ahIdlFile = config.getConf("ahIdlFile");
    if (ahIor == "") {
        ifstream ifs(ahIorFile.c_str());
        ifs >> ahIor;
        ifs.close();


      }


    // Security Configuration
    #ifdef SECURITY
    secureAppRepos=(config.getConf("secureAppRepos").compare("true")==0);
    #endif

    localDir = LOCAL_FS_DIR;
    localDir += "/lrmData/";
    mkdir(localDir.c_str(), 0700);
    string execTimeFileName = localDir;
    execTimeFileName += "execTime.dat";
    execTimeFile.open(execTimeFileName.c_str(), ios::app);
    
    pthread_mutex_init(&execTimeLock, NULL);
    lastCsTime=0; lastCuTime=0;

    pthread_mutex_init(&launchedAppsInfoLock, NULL);
    pthread_mutex_init(&finishedAppsInfoLock, NULL);

    pthread_mutex_init(&restartLock, NULL);
    pthread_cond_init (&restartCondition, NULL);
    pthread_mutex_init(&applicationRepositoryStubLock, NULL);
    pthread_mutex_init(&asctStubLock, NULL);
    pthread_mutex_init(&grmStubLock, NULL);
    //pthread_mutex_init(&execman_stub_mutex, NULL);
    
}

//---------------------------------------------------------------------
void LrmImpl::configureProxies() {
       
    // Name Resolving  
    nameServiceStub = new NameServiceStub(lua_open(),serverNameLocation,CosNamingIdlPath);
  	
    // Creates GRM stub    
    grmIor = nameServiceStub->resolve("GRM");
    if ( grmIor.compare("") == 0 ) {
        cerr << "[CRITICAL] Could not resolve GRM. Exiting..." << endl;
        exit(-1);
    }
    grmState = lua_open();    
    OrbUtils::initLuaState(grmState);
    OrbUtils::loadOrb(grmState, orbPath);
    OrbUtils::loadIdl(grmState, resourceManagementIdlPath);    
    grmStub = new GrmStub(grmState, grmIor);

    // Creates Application Repository stub
    appReposIor = nameServiceStub->resolve("AR");     
    if ( appReposIor.compare("") == 0 ) {
        cerr << "[CRITICAL] Could not resolve Application Repository. Exiting..." << endl;
        exit(-1);
    }
    struct lua_State *appReposState = lua_open();
    OrbUtils::initLuaState(appReposState);
    OrbUtils::loadOrb(appReposState, orbPath);
    OrbUtils::loadIdl(appReposState, appReposIdlPath);
    
    #ifdef SECURITY
    if(secureAppRepos) {
   		Config lsmConfig("arsc.conf");
    	appReposStub = new SecureApplicationRepositoryStub(appReposState, appReposIor, ArscImpl::init(lsmConfig)); 	
    }
    else
    #endif
    { appReposStub = new ApplicationRepositoryStub(appReposState, appReposIor); }

    // Creates Execution Manager stub
    if (useExecManagerFlag == true) {
        execManagerIor = nameServiceStub->resolve("EM");  
        if ( execManagerIor.compare("") == 0 ) {
            cerr << "[CRITICAL] Could not resolve Execution Manager. Exiting..." << endl;        
            exit(-1);
        }
        nExecManagerStubs = 4;
        execManagerStubs  = new ExecutionManagerStub *[nExecManagerStubs];
        executionManagerStubLockList = new pthread_mutex_t[nExecManagerStubs];
        for (int i=0; i<nExecManagerStubs; i++) {
            struct lua_State *execManState = lua_open();
            OrbUtils::initLuaState(execManState);
            OrbUtils::loadOrb(execManState, orbPath);
            OrbUtils::loadIdl(execManState, resourceManagementIdlPath);
            execManagerStubs[i] = new ExecutionManagerStub (execManState, execManagerIor);
            pthread_mutex_init(&executionManagerStubLockList[i], NULL);
        }
    }
    
    removePreviousExecutionDirectories();

}
  
  //---------------------------------------------------------------------
  void LrmImpl::configureServer(const Config & config){
    LrmSkeleton::init(this, config);
  }
  
  //---------------------------------------------------------------------
  void LrmImpl::copyFile(const string & srcPath, const string & dstPath){

    ifstream ifs(srcPath.c_str());
    ofstream ofs(dstPath.c_str());
    while(ifs.good()){
      char c;
      ifs.get(c);
      ofs << c;
    }
    ifs.close();
    ofs.close();

  }

  //---------------------------------------------------------------------
  long LrmImpl::remoteExecutionRequest(ExecutionSpecs * execSpecs){

      // Asks LUPA if an application can run at this host
      if (lupaEnabled && !lupa.canRunGridApplication()) {
          return 0;
      }

      pthread_t thread2;
      pthread_create(&thread2, NULL,(void * (*)(void *))
                     startRemoteExecutionWrapper ,(void *) execSpecs );
      pthread_detach(thread2);

      return 1;
  }
  
  //--------------------------------------------------------------------- 
  void * LrmImpl::startRemoteExecutionWrapper(void * ptr){
    
    ExecutionSpecs *data = (ExecutionSpecs *)ptr; 
    LrmImpl::singleInstance_->startRemoteExecution(*data);
    delete data;
    return NULL;

  }

  //--------------------------------------------------------------------
  void LrmImpl::startRemoteExecution(ExecutionSpecs execSpecs){

    // FIXME: Some features are missing
    // 1) We need to compare current resource availability with a master
    // threshold(2k: cpu <= 90%), and evaluate if current resources are
    // "in spec"
    // 2) We need to compare execution constraits with actual resource
    // availability and determine if GRM's hint is still accurate.
    // for 1) and 2) we need sort of a TCL validator. 2k makes use
    // a TAO_CONSTRAINT_VALIDATOR. For now, we will assume that GRM's
    // hint is still accurate, so we just launch the application
    // We are also temporarily excluding:
    // 3) DSRT
    // 4) Prelaunched processes
    
    #ifdef LRM_DEBUG
    cerr << "-------------------------------------------------------------------" << endl;
    cerr << "LrmImpl::remoteExecutionRequest-->>Starting Execution, request:" 
         << execSpecs.applicationId() << "|" << execSpecs.processId() 
         << ", args: " << execSpecs.appArgs() << endl;
    #endif
     // If this is an InteGrade process

    if( (execSpecs.applicationName().find(".class")==string::npos and execSpecs.applicationName().find(".jar")==string::npos )){
      cerr << "Integrade process" << endl;
    ostringstream executionIdStream;
    executionIdStream << execSpecs.applicationId() << ":" << execSpecs.processId();

    /** Revert the applicationId to the value assigned by the Asct, so that    *
     *  it will recognize the value.                                              */
    int rpos = execSpecs.applicationId().rfind(":");
    int npos = execSpecs.applicationId().npos;
    execSpecs.asctApplicationId(execSpecs.applicationId().substr(rpos+1, npos - rpos+1));
   
    /** Creates a directory for application execution */
    string executionId = executionIdStream.str();
    LinuxProcess::createDir(executionId);
    string currentAppPath = executionId + "/" + executionId;
    copyFile("asct.conf", executionId + "/asct.conf");    
    // Vinicius { copyFile("broker.conf",  executionId + "/broker.conf" ); }
    copyFile("ckp.conf",  executionId + "/ckp.conf" );
    {
        ofstream experFile;
        string ckpFileName = executionId + "/ckp.conf";
        experFile.open(ckpFileName.c_str(), ios::app);
        experFile << "execId " << executionId  << endl;
        experFile.close();
    }
    
        
    /** Download application code from the applicationRepository */
    pthread_mutex_lock(&applicationRepositoryStubLock);
	 string applicationFile;

     cout << execSpecs.basePath() << " " <<  execSpecs.applicationName() << " " << binaryName << endl; 
     cout << "previousGrmIor:" <<  execSpecs.previousGrmIor() << endl; 
     
	 if((execSpecs.previousGrmIor().compare("")) == 0) {
		 cout << "Getting application from the local AR" << endl;
		 applicationFile = appReposStub->getApplicationBinary(
                execSpecs.basePath(), execSpecs.applicationName(), binaryName);
	 }
	 else {
		 cout << "Getting application from a remotely identified AR via the local AR" << endl;
         applicationFile = appReposStub->getRemoteApplicationBinary(
                execSpecs.basePath(), execSpecs.applicationName(), binaryName,
                execSpecs.applicationRepositoryIor());
	 }
     cout << "downloadBinary-->>fileSize: " <<  applicationFile.length() << endl; 

     // write application code to file
     std::ofstream outputFile;
     outputFile.open(currentAppPath.c_str(),ios::out|ios::binary|ios::trunc);
     outputFile.write(applicationFile.c_str(),applicationFile.length());
     outputFile.close();
    pthread_mutex_unlock(&applicationRepositoryStubLock);
                  
    #ifdef LRM_DEBUG
    cerr << "LrmImpl::remoteExecutionRequest-->> Downloaded application id: "
         << execSpecs.appId() << ", args: " << execSpecs.appArgs() << endl;
    #endif
    
    /** Creates a new AsctStub for the requesting Asct if it was not already  *
     *  created. The list of available ASCTs are stored in a map asctStubs,   *
     *  using the Asct ior as the key to the map.                             */
    pthread_mutex_lock(&asctStubLock);
      map<string, AsctStub *>::iterator asctStubIt = 
            asctStubs.find( execSpecs.requestingAsctIor() );
      AsctStub *asctStub;
      if ( asctStubIt == asctStubs.end()) {
          struct lua_State *asctState = lua_open();
          OrbUtils::initLuaState(asctState);
          OrbUtils::loadOrb(asctState, orbPath);
          OrbUtils::loadIdl(asctState, resourceManagementIdlPath);
          asctStub = new AsctStub(asctState, execSpecs.requestingAsctIor() );
          asctStubs[execSpecs.requestingAsctIor()] = asctStub;
      }
      else 
          asctStub = (*asctStubIt).second;
      /**  Requests the input files to the requesting ASCT */
      asctStub->getInputFiles(execSpecs.asctApplicationId(), 
                              execSpecs.processId(), executionId); // Vinicius on replicaId
    pthread_mutex_unlock(&asctStubLock);
     
    struct stat bspFileStatBuf;
    string bspFilePath = executionId + "/bspExecution.conf";
    if (stat(bspFilePath.c_str(), &bspFileStatBuf) == 0) {
        ofstream bspFileStream(bspFilePath.c_str(), ios::app);
        bspFileStream << "applicationId " << execSpecs.applicationId();
    }
    
    /** Prepare time logging */
    struct timeval startTimeTv;
    gettimeofday(&startTimeTv, NULL);
    startTime = startTimeTv.tv_sec + startTimeTv.tv_usec/1000000.0;

    /** Creates a new linux process, starting a new thread to wait for the    *
     *  process to fininish. The new thread captures the exit status of       *
     *  the process and reports to the ExecutionManager and requesting ASCT.  */     
    int pid = LinuxProcess::createLinuxProcess(executionId, execSpecs.appArgs());

    cerr << "LrmImpl::remoteExecutionRequest-->>Execution started, appId:" << executionId
         << ", args:\"" << execSpecs.appArgs() << "\", pid:" << pid << endl; 

    AppInfoHolder appInfo;
    // Adds execution to the launchedAppsInfo table
    pthread_mutex_lock(&finishedAppsInfoLock);
    pthread_mutex_lock(&launchedAppsInfoLock);

      // Reinitializing a failed application    
      if (finishedAppsInfo.find(executionId) != finishedAppsInfo.end()) {
        appInfo = (*finishedAppsInfo.find(executionId)).second;
        appInfo.setPid( pid );
        appInfo.setRestartId( appInfo.restartId() + 1 );
        launchedAppsInfo[executionId] = appInfo;
        finishedAppsInfo.erase(executionId);
      }
      else {
        AppInfoHolder newAppInfo(pid, execSpecs.asctApplicationId(), 
                                 execSpecs.applicationId(), 
                                 execSpecs.processId(), asctStub, // Vinicius on replicaId
                                 execSpecs.outputFiles(), startTime);
        appInfo = newAppInfo;
        launchedAppsInfo[executionId] = appInfo;
        appExitFlags[executionId] = none_;        
      }     
      
    pthread_mutex_unlock(&launchedAppsInfoLock);
    pthread_mutex_unlock(&finishedAppsInfoLock);   
         
    /** Notifies the ASCT that execution has begun */    
    pthread_mutex_lock(&asctStubLock);   
      asctStub->setExecutionAccepted(LrmSkeleton::singleInstance().lrmIor(), executionId,
				                     execSpecs.asctApplicationId(), execSpecs.processId()); // Vinicius on replicaId
    pthread_mutex_unlock(&asctStubLock);

    /** Notifies the ExecutionManager that execution has begun */    
    if (useExecManagerFlag == true) {
        int l;
        for (l=0; l<nExecManagerStubs ;l++)
            if (pthread_mutex_trylock(&executionManagerStubLockList[l]) == 0) {
                execManagerStubs[l]->setProcessExecutionStarted(execSpecs.applicationId(), execSpecs.processId(), // Vinicius on replicaId
                                                                LrmSkeleton::singleInstance().lrmIor(),
                                                                appInfo.restartId(), executionId);
                pthread_mutex_unlock(&executionManagerStubLockList[l]);
                break;
            }
        #ifdef LRM_DEBUG
        cerr << "LrmImpl::remoteExecutionRequest-->> ExecManager Notified!!! id:" << execSpecs.appId() << endl;
        #endif
        if (l == nExecManagerStubs)
            cerr << "LrmImpl::remoteExecutionRequest-->> ERROR: Could not acquire lock for the ExecutionManagerStub!" << endl;
    }
   
    /**  This thread blocks waiting for the exit status of the launched  *
     *   process, reporting to the ExecutionManager and requesting ASCT. */    
    int termStatus;
    pid_t termChildId = waitpid(pid, &termStatus, 0);
    
    /** Notify the Asct and ExecutionManager about the application finish */    
    notifyStatus(executionId, termChildId, termStatus);
     } else {
       //if it is a Mag process
       // Vinicius {
       ostringstream executionIdStream;
       executionIdStream << execSpecs.applicationId() << ":" << execSpecs.processId(); // Vinicius on replicaId
       cout << "MAG process " << executionIdStream.str() << endl;

       /** Revert the applicationId to the value assigned by the Asct, so that    *
        *  it will recognize the value.                                              */
       int rpos = execSpecs.applicationId().rfind(":");
       int npos = execSpecs.applicationId().npos;
       // tirei execSpecs.asctApplicationId(execSpecs.applicationId().substr(rpos+1, npos - rpos+1));

       /** Creates a directory for application execution */
       string executionId = executionIdStream.str();
       //LinuxProcess::createDir(executionId);
       // FIXME Vari�vel sem fun�ao 
       // string currentAppPath = executionId + "/" + executionId;
       // copyFile("asct.conf", executionId + "/asct.conf");    
       // Vinicius { copyFile("broker.conf",  executionId + "/broker.conf" ); }
       // copyFile("ckp.conf",  executionId + "/ckp.conf" );
       //{
       //    ofstream experFile;
       //    string ckpFileName = executionId + "/ckp.conf";
       //    experFile.open(ckpFileName.c_str(), ios::app);
       //    experFile << "execId " << executionId  << endl;
       //    experFile.close();
       //}
      // } Vinicius
      struct lua_State * agentHandlerState;
      agentHandlerState = lua_open();

      OrbUtils::initLuaState(agentHandlerState);
      OrbUtils::loadOrb(agentHandlerState, orbPath);
cout << "ahIdlFile " << ahIdlFile << endl;
      OrbUtils::loadIdl(agentHandlerState, ahIdlFile);

cout << "ahIor " << ahIor << endl;
      AgentHandlerStub agentHandlerStub(agentHandlerState, ahIor);
      // Vinicius DEBUG {
      cout << "ExecSpecs.applicationId: " << execSpecs.applicationId().c_str() << endl;
      // } Vinicius 
      agentHandlerStub.remoteExecutionRequest(execSpecs);

      lua_close(agentHandlerState);


    }
  
  }

  //---------------------------------------------------------------------
  void LrmImpl::kill(const string & appId){

    if(LinuxSpecifics::isAppRunning(appId + "/" + appId)){
      // Remove process from active processes list      
      pthread_mutex_lock(&launchedAppsInfoLock);
        int pid = launchedAppsInfo[appId].pid();
        appExitFlags[appId] = kill_;
      pthread_mutex_unlock(&launchedAppsInfoLock);
      LinuxSpecifics::killProcess(pid);
    }
       
    // TODO: Determine if we should clean up the files, return partial results, etc...
  }

  //--------------------------------------------------------------------
  int LrmImpl::getLastCkpNumber(const string & appId) {
    int lastCkpNumber = -1;
    DIR *execDir = opendir(appId.c_str());
    if (execDir == NULL) {
      cerr << "LrmImpl::getLastCkpNumber-->>Invalid directory: " << appId << endl;
      return -1;
    } 
    struct dirent *file;
    file = readdir(execDir);
    while (file != NULL) {
      string filename = file->d_name;
      string::size_type extPos = filename.find(".ckp");
      /** 'filename' has the extension ".ckp" */
      if (extPos != string::npos && extPos == filename.length()-4) {
        string number = filename.substr(0, extPos);
        unsigned int numberPos;
        for (numberPos=0; numberPos < extPos; numberPos++)
          if(isdigit(number[numberPos]) == 0) break;
        /** All characters in 'filename' are digits */
        if (numberPos == extPos) {
          int ckpNumber = atoi(number.c_str());
          if (lastCkpNumber < ckpNumber) 
            lastCkpNumber = ckpNumber;
        }
      } // if (extPos != std::npos)
      file = readdir(execDir);     
    } // while (file != NULL)
    return lastCkpNumber;
  }

  //---------------------------------------------------------------------
  int LrmImpl::restartExecution(const string * appId, const string * appArgs){
     
      const string **data = new const string *[2];
      data[0] = appId;
      data[1] = appArgs;

      appId   = new string (appId->c_str());
      appArgs = new string (appArgs->c_str());

      pthread_mutex_lock(&restartLock);      

        /** A new thread is created to wait for the exit status
         *  of the restarted application */              
        pthread_t thread2;
        pthread_create(&thread2, NULL,(void * (*)(void *))
                       restartExecWrapper ,(void *) data );
        pthread_detach(thread2);
                
        map<string, enum RestartFlagsEnum>::iterator it = appRestartFlags.find(*appId);
        /** Waits for the restarting process to finish */          
        while (it == appRestartFlags.end()) {
          pthread_cond_wait(&restartCondition, &restartLock);
          it = appRestartFlags.find(*appId);
        }        
        /** Is used to notify the execution manager if the application was
         *  restarted succesfully or not. */
        enum RestartFlagsEnum restartFlag = (*it).second;
        appRestartFlags.erase(*appId);

      pthread_mutex_unlock(&restartLock);
                                     
      if ( restartFlag == restarted_ ) {
        cerr << "LrmImpl::restartExecution-->> (" << *appId <<  ") Restarted succesfully." << endl;         
        return 0;
      } 
      else if ( restartFlag == notRunning_ ) {
        cerr << "LrmImpl::restartExecution-->> (" << *appId <<  ") Application not running." << endl;
        return -1;
      } 
      else 
        assert(false);
     
      // TODO: Insert a semaphore to ensure that the application was restarted
  }
  
  //---------------------------------------------------------------------
  
  void * LrmImpl::restartExecWrapper(void * ptr){
    
    const string **data = (const string **)ptr; 
    LrmImpl::singleInstance_->restartExecThread(*(data[0]), *(data[1]));
    delete data[0];
    delete data[1];    
    delete[] data;        
    return NULL;

  }
  
  //--------------------------------------------------------------------
  void LrmImpl::restartExecThread(const string & appId, const string & appArgs) {

    #ifdef LRM_DEBUG
    cerr << "LrmImpl::restartingExecution-->>appId:" << appId << " appArgs:"<< appArgs << endl;
    #endif

    AppInfoHolder appInfo;
    pthread_mutex_lock(&restartLock);    
    
    /** Gets the current process id 'pid' from 'appId' */
    pthread_mutex_lock(&launchedAppsInfoLock);
    {
      map<string, AppInfoHolder>::iterator it = launchedAppsInfo.find(appId);
      
      /** Process was not running */
      if (it == launchedAppsInfo.end()) {
        pthread_mutex_unlock(&launchedAppsInfoLock);     

        //pthread_mutex_lock(&restart_mutex);    
          appRestartFlags.insert(make_pair(appId, notRunning_));    
          pthread_cond_broadcast(&restartCondition);
        pthread_mutex_unlock(&restartLock);      
        return;
      }
      appInfo = (*it).second;                 

      /** Kills the running process */
      int killFlag = LinuxSpecifics::killProcess(appInfo.pid());

      /** Process was not running but it is in lanchedAppsInfoList */
      if (killFlag != 0) {
        cerr << "LrmImpl::restartExecution-->>appId:" << appId 
             << " Process already killed, but in running apps List!!!!" << endl;  
        pthread_mutex_unlock(&launchedAppsInfoLock);        
         
        //pthread_yield (); // Allows the notify status thread to execute     
        //pthread_mutex_lock(&restart_mutex);    
          appRestartFlags.insert(make_pair(appId, notRunning_));    
          pthread_cond_broadcast(&restartCondition);
        pthread_mutex_unlock(&restartLock);
        return;              
      }
      
      appExitFlags[appId] = restart_;
      //launchedAppsInfo.erase(appId);      
    }
    pthread_mutex_unlock(&launchedAppsInfoLock);    
    
    struct timeval startTimeTv;
    gettimeofday(&startTimeTv, NULL);
    startTime = startTimeTv.tv_sec + startTimeTv.tv_usec/1000000.0;

    int newPid = LinuxProcess::createLinuxProcess(appId, appArgs);
    appInfo.setPid(newPid);
    appInfo.setRestartId( appInfo.restartId() + 1 );
     
    /** Sets 'appId' with the new pid 'newPid' */
    pthread_mutex_lock(&launchedAppsInfoLock);
      launchedAppsInfo[appId] = appInfo;
    pthread_mutex_unlock(&launchedAppsInfoLock);

    #ifdef LRM_DEBUG        
    cerr << "LrmImpl::restartExecution-->> New entry added to launchedAppsInfo,"
         << " appId="<< appId << " pid=" << newPid << endl; 
    #endif           

    appRestartFlags.insert(make_pair(appId, restarted_));    
    pthread_cond_broadcast(&restartCondition);    
    pthread_mutex_unlock(&restartLock);

    /**  This thread blocks wating for the exit status of the launched        *
     *   process, reporting to the ExecutionManager and requesting ASCT.      */    
    int termStatus;
    pid_t termChildId = waitpid(newPid, &termStatus, 0);
       
    /** Notify the Asct and ExecutionManager about the application finish.    */        
    notifyStatus(appId, termChildId, termStatus);

  }

  //--------------------------------------------------------------------
  void LrmImpl::notifyStatus(string appId, pid_t termChildId, int termStatus) {
    
    #ifdef LRM_DEBUG
    /** Display the list of active processes */
    pthread_mutex_lock(&launchedAppsInfoLock);                          
    {
      map<string, AppInfoHolder>::iterator it = launchedAppsInfo.begin();
        cerr << "LrmImpl::notifyStatus-->> Running processes(" << appId <<  ") [";
        for ( ; it != launchedAppsInfo.end(); it++)
          cerr << (*it).first << "|" ;
        cerr << "]" << endl;
    }
    pthread_mutex_unlock(&launchedAppsInfoLock);                  
    #endif    
    
    string asctApplicationId, applicationId, processId; // Vinicius on replicaId
    AsctStub *asctStub;
      
    pthread_mutex_lock(&launchedAppsInfoLock);
      enum ExitFlagsEnum exitFlag = appExitFlags[appId];
      /** The value 'restart_' indicates that the process is being restarted. *
       *  The ExecutionManager is already aware of the restart and should not *
       *  be reported. The Asct should also not be reported.                  */
      if ( exitFlag == restart_ ) { // Ignore application exit
        #ifdef LRM_DEBUG
          cerr << "LrmImpl::notifyStatus()-->> Restarting!!! appId:" << appId << endl;    
        #endif
        appExitFlags[appId] = none_;             
        pthread_mutex_unlock(&launchedAppsInfoLock);
        return;                
      }        
      map<string, AppInfoHolder>::iterator it = launchedAppsInfo.find(appId);
      /** In case the application that was just finished was not running      *
       *  This is an error that should not happen.                            */
      if (it == launchedAppsInfo.end()) { 
        #ifdef LRM_DEBUG
          cerr << "LrmImpl::notifyStatus()-->> Application was not running!!!" << endl;
        #endif
        pthread_mutex_unlock(&launchedAppsInfoLock);
        return;        
      }
      /** Saves appId data and erase from 'launchedAppsInfo' */
      pthread_mutex_lock(&finishedAppsInfoLock);
        finishedAppsInfo[appId] = (*it).second;
      pthread_mutex_unlock(&finishedAppsInfoLock);
      asctStub         = (*it).second.asctStub();
      applicationId = (*it).second.applicationId();    
      processId = (*it).second.processId();
      //replicaId = (*it).second.replicaId(); // Vinicius
      asctApplicationId = (*it).second.asctApplicationId();    
      launchedAppsInfo.erase(appId);        
    pthread_mutex_unlock(&launchedAppsInfoLock);     
    
    cerr << "LrmImpl::notifyStatus()-->> appFinished --"
         << "Process:"  << termChildId
         << ", Main:"   << applicationId
         << ", Node:"   << processId << endl;
         //<< ", Rep:"   << replicaId << endl; // Vinicius

    int exitStatus = 0;
    // Application will not be reinitialized
    if (exitFlag == kill_) exitStatus = -2;
    
    // Normal exit: status --> WEXITSTATUS(termStatus);
    else if ( WIFEXITED(termStatus) ) { 
        #ifdef LRM_DEBUG
        cerr << "LrmImpl::notifyStatus() -->> Normal exit" << endl;
        #endif
        exitStatus = 0;            
    }

    // Abnormal exit
    else if ( WIFSIGNALED(termStatus) ) { 
        // Process was explicitly killed and should be reinitialized
        if (WTERMSIG(termStatus) == SIGKILL || WTERMSIG(termStatus) == SIGTERM) {
            #ifdef LRM_DEBUG
            cerr << "LrmImpl::notifyStatus() -->> Explicitly Killed! Restarting" << endl;
            #endif
            exitStatus = 1;
        }
        else { // Process ended due to other failure and will not be reinitialized
            #ifdef LRM_DEBUG
            cerr << "LrmImpl::notifyStatus() -->> Abnormal exit" << endl;
            #endif
            exitStatus = -1;
        }
    }
    
    int reportExecutionFinishedFlag = 0;
    /** Notifies the ExecutionManager */
    if (useExecManagerFlag == true) {
        #ifdef LRM_DEBUG
        cerr << "LrmImpl::notifyStatus() -->> Notifying ExecutionManager. [" << appId << "]" << endl;
        #endif
        int l;
        for (l=0; l<nExecManagerStubs ;l++)
            if (pthread_mutex_trylock(&executionManagerStubLockList[l]) == 0) {
            /** If the ExecutionManager returns 1 to the 'reportExecutionFinished'  *
              * call, it means the application will not be reinitialized.           */
            reportExecutionFinishedFlag = 
            execManagerStubs[l]->setProcessExecutionFinished(applicationId, processId, // Vinicius on replicaId 
                                                             finishedAppsInfo[appId].restartId(), exitStatus);
            pthread_mutex_unlock(&executionManagerStubLockList[l]);    
            break;          
        }

        #ifdef LRM_DEBUG
        cerr << "LrmImpl::notifyStatus() -->> ExecutionManager Notified!!!. [" << appId << "|" << reportExecutionFinishedFlag <<"]" << endl;
        #endif        
        if (l == nExecManagerStubs)
            cerr << "LrmImpl::notifyStatus()-->> ERROR: Could not acquire lock for the ExecutionManagerStub!" << endl;        
    }
    
    /** Notifies the Asct, since the application will not be reinitialized.   *
     *  If not using an ExecutionManager, the ASCT is always notified.        *
     *  Using an ExecManager, the ASCT is notified depending on exitStatus    */   
    if ( ( exitFlag == none_ || exitFlag == kill_ ) &&
         ( useExecManagerFlag == false || exitStatus <= 0 || reportExecutionFinishedFlag == 1) ) {
    
        #ifdef LRM_DEBUG            
        cerr << "LrmImpl::notifyStatus() -->> Notifying ASCT. [" << appId 
             << "|" << reportExecutionFinishedFlag << "]" << endl;
        #endif
        
        pthread_mutex_lock(&asctStubLock);
	// Vinicius putting 3 arguments{
	cout << "Calling asctStub->setExecutionFinished" << endl;
        asctStub->setExecutionFinished(asctApplicationId, processId); // Vinicius on replicaId
	// } Vinicius
        pthread_mutex_unlock(&asctStubLock);      
    }

    pthread_mutex_lock(&launchedAppsInfoLock);
    appExitFlags[appId] = none_;             
    pthread_mutex_unlock(&launchedAppsInfoLock);    
    
  }

  //--------------------------------------------------------------------
