#include "BspProxyStubPool.hpp"
#include "NoSuchTaskException.hpp"

#include <sstream>
#include <iostream>

#include "utils/c++/OrbUtils.hpp"
#include "utils/c++/LuaUtils.hpp"




  //--------------------------------------------------------------------------------
  BspProxyStubPool::BspProxyStubPool(const Config & config){

    orbPath = config.getConf("orbPath");
    bspIdlPath = config.getConf("bspIdlPath");
    lastCreatedProxy = 0;
    clientSideState = lua_open();
    OrbUtils::initLuaState(clientSideState);
    OrbUtils::loadOrb(clientSideState, orbPath);

    OrbUtils::loadIdl(clientSideState, bspIdlPath);
  }

  //OrbUtils::instantiateProxy (state, aGrmIor, "IDL:interfaces/Grm:1.0", "grmProxy");

  //--------------------------------------------------------------------------------
  void BspProxyStubPool::createNewProxy(const int & taskPid, const string & ior){

    stringstream ss;
    string proxyId;
    ss << lastCreatedProxy;
    ss >> proxyId;

    lastCreatedProxy++;
    proxyId = "proxy" + proxyId;

    //cerr << "BspProxyStubPool::createNewProxy --> (" + proxyId + ") ior: " << ior << endl;

    string bspProxy("IDL:bspLib/BspProxy:1.0");
    OrbUtils::instantiateProxy (clientSideState, ior, bspProxy, proxyId);
    tasks[taskPid] = proxyId;
    iors[taskPid] = ior;
  }

  //--------------------------------------------------------------------------------
  const string & BspProxyStubPool::getTaskId(const int & taskPid) const{

    std::map<int, std::string>::const_iterator it = tasks.find(taskPid);
    if(it != tasks.end())
      return (*it).second;
    else{
      cerr << "[ERROR] BspProxyStubPool::getTaskId->No such task id: " << taskPid << endl;
      throw NoSuchTaskException();
    }
  }

  //--------------------------------------------------------------------------------
  const string & BspProxyStubPool::getIor(const int & taskPid) const{

    std::map<int, std::string>::const_iterator it = iors.find(taskPid);
    if(it != iors.end())
      return (*it).second;
    else{
      cerr << "[ERROR] BspProxyStubPool::getIor->No such task id: " << taskPid << endl;
      throw NoSuchTaskException();
    }
  }

  //--------------------------------------------------------------------------------
  void BspProxyStubPool::registerOtherProcessIors(const int & taskPid){

    int initialStackSize = lua_gettop(clientSideState);

    lua_getglobal(clientSideState, getTaskId(taskPid).c_str());
    lua_pushstring(clientSideState, "registerOtherProcessIors");
    lua_gettable(clientSideState, -2);
    lua_getglobal(clientSideState, getTaskId(taskPid).c_str());
    lua_newtable(clientSideState);
    for(unsigned int i = 1; i <= tasks.size(); i++){
      lua_newtable(clientSideState);
      LuaUtils::setFieldOnTable(clientSideState,"pid", i, -1);
      LuaUtils::setFieldOnTable(clientSideState,"ior", getIor(i), -1);
      lua_rawseti(clientSideState,-2, i);
    }

    //std::cerr << "BspProxyImpl::registerOtherProcessIors --> SENDING " << iors.size() << std::endl;        
    
    if (lua_pcall(clientSideState, 2, 0, 0) != 0){
      cerr << "[ERROR] BspProxyStubPool::registerOtherProcessIors->Lua error: "
           << lua_tostring(clientSideState, -1) << endl;
      lua_pop(clientSideState, 1);
    }

    lua_pop(clientSideState, 1);//removes proxy from stack
    assert(initialStackSize == lua_gettop(clientSideState));
  }

