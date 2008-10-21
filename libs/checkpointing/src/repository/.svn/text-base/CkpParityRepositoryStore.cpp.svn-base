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
CkpParityRepositoryStore::CkpParityRepositoryStore(const Config & ckpConfig) : CkpRepositoryStore(ckpConfig) {

    try {
       nRecover = StringUtils::string2int(ckpConfig.getConf("nFragments"));        
    }
    catch(...) { // NoSuchConfigException
       nRecover = 2;
    }
    
    nFragments = nRecover + 1;
                
    ckpReposManagerStub->setCkpReposMode(execId, parityLocal, nRecover, nFragments - nRecover);
}

//---------------------------------------------------------------------------

CkpParityRepositoryStore::~CkpParityRepositoryStore() {
}

//---------------------------------------------------------------------------

void CkpParityRepositoryStore::encodeData
(void *data, long dataSize, unsigned char ** & encData, int & fragmentSize, string * & checksumList) {

    fragmentSize = (dataSize%nRecover == 0) ? dataSize/nRecover : dataSize/nRecover+1;

    unsigned char *charData = (unsigned char *)data;

    encData = new unsigned char *[nFragments];
    for (int slice=0; slice < nRecover; slice++)
        encData[slice] = charData + slice*fragmentSize;   
    encData[nRecover] = new unsigned char[fragmentSize];
           
    /** Evaluates the parity */
    // TODO: We can transform chars into longs    
    for (int i=0; i<fragmentSize; i++) {
        encData[nRecover][i] = charData[i];
        for(int k=1; k<nRecover; k++)
            encData[nRecover][i] = encData[nRecover][i]^(encData[k][i]);            
    }            

    /** Evaluates checksum list */
    checksumList = new string[nFragments+1];    
    for (int i=0; i<nFragments; i++)
        checksumList[i] = CkpUtils::getChecksum(encData[i], fragmentSize);
    checksumList[nFragments] = CkpUtils::getChecksum((unsigned char *)data, dataSize);
        
}

void CkpParityRepositoryStore::deleteEncodedData
(unsigned char ** encData, string * checksumList) {
    
    delete[] encData[nRecover];
    delete[] encData;
    delete[] checksumList; 
}

//---------------------------------------------------------------------------

void CkpParityRepositoryStore::prepareRecoveredData
(void * & data, unsigned char ** & recoveredData, long ckpSize, long & fragmentSize) {
        
    /** Allocates data */    
    fragmentSize = (ckpSize%nRecover == 0) ? ckpSize/nRecover : ckpSize/nRecover+1;        
    data = new unsigned char [ckpSize];
    
    /** Allocates recoveredData */
    recoveredData = new unsigned char *[nRecover];
    unsigned char *charData = (unsigned char *)data;
    for (int fragment=0; fragment < nRecover; fragment++)
        recoveredData[fragment] = (unsigned char *)(charData + fragment*fragmentSize);   
    recoveredData[nRecover] = new unsigned char[fragmentSize];
}

void CkpParityRepositoryStore::processData
(void * & data, long & dataSize, unsigned char **recoveredData, int *fragmentNumbers, long fragmentSize) {

    dataSize = fragmentSize*nRecover;
    
    int missingSlice=-1;
    for (int i=0; i<nFragments; i++)
        if (fragmentNumbers[i] != i) {
            missingSlice = i;
            break;
        }

    if (missingSlice >= 0) {
        for (int i=0; i<fragmentSize; i++)
            for(int k=0; k<nRecover; k++) 
                if (k != missingSlice)
                    recoveredData[missingSlice][i] = recoveredData[missingSlice][i]^recoveredData[k][i];        
    }
            
    delete[] recoveredData;            
}
  
//---------------------------------------------------------------------------
