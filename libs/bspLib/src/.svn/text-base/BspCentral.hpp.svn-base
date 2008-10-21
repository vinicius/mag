#ifndef BspCentral_HPP
#define BspCentral_HPP

#include "BspProxyStubPool.hpp"
#include "DrmaManager.hpp"
#include "BsmpManager.hpp"
#include "BaseProcess.hpp"
#include "utils/c++/NameServiceStub.hpp"

  class BspCentral{

    private:

      BspProxyStubPool * stubPool;
      NameServiceStub *nameServiceStub;
      DrmaManager * drmaManager;
      BsmpManager * bsmpManager;
      BaseProcess * process;

    public:
    
      BspCentral();

      void bspBegin(){process->bspBegin(); }

      int bspPid(){return process->getMyPid(); }

      int bspNprocs(){return process->getTotalNumProcs();}

      void bspSync(){process->bspLocalSynch(); }

      void bspPushregister (const void * ident, int size, unsigned long type = 0){
        drmaManager->addPendingRegistration(ident, size, type);
      }

      void bspPopregister(const void * ident){
        drmaManager->addPendingDeregistration(ident);
      }

      void bspPut(int pid, const void *src, void *dst, int offset, int nbytes){
        drmaManager->bspPut(pid, src, dst, offset, nbytes);
      }

      void bspGet(int pid, const void * src, int offset, void * dst, int nbytes){
        drmaManager->bspGet(pid, src, offset, dst, nbytes);
      }

      void bspSend(int pid,const void *tag, const void * payload,int payload_bytes){
        bsmpManager->bspSend(pid, tag, payload, payload_bytes);
      }

      void bspMove(void *payload,int reception_bytes){
        bsmpManager->bspMove(payload, reception_bytes);
      }

      void bspQSize(int *packets, int *accum_nbytes){
        bsmpManager->bspQSize(packets, accum_nbytes);
      }

      void bspSetTagSize(int *tag_bytes){
        bsmpManager->bspSetTagSize(tag_bytes);
      }

      void bspGetTag(int *status, void *tag){
        bsmpManager->bspGetTag(status, tag);
      }

  };


#endif//BspCentral_HPP
