extern "C" {
#include <lua.h>
#include <lualib.h>
#include <lauxlib.h>
}

#include <cassert>
#include <iostream>
using namespace std;

#include "utils/c++/LuaUtils.hpp"
#include "utils/c++/OrbUtils.hpp"

#include "ApplicationRepositoryStub.hpp"


  //----------------------------------------------------------------------
  ApplicationRepositoryStub::ApplicationRepositoryStub(lua_State * aState,
                                                       const string & aAppReposIor):state(aState){
    OrbUtils::instantiateProxy (state, aAppReposIor,
                               "IDL:clusterManagement/ApplicationRepository:1.0",
                               "appReposProxy");
  }

  //----------------------------------------------------------------------
  string ApplicationRepositoryStub::registerApplication(const string & appPath){

     int stackTop = lua_gettop(state);
     lua_getglobal(state, "appReposProxy");
     lua_pushstring(state, "registerApplication");
     lua_gettable(state, -2);
     lua_getglobal(state, "appReposProxy");
     LuaUtils::openFileForRead(state, appPath);
     LuaUtils::readFullFile(state);
     lua_call(state, 2, 1);//in:proxy & file out: appId
     string appId(lua_tostring(state, -1));
     lua_pop(state, 2);//appId & appReposProxy
     LuaUtils::closeDefaultFile(state);
     assert(stackTop == lua_gettop(state));
     return appId;
  }

  //----------------------------------------------------------------------
  string ApplicationRepositoryStub::getApplicationBinary(
        const string & basePath, const string & applicationName, const string & binaryName){

     int stackTop = lua_gettop(state);
     lua_getglobal(state, "appReposProxy");//appReposProxy on stack
     lua_pushstring(state, "getApplicationBinary");  // modified by Vidal 02/2005
     lua_gettable(state, -2);
     lua_getglobal(state, "appReposProxy");
     lua_pushlstring(state, basePath.c_str(), basePath.length());
     lua_pushlstring(state, applicationName.c_str(), applicationName.length());
     lua_pushlstring(state, binaryName.c_str(), binaryName.length());

     if (lua_pcall(state, 4, 1, 0) != 0){
       cerr << "--> " << applicationName << " <--" << endl;
          cerr << "[ERROR] ApplicationRepositoryStub::getApplicationBinary -> Lua error: "
          << lua_tostring(state, -1) << endl;
         lua_pop(state, 1);
     }
     int appIndex = lua_gettop(state);
     std::string value( lua_tostring(state,-1),lua_strlen(state,-1));
     
     lua_pop(state, 2);//removes appReposProxy,app
     
     assert(stackTop == lua_gettop(state));
     
     return value;
  }

  //----------------------------------------------------------------------
//  string ApplicationRepositoryStub::downloadRemoteApplication(const string & appId, const string & appReposId){
  string ApplicationRepositoryStub::getRemoteApplicationBinary(
        const string & basePath, const string & applicationName, const string & binaryName,
        const string & applicationRepositoryIor){

     int stackTop = lua_gettop(state);
     lua_getglobal(state, "appReposProxy");//appReposProxy on stack
     lua_pushstring(state, "getRemoteApplicationBinary");  // modified by Vidal 02/2005
     lua_gettable(state, -2);
     lua_getglobal(state, "appReposProxy");
     lua_pushlstring(state, basePath.c_str(), basePath.length());
     lua_pushlstring(state, applicationName.c_str(), applicationName.length());
     lua_pushlstring(state, binaryName.c_str(), binaryName.length());
     lua_pushlstring(state, applicationRepositoryIor.c_str(), applicationRepositoryIor.length());

     if (lua_pcall(state, 5, 1, 0) != 0){
       cerr << "--> " << applicationName << " <--" << endl;
          cerr << "[ERROR] ApplicationRepositoryStub::getRemoteApplicationBinary -> Lua error: "
          << lua_tostring(state, -1) << endl;
         lua_pop(state, 1);
     }
     int appIndex = lua_gettop(state);
     std::string value( lua_tostring(state,-1),lua_strlen(state,-1));
     lua_pop(state, 2);//removes appReposProxy,app
     assert(stackTop == lua_gettop(state));
     return value;
  }
