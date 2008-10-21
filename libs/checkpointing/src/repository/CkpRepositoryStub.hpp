#ifndef _CKPREPOSITORYSTUB_HPP_
#define _CKPREPOSITORYSTUB_HPP_

#include <string>
using namespace std;

class CkpRepositoryStub {

  string execId;
  int sockfdTemp;  
  int connectToServer(string ipAddress, short port);

public:
  CkpRepositoryStub(string execId_) : execId(execId_), sockfdTemp(0){}
  
  void getDataSize(string & ipAddress, short portNumber, long & dataSize, int ckpNumber);
  void getDataBody(string & ipAddress, short portNumber, void * data, long & dataSize, int ckpNumber);

  /**
   * Transfer the checkpoint ckpNumber from 'data' to ipAddress:portNumber.
   * Returns the number of transfered bytes or -1 if a failure occurs;
   */  
  int transferData(string & ipAddress, short portNumber, void *data, long nbytes, int ckpNumber, int fragmentNumber);  
  
  /**
   * Reads the checkpoint ckpNumber from ipAddress:portNumber and puts into 'data'.
   * Returns the number of bytes read or -1 if a failure occurs;
   */
  int readData(string & ipAddress, short portNumber, void * & data, long & dataSize, int ckpNumber, int fragmentNumber);
  
};

#endif /*_CKPREPOSITORYSTUB_HPP_*/
