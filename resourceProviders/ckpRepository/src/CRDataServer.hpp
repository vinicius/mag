// CRDataServer.hpp

#ifndef CR_DATA_SERVER_HPP
#define CR_DATA_SERVER_HPP

#include "utils/c++/CkpReposManagerStub.hpp"
#include <string>

class CRDataServer {

private:
  CkpReposManagerStub & ckpReposStub;
  short port;  
  std::string ipAddress;  
  std::string outputDir;
  int ckpReposId;
  
  short maxPort;
  
  int sockfd;        

  void readStream(int sockfd);
  void setupServer();
public:
  CRDataServer(CkpReposManagerStub & stub, const int & port);
  
  string getIpAddress () {return ipAddress;}
  int getPort () {return port;}
  
  string getOutputDir () {return outputDir;}
  int getCkpReposId () {return ckpReposId;}
  
  void startServer();
};


#endif // CR_DATA_SERVER_HPP
