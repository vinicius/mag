extern "C" {
#include <pthread.h>
#include <lualib.h>
#include <lauxlib.h>
#include <lua.h>
}

#include "LrmSkeleton.hpp"
#include "LrmImpl.hpp"
#include "LinuxSpecifics.hpp"

#include "utils/c++/Config.hpp"
#include "utils/c++/OrbUtils.hpp"
#include "utils/c++/LuaUtils.hpp"
#include "utils/c++/StringUtils.hpp"

#include "ExecutionSpecs.hpp"

using std::cerr;
using std::endl;
 
LrmSkeleton * LrmSkeleton::singleInstance_ = NULL;

//-----------------------------------------------------------------------
LrmSkeleton & LrmSkeleton::init(LrmImpl * lrmImpl_, const Config & config){

    if(LrmSkeleton::singleInstance_ == NULL){
        LrmSkeleton::singleInstance_ = new LrmSkeleton(lrmImpl_, config);
        pthread_t thread;
        pthread_create(&thread, NULL,(void * (*)(void *))
                        serverSideSetup ,(void *) NULL );
        pthread_detach(thread);

    }
    return *LrmSkeleton::singleInstance_;
}

//-----------------------------------------------------------------------
void * LrmSkeleton::serverSideSetup(void * ptr){
 
    cerr << "Calling LrmSkeleton::serverSideSetup" << endl;   
    
    lua_State *state = LrmSkeleton::singleInstance_->serverSideState;

    LuaUtils::getField(state,"oil.run");
    if(lua_pcall(state, 0, 0, 0) != 0){
      lua_getglobal(state, "tostring");
      lua_insert(state, -2);
      lua_pcall(state, 1, 1, 0);
      cerr << "[ERROR] LrmSkeleton::serverSideSetup -> OiL error: " << endl
           << lua_tostring(state, -1) << " error " << endl;
      lua_pop(state, 1);
    }
    return NULL;
}

