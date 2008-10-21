#ifndef DrmaManager_HPP
#define DrmaManager_HPP

#include "Registration.hpp"
#include "Deregistration.hpp"
#include "BspPut.hpp"
#include "BspGetRequest.hpp"
#include "BspGetReply.hpp"
#include "BaseProcess.hpp"

#include "utils/c++/GuardedVariable.hpp"
#include "utils/c++/Condition.hpp"

#include "DataConverters.hpp"

#include <vector>
#include <pthread.h>

using namespace std;

class BaseProcess;//For cross-referencing to work
class BspProxyStubPool;


  /**
    DrmaManager - performs all BSP DRMA related operations.

    DrmaManager keeps track of pending puts and gets, and also maintains the
    BSP registration stack.

    @author Andrei Goldchleger
    @date December, 2003


  */
  class DrmaManager{

   private:

      //Fields------------------------------------------------------------------------------

      std::vector<Registration> registrations;           /**< BSP Registration "stack" */

      std::vector<Registration> pendingRegistrations;    /**< Pending registrations that will become
                                                              effective on the next superstep
                                                         */
      std::vector<Deregistration> pendingDeregistrations;/**< Pending deregistrations that will become
                                                              effective on the next superstep
                                                         */

      std::vector<BspPut> pendingPuts;            /**< Pending puts that will become
                                                              effective on the next superstep
                                                         */

      std::vector<BspGetRequest> pendingGetRequests;    /**< bsp_get requests that will be
                                                            replied at the end of the superstep
                                                        */
      std::vector<BspGetReply> pendingGetReplies;       /**< replies to the bsp_get requests
                                                            made by the local task
                                                        */

      //GuardedVariable<int> superstep_;                   /**< Current superstep*/

      pthread_mutex_t pendingRegistrationsLock;          /**< Lock for container access*/
      pthread_mutex_t pendingDeregistrationsLock;        /**< Lock for container access*/
      pthread_mutex_t pendingPutsLock;                   /**< Lock for container access*/
      pthread_mutex_t pendingGetRequestsLock;            /**< Lock for container access*/
      pthread_mutex_t pendingGetRepliesLock;             /**< Lock for container access*/

      GuardedVariable<int> pendingGetRepliesCount;       /**< Number of expected replies
                                                              to the bsp_get requests. We
                                                              need to be sure that we received
                                                              all replies before starting
                                                              the next superstep */

      Condition noPendingGetReplies;                    /**< Indicates if wether or not there
                                                             are pending replies */

      BaseProcess * baseProcess_;                         /**< Necessary in order to call
                                                             methods on remote tasks */

      BspProxyStubPool * stubPool_;

      DataConverters * dataConverter_;
      char dstArch;

      //Methods----------------------------------------------------------------------------

      /**
        Reply to any bsp_get requested during the last superstep
      */
      void commitPendingGetRequests();

      /**
        Makes effective all bsp_get requests that were replied
        during the last superstep
      */
      void commitPendingGetReplies();

      /**
        Commit pending registrations.
      */
      void commitRegistrations();

      /**
        Commit pending deregistrations.
      */
      void commitDeregistrations();

      /**
        Commit pending puts.
      */
      void commitPendingPuts();

      /**
        Removes a registration from the DRMA registration stack

        @param dereg - the deregistrtation corresponding to the registration
        that will be removed.
      */
      void deregister(Deregistration & dereg);

      /**
        Returns the last registration for a given memory address.

        @param ptr - the memory address to be looked up.
      */
      int findLastRegistration(const void * ptr);

      /**
        Returns a BSP registration

        @param pos - the stack index of the wanted registration
      */
      Registration & getRegistration(int pos);




  public:

      void setBaseProcess(BaseProcess * baseProcess){baseProcess_ = baseProcess; }

      /**
        Add a BSP registration that will become effective at next barrier

        @param ptr - pointer to the memory region being registered
        @param size - size of the memory region being registered, in bytes
      */
      void addPendingRegistration(const void * ptr, int size, unsigned long type);

      /**
        Add a BSP deregistration request that will become effective at next barrier

        @param ptr - pointer to the memory region being unregistered
      */
      //TODO: I suppose that removeRegistration should remove the LAST INSERTED
      //registration of a given memory address. It is not clear, though, if this
      //is the correct behavior
      void addPendingDeregistration(const void * ptr);

      /**
        Creates a DrmaManager Object.
      */
      DrmaManager(BspProxyStubPool  * stubPool, DataConverters *dataConverter);

      /**
        Called at the end of the superstep, commits all pending operations.
      */
      void processPendingOperations();

      /**
        Increments tthe superstep
      */
      //void incSuperstep(){ superstep_.inc(); }

      /**
        Returns the current superstep
      */
      //int superstep(){ return superstep_.value(); }

      /**
        Increments the number of bsp_get replies that we expect to receive
      */


      /**
        Adds a bsp_put request to the pending bsp_put queue.

        @param bspPut - The DrmaOperating object containing the put
        details.
      */
      void addPendingPut(const BspPut & bspPut);


      /**
        Adds a bsp_get request that will be processed by the end
        of the superstep

        @param request - Information regarding the get request
      */
      void addPendingGetRequest(const BspGetRequest & request);

      /**
        Adds a reply to one of our bsp_get requests
        
        @param reply - Information regarding the bsp_get reply
      */
      void addPendingGetReply(const BspGetReply & reply);
      
            /**
        The implementation of the bsp_put operation. Allows a BSP task to write
        on the address space of a task.

        @param pid - The BSP process ID of the remote task on which bsp_put is
        going to be invoked
        @param src - The local address from which memory contents will be copied
        to the remote BSP task
        @param dst - The local address that will be translated to a logical BSP
        addressm, which is unique to all BSP tasks
        @param offset - an offset from src that will hold when copying the memory
        area to the remote BSP task
        @param nBytes - the number of bytes to be copied from <b>src + offset</b> to <b>dst</b>
      */
      void bspPut(int pid, const void * src, void * dst, int offset, int nBytes);

            /**
        Implements the bsp_get method. Allows the local task to read from memory
        from a remote process.

        @param pid - The BSP process ID from where memory contents will be read
        @param src - Memory address from where data will be copied
        @param offset - Offset that will be added to the source address to determine
                        the starting position from where data will be copied
        @param dst - The destination address to were data will be written
        @param nBytes - Number of bytes to be read
      */
      void bspGet(int pid, const void * src, int offset, void * dst, int nBytes);

      






  };

#endif//DrmaManager_HPP
