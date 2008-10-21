#include "NameServiceStub.hpp"

/* Name Service Stub Constructor
 * @author José de Ribamar Braga
 */
NameServiceStub::NameServiceStub(lua_State *nState,string serverNameIor, string CosNamingIdlPath)
{
    nameState = nState;
    OrbUtils::initLuaState(nState);
    OrbUtils::loadOrb(nameState, "");
    OrbUtils::loadIdl(nameState, CosNamingIdlPath);	
    OrbUtils::instantiateProxy(nameState,
                                 serverNameIor, 
                                 "IDL:omg.org/CosNaming/NamingContextExt:1.0", 
                                 NAME_SERVER_PROXY);    
}

/* Create a binding for name <name> and object (proxyName) <proxyName> in the naming
 * context.
 * @param name - the string name of the object
 * @param proxyName - proxyName of the object
 */
void NameServiceStub::bind(string name,string proxyName){
	
	//naming:bind({{id="Naming", kind="COS"}}, naming)
	int stackTop = lua_gettop(nameState);
    lua_getglobal(nameState, NAME_SERVER_PROXY);
    lua_pushstring(nameState, "bind");
    lua_gettable(nameState, -2);
    lua_getglobal(nameState, NAME_SERVER_PROXY);
    
    lua_newtable(nameState);
    lua_pushnumber(nameState,1);
    lua_newtable(nameState);
    LuaUtils::setFieldOnTable(nameState, "id", name.c_str(), -1);
    LuaUtils::setFieldOnTable(nameState, "kind", "", -1);
    lua_settable(nameState,-3);
    
    lua_getglobal(nameState, proxyName.c_str());    
 	
 	if (lua_pcall(nameState, 3, 0, 0) != 0){
 	  lua_getglobal(nameState, "tostring");
      lua_insert(nameState, -2);
      lua_pcall(nameState, 1, 1, 0);
      cerr << "[ERROR] NameServer::bindName ->Lua error: "
           << "Erro" << endl<< lua_tostring(nameState, -1) << " erro " << endl;
      lua_pop(nameState, 1);
 	}
 	  lua_pop(nameState, 1);
 	assert(stackTop == lua_gettop(nameState));
}

 /* Remove the name binding from the context.
  * @param name - name of the object
  * 
 */
void NameServiceStub::unbind(string name){
	// obj:unbind({{id="Naming", kind=""}})
	int stackTop = lua_gettop(nameState);
    lua_getglobal(nameState, NAME_SERVER_PROXY);
    lua_pushstring(nameState, "unbind");
    lua_gettable(nameState, -2);
    lua_getglobal(nameState, NAME_SERVER_PROXY);
    
    lua_newtable(nameState);
    lua_pushnumber(nameState,1);
    lua_newtable(nameState);
    LuaUtils::setFieldOnTable(nameState, "id", name.c_str(), -1);
    LuaUtils::setFieldOnTable(nameState, "kind", "", -1);
    lua_settable(nameState,-3);
    
 	if (lua_pcall(nameState, 2, 0, 0) != 0){
 	 lua_getglobal(nameState, "tostring");
     lua_insert(nameState, -2);
     lua_pcall(nameState, 1, 1, 0);	
     cerr << "[ERROR] NameServer::unbindName ->Lua error: "
           << lua_tostring(nameState, -1) << endl;
      lua_pop(nameState, 1);
 	}
 	lua_pop(nameState, 1);
 	
    assert(stackTop == lua_gettop(nameState));
	
}
/* Return object reference (IOR) that is bound to the name.
 * @param registeredName - registered name of the object in server name
 * @returns IOR string 
 */
 
string NameServiceStub::resolve(string registeredName)
{
   
    int stackTop = lua_gettop(nameState);
    lua_getglobal(nameState, NAME_SERVER_PROXY);
    lua_pushstring(nameState, "resolve");
    lua_gettable(nameState, -2);
    lua_getglobal(nameState, NAME_SERVER_PROXY);
    //lua_pushstring(nameState,registeredName.c_str());
    
    lua_newtable(nameState);
    lua_pushnumber(nameState,1);
    lua_newtable(nameState);
    LuaUtils::setFieldOnTable(nameState, "id", registeredName.c_str(), -1);
    LuaUtils::setFieldOnTable(nameState, "kind", "", -1);
    lua_settable(nameState,-3);

    string stubIor = "";    
    int resolveReturnStatus = lua_pcall(nameState, 2, 1, 0);
    
    if (resolveReturnStatus == 0) {

      //cerr << registeredName << " found!" << endl;     
      LuaUtils::getField(nameState, "oil.ior.encode");
      lua_insert(nameState,-2); //swap -1 and -2 position
    
      if (lua_pcall(nameState, 1, 1, 0) != 0){
        lua_getglobal(nameState, "tostring");
        lua_insert(nameState, -2);
        lua_pcall(nameState, 1, 1, 0);  
        cerr << "[ERROR] NameServer::get_ior -> Lua error: "
             << lua_tostring(nameState, -1) << endl;
        lua_pop(nameState, 1);
      }
        
      stubIor = lua_tostring(nameState,-1);
      lua_pop(nameState,1);      
    }        
    else { // Error resolving name!
       lua_getglobal(nameState, "tostring");
       lua_insert(nameState, -2);
       lua_pcall(nameState, 1, 1, 0);
       cerr << "[ERROR] NameServer::resolve -> Lua error: "
            << lua_tostring(nameState, -1) << endl;
       lua_pop(nameState, 1);
 	}
    
 
    lua_pop(nameState,1); // Removes nameServerProxy from the stack
    assert(stackTop == lua_gettop(nameState));
 	
    return stubIor;	
}

