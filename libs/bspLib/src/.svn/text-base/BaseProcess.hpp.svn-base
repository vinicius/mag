#ifndef BaseProcess_HPP
#define BaseProcess_HPP

#include "utils/c++/GuardedVariable.hpp"
#include "utils/c++/Condition.hpp"

class BspProxyStubPool;
class DrmaManager;
class BsmpManager;



  class BaseProcess{

    protected:

      int myPid;                            /**< PID of this BSP task */
      int totalNumProcs;                    /**< Total number of tasks in the BSP program */
      GuardedVariable<int> superstep_;       /**< The current superstep of the BSP application */

      Condition globalInitDone;             /**< Indicates Wether or not global initialization is
                                                 complete, that is, each tasks has its
                                                 pid and knows the IORs of each other
                                            */

      Condition synchDone;                  /**< Indicate wether or not a bsp_synch is
                                                 complete
                                            */

      BspProxyStubPool * stubPool_;
      DrmaManager * drmaManager_;
      BsmpManager * bsmpManager_;



      BaseProcess(BspProxyStubPool * stubPool,
                  DrmaManager * drmaManager,
                  BsmpManager * bsmpManager):
                                stubPool_(stubPool),
                                drmaManager_(drmaManager),
                                bsmpManager_(bsmpManager) {}
                                
      virtual ~BaseProcess(){}                                
                                



      //Methods--------------------------------------------------------------------------

    public:

      virtual void bspLocalSynch() = 0;
      virtual void bspBegin() = 0;


      /**
        Return the BSP PID of the local task
      */
      int getMyPid(){ return myPid; }

      /**
        Sets the BSP PID of the local task
      */
      void setMyPid(int pid){ myPid = pid; }

      /**
        Sets the total number of tasks in the BSP application
      */
      void setTotalNumProcs(int numProcs){ totalNumProcs = numProcs; }

      /**
        Gets the total number of tasks in the BSP application
      */
      int  getTotalNumProcs(){ return totalNumProcs; }

      int superstep(){ return superstep_.value(); }
      //void incSuperstep(){ return superstep.inc(); }



  };

#endif//BaseProcess_HPP




