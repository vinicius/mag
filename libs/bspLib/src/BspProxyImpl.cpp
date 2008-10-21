#include "BspProxyImpl.hpp"
#include "utils/c++/OrbUtils.hpp"
#include "utils/c++/LuaUtils.hpp"

#include "BaseProcess.hpp"
#include "RegularProcess.hpp"
#include "ProcessZero.hpp"
#include "DrmaManager.hpp"
#include "BsmpManager.hpp"


#include <iostream>//FIXME: For debugging purposes only

#include <map>

#include <cassert>
#include <unistd.h>

extern "C" {
#include <lualib.h>
#include <lauxlib.h>
}



   pthread_mutex_t registerRemoteIorLock;
    
  //Singleton initialization-----------------------------------------------
  BspProxyImpl * BspProxyImpl::singleInstance = NULL;

  //-----------------------------------------------------------------------
  BspProxyImpl::BspProxyImpl(DrmaManager * drmaManager,
                             BsmpManager * bsmpManager,
                             const Config & config):
                              baseProcess_(NULL),
                              drmaManager_(drmaManager),
                              bsmpManager_(bsmpManager){


    serverSideState = lua_open();
    OrbUtils::initLuaState(serverSideState);
    OrbUtils::loadOrb(serverSideState, config.getConf("orbPath"));

    std::cerr << "BspProxyImpl::loadingIdl --> A " << std::endl;
    
    OrbUtils::loadIdl(serverSideState, config.getConf("bspIdlPath"));

    std::cerr << "BspProxyImpl::loadingIdl --> B " << std::endl;

    lua_register(serverSideState,"registerRemoteIorWrapper",registerRemoteIorWrapper);
    lua_register(serverSideState,"takeYourPidWrapper",takeYourPidWrapper);
    lua_register(serverSideState,"bspSynchWrapper",bspSynchWrapper);
    lua_register(serverSideState,"bspPutWrapper",bspPutWrapper);
    lua_register(serverSideState,"bspGetRequestWrapper",bspGetRequestWrapper);
    lua_register(serverSideState,"bspGetReplyWrapper",bspGetReplyWrapper);
    lua_register(serverSideState,"bspSynchDoneWrapper",bspSynchDoneWrapper);
    lua_register(serverSideState,"bspSendWrapper",bspSendWrapper);
    lua_register(serverSideState,"registerOtherProcessIorsWrapper",registerOtherProcessIorsWrapper);

    //Servant Implementation
    lua_dostring(serverSideState, " BspProxyImpl = {"
                        " registerRemoteIor = function (self, ior, pid)"
                        "   registerRemoteIorWrapper (ior, pid)"
                        " end,"
                        " takeYourPid = function (self, pid)"
                        "   takeYourPidWrapper (pid)"
                        " end,"
                        " bspPut = function (self, bspPut)"
                        "   bspPutWrapper(bspPut)"
                        " end,"
                        " bspGetRequest = function (self, request)"
                        "   bspGetRequestWrapper(request)"
                        " end,"
                        " bspGetReply = function (self, reply)"
                        "   bspGetReplyWrapper(reply)"
                        " end,"
                        " bspSynch = function (self, pid, superstep)"
                        "   bspSynchWrapper(pid, superstep)"
                        " end,"
                        " bspSynchDone = function (self)"
                        "   bspSynchDoneWrapper()"
                        " end,"
                        " bspSend = function (self, bspSend)"
                        "   bspSendWrapper(bspSend)"
                        " end,"
                        " registerOtherProcessIors = function (self, processInfoSequence)"
                        "   registerOtherProcessIorsWrapper(processInfoSequence)"
                        " end,"
                        " }"
                 );


    lua_dostring(serverSideState,
		 "bspProxyServant = oil.createservant(BspProxyImpl,'IDL:bspLib/BspProxy:1.0')");
    myIor = OrbUtils::getIor(serverSideState, "bspProxyServant");
  }


  //-----------------------------------------------------------------------
  BspProxyImpl & BspProxyImpl::init(DrmaManager * drmaManager,
                                    BsmpManager * bsmpManager, const Config & config){



    if(BspProxyImpl::singleInstance == NULL){

      BspProxyImpl::singleInstance = new BspProxyImpl(drmaManager, bsmpManager, config);
      pthread_t thread;
      pthread_create(&thread, NULL,(void * (*)(void *))
                      serverSideSetupWrapper ,(void *) NULL );
      pthread_detach(thread);

    }

    return *BspProxyImpl::singleInstance;
  }

  //-----------------------------------------------------------------------

  BspProxyImpl & BspProxyImpl::getInstance(){ return *BspProxyImpl::singleInstance; }

  //-----------------------------------------------------------------------


  void BspProxyImpl::serverSideSetup(){
    lua_State *state = serverSideState;
    LuaUtils::getField(state,"oil.run");
    if(lua_pcall(state, 0, 0, 0) != 0){
      cerr << "[ERROR] BspProxyImpl::serverSideSetup->Lua error: "
           << lua_tostring(state, -1) << endl;
      lua_pop(state, 1);
    }
  }

  //-----------------------------------------------------------------------
  void * BspProxyImpl::serverSideSetupWrapper(void * ptr){

    BspProxyImpl::singleInstance->serverSideSetup();
    return NULL;
  }


  //-----------------------------------------------------------------------
  int BspProxyImpl::registerRemoteIorWrapper(struct lua_State * state){


    std::cerr << "BspProxyImpl::registerRemoteIorWrapper --> A " << std::endl;
    int n = lua_gettop(state);
    assert(n == 2);
    //std::cerr << "BspProxyImpl::registerRemoteIorWrapper --> B " << std::endl;    
    string ior(lua_tostring(state, 1));
    int pid((int)lua_tonumber(state, 2));    
    //std::cerr << "BspProxyImpl::registerRemoteIorWrapper --> C " << pid << std::endl;        
    //sleep(pid);
    while (BspProxyImpl::singleInstance->baseProcess_ == NULL) sleep(1);
    ((ProcessZero *) BspProxyImpl::singleInstance->baseProcess_)->registerRemoteIor(ior, pid);
    std::cerr << "BspProxyImpl::registerRemoteIorWrapper --> Finished!!!" << std::endl;
    return 0;
  }


  //-----------------------------------------------------------------------

  int BspProxyImpl::takeYourPidWrapper(struct lua_State * state){

    int n = lua_gettop(state);
    assert(n == 1);
    int pid = int (lua_tonumber(state, 1));
    ((RegularProcess *) BspProxyImpl::singleInstance->baseProcess_)->setMyPid(pid);

    return 0;
  }

  //-----------------------------------------------------------------------
  int BspProxyImpl::bspPutWrapper(struct lua_State * state){

    int n = lua_gettop(state);
    assert(n == 1);

    BspPut bspPut;

    bspPut.arch(LuaUtils::getIntFromTable(state, "arch"))
          .logicAddr(LuaUtils::getIntFromTable(state, "logicAddr"))
          .offset(LuaUtils::getIntFromTable(state, "offset"))
          .nBytes(LuaUtils::getIntFromTable(state, "nBytes"))
          .superstep(LuaUtils::getIntFromTable(state, "superstep"));

    lua_pushstring(state, "memArea");
    lua_gettable(state, -2);
    bspPut.writeInMem(lua_tostring(state,-1));
    lua_pop(state, 1);

    BspProxyImpl::singleInstance->drmaManager_->addPendingPut(bspPut);
    return 0;

  }

  //-----------------------------------------------------------------------
  int BspProxyImpl::bspGetRequestWrapper(struct lua_State * state){

    int n = lua_gettop(state);
    assert(n == 1);

    BspGetRequest request;
    request.pid(LuaUtils::getIntFromTable(state, "pid"))
           .logicSrc(LuaUtils::getIntFromTable(state, "logicSrc"))
           .offset(LuaUtils::getIntFromTable(state, "offset"))
           .nBytes(LuaUtils::getIntFromTable(state, "nBytes"))
           .superstep(LuaUtils::getIntFromTable(state, "superstep"))
           .dst((void *)LuaUtils::getLongFromTable(state, "dst"));

    lua_pop(state, 1);
    BspProxyImpl::singleInstance->drmaManager_->addPendingGetRequest(request);
    return 0;

  }

  //-----------------------------------------------------------------------
  int BspProxyImpl::bspGetReplyWrapper(struct lua_State * state){

    int n = lua_gettop(state);
    assert(n == 1);

    BspGetReply reply;
    reply.type(LuaUtils::getIntFromTable(state, "dataType"))
         .arch(LuaUtils::getIntFromTable(state, "arch"))
         .nBytes(LuaUtils::getIntFromTable(state, "nBytes"))
         .superstep(LuaUtils::getIntFromTable(state, "superstep"))
         .dst((void *)LuaUtils::getLongFromTable(state, "dst"));
    lua_pushstring(state, "memArea");
    lua_gettable(state, -2);
    reply.writeInMem(lua_tostring(state,-1));

    lua_pop(state, 1);
    BspProxyImpl::singleInstance->drmaManager_->addPendingGetReply(reply);
    return 0;

  }

  //-----------------------------------------------------------------------
  int BspProxyImpl::bspSynchWrapper(struct lua_State * state){
  
    int n = lua_gettop(state);
    assert(n == 2);
    //FIXME: For infiormational purposes unly. Uncomment if necessary
    //int pid = int (lua_tonumber(state, 1));
    //int superstep = int (lua_tonumber(state, 2));

/*    cerr << "BspProxyImpl::bspSynch from pid: " <<  lua_tonumber(state, -2)
         << " in ref to superstep: " << lua_tonumber(state, -1) <<  endl;*/
    ((ProcessZero *) BspProxyImpl::singleInstance->baseProcess_)->bspSynch();
    return 0;

  }

  //-----------------------------------------------------------------------
  int BspProxyImpl::bspSynchDoneWrapper(struct lua_State * state){
    //cerr << "BspProxyImpl::bspSynchDone from pid: " <<  lua_tonumber(state, -1) << endl;
    ((RegularProcess *) BspProxyImpl::singleInstance->baseProcess_)->bspSynchDone();
    return 0;

  }

  //-----------------------------------------------------------------------
  int BspProxyImpl::bspSendWrapper(struct lua_State * state){

    int n = lua_gettop(state);
    assert(n == 1);

    BspSend bspSend;

    bspSend.tagSize(LuaUtils::getIntFromTable(state, "tagSize"))
          .nBytes(LuaUtils::getIntFromTable(state, "nBytes"))
          .superstep(LuaUtils::getIntFromTable(state, "superstep"));


    lua_pushstring(state, "payload");
    lua_gettable(state, -2);
    bspSend.writeInMem(lua_tostring(state,-1));
    lua_pop(state, 1);
    
    lua_pushstring(state, "tag");
    lua_gettable(state, -2);
    bspSend.writeTag(lua_tostring(state,-1));
    lua_pop(state, 1);


    BspProxyImpl::singleInstance->bsmpManager_->addSend(bspSend);
    return 0;
  }

  //-----------------------------------------------------------------------
  int BspProxyImpl::registerOtherProcessIorsWrapper(struct lua_State * state){

    int n = lua_gettop(state);
    assert(n == 1);

    map<int,std::string> iors;
    lua_pushstring(state, "n");
    lua_gettable(state, -2);
    int numProcInfo = int(lua_tonumber(state, -1));
    lua_pop(state, 1);

    for(int i = 1; i <= numProcInfo; i++){
      lua_rawgeti(state, -1, i);
      iors[LuaUtils::getIntFromTable(state, "pid")] = LuaUtils::getStringFromTable(state, "ior");
      lua_pop(state, 1);
    }

    std::cerr << "BspProxyImpl::registerOtherProcessIorsWrapper, size=" << iors.size() << std::endl;        
    
    ((RegularProcess *) BspProxyImpl::singleInstance->baseProcess_)->registerOtherProcessIors(iors);
    return 0;
  }










