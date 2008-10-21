#include "CkpStore.hpp"
#include "CheckpointingLib.hpp"

#include <sstream>
#include <cassert>

#include <unistd.h>
#include <fcntl.h>

#include <iostream>

using std::cout;
using std::endl;
using std::cerr;

void CkpStore::saveData(void *data, long nbytes, int ckpNumber) {
  cerr << "CkpStore::saveData --> Empty implementation" <<  endl;
}

void CkpStore::recoverCkpData(void * & data, long & dataSize, int ckpNumber) {
  cerr << "CkpStore::recoverCkpData --> Empty implementation" <<  endl;
}
   
int CkpStore::getLastCkpNumber() {
    return -1;
}

//---------------------------------------------------------------------------

int CkpFileStore::getLastCkpNumber() {
    return -1;
}

void CkpFileStore::saveData(void *data, long nbytes, int ckpNumber) {
  
  std::ostringstream tmpFileName;
  tmpFileName << path << ckpNumber << ".tmp";

  if (delay > 0)
    sleep(delay);
  
  // Creates the File
  int outFile = creat(tmpFileName.str().c_str(),0600);
  if (outFile == -1) {
    cerr << "CkpFileStore::saveData--> Error opening file " 
	 << tmpFileName.str().c_str() << " for saving." << endl ;
    return;
  }

  // Writes the data to the file
  int writenBytes = write(outFile, data, nbytes);
  close(outFile);

  std::ostringstream ckpFileName;
  ckpFileName << path << ckpNumber << ".ckp";
  rename (tmpFileName.str().c_str(), ckpFileName.str().c_str());

  int nKeep=3;
  if (ckpNumber > nKeep-1) {
    std::ostringstream oldCkpFileName;
    oldCkpFileName << path << ckpNumber-nKeep << ".ckp";
    unlink(oldCkpFileName.str().c_str());
  }

  cerr << "CKP ---> Saved " << writenBytes << " bytes to "
       << ckpFileName.str().c_str()  << " (" << delay << ")." << endl;

}

void CkpFileStore::recoverCkpData(void * & data, long & dataSize, int ckpNumber) {
  std::ostringstream ckpFile;
  if (ckpNumber < 0) ckpNumber = 0;
  
  ckpFile << path << ckpNumber << ".ckp";
  
  int inFile = open(ckpFile.str().c_str(), O_RDONLY);
  if (inFile == -1) {
    cout << "No data retrieved from " << ckpFile.str().c_str() << ". Exiting..." << endl;  
    exit(-1);
  }

  //  ckp_datasize_t dataSize;
  //  read(inFile, &dataSize, sizeof(ckp_datasize_t));
  struct stat *fileStat = new struct stat;
  fstat(inFile, fileStat);
  dataSize = (long)fileStat->st_size;
  delete fileStat;

  data = malloc(dataSize);
  ckp_datasize_t readBytes = read(inFile, data, dataSize);

  assert ((long)readBytes == dataSize);
  close (inFile);
    
  //int temp = recoverCheckpoint(ckpFile.str().c_str(), (void **)&data, &dataSize);
  
  if (readBytes > 0)
    cout << readBytes << " bytes read from " << ckpFile.str().c_str() << "." << endl;  
  else {
    cout << "No data retrieved from " << ckpFile.str().c_str() << ". Exiting..." << endl;  
    exit(-1);
  }
}

//---------------------------------------------------------------------------

void CkpNoStore::saveData(void *data, long nbytes, int ckpNumber) {
  
  cerr << "CkpNoStore::saveData --> Checkpoint of " << nbytes 
       << " bytes generated but not saved." << endl;
  
}

void CkpNoStore::recoverCkpData(void * & data, long & dataSize, int ckpNumber) {
  
  cerr << "CkpNoStore::recoverCkpData --> Empty implementation " << endl;
  
}

//---------------------------------------------------------------------------