//---------------------------------------------------------------------
LrmSkeleton::LrmSkeleton(LrmImpl * lrmImpl, const Config & config): lrmImpl_(lrmImpl) {
    
    serverSideState = lua_open();
    OrbUtils::initLuaState(serverSideState);
    OrbUtils::loadOrb(serverSideState, config.getConf("orbPath"));
    OrbUtils::loadIdl(serverSideState, config.getConf("resourceManagementIdlPath"));

    lua_register(serverSideState, "setSampleIntervalWrapper", setSampleIntervalWrapper);
    lua_register(serverSideState, "setKeepAliveIntervalWrapper", setKeepAliveIntervalWrapper);
    lua_register(serverSideState, "requestExecutionWrapper", requestExecutionWrapper);
    lua_register(serverSideState, "requestOutputFilesWrapper", requestOutputFilesWrapper);
    lua_register(serverSideState, "getLastCheckpointNumberWrapper", getLastCheckpointNumberWrapper);
    lua_register(serverSideState, "restartExecutionWrapper", restartExecutionWrapper);    
    lua_register(serverSideState, "killProcessWrapper", killProcessWrapper);

    //servant implementation
    lua_dostring(serverSideState, " lrmImpl = {"
                        " setSampleInterval = function (self, seconds)"
                        "   setSampleIntervalWrapper(seconds)"
                        " end,"
                        " setKeepAliveInterval = function (self, seconds)"
                        "   setKeepAliveIntervalWrapper(seconds)"
                        " end,"
                        " requestExecution = function (self, applicationExecutionInformation, processExecutionInformation)"
                        "   return requestExecutionWrapper(applicationExecutionInformation, processExecutionInformation)"
                        " end,"
                        " requestOutputFiles = function(self, executionId)"
                        "   return requestOutputFilesWrapper(executionId)"
                        " end,"
                        " getLastCheckpointNumber = function(self, executionId)"
                        "   return getLastCheckpointNumberWrapper(executionId)"
                        " end,"
                        " restartExecution = function(self, executionId, processArguments)"
                        "   return restartExecutionWrapper(executionId, processArguments)"
                        " end,"
                        " killProcess  = function(self, executionId)"
                        "   killProcessWrapper(executionId)"
                        " end,"
                        " isAlive = function(self)"
                        "   return true"
                        " end,"                        
                        " }"
                );

    lua_dostring(serverSideState,
                 "lrmServant = oil.createservant(lrmImpl, 'IDL:resourceProviders/Lrm:1.0')");
    lrmIor_ = OrbUtils::getIor(serverSideState, "lrmServant");
    cout << "LrmSkeleton::serverSideSetup::LRM IOR: " << lrmIor_ << endl;
}

  //---------------------------------------------------------------------
  int LrmSkeleton::setSampleIntervalWrapper(struct lua_State * state){
    int n = lua_gettop(state);
    int sampleIntervalTmp = int(lua_tonumber(state, n));
    LrmSkeleton::singleInstance_->lrmImpl_->setSampleInterval(sampleIntervalTmp);
    return 0;
  }

  //---------------------------------------------------------------------
  int LrmSkeleton::setKeepAliveIntervalWrapper(struct lua_State * state){

    int n = lua_gettop(state);
    int keepAliveIntervalTmp = int(lua_tonumber(state, n));
    LrmSkeleton::singleInstance_->lrmImpl_->setKeepAliveInterval(keepAliveIntervalTmp);
    return 0;
  }

  //---------------------------------------------------------------------
  int LrmSkeleton::requestExecutionWrapper(struct lua_State * state){
    
    cout << "LrmSkeleton::requestExecutionWrapper" << endl;
        
    ExecutionSpecs *execSpecs = new ExecutionSpecs;

    // Here we have a table ProcessExecutionInformation at the top of the stack
    lua_pushstring(state, "executionRequestId");
    lua_gettable(state, -2);
    execSpecs->applicationId(LuaUtils::getStringFromTable(state,"requestId"));
    execSpecs->processId(LuaUtils::getStringFromTable(state,"processId"));
    // Vinicius {
    //execSpecs->replicaId(LuaUtils::getStringFromTable(state,"replicaId"));
    //cout << "ReplicaId: " << execSpecs->replicaId().c_str() << endl;
    // } Vinicius
    lua_pop(state, 1); // removes executionRequestId table
    
    execSpecs->appArgs(LuaUtils::getStringFromTable(state,"processArguments"));    

    lua_pushstring(state, "outputFileNames");
    lua_gettable(state, -2);
    execSpecs->outputFiles(LuaUtils::extractStringSequence(state, -1));
    lua_pop(state, 1); // removes outputFileNames table    
    
    lua_pop(state, 1); // removes DistinctExecutionSpecs table    
    // ----------------------------------------------------------------------

    // Here we have a table ApplicationExecutionInformation at the top of the stack    
    execSpecs->requestingAsctIor(LuaUtils::getStringFromTable(state,"requestingAsctIor"));
    execSpecs->grmIor(LuaUtils::getStringFromTable(state,"originalGrmIor"));
    execSpecs->previousGrmIor(LuaUtils::getStringFromTable(state,"previousGrmIor"));
    execSpecs->applicationRepositoryIor(LuaUtils::getStringFromTable(state,"applicationRepositoryIor"));
    execSpecs->basePath(LuaUtils::getStringFromTable(state,"basePath"));
    execSpecs->applicationName(LuaUtils::getStringFromTable(state,"applicationName"));
    // execSpecs->binaryName(LuaUtils::getStringFromTable(state,"binaryName"));
    execSpecs->appConstraints(LuaUtils::getStringFromTable(state,"applicationConstraints"));
    execSpecs->appPreferences(LuaUtils::getStringFromTable(state,"applicationPreferences"));
    execSpecs->numberOfReplicas(LuaUtils::getStringFromTable(state,"numberOfReplicas"));
    // Vinicius {
    cout << "Number of Replicas: " << execSpecs->numberOfReplicas().c_str() << endl;
    cout << "ProcessId: " << execSpecs->processId() << endl;
    // } Vinicius
        
    lua_pop(state, 1); // removes ApplicationExecutionInformation table
    // ----------------------------------------------------------------------

    long test = 0;
    test = LrmSkeleton::singleInstance_->lrmImpl_->remoteExecutionRequest(execSpecs);
    lua_pushnumber(state, test);

    return 1;
  }

  //---------------------------------------------------------------------
  int LrmSkeleton::requestOutputFilesWrapper(struct lua_State * state){
    // Vinicius uncommentend {
    cout << "LrmSkeleton::requestOutputFilesWrapper" << endl;
    // } Vinicius
    string executionId(lua_tostring(state,-1));
    lua_pop(state, 1);//Pops executionId
    const AppInfoHolder appInfo = LrmSkeleton::singleInstance_->lrmImpl_->appInfo(executionId);

    lua_newtable(state);
    int lastSetFile = 1;
    for(unsigned int i = 0; i < appInfo.outputFiles().size(); i++){
      // Vinicius uncommented {
      cout << "i: " << i << " " << appInfo.outputFiles()[i] << endl;
      // } Vinicius
      lua_newtable(state);
      LuaUtils::setFieldOnTable(state, "fileName", StringUtils::stripPath(appInfo.outputFiles()[i]).c_str(), -1);
      lua_pushstring(state, "file");
      try{
        string appPath = executionId + "/" +  StringUtils::stripPath(appInfo.outputFiles()[i]);
	// Vinicius {
        cout << "appPath: " << appPath << endl;
        // } Vinicius
        LuaUtils::openFileForRead(state, appPath);
        LuaUtils::readFullFile(state);        
        lua_settable(state, -3);
        lua_rawseti(state, -2, lastSetFile++);
        LuaUtils::closeDefaultFile(state);
      }
      //FIXME: This is being added for the case when a ASCT request files that do not
      //necessarily exist in a LRM. This happens, for example, In BSP executions where
      //just one node outputs a given file. If possible, it will be better to fix the
      //design thus avoiding these situations. Alternatively, we should at least warn
      //the requesting ASCT via a CORBA exception, ant let the calling asct decide if
      //it is an error condition.
      catch(const std::exception & e){
        cerr << "LrmSkeleton::outputFilesWrapper -->>\"Silent Fail\" "
             << " -- Someone asked for an inexistent file: "
             << appInfo.outputFiles()[i]
             << endl;
        lua_pop(state, 2);//Popping Table & 'file' string
      }

    }
    
    LrmSkeleton::singleInstance_->lrmImpl_->removeAppInfo(executionId);
        
    return 1;
  }

  //---------------------------------------------------------------------
  int LrmSkeleton::killProcessWrapper(struct lua_State * state){
    cout << "LrmSkeleton::killProcess" << endl;
    string executionId(lua_tostring(state,-1));
    lua_pop(state, 1);//Pops executionId
    LrmSkeleton::singleInstance_->lrmImpl_->kill(executionId);
    return 0;
  }

  //---------------------------------------------------------------------
  int LrmSkeleton::getLastCheckpointNumberWrapper(struct lua_State * state){
    //cout << "LrmSkeleton::getLastCkpNumber" << endl;
    string executionId(lua_tostring(state,-1));
    lua_pop(state, 1);//Pops executionId
    int ckpNumber = LrmSkeleton::singleInstance_->lrmImpl_->getLastCkpNumber(executionId);
    cout << "LrmSkeleton::getLastCheckpointNumber-->checkpointNumber: " << ckpNumber << endl;
    lua_pushnumber(state, ckpNumber);    
    return 1;
  }

  //---------------------------------------------------------------------
  int LrmSkeleton::restartExecutionWrapper(struct lua_State * state){
    cout << "LrmSkeleton::restartExecution" << endl;
    string *appArgs = new string(lua_tostring(state,-1));
    lua_pop(state, 1);// pops appArgs
    string *executionId = new string(lua_tostring(state,-1));
    lua_pop(state, 1);// pops executionId    
    int restartFlag = LrmSkeleton::singleInstance_->lrmImpl_->restartExecution(executionId, appArgs);
    lua_pushnumber(state, restartFlag);
    return 1;
  }

