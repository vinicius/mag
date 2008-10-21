#ifndef BsmpManager_HPP
#define BsmpManager_HPP


#include "BspSend.hpp"
#include "DataConverters.hpp"

#include "utils/c++/GuardedVariable.hpp"
#include "utils/c++/Condition.hpp"


#include <vector>

  /**
    BsmpManager - performs all BSP BSMP related operations.

    BsmpManager keeps track of pending puts and gets, and also maintains the
    BSP registration stack.

    @author Carlos Queiroz
    @date August, 2004


  */
  
  class BaseProcess;
  class BspProxyStubPool;

  class BsmpManager{

   private:

      std::vector<BspSend> messageQueue_;  ///< BSMP received messages

      //GuardedVariable<int> tag_size_;
      //GuardedVariable<int> last_tag_size_;
      BaseProcess * baseProcess_;
      BspProxyStubPool * stubPool_;

      DataConverters * dataConverter_;

      //GuardedVariable<int> superstep_; ///< Current superstep

      pthread_mutex_t messageQueueLock_; ///< Lock for container access



      GuardedVariable<int> currentTagSize_;//FIXED: adicionei
      GuardedVariable<int> nextTagSize_;//FIXED: adicionei


      //Methods----------------------------------------------------------------------------


      /**
        Commit pending sends.
      */
      void commitPendingSends();




  public:

      /**
        Creates a BsmpManager Object.
      */
      BsmpManager(BspProxyStubPool * stubPool, DataConverters *dataConverter);

      void setBaseProcess(BaseProcess * baseProcess){baseProcess_ = baseProcess; }

      /**
        Adds a bsp_send request to the pending bsp_send queue.

        @param bspSend - The BspSend object containing the send
        details.
      */
      void addSend(const BspSend & bspSend);

      /**
       * Removes payload msg from bsp_send queue
       * @param payload
       * @param reception_bytes
       */
	  void bspMove(void * payload, int reception_bytes);

      /**
       *
       * Returns tag id and the length of the next payload msg
       * @param status
       * @param tag
       */
      void bspGetTag(int * status, void * tag);


     /**
      *
      *
      * @param packets
      * @param accum_bytes
      */
	 void bspQSize(int *packets, int *accum_nbytes);

      /**
       *
       *@param tag_bytes
       *
       */
      void bspSetTagSize(int * tag_bytes);


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

      void bspSend(int pid,const void *tag, const void * payload,int payload_bytes);


  };

#endif//BsmpManager_HPP


