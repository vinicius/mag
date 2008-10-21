#ifndef CKPREPOSITORYUPDATER_HPP_
#define CKPREPOSITORYUPDATER_HPP_

#include "utils/c++/CkpReposManagerStub.hpp"
#include "CRDataServer.hpp"

class CkpRepositoryUpdater
{
private:
    int updateInterval;
    int ckpReposId;
    string outputDir;
    CkpReposManagerStub & ckpReposManagerStub;
        
    CkpRepositoryUpdater(CkpReposManagerStub & stub, int ckpReposId, int updateInterval, const string & outputDir);       
    static void *run( void *ptr );
    
    void removeFiles(CheckpointRemovalList removalList);
    void updateLoop ();
    
public:
	virtual ~CkpRepositoryUpdater();
    
    static void launchCkpRepositoryUpdater( CkpReposManagerStub & stub, int ckpReposId, int updateInterval, const string & outputDir );    
};

#endif /*CKPREPOSITORYUPDATER_HPP_*/
