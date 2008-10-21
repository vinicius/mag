#include "../CkpUtils.hpp"
#include "../CkpLogger.hpp"
#include "CkpRepositoryStub.hpp"
#include "CkpRepositoryStore.hpp"
#include "../../../shared/utils/c++/SocketUtils.hpp"
#include "../../../shared/utils/c++/StringUtils.hpp"

#include <iostream>
#include <fstream>
#include <sstream>
#include <cassert>
#include <string>

#include <unistd.h>
#include <fcntl.h>

//---------------------------------------------------------------------------

CkpRepositoryStore::CkpRepositoryStore(const Config & ckpConfig) {

    try {
        string ckpPath = ckpConfig.getConf("location");
        fileStoreBufferLock = new pthread_mutex_t;
        pthread_mutex_init(fileStoreBufferLock, NULL);
        ckpFileStore = new CkpFileStore(ckpPath, 0);
        ckpFileStore->setReleaseMemory(false);
        ckpFileStore->setBufferLock(fileStoreBufferLock);
    }
    catch(...) { // NoSuchConfigException
        ckpFileStore = 0;
        fileStoreBufferLock = 0;
    }

    execId = ckpConfig.getConf("execId");      
    ckpReposStub = new CkpRepositoryStub(execId);    
    
    Config corbaConfig("asct.conf"); 
    ckpReposManagerStub = new CkpReposManagerStub(corbaConfig);      
    ckpInfo = 0;
}

CkpRepositoryStore::~CkpRepositoryStore() {
    if (fileStoreBufferLock) delete fileStoreBufferLock;
    if (ckpInfo) delete ckpInfo;
    if (ckpFileStore) delete ckpFileStore;    
    delete ckpReposStub;
    delete ckpReposManagerStub;  
    
}

//---------------------------------------------------------------------------
int CkpRepositoryStore::getLastCkpNumber() {

    ckpInfo = new CkpInfo();
    *ckpInfo = ckpReposManagerStub->getLastCkpInfo(execId);        

    ostringstream out1;
    out1 << "CkpRepositoryStore::recoverCkpData -> last ckpNumber is " << ckpInfo->ckpNumber << ".";        
    ckpLogger.debug(out1.str());        
    
    return ckpInfo->ckpNumber;
    
}    

//---------------------------------------------------------------------------
void CkpRepositoryStore::saveData(void *data, long dataSize, int ckpNumber) {

    unsigned char **encData = NULL;
    int fragmentSize;
    string * checksumList = NULL;
        
    /** encodes 'data' into fragments and put into 'encData' and their size in 'fragmentSize' */
    this->encodeData(data, dataSize, encData, fragmentSize, checksumList);

    /** gets the list of checkpoints repositories to store the fragments */
    CkpReposAddress ckpReposAddress = ckpReposManagerStub->getCkpRepos(execId, ckpNumber, checksumList, nFragments+1, dataSize);
    
    if (ckpFileStore) {
        ostringstream out;
        out << "CkpRepositoryStore::saveCkpData --> saving to local directory.";
        ckpLogger.debug(out.str());
        ckpFileStore->saveCkpData(data, dataSize, ckpNumber);        
    }
        
    for (int fragment=0; fragment < nFragments; fragment++) {
        ostringstream out;                            
        out << "CkpRepositoryStore::saveCkpData --> " << ckpReposAddress.ipAddress[fragment] << " " << ckpReposAddress.port[fragment];
        ckpLogger.debug(out.str());
        ckpReposStub->transferData(ckpReposAddress.ipAddress[fragment], ckpReposAddress.port[fragment], encData[fragment], fragmentSize, ckpNumber, fragment);
        cerr << "ok1!" << endl;
    }      
    
    cerr << "ok2" << endl;
                
    /** waits the local file storage thread from FileStore to finish */
    if (fileStoreBufferLock) {
        pthread_mutex_lock(fileStoreBufferLock);
        pthread_mutex_unlock(fileStoreBufferLock);
    }
    
    cerr << "Deleting data!" << endl;
    
    this->deleteEncodedData(encData, checksumList);

    ostringstream out;
    out << "Finished saving data!";
    ckpLogger.debug(out.str());
        
    cerr << "Finished saving data!" << endl;
}

//---------------------------------------------------------------------------

