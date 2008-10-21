#ifndef NAMESERVICESTUB_HPP_
#define NAMESERVICESTUB_HPP_
#define NAME_SERVER_PROXY "nameServerProxy"

#include "OrbUtils.hpp"
#include "LuaUtils.hpp"
#include <string>
#include<iostream>
#include<cassert>

using std::string;
using std::cerr;
using std::endl;


extern "C" {
#include <lua.h>
#include <lualib.h>
#include <lauxlib.h>
}


using std::string;

//IMPORTANT: This class is not multithread-safe. Users of this class must ensure that
  // 'state' is safely isolated when concurrently accessed
class NameServiceStub
{
private:

lua_State *nameState;


public:
	// Constructor
	NameServiceStub(lua_State *nState,string serverNameIor, string CosNamingIdlPath);
	void bind(string name,string proxyName);
	void unbind(string name);
	string resolve(string registeredName);


};

#endif /*NAMESERVICESTUB_HPP_*/
