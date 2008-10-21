
extern "C" {
#include <lua.h>
#include <lualib.h>
#include <lauxlib.h>
}


#include "utils/c++/LuaUtils.hpp"
#include "utils/c++/OrbUtils.hpp"
#include <assert.h>

#include "ArsmStub.hpp"
#include "ArscImpl.hpp"
#include "MessagePacket.hpp"
#include "ContextInitiationException.hpp"
#include "SelfTestException.hpp"
#include "ContextFinalizationException.hpp"
class MessagePacket;



  //----------------------------------------------------------------------
  ArsmStub::ArsmStub(lua_State * aState, const string & arsmIor):state(aState){
    //FIXME: This assumes that state ALREADY loaded the IDL for the arsm. Due to
    //O2's current design it is not possible to load the GRM IDL separately.
    OrbUtils::instantiateProxy (state, arsmIor, "interfaces::Arsm", "arsmProxy");

  }
 //-----------------------------------------------------------------------
  ArsmStub::ArsmStub(){
  	
  }
MessagePacket ArsmStub::initiateContext(MessagePacket contextStream)
 {
	int stackTop = lua_gettop(state);
	lua_getglobal(state, "arsmProxy");
	lua_pushstring(state, "initiateContext");
	lua_gettable(state, -2);
	lua_getglobal(state, "arsmProxy");
    lua_pushlstring(state, contextStream.getStream().c_str(),contextStream.getStream().length());	
 	if (lua_pcall(state, 2, 1, 0) != 0){
		lua_getglobal(state, "tostring");
  		lua_insert(state, -2);
  		lua_pcall(state, 1, 1, 0);
  		cerr << "[ERROR] ArsmStub::initiateContext->Lua error: "
       	<< lua_tostring(state, -1) << endl;
  		lua_pop(state, 1);
  		throw ContextInitiationException();
 	}
 		std::string value( lua_tostring(state,-1),lua_strlen(state,-1));
		MessagePacket msg = MessagePacket(value);
    		lua_pop(state,2);//Removes arsmProxy and  value from stack
    		assert(stackTop == lua_gettop(state));
		return  msg;		
}
MessagePacket ArsmStub::selfTest(MessagePacket msgGSS)
 {
    int stackTop = lua_gettop(state);
    std::string id;
    lua_getglobal(state, "arsmProxy");
    lua_pushstring(state, "test");
    lua_gettable(state, -2);
    lua_getglobal(state, "arsmProxy");
    lua_pushlstring(state, msgGSS.getStream().c_str(),msgGSS.getStream().length());
    if (lua_pcall(state, 2, 1, 0) != 0){
		lua_getglobal(state, "tostring");
  		lua_insert(state, -2);
  		lua_pcall(state, 1, 1, 0);
  		cerr << "[ERROR] ArsmStub::selTest->Lua error: "
       	<< lua_tostring(state, -1) << endl;
  		lua_pop(state, 1);
  		throw SelfTestException();
 	}
	std::string value( lua_tostring(state,-1),lua_strlen(state,-1));
	MessagePacket msg = MessagePacket(value);
	lua_pop(state,2); //Removes arsmProxy from stack
	assert(stackTop == lua_gettop(state));
	return  msg;
}

void  ArsmStub::finalizeContext(string contextStream){
 	MessagePacket msgFinish = MessagePacket(contextStream);
 	int stackTop = lua_gettop(state);
        lua_getglobal(state, "arsmProxy");
        lua_pushstring(state, "finalizeContext");
        lua_gettable(state, -2);
        lua_getglobal(state, "arsmProxy");
        lua_pushlstring(state, msgFinish.getStream().c_str(),msgFinish.getStream().length());
	    if (lua_pcall(state, 2, 0, 0) != 0){
	    		lua_getglobal(state, "tostring");
	  		lua_insert(state, -2);
  			lua_pcall(state, 1, 1, 0);
	  		cerr << "[ERROR] ArsmStub::selTest->Lua error: "
    	   		<< lua_tostring(state, -1) << endl;
  			lua_pop(state, 1);
  			throw ContextFinalizationException();
	     }
      	lua_pop(state,1); //Removes arsmProxy from stack
		assert(stackTop == lua_gettop(state)); 	
}


