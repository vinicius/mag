// CkpFileStore.hpp

#ifndef CKP_STORE_HPP
#define CKP_STORE_HPP

#include "utils/c++/Config.hpp"
#include "utils/c++/CkpReposManagerStub.hpp"

#include <pthread.h>
#include <string>

//----------------------------------------------------------------

struct CkpData {
  class CkpStore *ckpStore;
  void *buffer;
  long nBytesTotal;
  int ckpNumber;  
  bool releaseMemory;
  pthread_mutex_t *bufferLock;
};

//-------------------------------------------------------------------

class CkpStore {

  bool releaseMemory;  
  int lastCkpStored;
  
  /** Used to check if the thread that saves the data has finished.
   *  It is locked before creating the thread, and released when the thread finishes. */
  pthread_mutex_t *bufferLock;
  
  virtual void saveData(void *data, long nbytes, int ckpNumber);
  
public:
  
  CkpStore() : releaseMemory(true), bufferLock(0){};
  virtual ~CkpStore(){}
  
  void setBufferLock(pthread_mutex_t *bufferLock) {
    this->bufferLock = bufferLock;    
  };
  
  void setReleaseMemory(bool flag) {releaseMemory = flag;}
  
  static void *saveData(void *data) {
    struct CkpData *ckpData = (CkpData *)data;   
    (ckpData->ckpStore)->saveData(ckpData->buffer, ckpData->nBytesTotal, ckpData->ckpNumber);
    if (ckpData->bufferLock) pthread_mutex_unlock(ckpData->bufferLock);
    if (ckpData->releaseMemory) delete[] (char *)ckpData->buffer;
    delete ckpData;
    return 0;
  }

  virtual int getLastCkpNumber();
  
  virtual void recoverCkpData(void * & data, long & dataSize, int ckpNumber);

  void saveCkpData(void *buffer, long nBytesTotal, int ckpNumber){
    struct CkpData *ckpData = new CkpData;
    ckpData->ckpStore = this;
    ckpData->buffer = buffer;
    ckpData->nBytesTotal = nBytesTotal;
    ckpData->ckpNumber = ckpNumber;
    ckpData->releaseMemory = releaseMemory;    
    ckpData->bufferLock = bufferLock;        
    if (bufferLock) pthread_mutex_lock(bufferLock);
    pthread_t saveThread;
    pthread_create(&saveThread, NULL,(void * (*)(void *))&CkpStore::saveData,(void *) ckpData );
    pthread_detach(saveThread);
  }
  
};

//-----------------------------------------------------------------------------

class CkpFileStore : public CkpStore {
  
  std::string path;
  int delay;

  void saveData(void *data, long nbytes, int ckpNumber);

public:

  CkpFileStore(std::string path, int delay=0) {
    this->path = path;
    this->delay = delay;
  }

  int getLastCkpNumber();
  
  void recoverCkpData(void * & data, long & dataSize, int ckpNumber);
  
};

//-----------------------------------------------------------------------------

class CkpNoStore : public CkpStore {
  
  void saveData(void *data, long nbytes, int ckpNumber);
  
public:
  void recoverCkpData(void * & data, long & dataSize, int ckpNumber);
  
};

#endif // CKP_STORE_HPP
