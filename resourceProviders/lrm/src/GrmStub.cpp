#include <cassert>

#include "NodeStaticInformation.hpp"
#include "DynamicInformationMonitor.hpp"
#include "ExecutionSpecs.hpp"

extern "C" {
#include <lua.h>
#include <lualib.h>
#include <lauxlib.h>
}


#include "utils/c++/LuaUtils.hpp"
#include "utils/c++/OrbUtils.hpp"

#include "GrmStub.hpp"

#include <iostream>


  //----------------------------------------------------------------------
  GrmStub::GrmStub(lua_State * aState, const string & aGrmIor)
     : state(aState), grmIor(aGrmIor) {

    OrbUtils::instantiateProxy (state, aGrmIor, "IDL:clusterManagement/Grm:1.0", "grmProxy");
  }


  //----------------------------------------------------------------------
  void GrmStub::registerLrm(const string & lrmIor, const NodeStaticInformation  & lsi){

    int stackTop = lua_gettop(state);
    lua_getglobal(state, "grmProxy");
    lua_pushstring(state, "registerLrm");
    lua_gettable(state, -2);
    lua_getglobal(state, "grmProxy");
    lua_pushstring(state, lrmIor.c_str());
    //Creates a table with static info
    lua_newtable(state);
    LuaUtils::setFieldOnTable(state,"hostName", lsi.getHostName(), -1);
    LuaUtils::setFieldOnTable(state, "osName", lsi.getOsName(), -1);
    LuaUtils::setFieldOnTable(state, "osVersion", lsi.getOsVersion(), -1);
    LuaUtils::setFieldOnTable(state, "processorName", lsi.getProcessorName(), -1);
    LuaUtils::setFieldOnTable(state, "processorMhz", lsi.getProcessorMhz(), -1);
    LuaUtils::setFieldOnTable(state, "totalRam", lsi.getTotalRAM(), -1);
    LuaUtils::setFieldOnTable(state, "totalSwap", lsi.getTotalSwap(), -1);
    if (lua_pcall(state, 3, 0, 0) != 0){
      lua_getglobal(state, "tostring");
      lua_insert(state, -2);
      lua_pcall(state, 1, 1, 0);
      cerr << "[ERROR] GrmStub::registerLrm -> OiL error: " << endl
           << lua_tostring(state, -1) << " error " << endl;
      lua_pop(state, 1);
    }

    lua_pop(state,1);//Removes grmProxy from stack
    assert(stackTop == lua_gettop(state));

  }

  //----------------------------------------------------------------------
  void GrmStub::updateLrmInformation(const string & lrmIor, const DynamicInformationMonitor & dynamicInformationMonitor){

    int stackTop = lua_gettop(state);
    lua_getglobal(state, "grmProxy");
    lua_pushstring(state, "updateLrmInformation");
    lua_gettable(state, -2);
    lua_getglobal(state, "grmProxy");
    lua_pushstring(state, lrmIor.c_str());
    //Creates a table with dynamic info
    lua_newtable(state);
    LuaUtils::setFieldOnTable(state, "freeRam", dynamicInformationMonitor.getFreeRAM(), -1);
    LuaUtils::setFieldOnTable(state, "freeSwap", dynamicInformationMonitor.getFreeSwap(), -1);
    LuaUtils::setFieldOnTable(state, "freeDiskSpace", dynamicInformationMonitor.getFsFree(), -1);
    LuaUtils::setFieldOnTable(state, "cpuUsage", dynamicInformationMonitor.getCpuUsage(), -1);

    if (lua_pcall(state, 3, 0, 0) != 0){
      lua_getglobal(state, "tostring");
      lua_insert(state, -2);
      lua_pcall(state, 1, 1, 0);
      cerr << "[ERROR] GrmStub::updateLrmInformation -> OiL error: " << endl
           << lua_tostring(state, -1) << " error " << endl;
      lua_pop(state, 1);
    }

    lua_pop(state,1);//Removes grmProxy from stack
    assert(stackTop == lua_gettop(state));

  }
