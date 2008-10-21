#ifndef LRMIMPL_HPP
#define LRMIMPL_HPP

#include <cassert>
#include <string>
#include <fstream>
#include <map>
#include <vector>
#include <sys/time.h>
#include <sys/types.h>


#include "AsctStub.hpp"
#include "ExecutionManagerStub.hpp"
#include "ApplicationRepositoryStub.hpp"
#include "utils/c++/NameServiceStub.hpp"

#ifdef SECURITY
#include "SecureApplicationRepositoryStub.hpp"
#endif


#include "../../lupa/src/Lupa.hpp"

#include "utils/c++/GuardedVariable.hpp"
#include "AppInfoHolder.hpp"

using std::string;
using std::map;

class GrmStub;
class NodeStaticInformation;
class DynamicInformationMonitor;
class ExecutionSpecs;
class RequestAcceptanceInformation;
class Config;

#include <iostream> //FIXME: Remove

using std::cerr;
using std::endl;

/**

  LrmImpl - Implementation of the LRM IDL interface.

  LrmImpl implements LRM interface. It also hosts methods for setting
  up the server side of the LRM service (tasks such as ORB instantiation,
  proxy creation, etc). Finally, LRM periodically uploads resource
  information of a given node to the GRM.

  LRM is a sigleton, because we are using threads and the only way to
  launch threads are via static methods. We could use other mechanisms
  to do that, but since we don't need more than one LRM object, this
  solution is ok for now.

  @author Andrei Goldchleger

  @todo This class was heavily refectored, but maybe further refctoring is
  possible. Some possibilities include extracting part of the functionality
  to other classes.

*/

class LrmImpl{

 private:
 

 bool secureAppRepos;          /**< Indicates if AppRepos is secure*/

  Lupa lupa;                        /**< LUPA - Local Usage Pattern Analyser */
  int lupaEnabled;                  /**< LUPA's enabling flag */

  string grmIor;                    /**< The IOR of the GRM we will contact */
  string serverNameLocation;        /**< The Ior of the Server Name we will contact */  
  string appReposIor;               /**< The IOR of the Application Repository we will contact */
  string execManagerIor;            /**< The IOR of the Execution Manager */
  bool useExecManagerFlag;          /**< Indicates if the Execution Manager should be contacted */
  
  GuardedVariable<long> sampleInterval;    /**< Amount of time, in seconds, between queries about
                                                resource availability
                                           */

  GuardedVariable<long> keepAliveInterval; /**< Maximun interval, in seconds, before LRM sends an
                                                update to the GRM, even without significat changes
                                                in resource availability
                                           */
  float threshold;                  /**< A percentage that indicates how much resource
                                         availability must change in order to be considered
                                         significant
                                    */


  string orbPath;                   /**< Path to the O2 files. MUST end with /?. Example:
                                         /usr/local/o2/? */
                                         
  string CosNamingIdlPath;           /**< Path to the CosNaming IDL*/
  string appReposIdlPath;           /**< Path to the Application Repository IDL*/
  string resourceManagementIdlPath; /**< Path to the Resource Management IDL. */


  GuardedVariable<int> appId;       /**< Holds last used local application Id. Used
                                            to rename applications downloaded from the
                                            Application Repository, making sure that
                                            there is no naming conflicts between multiple
                                            applications that might be running
                                    */
  string ahIorFile;
  string ahIor;
  string ahIdlFile;

  struct lua_State * grmState; /**< Lua state representing LrmImpl's client side*/

  /** Used to determine how a execution was finished */
  enum ExitFlagsEnum {none_, restart_, kill_};
  map<string, enum ExitFlagsEnum> appExitFlags;

  /** Contains a list of active and finished processes
   *  Before using a map, the respective mutex must be locked */
  map<string, AppInfoHolder> launchedAppsInfo;
  map<string, AppInfoHolder> finishedAppsInfo;
  pthread_mutex_t launchedAppsInfoLock;
  pthread_mutex_t finishedAppsInfoLock;

  /** The following are used by the restart methods to synchronize the
   *  restarting and communicate between the restarting threads */
  pthread_mutex_t restartLock;
  pthread_cond_t  restartCondition;
  enum RestartFlagsEnum {restarted_, notRunning_};
  map<string, enum RestartFlagsEnum> appRestartFlags;

  //
  ofstream execTimeFile;
  string localDir; // A directory in the local filesystem for saving lrmData
  double lastCsTime, lastCuTime;
  pthread_mutex_t execTimeLock;
  
