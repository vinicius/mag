#include "CheckpointingLib.hpp"
#include "CheckpointTimer.hpp"
#include "CkpCompilerStack.hpp"
#include "CkpRestoredData.hpp"
#include "HeapController.hpp"
#include "CkpLogger.hpp"
#include "CkpStore.hpp"
#include "repository/CkpRepositoryStore.hpp"

#include "utils/c++/Config.hpp"
#include "utils/c++/StringUtils.hpp"

#include <iostream>
#include <sstream>
#include <cassert>

extern "C" {
#include <fcntl.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <sys/stat.h>
}

using namespace std;

CheckpointTimer *ckpTimer = NULL;

int testCheckpointTimer() {

  if (ckpTimer == NULL)
    return 0;

  if (ckpTimer->testFinishedInterval())
    return 1;
  
  return 0;
}

int saveCheckpoint(const char *filename, void *data, long nbytes) {

  // Creates the File
  int outFile = creat(filename,0666);
  if (outFile == -1) 
    return -1;

  ckp_datasize_t dataSize = (ckp_datasize_t)nbytes;
  // Writes the data to the file
  write(outFile, &dataSize, sizeof(ckp_datasize_t));
  int writenBytes = write(outFile, data, nbytes);
  close(outFile);
  
  return writenBytes;
}

int recoverCheckpoint(const char *filename, void **data, long* bufferSize) { 
	
  int inFile = open(filename, O_RDONLY);
  if (inFile == -1) 
    return -1;

  //  ckp_datasize_t dataSize;
  //  read(inFile, &dataSize, sizeof(ckp_datasize_t));
  struct stat *fileStat = new struct stat;
  fstat(inFile, fileStat);
  *bufferSize = (long)fileStat->st_size;
  delete fileStat;

  *data = malloc(*bufferSize);
  ckp_datasize_t readBytes = read(inFile, *data, *bufferSize);

  assert ((long)readBytes == *bufferSize);

  return (int)readBytes;
}

//------------------------------------------------------------------------------
// Used by ckpCompiler
//------------------------------------------------------------------------------

int ckp_restoring = 0;
CkpStore *ckpStore = NULL;     // initialized at ckp_initialize_stack
CkpCompilerStack *ckpStack = NULL; // initialized at ckp_initialize_stack
CkpRestoredData  *ckpRestoredData = NULL;
HeapController heapController;

string createCkpStore(Config & ckpConfig);
//-------

void initCheckpointing(int argc, char **argv) {

  int initial_capacity = 1000;

  if (ckpStore) delete ckpStore;
  ckpStore = NULL;
  string ckpStoreType;

  // Try to locate a "ckp.conf" config file in the current directory
  try { 
    Config ckpConfig("ckp.conf"); 
    ckpStoreType = createCkpStore(ckpConfig);
  } 
  // If 'ckp.conf' is not found, use default values
  catch(...) { 
    ckpStore = new CkpFileStore(string(""), 0);
    if (ckpTimer) delete ckpTimer;
    ckpTimer = new CheckpointTimer(30);    
    ckpStoreType = "current directory";
  }     
  
  // Initializes the checkpointing stack
  ckpStack = new CkpCompilerStack(initial_capacity, &heapController, ckpStore);

  /** 
   * Restarts the execution if application started as:
   * <applicationName> -r<ckpNumber>
   */
  int ckpNumber = -1;
  if (argc >= 2 && argv[1][0] == '-' && argv[1][1] == 'r') {
    
    // TODO: This is probably never called!
    ckp_restoring = 1;
    if (argv[1][2] != '\0') 
        ckpNumber = atoi(argv[1] + 2);
    else 
        ckpNumber = 0;        
    ckp_restore_data(ckpNumber);
  }
  /**
   * Get last checkpoint number from CkpStore 
   */
  else {
    ckpNumber = ckpStore->getLastCkpNumber();     
    if (ckpNumber >= 0) {
        ckp_restoring = 1;
        ckp_restore_data(ckpNumber);         
    } 
  }
              
  if (ckp_restoring == 1) {
    ostringstream out1;
    out1 << "initCheckpointing --> restarting application from checkpoint number " 
         << ckpNumber << " using storage strategy " << ckpStoreType << ".";
    ckpLogger.debug(out1.str());
  }
  else {
      ckpLogger.debug("initCheckpointing --> checkpointing started using storage " + ckpStoreType + ".");
  }
    
}

