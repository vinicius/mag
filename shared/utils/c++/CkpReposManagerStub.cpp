#include <cassert>

extern "C" {
#include <lua.h>
#include <lualib.h>
#include <lauxlib.h>
}

#include "LuaUtils.hpp"
#include "OrbUtils.hpp"
#include "StringUtils.hpp"
#include "NameServiceStub.hpp"
#include "NoSuchConfigException.hpp"

#include "CkpReposManagerStub.hpp"

#include <iostream>
#include <fstream>
using std::cerr;
using std::endl; 
using std::ifstream; 

  //----------------------------------------------------------------------
  CkpReposManagerStub::CkpReposManagerStub(const Config & config) {

    string serverNameLocation;
    string cosNamingIdlPath;   
    string orbPath;
    string ckpReposManagerIdl;
    try {
        serverNameLocation = config.getConf("serverNameRef");
        cosNamingIdlPath = config.getConf("cosNamingIdlPath");   
        orbPath = config.getConf("orbPath");
        ckpReposManagerIdl = config.getConf("ckpReposManagerIdlPath");       
    }
    catch (NoSuchConfigException e) {
        cerr << "[CRITICAL] CkpReposStub::CkpReposStub -> field from config file not found! Exiting..." << endl;
        cerr << e.what() << endl;
        exit(-1);
    }
    
    struct lua_State *ckpReposManagerState = lua_open();
    OrbUtils::initLuaState(ckpReposManagerState);
    OrbUtils::loadOrb(ckpReposManagerState, orbPath);
    OrbUtils::loadIdl(ckpReposManagerState, ckpReposManagerIdl);

    NameServiceStub nameServiceStub(lua_open(),serverNameLocation,cosNamingIdlPath);

    // Gets GRM ior 
    string ckpReposManagerIor = nameServiceStub.resolve("CkpReposManager");
    if ( ckpReposManagerIor.compare("") == 0 ) {
        cerr << "[CRITICAL] CkpReposStub::CkpReposStub -> Could not resolve CkpReposManager. Exiting..." << endl;
        exit(-1);
    }
    
    OrbUtils::instantiateProxy (ckpReposManagerState, ckpReposManagerIor, 
        "IDL:interfaces/CkpReposManager:1.0", "ckpReposManagerProxy");
  
    state = ckpReposManagerState;
    
    stubMutex = new pthread_mutex_t;
    pthread_mutex_init(stubMutex, NULL);

  }

  //--------------------------------------------------------------------------------
  int CkpReposManagerStub::registerCkpRepos (const string & ipAddress, const int & port, const int & availableSpace) {

    pthread_mutex_lock( stubMutex );
   
    int stackTop = lua_gettop(state);
    lua_getglobal(state, "ckpReposManagerProxy");
    lua_pushstring(state, "registerCkpRepos");
    lua_gettable(state, -2);
    lua_getglobal(state, "ckpReposManagerProxy");
    lua_pushstring(state, ipAddress.c_str());       
    lua_pushnumber(state, port);
    lua_pushnumber(state, availableSpace);
    if (lua_pcall(state, 4, 1, 0) != 0){
      cerr << "[ERROR] CkpReposStub::registerCkpRepos->Lua error: "
           << lua_tostring(state, -1) << endl;
      lua_pop(state, 1);
    }

    int ckpReposId = (int)lua_tonumber(state, -1);
    lua_pop(state, 2);//removing proxy + result
    assert(stackTop == lua_gettop(state));
    
    pthread_mutex_unlock( stubMutex );
        
    return ckpReposId;
  }

  //--------------------------------------------------------------------------------
  void CkpReposManagerStub::notifyCkpStored
    (const int & ckpReposId, const string & executionId, const int & ckpNumber, const int & fragmentNumber, const int & availableSpace) {

    pthread_mutex_lock( stubMutex );
    
    int stackTop = lua_gettop(state);
    lua_getglobal(state, "ckpReposManagerProxy");
    lua_pushstring(state, "notifyCkpStored");
    lua_gettable(state, -2);
    lua_getglobal(state, "ckpReposManagerProxy");
    lua_pushnumber(state, ckpReposId);    
    lua_pushstring(state, executionId.c_str());       
    lua_pushnumber(state, ckpNumber);    
    lua_pushnumber(state, fragmentNumber);    
    lua_pushnumber(state, availableSpace);
    if (lua_pcall(state, 6, 0, 0) != 0){
      cerr << "[ERROR] CkpReposStub::notifyCkpStored->Lua error: "
           << lua_tostring(state, -1) << endl;
      lua_pop(state, 1);
    }
  
    lua_pop(state, 1);//removing proxy
    assert(stackTop == lua_gettop(state));
    
    pthread_mutex_unlock( stubMutex );    
  }
  
  //--------------------------------------------------------------------------------  
  void CkpReposManagerStub::setCkpReposMode(
    const string & executionId, int mode, int nNodes, int nExtra) {

    pthread_mutex_lock( stubMutex );
        
    int stackTop = lua_gettop(state);
    lua_getglobal(state, "ckpReposManagerProxy");
    lua_pushstring(state, "setCkpReposMode");
    lua_gettable(state, -2);
    lua_getglobal(state, "ckpReposManagerProxy");
    lua_pushstring(state, executionId.c_str());
    lua_pushnumber(state, mode);
    lua_pushnumber(state, nNodes);
    lua_pushnumber(state, nExtra);

    if (lua_pcall(state, 5, 0, 0) != 0){
      cerr << "[ERROR] CkpReposManagerStub::setCkpReposMode->Lua error: "
           << lua_tostring(state, -1) << endl;
      lua_pop(state, 1);
    }
           
    lua_pop(state, 1);//removing proxy 
    assert(stackTop == lua_gettop(state));
    
    pthread_mutex_unlock( stubMutex );
  }

  //--------------------------------------------------------------------------------
  CkpReposAddress CkpReposManagerStub::getCkpRepos(
    const string & executionId, int ckpNumber, string checksumList[], int nChecksum, int ckpSize) {

    pthread_mutex_lock( stubMutex );
    
    string ipAddress;
    system("hostname -i > /tmp/machineIp.dat");
    ifstream ipFile("/tmp/machineIp.dat"); 
    ipFile >> ipAddress;
    ipFile.close();  
    
    int stackTop = lua_gettop(state);
    lua_getglobal(state, "ckpReposManagerProxy");
    lua_pushstring(state, "getCkpRepos");
    lua_gettable(state, -2);
    lua_getglobal(state, "ckpReposManagerProxy");
    lua_pushstring(state, executionId.c_str());       
    lua_pushstring(state, ipAddress.c_str());           
    lua_pushnumber(state, ckpNumber);

    // Puts the array into the stack
    lua_newtable(state);
    for (int i=0; i<nChecksum; i++) {
        int tmpIndex = LuaUtils::convertStackIndex(state, -1);
        lua_pushnumber(state, i+1);
        lua_pushlstring(state, checksumList[i].c_str(), checksumList[i].length());
        lua_settable(state, tmpIndex);
        // LuaUtils::setFieldOnTable(state, "requestId", applicationId.c_str(), -1);
    }
    
    lua_pushnumber(state, ckpSize);
    
    if (lua_pcall(state, 6, 1, 0) != 0){
      cerr << "[ERROR] CkpReposManagerStub::getCkpRepos->Lua error: "
           << lua_tostring(state, -1) << endl;
      lua_pop(state, 1);
    }

    CkpReposAddress ckpReposAddress;
    
    lua_pushstring(state, "ipAddress");
    lua_gettable(state, -2);
    ckpReposAddress.ipAddress = LuaUtils::extractStringSequence(state, -1);
    lua_pop(state, 1);

    lua_pushstring(state, "portNumber");
    lua_gettable(state, -2);
    ckpReposAddress.port = LuaUtils::extractShortSequence(state, -1);
    lua_pop(state, 1);
    
    lua_pop(state, 2);//removing proxy + result
    assert(stackTop == lua_gettop(state));

    pthread_mutex_unlock( stubMutex );
        
    return ckpReposAddress;
  }

  //--------------------------------------------------------------------------------
  CkpInfo CkpReposManagerStub::getLastCkpInfo(const string & executionId) {

    pthread_mutex_lock( stubMutex );
    
    int stackTop = lua_gettop(state);
    lua_getglobal(state, "ckpReposManagerProxy");
    lua_pushstring(state, "getLastCkpInfo");
    lua_gettable(state, -2);
    lua_getglobal(state, "ckpReposManagerProxy");
    lua_pushstring(state, executionId.c_str());       

    if (lua_pcall(state, 2, 1, 0) != 0){
      cerr << "[ERROR] CkpReposManagerStub::getLastCkpInfo->Lua error: "
           << lua_tostring(state, -1) << endl;
      lua_pop(state, 1);
    }

    CkpInfo ckpInfo;        
    ckpInfo.ckpNumber = LuaUtils::getIntFromTable(state, "ckpNumber");    

    ckpInfo.ckpSize = LuaUtils::getLongFromTable(state, "ckpSize");
    
    lua_pushstring(state, "ipAddress");
    lua_gettable(state, -2);
    ckpInfo.ipAddress = LuaUtils::extractStringSequence(state, -1);
    lua_pop(state, 1);

    lua_pushstring(state, "portNumber");
    lua_gettable(state, -2);
    ckpInfo.port = LuaUtils::extractShortSequence(state, -1);
    lua_pop(state, 1);

    lua_pushstring(state, "checksum");
    lua_gettable(state, -2);
    ckpInfo.checksum = LuaUtils::extractStringSequence(state, -1);
    lua_pop(state, 1);
    
    // ckpInfo.ipAddress = LuaUtils::getStringFromTable(state, "ipAddress");
    // ckpInfo.port      = LuaUtils::getIntFromTable(state, "portNumber");    
        
    lua_pop(state, 2);//removing proxy + result
    assert(stackTop == lua_gettop(state));
    
    pthread_mutex_unlock( stubMutex );
        
    return ckpInfo;
  }

  //--------------------------------------------------------------------------------
  int CkpReposManagerStub::updateRepositoryStatus (const int & ckpReposId, const int & availableSpace) {

    pthread_mutex_lock( stubMutex );

    int stackTop = lua_gettop(state);
    lua_getglobal(state, "ckpReposManagerProxy");
    lua_pushstring(state, "updateRepositoryStatus");
    lua_gettable(state, -2);
    lua_getglobal(state, "ckpReposManagerProxy");
    lua_pushnumber(state, ckpReposId);
    lua_pushnumber(state, availableSpace);

    if (lua_pcall(state, 3, 1, 0) != 0){
      cerr << "[ERROR] CkpReposStub::updateRepositoryStatus->Lua error: "
           << lua_tostring(state, -1) << endl;
      lua_pop(state, 1);
    }

    int updateStatus = (int)lua_tonumber(state, -1);
    lua_pop(state, 2);//removing proxy + result
    assert(stackTop == lua_gettop(state));

    pthread_mutex_unlock( stubMutex );
        
    return updateStatus;
  }
  
  //--------------------------------------------------------------------------------  
  CheckpointRemovalList CkpReposManagerStub::getCheckpointRemovalList(const int & ckpReposId) {

    pthread_mutex_lock( stubMutex );
    
    int stackTop = lua_gettop(state);
    lua_getglobal(state, "ckpReposManagerProxy");
    lua_pushstring(state, "getCheckpointRemovalList");
    lua_gettable(state, -2);
    lua_getglobal(state, "ckpReposManagerProxy");
    lua_pushnumber(state, ckpReposId);       

    if (lua_pcall(state, 2, 1, 0) != 0){
      cerr << "[ERROR] CkpReposManagerStub::getCheckpointRemovalList->Lua error: "
           << lua_tostring(state, -1) << endl;
      lua_pop(state, 1);
    }

    CheckpointRemovalList removalList;        

    lua_pushstring(state, "executionId");
    lua_gettable(state, -2);
    removalList.executionId = LuaUtils::extractStringSequence(state, -1);
    lua_pop(state, 1);

    lua_pushstring(state, "ckpNumber");
    lua_gettable(state, -2);
    removalList.ckpNumber = LuaUtils::extractShortSequence(state, -1);
    lua_pop(state, 1);

    lua_pushstring(state, "fragmentNumber");
    lua_gettable(state, -2);
    removalList.fragmentNumber = LuaUtils::extractShortSequence(state, -1);
    lua_pop(state, 1);
    
    lua_pop(state, 2);//removing proxy + result
    assert(stackTop == lua_gettop(state));
    
    pthread_mutex_unlock( stubMutex );
        
    return removalList;
  }
