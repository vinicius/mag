#ifndef BspProxyStubPool_HPP
#define BspProxyStubPool_HPP

#include <string>
#include <map>
#include <vector>

#include "utils/c++/Config.hpp"
#include "BspPut.hpp"
#include "BspSend.hpp"
#include "BspGetRequest.hpp"
#include "BspGetReply.hpp"




using namespace std;

  /**
    BspProxyStubPool - Create and manage BspProxy's stubs.

    A BspProxy is a CORBA object that represents each BSP task. A BspProxyStub is
    dynamically created in this class when requested. All stubs exists only as
    entities in a LUA state. They are maintained together to save memory, so that
    we need to load only one copy of the ORB and IDL.
    
    @author Andrei Goldchleger

    @date november/2003

  */
  class BspProxyStubPool{

    private:


      //Member fields------------------------------------------------------------------
      string orbPath;                      /**< O2 directory */
      string bspIdlPath;                   /**< path to the BspLib IDL */

      struct lua_State * clientSideState; /**< lua_state representing the client side*/
      int lastCreatedProxy;                /**< Count the number of stubs already created */
      
      std::map<int, std::string> tasks;     /**< Ids for proxy retrieval from the stubPool */
      std::map<int, std::string> iors;      /**< iors of the remote tasks */
      
      //Private methods---------------------------------------------------------------


    public:

      int howManyProxies(){return tasks.size(); }

      const string & getIor(const int & taskPid) const;
      
       const string & getTaskId(const int & taskPid) const;

      /**
        Construct a BspProxyStubPool
        
        @param config - Holds configuration information, such as ORB & IDl paths
      */
      BspProxyStubPool(const Config & config);
      
      /**
        Creates a new proxy in the pool

        @param ior - IOR of the remote BspProxy object

        @returns a string identifier for this proxy that should be used on following
        remote method invocations
      */
      void createNewProxy(const int & taskPid, const string & ior);

      /**
        Calls the method <b>registerRemoteIor</b> on a remote object

        This method is used at initialization time, so that all tasks know the
        IOR of each other and can communicate on following steps.

        @param proxyId - Id of the remote object (The one received when createNewProxy was called)
        @param pid - BSP PID of one of the BSP tasks
        @param ior - IOR that allows one to reach a BSP Proxy

//       */
       void registerRemoteIor(const string & ior, const int & processPid);
// 
       /**

        Gives a PID to a given BSP task.

        This method is called ONLY by process zero at each of the BSP proxies
        in order to set the BSP PID of each of the tasks. This is called ONLY
        at initialization time

        @param proxyId - Id of the remote object
        @param pid - The new BSP PID of a given task

      */
      void takeYourPid(const int & taskPid);

      /**
        Performs a bsp_put on a remote task.

        @param proxyId - Id of the remote object
        @param bspPut - Te BspPut details
      */
      void bspPut(const int & taskPid, BspPut &  bspPut);

      /**
        Signals to Process zero that the local task reached the synchronization
        barrier point.

        @param proxyId - Id of the remote object
        @param pid - The PID of the local BSP task
      */
      void bspSynch(const int & taskPid, int superstep);

      /**
        Called by Process Zero only. Signals to remote tasks that barrier sinchronization
        is done.

        @param proxyId - Id of the remote object
        @param pid - The PID of the remote BSP task
      */
      void bspSynchDone(const int & taskPid);

      /**
        Requests a remote read to another BSP task.

        @param proxyId - Id of the remote object
        @param request - Contains information regarding the request
      */
      void bspGetRequest(const int & taskPid, const BspGetRequest & request);

      /**
        Reply a remote read made by another BSP task.

        @param proxyId - Id of the remote object
        @param response - Contains information regarding the reply
      */
      void bspGetReply(const int & taskPid, const BspGetReply & response);


      /**
       * Performs a bsp_send to the remote task
       * @param proxyId
       * @param bspSend
       */
      void bspSend(const int & taskPid, BspSend & bspSend);


      void registerOtherProcessIors(const int & taskPid);

      
      
  };


#endif//BspProxyStubPool_HPP