void CkpRepositoryStore::recoverCkpData(void * & data, long & dataSize, int ckpNumber) {

    if (ckpInfo == 0) this->getLastCkpNumber();    
    ckpNumber = ckpInfo->ckpNumber;

    //unsigned char **recoveredData = new unsigned char *[nRecover];
    unsigned char ** recoveredData = NULL;
    long fragmentSize;        
    this->prepareRecoveredData(data, recoveredData, ckpInfo->ckpSize, fragmentSize);
        
    /** Tries to obtain checkpointing data from the local filesystem */            
    bool recoveredLocal = false;                 
    if (ckpFileStore) {

        ostringstream out;
        out << "recoveringData from local filesystem.";
        ckpLogger.debug( out.str() );
        
        // TODO: check if file exists (currently fileStore just dies)
        ckpFileStore->recoverCkpData(data, dataSize, ckpNumber);
        
        string initialChecksum = ckpInfo->checksum[ nFragments ];
        string finalChecksum = CkpUtils::getChecksum ((unsigned char *)data, dataSize);                   
        if (initialChecksum.compare( finalChecksum ) == 0)
            recoveredLocal = true;
    }           
        
    /** Downloads nSlices slices from the returned repositories */    
    if (recoveredLocal == false) { 
        int *fragmentNumbers = new int[nRecover];        
        //long fragmentSize;
        int nRetrieved = 0;
        vector<int> missingFragments;

        for (int fragment=0, missingPos=0; nRetrieved < nRecover && fragment < nFragments; fragment++) {
            
            int currentFragment = -1;
            if (fragment < nRecover)
                currentFragment = fragment;
            else
                currentFragment = missingFragments[missingPos];

            fragmentNumbers[currentFragment] = -1;
  
            ostringstream out;
            out << "recoveringData from nRetrieved " << nRetrieved << " fragment " << fragment  << " at " << ckpInfo->ipAddress[fragment] << ":" << ckpInfo->port[fragment];
            ckpLogger.debug( out.str() );

            cerr << "recoveringData from nRetrieved " << nRetrieved << " fragment " << fragment  << " at " << ckpInfo->ipAddress[fragment] << ":" << ckpInfo->port[fragment] << endl;

            long bytesRead = ckpReposStub->readData( ckpInfo->ipAddress[fragment], ckpInfo->port[fragment],
                                ((void **)recoveredData)[currentFragment], fragmentSize, ckpNumber, fragment);

            cerr << "data read " << bytesRead << " of " << fragmentSize << " and " << ckpInfo->ckpSize << "." << endl;

            // Verify for each dowloaded fragment if it is correct
            bool fragmentRecovered = false;
            if ( bytesRead == fragmentSize ) {
                string fragmentChecksum = CkpUtils::getChecksum (recoveredData[currentFragment], fragmentSize);
                cerr << fragmentChecksum << " " << ckpInfo->checksum[fragment] << endl;
                if (fragmentChecksum.compare( ckpInfo->checksum[fragment] ) == 0) {
                    fragmentNumbers[currentFragment] = fragment;
                    nRetrieved++;
                    fragmentRecovered = true;
                    if (fragment >= nRecover)
                        missingPos++;
                }
                else
                    ckpLogger.debug("Error in slice checksum. Is " + fragmentChecksum + " and should be " + ckpInfo->checksum[fragment] + ".");
            }
            if (fragmentRecovered == false) {
                if (fragment < nRecover)
                    missingFragments.push_back(fragment);
            }

        }  

        cerr << "fragmentNumber: ";
        for (int i=0; i<nRecover; i++)
            cerr << fragmentNumbers[i] << " ";
        cerr << endl;
            
//        for (int fragment=0; nRetrieved < nRecover && fragment < nFragments; fragment++) {
//            ostringstream out;
//            out << "recoveringData from nRecovered " << nRetrieved << " slice " << fragment  << " at " << ckpInfo->ipAddress[fragment] << ":" << ckpInfo->port[fragment];
//            ckpLogger.debug( out.str() );
//
//            cerr << "reading data from stream " << fragment << endl;
//
//            long bytesRead = ckpReposStub->readData(
//                ckpInfo->ipAddress[fragment], ckpInfo->port[fragment], 
//                ((void **)recoveredData)[nRetrieved], fragmentSize, ckpNumber);
//
//            cerr << "data read " << bytesRead << " of " << fragmentSize << " and " << ckpInfo->ckpSize << "." << endl;
//
//            // Verify for each dowloaded fragment if it is correct
//            if ( bytesRead == fragmentSize ) {
//                string fragmentChecksum = CkpUtils::getChecksum (recoveredData[nRetrieved], fragmentSize);
//                cerr << fragmentChecksum << " " << ckpInfo->checksum[fragment] << endl;
//                if (fragmentChecksum.compare( ckpInfo->checksum[fragment] ) == 0) {
//                    fragmentNumbers[nRetrieved] = fragment;
//                    nRetrieved++;
//                }
//                else
//                    ckpLogger.debug("Error in slice checksum. Is " + fragmentChecksum + " and should be " + ckpInfo->checksum[fragment] + ".");
//            }
//        }  
        
        cerr << "finished reading data " << nRetrieved << " " << nRecover << endl;
        
        assert( nRetrieved == nRecover );
        
        cerr << "processing data." << endl;        
        
        /** reconstruct the data from fragments */
        this->processData(data, dataSize, recoveredData, fragmentNumbers, fragmentSize);

        delete[] fragmentNumbers;        
    }

    // TODO: check if the class correctly clear its memory
    // delete ckpInfo;
    
}
