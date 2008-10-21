#include <cassert>

extern "C" {
#include <lua.h>
#include <lualib.h>
#include <lauxlib.h>
}

#include "utils/c++/LuaUtils.hpp"
#include "utils/c++/OrbUtils.hpp"
#include "utils/c++/StringUtils.hpp"

#include "AsctStub.hpp"

#include <iostream>
using std::cerr;
using std::endl; 


  //----------------------------------------------------------------------
  AsctStub::AsctStub(lua_State * aState,
                     const string & aAsctIor):state(aState) {
                              
    OrbUtils::instantiateProxy (state, aAsctIor, "IDL:tools/Asct:1.0", "asctProxy");
    asctIor_ = aAsctIor;
  }


  //--------------------------------------------------------------------------------
  void AsctStub::setExecutionAccepted(const string & lrmIor,
                                      const string & executionId,
                                      const string & applicationId,
                                      const string & processId) {
				      //const string & replicaId){ // Vinicius

    int stackTop = lua_gettop(state);
    lua_getglobal(state, "asctProxy");
    lua_pushstring(state, "setExecutionAccepted");
    lua_gettable(state, -2);
    lua_getglobal(state, "asctProxy");
    lua_newtable(state);//our RequestAcceptanceInformation
    LuaUtils::setFieldOnTable(state, "lrmIor", lrmIor.c_str(), -1);
    LuaUtils::setFieldOnTable(state, "executionId", executionId.c_str(), -1);
    lua_pushstring(state, "executionRequestId");
    lua_newtable(state);//our AsctRequestId
    LuaUtils::setFieldOnTable(state, "requestId", applicationId.c_str(), -1);
    LuaUtils::setFieldOnTable(state, "processId", processId.c_str(), -1);
    //LuaUtils::setFieldOnTable(state, "replicaId", processId.c_str(), -1); // Vinicius
    lua_settable(state, -3);
    if (lua_pcall(state, 2, 0, 0) != 0){
       lua_getglobal(state, "tostring"); // new!
       lua_insert(state, -2); // new!
       lua_pcall(state, 1, 1, 0); // new!
      cerr << "[ERROR] AsctStub::acceptedExecutionRequest->Lua error: "
            << lua_tostring(state, -1) << endl;
       lua_pop(state, 1);
    }

    lua_pop(state, 1);//remove asctProxy
    assert(stackTop == lua_gettop(state));
  }

  //--------------------------------------------------------------------------------
  void AsctStub::getInputFiles(const string & applicationId,
                               const string & processId,
			       //const string & replicaId, // Vinicius
                               const string & destinationPath){

     cout << applicationId << " " <<  processId << " " << destinationPath << endl; 


    int stackTop = lua_gettop(state);
    lua_getglobal(state, "asctProxy");
    lua_pushstring(state, "getInputFiles");
    lua_gettable(state, -2);
    lua_getglobal(state, "asctProxy");
    lua_newtable(state);
    LuaUtils::setFieldOnTable(state, "requestId", applicationId.c_str(), -1);
    LuaUtils::setFieldOnTable(state, "processId", processId.c_str(), -1);
    //LuaUtils::setFieldOnTable(state, "replicaId", processId.c_str(), -1); // Vinicius
    if (lua_pcall(state, 2, 1, 0) != 0){
        cerr << "[ERROR] AsctStub::getInputFiles->Lua error: "
             << lua_tostring(state, -1) << endl;
        lua_pop(state, 1);
    }
    unsigned int fileCount = LuaUtils::getIntFromTable(state, "n");

    for(unsigned int i = 1; i <= fileCount; i++){
      lua_rawgeti(state, -1, i);
      string filename(LuaUtils::getStringFromTable(state, "fileName"));
      string filePath(destinationPath + "/" + filename);
      lua_pushstring(state, "file");
      lua_gettable(state, -2);
      LuaUtils::writeFile(state, filePath.c_str(), -1);
      lua_pop(state, 2); //removes FileStruct & File contents
    }

    lua_pop(state, 2); //removes proxy & filesequence
    assert(stackTop == lua_gettop(state));
  }


  //--------------------------------------------------------------------------------
  void AsctStub::setExecutionFinished(const string & applicationId,
                                      const string & processId) { 
				      //const string & replicaId) { // Vinicius
    cout << "AsctStub::setExecutionFinished" << endl;
    int stackTop = lua_gettop(state);
    lua_getglobal(state, "asctProxy");
    lua_pushstring(state, "setExecutionFinished");
    lua_gettable(state, -2);
    lua_getglobal(state, "asctProxy");
    lua_newtable(state);
    LuaUtils::setFieldOnTable(state, "requestId", applicationId.c_str(), -1);
    LuaUtils::setFieldOnTable(state, "processId", processId.c_str(), -1);
    // Vinicius {
    LuaUtils::setFieldOnTable(state, "replicaId", processId.c_str(), -1);
    // } Vinicius
    if (lua_pcall(state, 2, 0, 0) != 0){
      cerr << "[ERROR] AsctStub::appFinished->Lua error: "
           << lua_tostring(state, -1) << endl;
      lua_pop(state, 1);
    }

    lua_pop(state, 1);//removing asctProxy
    assert(stackTop == lua_gettop(state));
  }

