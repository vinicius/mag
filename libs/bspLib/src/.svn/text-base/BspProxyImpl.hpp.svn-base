#ifndef BspProxyImpl_HPP
#define BspProxyImpl_HPP

#include <string>
#include <vector>
#include <pthread.h>

extern "C" {
#include <lua.h>
}

using namespace std;

#include "utils/c++/Config.hpp"
#include "BspCentral.hpp"
     
  class DrmaManager;
  class BsmpManager;
  class BaseProcess;


  /**
    BspProxyImpl - implementation of the BspProxy IDL Interface

    A BspProxy represents each of the BSP tasks and allows communication betwaeen them,
    This class handles only the communications' details. Functionality id delecated to the
    BspCentral class.

    @author Andrei Goldchleger
    @date november/2003

  */
  class BspProxyImpl{

    private:

      //Fields--------------------------------------------------------------------------


      struct lua_State * serverSideState;  /**< lua_state representing the server side*/

      string myIor;                         /**< ior of this BspProxyImpl */

      BaseProcess * baseProcess_;
      DrmaManager * drmaManager_;
      BsmpManager * bsmpManager_;

      static BspProxyImpl * singleInstance; /**< singleton */


      //Methods--------------------------------------------------------------------------

      /**
        Private constructor for singleton

        @param bspCentral_ - pointer to a BspCentral
        @param config - for configuratios such as ORB & IDL path

      */
      BspProxyImpl(DrmaManager * drmaManager,
                   BsmpManager * bsmpManager,
                   const Config & config);

      /**
        Wrapper called  by Lua code in order to let us call 'registerRemoteIor' on
        the single instance.

      */
      static int registerRemoteIorWrapper(struct lua_State * state);

      /**
        Wrapper called  by Lua code in order to let us call 'takeYourPid' on
        the single instance.

      */
      static int takeYourPidWrapper(struct lua_State * state);

      /**
        Launches the request handler loop. Pthreads need a function pointer, so
        we need this static wrapper
      */
      static void * serverSideSetupWrapper(void * ptr);

      /**
        Launches the request handler loop.
      */
      void serverSideSetup();

      /**
        Wrapper called  by Lua code in order to let us call 'bspPut' on
        the single instance. 
      */
      static int bspPutWrapper(struct lua_State * state);

      /**
        Wrapper called  by Lua code in order to let us call 'bspSynch' on
        the single instance.
      */
      static int bspSynchWrapper(struct lua_State * state);

      /**
        Wrapper called  by Lua code in order to let us call 'bspSynchDone' on
        the single instance.
      */
      static int bspSynchDoneWrapper(struct lua_State * state);

      /**
        Wrapper called  by Lua code in order to let us call 'bspGet' on
        the single instance.
      */
      static int bspGetWrapper(struct lua_State * state);

      /**
        Wrapper called  by Lua code in order to let us call 'bspGetRequest' on
        the single instance.
      */
      static int bspGetRequestWrapper(struct lua_State * state);

      /**
        Wrapper called  by Lua code in order to let us call 'bspGetReply' on
        the single instance.
      */
      static int bspGetReplyWrapper(struct lua_State * state);


	 /**
        Wrapper called  by Lua code in order to let us call 'bspSend' on
        the single instance. 
      */
      static int bspSendWrapper(struct lua_State * state);
      
      static int registerOtherProcessIorsWrapper(struct lua_State * state);

    public:
      
      /**
        Returns the IOR of this  BspProxy
      */
      const string & getIor() { return myIor; }
      
      void setBaseProcess(BaseProcess * baseProcess){ baseProcess_ = baseProcess; }

      /**
        Initializes the single instance

        @param bspCentral_ - pointer to a BspCentral instance, so that BspProxyImpl
        can forward calls to it
        @param config - for configuratios such as ORB & IDL path

      */
      static BspProxyImpl & init(DrmaManager * drmaManager,
                                 BsmpManager * bsmpManager,
                                 const Config & config);

      /**
        Returns a reference to the single instance
      */
      static BspProxyImpl & getInstance();

  };



#endif//BspProxyImpl_HPP
