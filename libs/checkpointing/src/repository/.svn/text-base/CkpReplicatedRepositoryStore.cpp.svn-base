#include "../CkpUtils.hpp"
#include "../CkpLogger.hpp"
#include "CkpRepositoryStub.hpp"
#include "CkpRepositoryStore.hpp"
#include "../../../shared/utils/c++/StringUtils.hpp"

#include <iostream>
#include <sstream>
#include <cassert>
#include <string>

//---------------------------------------------------------------------------
CkpReplicatedRepositoryStore::CkpReplicatedRepositoryStore(const Config & ckpConfig) : CkpRepositoryStore(ckpConfig) {
                
    try {
       nFragments = StringUtils::string2int(ckpConfig.getConf("nCopies"));        
    }
    catch(...) { // NoSuchConfigException
       nFragments = 1;
    }    
    nRecover = 1;
                
    ckpReposManagerStub->setCkpReposMode(execId, replicated, nRecover, nFragments - nRecover);
}

//---------------------------------------------------------------------------

CkpReplicatedRepositoryStore::~CkpReplicatedRepositoryStore() {}

//---------------------------------------------------------------------------
 
void CkpReplicatedRepositoryStore::encodeData
(void *data, long dataSize, unsigned char ** & encData, int & fragmentSize, string * & checksumList) {
    
    fragmentSize = dataSize;
    encData = new unsigned char *[nFragments];
    for (int slice=0; slice < nFragments; slice++)
        encData[slice] = (unsigned char *)data;
        

    /** Evaluates checksum list */
    checksumList = new string[nFragments+1];
    string ckpChecksum = CkpUtils::getChecksum((unsigned char *)data, dataSize);
    for (int i=0; i<nFragments+1; i++)
        checksumList[i] = ckpChecksum;
               
}

void CkpReplicatedRepositoryStore::deleteEncodedData
(unsigned char ** encData, string * checksumList) {
    
    delete[] encData;
    delete[] checksumList; 
    
}

//---------------------------------------------------------------------------

void CkpReplicatedRepositoryStore::prepareRecoveredData
(void * & data, unsigned char ** & recoveredData, long ckpSize, long & fragmentSize) {
    
    /** Allocates data */    
    fragmentSize = ckpSize;        
    data = new unsigned char[ckpSize];
    
    /** Allocates recoveredData */
    recoveredData = new unsigned char *[nRecover];
    for (int fragment=0; fragment < nRecover; fragment++)
        recoveredData[fragment] = (unsigned char *)data;   
    
}


void CkpReplicatedRepositoryStore::processData
(void * & data, long & dataSize, unsigned char **recoveredData, int *fragmentNumbers, long fragmentSize) {

    // data is already set
    dataSize = fragmentSize;
    delete[] recoveredData;
}
