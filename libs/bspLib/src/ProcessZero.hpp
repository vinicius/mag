#ifndef ProcessZero_HPP
#define ProcessZero_HPP

#include "BaseProcess.hpp"

#include <string>

class BspProxyStubPool;
class DrmaManager;
class BsmpManager;

  class ProcessZero: public BaseProcess{

    private:

      pthread_mutex_t bspSynchLock;         /**< Lock to avoid race conditions between local
                                                 and remote synchronization requests. Relevant
                                                 only to process zero
                                            */

      GuardedVariable<int> nextPid;            /**< Last pid atributted to a BSP task. Relevant to
                                                 process zero only.
                                            */

      GuardedVariable<int> synchedProcecess;///< Number of tasks that called bsp_synch. Relevant
                                            ///<


    public:

      ProcessZero(BspProxyStubPool * stubPool, DrmaManager * drmaManager, BsmpManager * bsmpManager);

      void bspBegin(); 

      void registerRemoteIor(std::string ior, int processPid);

      void bspLocalSynch();

      void bspSynch();

  };

#endif//ProcessZero_HPP

