extern "C" {
#include <lua.h>
#include <lualib.h>
#include <lauxlib.h>
}

#include<cassert>

#include "utils/c++/LuaUtils.hpp"
#include "utils/c++/OrbUtils.hpp"


#include "ExecutionManagerStub.hpp"
#include "BspInfo.hpp"

  //----------------------------------------------------------------------
  ExecutionManagerStub::ExecutionManagerStub(const string & orbPath,
               const string & resourceManagementIdlPath,
               const string & executionManagerIor){

    state = lua_open();
    OrbUtils::initLuaState(state);
    OrbUtils::loadOrb(state, orbPath);
    OrbUtils::loadIdl(state, resourceManagementIdlPath);
    OrbUtils::instantiateProxy (state, executionManagerIor,
                                   "IDL:clusterManagement/ExecutionManager:1.0",
                                   "executionManagerProxy");

  }

  //--------------------------------------------------------------------------------
  BspInfo *ExecutionManagerStub::registerBspNode(const string & appId, const string & nodeId, 
                                                 const string & bspProxyIor){

    int stackTop = lua_gettop(state);
    lua_getglobal(state, "executionManagerProxy");
    lua_pushstring(state, "registerBspProcess");
    lua_gettable(state, -2);
    lua_getglobal(state, "executionManagerProxy");
    lua_newtable(state);
    LuaUtils::setFieldOnTable(state, "requestId", appId.c_str(), -1);
    LuaUtils::setFieldOnTable(state, "processId", nodeId.c_str(), -1);
    lua_pushstring(state, bspProxyIor.c_str());
    if (lua_pcall(state, 3, 1, 0) != 0){
        lua_getglobal(state, "tostring");
        lua_insert(state, -2);
        lua_pcall(state, 1, 1, 0);
        cerr << "[ERROR] ExecutionManagerStub::registerBspNode -> OiL error: " << endl
             << lua_tostring(state, -1) << " error " << endl;
        lua_pop(state, 1);
    }
    BspInfo *bspInfo = new BspInfo;
    lua_pushstring(state, "isProcessZero");
    lua_gettable(state, -2);
    if(lua_toboolean(state, -1) == 1)
      bspInfo->isProcessZero(true);
    else
      bspInfo->isProcessZero(false);
    lua_pop(state, 1);
    bspInfo->processZeroIor(LuaUtils::getStringFromTable(state, "processZeroIor"));
    lua_pop(state, 2);
    assert(stackTop == lua_gettop(state));
    return bspInfo;
  }