  /** Contains the binary identifier to use with the Application Repository */
  string binaryName;

  /** Stubs to access other InteGrade modules
   *  Before using a stub, the respective mutex must be locked */
  GrmStub *grmStub;
  //ExecutionManagerStub *execManagerStub;
  int nExecManagerStubs;
  ExecutionManagerStub **execManagerStubs;
  ApplicationRepositoryStub *appReposStub;
  NameServiceStub *nameServiceStub;
  map<string, AsctStub *> asctStubs;  
  pthread_mutex_t grmStubLock;
  //pthread_mutex_t execman_stub_mutex;  
  pthread_mutex_t *executionManagerStubLockList;  
  pthread_mutex_t applicationRepositoryStubLock;      
  pthread_mutex_t asctStubLock;
  
  double startTime; // TODO: RYC_EXP - REMOVE THIS AFTER THE TESTS!!!!  
    
  /**
    Copies a file from <i>srcPath</i> to <i>dstPath</i>

    @param srcPath - source path of the file
    @param dstPath - destination path
  */
  void copyFile(const string & srcPath, const string & dstPath);
 
  LrmImpl(const Config & config);
  /** Configure proxies to other InteGrade modules */
  void configureProxies();
  /** Configure the LRM server */
  void configureServer(const Config & config);

  /** LrmImpl is configured as a singleton */
  static LrmImpl * singleInstance_;

  /** Restarts an application execution */
  static void * restartExecWrapper(void * ptr);
  void restartExecThread(const string & appId, const string & appArgs);
  
  /** Executes an application */
  static void *startRemoteExecutionWrapper(void * ptr);  
  void startRemoteExecution(ExecutionSpecs execSpecs);
     
  /** Notifies the ExecutionManager e ASCT that an execution has finished */
  static void * notifyStatusWrapper(void * ptr);
  void notifyStatus(string appId, pid_t termChildId, int termStatus);

  //TODO: COMMENT ME
  void removePreviousExecutionDirectories();

 public:

   //TODO: COMMENT ME
   static LrmImpl & init(const Config & config);

  /**
   Periodically upload system information to a remote GRM.
   This method does not return.
  */
  void updateLoop();

  /**
    Allows a remote caller to modify the sample interval.

    The sample interval defines how many time the system 'sleeps' between
    two queries about resource utilization made to the OS.

    @param seconds - the new sample interval (in seconds).
  */
  void setSampleInterval(long seconds){ sampleInterval.set(seconds); }

  /**
    Allows a remote caller to modify the keep-alive interval.

    The keep-alive interval defines the maximum time that the
    LRM can spend without uploading information to the GRM. Even if
    the system is almost idle and there are no significant changes in
    resource availability, the LRM uploads resource availability information
    to the GRM at least every X seconds. This acts as a keep-alive which
    teels the GRM that a given LRM is still working.

    @param seconds - the new keep-alive interval (in seconds).

  */
  void setKeepAliveInterval(long seconds) { keepAliveInterval.set(seconds); }


   /**
     Called by a GRM asking if we can host a determined application.

     @param execSpecs - Data associated with the request(Requirements, preferences...)
   */
   long remoteExecutionRequest(ExecutionSpecs * execSpecs);

  //TODO: COMMENT ME
  AppInfoHolder appInfo(const string & appId){
    pthread_mutex_lock(&finishedAppsInfoLock);
      AppInfoHolder appInfo = finishedAppsInfo[appId];
    pthread_mutex_unlock(&finishedAppsInfoLock);
     return appInfo;
  }

  //TODO: COMMENT ME
  void removeAppInfo(const string & appId){
    pthread_mutex_lock(&finishedAppsInfoLock);
      if(! (finishedAppsInfo.erase(appId) == 1))
        assert(false);
    pthread_mutex_unlock(&finishedAppsInfoLock);
  }

  //TODO: COMMENT ME
  void kill(const string & appId);

  //TODO: COMMENT ME
  int getLastCkpNumber(const string & appId);
  
  /**
     Called by an ExecutionManager to restart an application execution.

     @param appId - The id of the application to be restarted
     @param appArgs - The arguments used when restarting the application
     @return - 0 if restarting was succesfull
              -1 if the application 'appId' was not running
  */
  int restartExecution(const string * appId, const string * appArgs);
  
#ifdef SECURITY  
   /** if LRM is secureAppRepos
   */
	bool isSecure();
#endif 
	
};
#endif//LRMIMPL_HPP

