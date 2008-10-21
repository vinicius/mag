extern "C" {
#include <pthread.h>
#include <lualib.h>
#include <lauxlib.h>
#include <lua.h>
}

#include "ArscSkeleton.hpp"
#include "ArscImpl.hpp"

#include "utils/c++/Config.hpp"
#include "utils/c++/OrbUtils.hpp"
#include "utils/c++/LuaUtils.hpp"
#include "utils/c++/StringUtils.hpp"

using std::cerr;
using std::endl;



  ArscSkeleton * ArscSkeleton::singleInstance_ = NULL;

  //-----------------------------------------------------------------------
  ArscSkeleton & ArscSkeleton::init(ArscImpl * lsmImpl_, const Config & config){
    if(ArscSkeleton::singleInstance_ == NULL){
      ArscSkeleton::singleInstance_ = new ArscSkeleton(lsmImpl_, config);
    }
    return *ArscSkeleton::singleInstance_;
  }

  //-----------------------------------------------------------------------
  void * ArscSkeleton::serverSideSetup(void * ptr){
    lua_dostring(ArscSkeleton::singleInstance_->serverSideState,
                                 " while 1 and not SHOULD_QUIT do"
                                 " lo_handleRequest()"
                                 "print ui"
                                 " end"
                );
    return NULL;
  }

  //---------------------------------------------------------------------
  ArscSkeleton::ArscSkeleton(ArscImpl * lsmImpl,
                             const Config & config):
                                                   arscImpl_(lsmImpl){
    serverSideState = lua_open();
    OrbUtils::initLuaState(serverSideState);
    OrbUtils::loadOrb(serverSideState, config.getConf("orbPath"));
    OrbUtils::loadIdl(serverSideState, config.getConf("securityIdlPath"));
    //servant implementation
    lua_dostring(serverSideState, " lsmImpl = {"
                        "initContext = function(self,name,pass)"
                        " initContextWrapper(name,pass)"
                        "end,"
                        
                        " setSampleInterval = function (self, seconds)"
                        "   setSampleIntervalWrapper(seconds)"
                        " end,"
                        " setKeepAliveInterval = function (self, seconds)"
                        "   setKeepAliveIntervalWrapper(seconds)"
                        " end,"
                        " remoteExecutionRequest = function (self, executionSpecs)"
                        "   remoteExecutionRequestWrapper(executionSpecs)"
                        " end,"
                        " requestOutputFiles = function(self, appId)"
                        "   return requestOutputFilesWrapper(appId)"
                        " end,"
                        " kill  = function(self, appId)"
                        "   killWrapper(appId)"
                        " end,"
                        " ping = function(self)"
                        " print('Getting pinged...')"
                        " end,"
                        " }"
                );

    lua_dostring(serverSideState,
                 "lsmServant = lo_createservant(lsmImpl,'interfaces::Lsm')");
    arscIor_ = OrbUtils::getIor(serverSideState, "lsmServant");
    //cout << "ArscSkeleton::serverSideSetup::LSM IOR: " << arscIor_ << endl;
  }

