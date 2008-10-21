#include <cassert>

extern "C" {
#include <lua.h>
#include <lualib.h>
#include <lauxlib.h>
}

#include "utils/c++/LuaUtils.hpp"
#include "utils/c++/OrbUtils.hpp"
#include "utils/c++/StringUtils.hpp"

#include "ExecutionManagerStub.hpp"

#include <iostream>
using std::cerr;
using std::endl; 


  //----------------------------------------------------------------------
  ExecutionManagerStub::ExecutionManagerStub(lua_State * aState,
                     const string & execManagerIor):state(aState){
    //FIXME: This assumes that state ALREADY loaded the IDL for the GRM. Due to
    //O2's current design it is not possible to load the GRM IDL separately.
    OrbUtils::instantiateProxy (state, execManagerIor, "IDL:clusterManagement/ExecutionManager:1.0",
                               "execManagerProxy");
  }

  //--------------------------------------------------------------------------------
  void ExecutionManagerStub::setProcessExecutionStarted
  (const string & applicationId, const string & processId, const string & lrmIor, // Vinicius on replicaId
   const int & restartId, const string & executionId) {

    int stackTop = lua_gettop(state);
    lua_getglobal(state, "execManagerProxy");
    lua_pushstring(state, "setProcessExecutionStarted");
    lua_gettable(state, -2);
    lua_getglobal(state, "execManagerProxy");
    lua_pushstring(state, lrmIor.c_str());       
    lua_pushstring(state, executionId.c_str()); 
    lua_pushnumber(state, restartId);
    lua_newtable(state);
    LuaUtils::setFieldOnTable(state, "requestId", applicationId.c_str(), -1);
    LuaUtils::setFieldOnTable(state, "processId", processId.c_str(), -1);
    LuaUtils::setFieldOnTable(state, "replicaId", processId.c_str(), -1);
    if (lua_pcall(state, 5, 0, 0) != 0){
      cerr << "[ERROR] ExecutionManagerStub::reportExecutionStarted->Lua error: "
           << lua_tostring(state, -1) << endl;
      lua_pop(state, 1);
    }

    lua_pop(state, 1);//removing execManagerProxy
    assert(stackTop == lua_gettop(state));
  }

  //--------------------------------------------------------------------------------
  int ExecutionManagerStub::setProcessExecutionFinished
  (const string & applicationId, const string & processId, // Vinicius on replicaId 
   const int & restartId, int exitStatus) {

    int stackTop = lua_gettop(state);
    lua_getglobal(state, "execManagerProxy");
    lua_pushstring(state, "setProcessExecutionFinished");
    lua_gettable(state, -2);
    lua_getglobal(state, "execManagerProxy");
    lua_pushnumber(state, restartId);
    lua_newtable(state);
    LuaUtils::setFieldOnTable(state, "requestId", applicationId.c_str(), -1);
    LuaUtils::setFieldOnTable(state, "processId", processId.c_str(), -1);
    //LuaUtils::setFieldOnTable(state, "replicaId", processId.c_str(), -1); // Vinicius
    lua_pushnumber(state, exitStatus);
    if (lua_pcall(state, 4, 1, 0) != 0){
      cerr << "[ERROR] ExecutionManagerStub::reportExecutionFinished->Lua error: "
           << lua_tostring(state, -1) << endl;
      lua_pop(state, 1);
    }
  
    int flag = (int)lua_tonumber(state, -1);
    lua_pop(state, 2);//removing asctProxy + result
    assert(stackTop == lua_gettop(state));

    return flag;
  }
