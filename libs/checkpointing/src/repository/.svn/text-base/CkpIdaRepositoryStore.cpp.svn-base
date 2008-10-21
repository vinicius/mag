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

CkpIdaRepositoryStore::CkpIdaRepositoryStore(const Config & ckpConfig) : CkpRepositoryStore(ckpConfig) {

    try {
       nRecover = StringUtils::string2int(ckpConfig.getConf("nFragments"));        
    }
    catch(...) { // NoSuchConfigException
       nRecover = 2;
    }

    try {
       nFragments = StringUtils::string2int(ckpConfig.getConf("nExtra")) + nRecover;        
    }
    catch(...) { // NoSuchConfigException
       nFragments = 3;
    }

    ckpReposManagerStub->setCkpReposMode(execId, ida, nRecover, nFragments - nRecover);
}

//---------------------------------------------------------------------------

CkpIdaRepositoryStore::~CkpIdaRepositoryStore() {
}

//---------------------------------------------------------------------------

void CkpIdaRepositoryStore::encodeData
(void *data, long dataSize, unsigned char ** & encData, int & fragmentSize, string * & checksumList) {

    encData = idaImpl.encodeData((unsigned char *)data, dataSize, nRecover, nFragments-nRecover);
    fragmentSize = (dataSize%nRecover == 0) ? dataSize/nRecover : dataSize/nRecover+1;    

    /** Evaluates checksum list */
    checksumList = new string[nFragments+1];
    for (int i=0; i<nFragments; i++) {
        checksumList[i] = CkpUtils::getChecksum(encData[i], fragmentSize);
    }
    checksumList[nFragments] = CkpUtils::getChecksum((unsigned char *)data, dataSize);
}

void CkpIdaRepositoryStore::deleteEncodedData
(unsigned char ** encData, string * checksumList) {
    
    for (int i=0; i<nFragments; i++)
        delete[] encData[i];
    delete[] encData;
    delete[] checksumList;
    
}

//---------------------------------------------------------------------------

void CkpIdaRepositoryStore::prepareRecoveredData
(void * & data, unsigned char ** & recoveredData, long ckpSize, long & fragmentSize) {

    fragmentSize = (ckpSize%nRecover == 0) ? ckpSize/nRecover : ckpSize/nRecover+1;
    data = NULL; // is allocated later in processData call to idaImpl.decodeData

    recoveredData = new unsigned char *[nRecover];
    for (int fragment=0; fragment < nRecover; fragment++)
        recoveredData[fragment] = new unsigned char[fragmentSize];
}

void CkpIdaRepositoryStore::processData
(void * & data, long & dataSize, unsigned char **recoveredData, int *fragmentNumbers, long fragmentSize) {

    data = idaImpl.decodeData(recoveredData, fragmentSize*nRecover, fragmentNumbers, nRecover, nFragments-nRecover);
    dataSize = fragmentSize*nRecover;
    
    for (int fragment=0; fragment<nRecover; fragment++)
        delete[] recoveredData[fragment];
    delete[] recoveredData;
        
}
  
//---------------------------------------------------------------------------

