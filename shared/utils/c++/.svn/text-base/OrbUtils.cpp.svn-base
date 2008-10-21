#include "OrbUtils.hpp"
#include "LuaUtils.hpp"

extern "C" {
#include <lualib.h>
#include <lauxlib.h>
#include <oilall.h>
#include <luasocket.h>
}

#include "NetInfo.hpp"

#include<iostream>
#include<cassert>

using std::cerr;
using std::endl;

//-----------------------------------------------------------------------
  void OrbUtils::initLuaState(lua_State * state){

    char integrade_env_var_name[8] = "IG_HOME";
    char *integrade_path;
    char luacompat_filename[255];

    // Getting INTEGRADE path
    integrade_path = getenv(integrade_env_var_name);
    if (integrade_path == NULL){
      printf("Please define the variable IG_HOME\n");
      exit(1);
    }
    // Setting luacompat Path + filename
    strcpy(luacompat_filename, integrade_path);
    strcat(luacompat_filename, "/shared/utils/c++/compat-5.1.lua");

    //Opening Libraries
    luaopen_base(state);
    luaopen_table(state);
    luaopen_io(state);
    luaopen_string(state);
    luaopen_math(state);
    luaopen_debug(state);


    // Loading luacompat
    lua_dofile(state,luacompat_filename);

    luaopen_luasocket(state);
  }

  //-----------------------------------------------------------------------

void OrbUtils::loadOrb(lua_State * state, string orbPath){
    int initialStackSize = lua_gettop(state);
    //    lua_pushstring(state, orbPath.c_str());
    //    cout << orbPath << endl;
    //    lua_setglobal(state, "LUA_PATH");
    luapreload_oilall(state);
    lua_pushliteral(state,"require");
    lua_rawget(state, LUA_GLOBALSINDEX);
    assert(lua_isfunction(state, -1));
    lua_pushliteral(state, "oil");
    if(lua_pcall(state, 1, 0, 0) != 0){
      cerr << "[ERROR] OrbUtils::loadOrb->Lua error: "
           << lua_tostring(state, -1) << endl;
      lua_pop(state, 1);
    }
    assert(initialStackSize == lua_gettop(state));

  }

  //-----------------------------------------------------------------------

  void OrbUtils::loadIdl(lua_State * state, string idlPath){
    int initialStackSize = lua_gettop(state);

    LuaUtils::getField(state, "oil.loadidlfile");
    lua_pushstring(state, idlPath.c_str());
    if(lua_pcall(state, 1, 0, 0) != 0){
      cerr << "[ERROR] OrbUtils::loadIdl->Lua error: "
           << lua_tostring(state, -1) << endl;
      lua_pop(state, 1);
    }

    assert(initialStackSize == lua_gettop(state));

  }

  //-----------------------------------------------------------------------

  //FIXME QUESTION: Better to return a string containing the proxy name???
  void OrbUtils::instantiateProxy(lua_State * state,
                                 string ior, string interfaceName, string proxyName){
    int initialStackSize = lua_gettop(state);

    LuaUtils::getField(state,"oil.createproxy"); //IOR on stack
    lua_pushstring(state, ior.c_str());
    lua_pushstring(state, interfaceName.c_str());

    if(lua_pcall(state, 2, 1, 0) != 0){
       lua_getglobal(state, "tostring");
       lua_insert(state, -2);
       lua_pcall(state, 1, 1, 0);
      cerr << "[ERROR] OrbUtils::instantiateProxy->Lua error: "
           << lua_tostring(state, -1) << endl;
      lua_pop(state, 1);
    }

    else {
      lua_setglobal(state, proxyName.c_str());
    }
   assert(initialStackSize == lua_gettop(state));

  }

 //-----------------------------------------------------------------------
 string OrbUtils::getIor(lua_State * state, const char * servantName){

   int initialStackSize = lua_gettop(state);

   lua_getglobal(state, servantName);
   lua_pushstring(state, "_ior");
   lua_gettable(state, -2);
   lua_getglobal(state,servantName);

   if(lua_pcall(state, 1, 1, 0) != 0){
      cerr << "[ERROR] OrbUtils::getIor->Lua error: "
           << lua_tostring(state, -1) << endl;
      lua_pop(state, 1);
   }

   string ior = string(lua_tostring(state, -1));

   lua_pop(state, 2);//servant, ior
   assert(initialStackSize == lua_gettop(state));
   return ior;

 }

 //-----------------------------------------------------------------------
 void OrbUtils::setVerboseMode(lua_State * state) {

    LuaUtils::getField(state, "oil.verbose.level");
    lua_pushnumber(state, 4);
    if(lua_pcall(state, 1, 0, 0) != 0){
      cerr << "[ERROR] OrbUtils::loadIdl->Lua error: "
           << lua_tostring(state, -1) << endl;
      lua_pop(state, 1);
    }
 } 
 //-----------------------------------------------------------------------
