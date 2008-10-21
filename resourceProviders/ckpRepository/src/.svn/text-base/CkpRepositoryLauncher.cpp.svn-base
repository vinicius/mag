#include "CRDataServer.hpp"
#include "CkpRepositoryUpdater.hpp"
#include "utils/c++/NoSuchConfigException.hpp"

#include <cstdlib>
#include <string>
#include <iostream>

int main(int argc, char **argv) {

    Config config("ckpRepos.conf");
    CkpReposManagerStub ckpReposManagerStub(config);
    int updateInterval = 30; // in seconds
        
    CRDataServer *dataServer = NULL;    
    try {        
        string port = config.getConf("port");                
        dataServer = new CRDataServer(ckpReposManagerStub, atoi(port.c_str()));
        CkpRepositoryUpdater::launchCkpRepositoryUpdater
                (ckpReposManagerStub, dataServer->getCkpReposId(), updateInterval, dataServer->getOutputDir());
                                 
        dataServer->startServer();
        delete dataServer;                
    }
    catch (NoSuchConfigException e) {
        std::cerr << "[CRITICAL] CkpRepositoryLauncher::main -> field 'port' from config file not found! Exiting..." << std::endl;
        exit(-1);
    }

    return 0;
}