//   //--------------------------------------------------------------------------------
  void BspProxyStubPool::registerRemoteIor(const string & ior, const int & processPid){

    int initialStackSize = lua_gettop(clientSideState);

    lua_getglobal(clientSideState, getTaskId(0).c_str());
    lua_pushstring(clientSideState, "registerRemoteIor");
    lua_gettable(clientSideState, -2);
    lua_getglobal(clientSideState, getTaskId(0).c_str());
    lua_pushstring(clientSideState, ior.c_str());
    lua_pushnumber(clientSideState, processPid);    

    if (lua_pcall(clientSideState, 3, 0, 0) != 0){
      cerr << "[ERROR] BspProxyStubPool::registerRemoteIor->Lua error: "
           << lua_tostring(clientSideState, -1) << endl;
      lua_pop(clientSideState, 1);
    }

    lua_pop(clientSideState, 1);//removes proxy from stack
    assert(initialStackSize == lua_gettop(clientSideState));

  }

  //--------------------------------------------------------------------------------
  void BspProxyStubPool::takeYourPid(const int & taskPid){

    int initialStackSize = lua_gettop(clientSideState);

    lua_getglobal(clientSideState, getTaskId(taskPid).c_str());
    lua_pushstring(clientSideState, "takeYourPid");
    lua_gettable(clientSideState, -2);
    lua_getglobal(clientSideState, getTaskId(taskPid).c_str());
    lua_pushnumber(clientSideState, taskPid);
    if (lua_pcall(clientSideState, 2, 0, 0) != 0){
       cerr << "[ERROR] BspProxyStubPool::takeYourPid->Lua error: "
            << lua_tostring(clientSideState, -1) << endl;
       lua_pop(clientSideState, 1);
    }

    lua_pop(clientSideState, 1);//removes proxy from stack
    assert(initialStackSize == lua_gettop(clientSideState));

  }

  //--------------------------------------------------------------------------------
  void BspProxyStubPool::bspPut(const int & taskPid, BspPut & bspPut){


    int initialStackSize = lua_gettop(clientSideState);

    lua_getglobal(clientSideState, getTaskId(taskPid).c_str());
    lua_pushstring(clientSideState, "bspPut");
    lua_gettable(clientSideState, -2);
    lua_getglobal(clientSideState, getTaskId(taskPid).c_str());
    lua_newtable(clientSideState);

    LuaUtils::setFieldOnTable(clientSideState, "arch", bspPut.arch(), -1);
    LuaUtils::setFieldOnTable(clientSideState, "logicAddr", bspPut.logicAddr(), -1);
    LuaUtils::setFieldOnTable(clientSideState, "offset", bspPut.offset(), -1);
    LuaUtils::setFieldOnTable(clientSideState, "nBytes", bspPut.nBytes(), -1);
    LuaUtils::setFieldOnTable(clientSideState, "superstep", bspPut.superstep(), -1);
    lua_pushstring(clientSideState, "memArea");

    const char * memArea = (char *) bspPut.memArea();
    lua_pushlstring(clientSideState, memArea, bspPut.nBytes());
    lua_settable(clientSideState, -3);

    if (lua_pcall(clientSideState, 2, 0, 0) != 0){
      cerr << "[ERROR] ToTask: " << taskPid << " BspProxyStubPool::bspPut->Lua error: "
           << lua_tostring(clientSideState, -1) << endl;
      lua_pop(clientSideState, 1);
    }

    lua_pop(clientSideState, 1);//removes proxy from stack
    assert(initialStackSize == lua_gettop(clientSideState));

  }

  //--------------------------------------------------------------------------------
  void BspProxyStubPool::bspGetRequest(const int & taskPid, const BspGetRequest & request){

    int initialStackSize = lua_gettop(clientSideState);

    lua_getglobal(clientSideState, getTaskId(taskPid).c_str());
    lua_pushstring(clientSideState, "bspGetRequest");
    lua_gettable(clientSideState, -2);
    lua_getglobal(clientSideState, getTaskId(taskPid).c_str());
    lua_newtable(clientSideState);
    LuaUtils::setFieldOnTable(clientSideState, "pid", request.pid(), -1);
    LuaUtils::setFieldOnTable(clientSideState, "logicSrc", request.logicSrc(), -1);
    LuaUtils::setFieldOnTable(clientSideState, "offset", request.offset(), -1);
    LuaUtils::setFieldOnTable(clientSideState, "nBytes", request.nBytes(), -1);
    LuaUtils::setFieldOnTable(clientSideState, "superstep", request.superstep(), -1);
    LuaUtils::setFieldOnTable(clientSideState, "dst", (unsigned long)request.dst(), -1); 
    //FIXME: check if cast is ok

    if (lua_pcall(clientSideState, 2, 0, 0) != 0){
      cerr << "[ERROR] BspProxyStubPool::bspGetRequest->Lua error: "
           << lua_tostring(clientSideState, -1) << endl;
      lua_pop(clientSideState, 1);
    }

    lua_pop(clientSideState, 1);//removes proxy from stack
    assert(initialStackSize == lua_gettop(clientSideState));

  }

  //--------------------------------------------------------------------------------
  void BspProxyStubPool::bspGetReply(const int & taskPid,
                                        const BspGetReply & reply){

    int initialStackSize = lua_gettop(clientSideState);

    lua_getglobal(clientSideState, getTaskId(taskPid).c_str());
    lua_pushstring(clientSideState, "bspGetReply");
    lua_gettable(clientSideState, -2);
    lua_getglobal(clientSideState, getTaskId(taskPid).c_str());
    lua_newtable(clientSideState);
    LuaUtils::setFieldOnTable(clientSideState, "dataType", reply.type(), -1);
    LuaUtils::setFieldOnTable(clientSideState, "arch", reply.arch(), -1);
    LuaUtils::setFieldOnTable(clientSideState, "nBytes", reply.nBytes(), -1);
    LuaUtils::setFieldOnTable(clientSideState, "superstep", reply.superstep(), -1);
    LuaUtils::setFieldOnTable(clientSideState, "dst", (unsigned long)reply.dst(), -1);
    lua_pushstring(clientSideState, "memArea");//FIXME: Add method to LuaUtils
    const char * memArea = (char *) reply.memArea();
    lua_pushlstring(clientSideState, memArea, reply.nBytes());
    lua_settable(clientSideState, -3);

    if (lua_pcall(clientSideState, 2, 0, 0) != 0){
      cerr << "[ERROR] BspProxyStubPool::bspGetReply->Lua error: "
           << lua_tostring(clientSideState, -1) << endl;
      lua_pop(clientSideState, 1);
    }

    lua_pop(clientSideState, 1);//removes proxy from stack
    assert(initialStackSize == lua_gettop(clientSideState));

  }

  //--------------------------------------------------------------------------------
  void BspProxyStubPool::bspSynch(const int & taskPid, int superstep){

    int initialStackSize = lua_gettop(clientSideState);

    lua_getglobal(clientSideState, getTaskId(0).c_str());
    lua_pushstring(clientSideState, "bspSynch");
    lua_gettable(clientSideState, -2);
    lua_getglobal(clientSideState, getTaskId(0).c_str());
    lua_pushnumber(clientSideState, taskPid);
    lua_pushnumber(clientSideState, superstep);

    if (lua_pcall(clientSideState, 3, 0, 0) != 0){
      cerr << "[ERROR] BspProxyStubPool::bspSynch->Lua error: "
           << lua_tostring(clientSideState, -1) << endl;
      lua_pop(clientSideState, 1);
    }

    lua_pop(clientSideState, 1);//removes proxy from stack
    assert(initialStackSize == lua_gettop(clientSideState));

  }

  //--------------------------------------------------------------------------------
  void BspProxyStubPool::bspSynchDone(const int & taskPid){


    int initialStackSize = lua_gettop(clientSideState);

    lua_getglobal(clientSideState, getTaskId(taskPid).c_str());
    lua_pushstring(clientSideState, "bspSynchDone");
    lua_gettable(clientSideState, -2);
    lua_getglobal(clientSideState, getTaskId(taskPid).c_str());
    //lua_pushnumber(clientSideState, pid);

    if (lua_pcall(clientSideState, 1, 0, 0) != 0){
      cerr << "[ERROR] BspProxyStubPool::bspSynchDone->Lua error: "
           << lua_tostring(clientSideState, -1) << endl;
      lua_pop(clientSideState, 1);
    }

    lua_pop(clientSideState, 1);//removes proxy from stack
    assert(initialStackSize == lua_gettop(clientSideState));

  }



  //--------------------------------------------------------------------------------
  void BspProxyStubPool::bspSend(const int & taskPid, BspSend & bspSend){

    int initialStackSize = lua_gettop(clientSideState);

    lua_getglobal(clientSideState, getTaskId(taskPid).c_str());
    lua_pushstring(clientSideState, "bspSend");
    lua_gettable(clientSideState, -2);
    lua_getglobal(clientSideState, getTaskId(taskPid).c_str());
    lua_newtable(clientSideState);


    LuaUtils::setFieldOnTable(clientSideState, "nBytes", bspSend.nBytes(), -1);
    LuaUtils::setFieldOnTable(clientSideState, "tagSize", bspSend.tagSize(), -1);
    LuaUtils::setFieldOnTable(clientSideState, "superstep", bspSend.superstep(), -1);
    lua_pushstring(clientSideState, "payload");
    const char * payload = (char *) bspSend.payload();
    lua_pushlstring(clientSideState, payload, bspSend.nBytes());
    lua_settable(clientSideState, -3);
    lua_pushstring(clientSideState, "tag");
    const char * tag = (char *) bspSend.tag();
    lua_pushlstring(clientSideState, tag, bspSend.tagSize());
    lua_settable(clientSideState, -3);




    if (lua_pcall(clientSideState, 2, 0, 0) != 0){
      cerr << "[ERROR] BspProxyStubPool::bspSend->Lua error: "
           << lua_tostring(clientSideState, -1) << endl;
      lua_pop(clientSideState, 1);
    }

    lua_pop(clientSideState, 1);//removes proxy from stack
    assert(initialStackSize == lua_gettop(clientSideState));

  }
  
 

  
 



