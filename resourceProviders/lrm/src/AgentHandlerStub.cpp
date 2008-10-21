#include <cassert>
extern "C" {
#include <lua.h>
#include <lualib.h>
#include <lauxlib.h>
}

#include "utils/c++/LuaUtils.hpp"
#include "utils/c++/OrbUtils.hpp"
#include "utils/c++/StringUtils.hpp"

#include "AgentHandlerStub.hpp"

#include <iostream>
using std::cerr;
using std::endl; 

  //----------------------------------------------------------------------
  AgentHandlerStub::AgentHandlerStub(lua_State * aState, const string & aAhIor):state(aState){
   
     OrbUtils::instantiateProxy(state, aAhIor, "IDL:clusterManagement/AgentHandler:1.0", "ahProxy");
  }


  //----------------------------------------------------------------------
  void AgentHandlerStub::remoteExecutionRequest(ExecutionSpecs & execSpecs) {

    // Vinicius {
    cout << "AgentHandlerStub::remoteExecutionRequest" << endl;
    // } Vinicius
    int stackTop = lua_gettop(state);
    lua_getglobal(state, "ahProxy");
    lua_pushstring(state, "requestExecution");
    lua_gettable(state, -2);

    lua_getglobal(state, "ahProxy");
    
    lua_newtable(state);//our ApplicationExecutionInformation
   
    LuaUtils::setFieldOnTable(state, "requestingAsctIor", execSpecs.requestingAsctIor().c_str(), -1);
    LuaUtils::setFieldOnTable(state, "originalGrmIor",execSpecs.grmIor().c_str(), -1);
    LuaUtils::setFieldOnTable(state, "previousGrmIor",execSpecs.previousGrmIor().c_str(), -1);
    LuaUtils::setFieldOnTable(state, "applicationType", "0", -1);//regular application
    LuaUtils::setFieldOnTable(state, "applicationRepositoryIor", execSpecs.applicationRepositoryIor().c_str(), -1);
    LuaUtils::setFieldOnTable(state, "applicationName", execSpecs.applicationName().c_str(), -1);
    LuaUtils::setFieldOnTable(state, "basePath", execSpecs.basePath().c_str(), -1);


    LuaUtils::setFieldOnTable(state, "applicationConstraints", execSpecs.appConstraints().c_str(), -1);
    LuaUtils::setFieldOnTable(state, "applicationPreferences", execSpecs.appPreferences().c_str(), -1);
    
    lua_pushstring(state, "availableBinaries");
    // new array
    lua_newtable (state);
    // array size
    lua_pushliteral (state, "n");
    lua_pushnumber (state, 0);
    lua_rawset (state, -3);
    lua_settable(state, -3);

    LuaUtils::setFieldOnTable(state, "forceDifferentNodes", "false", -1);
  
    LuaUtils::setFieldOnTable(state, "numberOfReplicas",execSpecs.numberOfReplicas(),-1);//regular application  
    //lua_settable(state, -1);

    lua_newtable(state);//our ProcessExecutionInformation
    lua_pushstring(state, "executionRequestId");
    lua_newtable(state);//our ExecutionRequestId
    LuaUtils::setFieldOnTable(state, "requestId", execSpecs.applicationId().c_str(), -1);
    LuaUtils::setFieldOnTable(state, "processId", execSpecs.processId().c_str(), -1);
    LuaUtils::setFieldOnTable(state, "replicaId", execSpecs.processId().c_str(), -1);    
    lua_settable(state, -3);

    LuaUtils::setFieldOnTable(state, "processArguments", execSpecs.appArgs().c_str(), -1);

    vector<string> outputFiles = execSpecs.outputFiles();
    unsigned int fileCount = outputFiles.size();

    lua_pushstring(state, "outputFileNames");
    // new array
    lua_newtable (state);
    for (unsigned int i = 1; i <= fileCount; i++) {
      // index
      lua_pushnumber (state, i);

      //value
      lua_pushstring (state, outputFiles[i - 1].c_str());
      lua_rawset (state, -3);
    }

    // array size
    lua_pushliteral (state, "n");
    lua_pushnumber (state, fileCount);
    lua_rawset (state, -3);
    lua_settable(state, -3);



    if (lua_pcall(state, 3, 0, 0) != 0){
       lua_getglobal(state, "tostring"); // new!
       lua_insert(state, -2); // new!
       lua_pcall(state, 1, 1, 0); // new!
      cerr << "[ERROR] AgentHandlerStub::executeApp->Lua error: "
            << lua_tostring(state, -1) << endl;
       lua_pop(state, 1);
    }

    lua_pop(state,1);//Removes ahProxy from stack
    assert(stackTop == lua_gettop(state));

  }
