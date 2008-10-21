#ifndef CKPREPOSITORYSTORE_HPP_
#define CKPREPOSITORYSTORE_HPP_

#include "CkpRepositoryStub.hpp"
#include "../CkpStore.hpp"
#include "../ida/IDAImpl.h"

enum CkpReposStoreMode {single, replicated, parityLocal, parityGlobal, ida}; 

// -------
  
class CkpRepositoryStore : public CkpStore {
  
protected:  

  /** Stub to access remote repositories */
  CkpRepositoryStub *ckpReposStub;
  /** Stub to access the CRM (Checkpoint Repository Manager) */
  CkpReposManagerStub *ckpReposManagerStub;  
  /** The id of the running process */
  string execId;  
  
  /** Contains information about the last stored checkpoint.
   *  Is set by the getLastCkpNumber and recoverCkpData methods. */
  CkpInfo *ckpInfo;
  
  /** When not null, stores a copy of the checkpoint in the local file system */
  CkpFileStore *ckpFileStore;
  /** Used to check if the storage thread from FileStore has finished */  
  pthread_mutex_t *fileStoreBufferLock;
  
  /** Number of fragments of the file */
  int nFragments;
  /** Number of fragments necessary to reconstruct the file */
  int nRecover;
    
  void saveData(void *data, long nbytes, int ckpNumber);

  virtual void encodeData(void *data, long dataSize, unsigned char ** & encData, int & fragmentSize, string * & checksumList) = 0;
  virtual void deleteEncodedData(unsigned char ** encData, string * checksumList) = 0; 

  virtual void processData(void * & data, long & dataSize, unsigned char **recoveredData, int *fragmentNumbers, long fragmentSize) = 0;
  virtual void prepareRecoveredData(void * & data, unsigned char ** & recoveredData, long ckpSize, long & fragmentSize) = 0;

public:
  CkpRepositoryStore(const Config & ckpConfig);
  ~CkpRepositoryStore();
   
  int getLastCkpNumber();
  
  void recoverCkpData(void * & data, long & dataSize, int ckpNumber);
  
};

// -------

class CkpReplicatedRepositoryStore : public CkpRepositoryStore {

  virtual void encodeData(void *data, long dataSize, unsigned char ** & encData, int & fragmentSize, string * & checksumList);
  virtual void deleteEncodedData(unsigned char ** encData, string * checksumList);

  virtual void prepareRecoveredData(void * & data, unsigned char ** & recoveredData, long ckpSize, long & fragmentSize);
  virtual void processData(void * & data, long & dataSize, unsigned char **recoveredData, int *fragmentNumbers, long fragmentSize);
  
public:
  CkpReplicatedRepositoryStore(const Config & ckpConfig);
  ~CkpReplicatedRepositoryStore();
};

// -------

class CkpParityRepositoryStore : public CkpRepositoryStore {

  virtual void encodeData(void *data, long dataSize, unsigned char ** & encData, int & fragmentSize, string * & checksumList);
  virtual void deleteEncodedData(unsigned char ** encData, string * checksumList);
  
  virtual void prepareRecoveredData(void * & data, unsigned char ** & recoveredData, long ckpSize, long & fragmentSize);
  virtual void processData(void * & data, long & dataSize, unsigned char **recoveredData, int *fragmentNumbers, long fragmentSize);
  
public:
  CkpParityRepositoryStore(const Config & ckpConfig);
  ~CkpParityRepositoryStore();
};

// -------

class CkpIdaRepositoryStore : public CkpRepositoryStore {

  IDAImpl idaImpl;
  
  virtual void encodeData(void *data, long dataSize, unsigned char ** & encData, int & fragmentSize, string * & checksumList);
  virtual void deleteEncodedData(unsigned char ** encData, string * checksumList);
  
  virtual void prepareRecoveredData(void * & data, unsigned char ** & recoveredData, long ckpSize, long & fragmentSize);
  virtual void processData(void * & data, long & dataSize, unsigned char **recoveredData, int *fragmentNumbers, long fragmentSize);
  
public:
  CkpIdaRepositoryStore(const Config & ckpConfig);
  ~CkpIdaRepositoryStore();
};

//-----------------------------------------------------------------------------

#endif /*CKPREPOSITORYSTORE_HPP_*/