string createCkpStore(Config & ckpConfig) {

  string ckpStoreType;
  try {

    ckpStoreType = ckpConfig.getConf("ckpStore");
    int ckpInterval;

    if (ckpStoreType.compare("noCheckpointing") == 0) {
      std::cerr << "createCkpStore --> NoCheckpointing" << std::endl;
      ckpInterval = 60*60*24*365; // a year =)    
      ckpStore = new CkpNoStore();    
    }
    else if (ckpStoreType.compare("ckpNoStore") == 0) {
      ckpInterval = StringUtils::string2int(ckpConfig.getConf("interval"));
      ckpStore = new CkpNoStore();    
      std::cerr << "createCkpStore --> CkpNoStore " << std::endl;
    }
    else if (ckpStoreType.compare("ckpLocalStore") == 0) {
      ckpInterval = StringUtils::string2int(ckpConfig.getConf("interval"));
      string ckpPath = ckpConfig.getConf("location");
      ckpStore = new CkpFileStore(ckpPath, 0);
      std::cerr << "createCkpStore --> CkpLocalStore " << ckpPath << std::endl;
    }
    else if (ckpStoreType.compare("ckpReposStore") == 0) {
//      ckpInterval = StringUtils::string2int(ckpConfig.getConf("interval"));
//      ckpStore = new CkpRepositoryStore(ckpConfig);
//      ((CkpRepositoryStore *)ckpStore)->setCkpReposMode(1, 2, 0);
      std::cerr << "createCkpStore --> CkpReposStore ERROR! Not implemented" << std::endl;
    }
    else if (ckpStoreType.compare("ckpReplicatedReposStore") == 0) {
      ckpInterval = StringUtils::string2int(ckpConfig.getConf("interval"));
      ckpStore = new CkpReplicatedRepositoryStore(ckpConfig);
      std::cerr << "createCkpStore --> CkpReplicatedReposStore " << std::endl;
    }
    else if (ckpStoreType.compare("ckpParityReposStore") == 0) {
      ckpInterval = StringUtils::string2int(ckpConfig.getConf("interval"));
      ckpStore = new CkpParityRepositoryStore(ckpConfig);
      std::cerr << "createCkpStore --> CkpLocalParityReposStore " << std::endl;
    }    
    else if (ckpStoreType.compare("ckpIdaReposStore") == 0) {
      ckpInterval = StringUtils::string2int(ckpConfig.getConf("interval"));
      ckpStore = new CkpIdaRepositoryStore(ckpConfig);
      std::cerr << "createCkpStore --> CkpIdaReposStore " << std::endl;
    }
    

    if (ckpTimer) delete ckpTimer;
    ckpTimer = new CheckpointTimer(ckpInterval);

  }
  catch(...) { // NoSuchConfigException
    std::cerr << "ERROR: createCkpStore --> " 
	      << "Invalid parameters setting checkpointing storage type." << std::endl;
  }

  return ckpStoreType;   
}

// Deprecated
void set_ckp_store(int ckpSaveFlag, int delay) {

  if (ckpStore) delete ckpStore;

  if (ckpSaveFlag == -1) /** Data is saved to the local directory with no delay */
    ckpStore = new CkpFileStore("", delay);
  else if (ckpSaveFlag == -10)
    ckpStore = new CkpFileStore("/tmp/", delay);
  else if (ckpSaveFlag == -20)
    ckpStore = new CkpNoStore();
  else if (ckpSaveFlag == -30)
    std::cerr << "ERROR: set_ckp_store --> Functionality not implemented." << std::endl;
  
  ckpStack->setCkpStore(ckpStore); 

}

int ckp_push_data(void *data, long nbytes, unsigned long type, void (*func)(void *)) {
    return ckpStack->ckpPushData(data, nbytes, type, func);
}

int ckp_pop_data(void) {
    return ckpStack->ckpPopData(1);
}

int ckp_npop_data(int nVar) {
    return ckpStack->ckpPopData(nVar);
}

int ckp_save_stack_data(int ckpNumber) {
  int returnValue = ckpStack->ckpSaveStackData(ckpNumber);  
  //sleep(1);
  return returnValue;
}

//-------

int ckp_restore_data(int ckpNumber) {

    if (ckpRestoredData != NULL) return -1;

    ckpRestoredData = new CkpRestoredData;
    int bytesRead = ckpRestoredData->ckpRestoreCkpData(ckpStore, ckpNumber);
    ckpStack->setLastCkpNumber(ckpNumber);
    return bytesRead;    
}

int ckp_get_data(void *data, long nbytes, unsigned long type, void (*func)(void *)) {
    return ckpRestoredData->ckpGetData(data, nbytes, type, func);
}

//------------------------------------------------------------------------------
// Memory Management
//------------------------------------------------------------------------------

void *ckp_malloc(size_t size) {
  void *address = malloc(size);
  heapController.addAllocatedMemory(address, size);  
  return address;
}

void *ckp_calloc(size_t nmenb, size_t size) {
  void *address = calloc(nmenb, size);
  heapController.addAllocatedMemory(address, size);  
  return address;
}

void ckp_free(void *ptr) {
  free(ptr);
  heapController.removeAllocatedMemory(ptr);  
}

void *ckp_realloc(void *ptr, size_t size) {
  heapController.removeAllocatedMemory(ptr);      
  void *address = realloc(ptr, size);
  heapController.addAllocatedMemory(address, size);  
  return address;
}

