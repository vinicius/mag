#include "CkpRepositoryUpdater.hpp"
#include "utils/c++/FileUtils.hpp"
#include <iostream>
#include <sstream>
#include <pthread.h>

CkpRepositoryUpdater *ckpRepositoryUpdater;

CkpRepositoryUpdater::CkpRepositoryUpdater
( CkpReposManagerStub & stub, int ckpReposId, int updateInterval, const string & outputDir ) : ckpReposManagerStub( stub )
{
    this->outputDir = outputDir;
    this->updateInterval = updateInterval;
    this->ckpReposId = ckpReposId;
}

CkpRepositoryUpdater::~CkpRepositoryUpdater()
{
}

void CkpRepositoryUpdater::removeFiles(CheckpointRemovalList removalList) {
    
    std::cerr << "CkpRepositoryUpdater -> removing " << removalList.ckpNumber.size() << " fragments." << std::endl;        
    
    int nRemovals = removalList.ckpNumber.size();
    for (int fileIndex=0; fileIndex < nRemovals; fileIndex++) {
        string & executionId   = removalList.executionId[fileIndex];
        short & ckpNumber      = removalList.ckpNumber[fileIndex];
        short & fragmentNumber = removalList.fragmentNumber[fileIndex];       
        std::ostringstream ckpFileName;
        ckpFileName << this->outputDir << this->ckpReposId << "." << executionId << "_" << ckpNumber << "_" << fragmentNumber << ".ckp";
        int status = unlink(ckpFileName.str().c_str());
        if (status == -1) {
            std::cerr << "CkpRepositoryUpdater -> Error removing file '" << ckpFileName.str() << "'." << std::endl;
        }        
    }
    
}

void CkpRepositoryUpdater::updateLoop ( ) {
    
    while (true) {
        sleep(updateInterval);

        int availableSpace = FileUtils::getAvailableDiskSpace(this->outputDir.c_str());        
        int updateStatus = ckpReposManagerStub.updateRepositoryStatus(this->ckpReposId, availableSpace);
        std::cerr << "CkpRepositoryUpdater::updateLoop -> updateStatus=" << updateStatus << " availableSpace=" << availableSpace << "." << std::endl;
        if (updateStatus == 1)
            this->removeFiles( ckpReposManagerStub.getCheckpointRemovalList(this->ckpReposId) );
        else if (updateStatus == -1)                
            std::cerr << "CkpRepositoryUpdater::updateLoop -> Error updating status." << std::endl;
    }
}

void *CkpRepositoryUpdater::run( void *ptr ) {
    ckpRepositoryUpdater->updateLoop();
    return NULL;
}

void CkpRepositoryUpdater::launchCkpRepositoryUpdater( CkpReposManagerStub & stub, int ckpReposId, int updateInterval, const string & outputDir ) {
        
    ckpRepositoryUpdater = new CkpRepositoryUpdater( stub, ckpReposId, updateInterval, outputDir );

    pthread_t thread1;
    pthread_create( &thread1, NULL, CkpRepositoryUpdater::run, NULL);
    pthread_detach(thread1);
           
}
