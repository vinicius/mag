// TestStore.cpp

#include "CkpStore.hpp"

#include <cassert>
#include <iostream>
#include <sstream>
#include <unistd.h>

#define DATA_SIZE 1000000

int main (int argc, char **argv) {
 
  Config ckpConfig("ckp.conf");   
  class CkpStore *ckpStore = new CkpRepositoryStore(ckpConfig);

  char *data0   = (char *)malloc(DATA_SIZE*sizeof(char));  
  char *data1   = (char *)malloc(DATA_SIZE*sizeof(char));  
  char *dataCpy = (char *)malloc(DATA_SIZE*sizeof(char));  

  memcpy(dataCpy, data1, DATA_SIZE);

  ckpStore->saveCkpData(data0, DATA_SIZE, 0);
  ckpStore->saveCkpData(data1, DATA_SIZE, 1);

  std::cout << "Data sent. Sleeping..." << std::endl;
  sleep(2);
  
  void *dataRes;
  long dataSize;
  ckpStore->recoverCkpData(dataRes, dataSize, 1);

  std::cout << "dataSize = " << dataSize << std::endl;

  for (int i=0; i<DATA_SIZE; i++) {
    assert(dataCpy[i] == ((char *)dataRes)[i]);
  }

}
