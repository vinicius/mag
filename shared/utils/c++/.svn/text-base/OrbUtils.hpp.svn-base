#ifndef OrbUtils_HPP
#define OrbUtils_HPP

#include <string>

extern "C" {
#include <lua.h>
}


using namespace std;

  /** 
    OrbUtils - Holds some helper methods for common tasks involving
    O2 ORB.
    
    Please be advised that, as all lua involving operations, those
    operations involves messing with a lua_state, including its 
    API's stacks. All methods contained here tries to keep the state
    as clean as possible. But keep in mind that you should be careful, 
    for example, if tou call those methods on the same state in a multithreaded
    environment, as these methods are not synchronized.

   @author Andrei Goldchleger
  */
  class OrbUtils{

    public:

     /**
      Initializes a given lua_State by opening needed lua libraries

      @param state - The lua_State to be initialized

     */
     static void initLuaState(lua_State * state);

     /**
      Loads O2 files(Lua source code) in a given lua_State in order to allow the
      ORB to be used.
AsctImpl
      @param state - the lua_State in which O2 should be loaded
      @param orbPath - directory that holds O2 files
     */
     static void loadOrb(lua_State * state, string orbPath);

     /**
     Loads a given IDL, so that "stubs/skeletons" can be dynamically
     instantiated.

     @param state - the lua_State in which the IDL should be loaded
     @param idlPath - the path to the IDL to be loaded
     */
     static void loadIdl(lua_State * state, string idlPath);

     /**Instantiate a proAsctImplxy for a given servant

     @param state - the lua_State in which the proxy should be loaded
     @param ior - IOR of the servant to be acessed
     @param interfaceName - Complete identifier if a given interface (e.g. myModule::Foo)
     @param proxyName - the name of the that will hold a reference to the proxy
     */
     static void instantiateProxy(lua_State * state,
                                 string ior, string interfaceName, string proxyName);

     /**
      Returns the ior of a given servant
      
      @param state - the lua_State in which the servant is located
      @param servantName - the name of the servant which will have its ior returned
     */
     static string getIor(lua_State * state, const char * servantName);

     /**
      Activates verbose mode in a OiL instance
      
      @param state - the lua_State in which the servant is located
     */
     static void setVerboseMode(lua_State * state);

  };

#endif//OrbUtils_HPP
