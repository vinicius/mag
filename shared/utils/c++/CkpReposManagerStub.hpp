#ifndef CkpReposStub_HPP
#define CkpReposStub_HPP

#include <string>
#include <vector>
#include <pthread.h>
#include "utils/c++/Config.hpp"
struct lua_State;

using std::string;
using std::vector;

struct CkpReposAddress {
    vector<string> ipAddress;
    vector<short> port;    
};

struct CheckpointRemovalList {
    vector<string> executionId;
    vector<short> ckpNumber;
    vector<short> fragmentNumber;
};
    
struct CkpInfo {
    int ckpNumber;
    long ckpSize;
    vector<string> ipAddress;
    vector<short> port;    
    vector<string> checksum;    
};

// IMPORTANT: This class is not multithread-safe. Users of this class must ensure that
// 'state' is safely isolated when concurrently accessed
class CkpReposManagerStub{

private:
  struct lua_State * state;
  pthread_mutex_t * stubMutex;

public:
  CkpReposManagerStub(const Config & config);

  int registerCkpRepos (const string & ipAddress, const int & port, const int & availableSpace);
    
  void notifyCkpStored (const int & ckpReposId, const string & executionId, const int & ckpNumber, const int & fragmentNumber, const int & availableSpace);
                        
  void setCkpReposMode(const string & executionId, int mode, int nNodes, int nExtra);
  
  CkpReposAddress getCkpRepos(const string & executionId, int ckpNumber, string checksumList[], int nChecksum, int ckpSize);

  CkpInfo getLastCkpInfo(const string & executionId);
  
  int updateRepositoryStatus (const int & ckpReposId, const int & availableSpace);
  
  CheckpointRemovalList getCheckpointRemovalList(const int & ckpReposId);  
                        
};

#endif // CkpReposStub_HPP


