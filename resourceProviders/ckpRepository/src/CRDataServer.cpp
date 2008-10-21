// CRDataServer.cpp

#include "CRDataServer.hpp"
#include "utils/c++/SocketUtils.hpp"
#include "utils/c++/FileUtils.hpp"
// gethostbyname

#include <fcntl.h>

#include <iostream>
#include <fstream>
#include <sstream>

#include <sys/types.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <strings.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>

struct DataInfo {
  char *data;  
  long dataSize;
  char *execId;
  long execIdSize;  
  int ckpNumber;
  int fragmentNumber;
};

//------------------------------------------------------------------------------
CRDataServer::CRDataServer(CkpReposManagerStub & stub, const int & port) : ckpReposStub(stub) {
    
    this->port = port;
    this->maxPort = this->port + 100;
  
    // sets and creates output dir
    this->outputDir = "/tmp/ckpRep/";
    mkdir(outputDir.c_str(), 0700);
        
    // obtains the ipAddress of the host
    system("hostname -i > machineIp.dat");
    ifstream ipFile("machineIp.dat");
    ipFile >> this->ipAddress;
    ipFile.close();
      
    // Initializes Server  
    this->setupServer();      
}

//------------------------------------------------------------------------------
void CRDataServer::readStream(int sockfd) {

  long methodType = SocketUtils::readUint32(sockfd);

  // Store checkpoint data --> ckpNumber dataSize data
  if (methodType == 1) { 

    DataInfo dataInfo;    
    // Reads the executionId from the stream to 'data'
    dataInfo.execIdSize = (long)SocketUtils::readUint32(sockfd);        
    dataInfo.execId = new char[dataInfo.execIdSize+1];
    dataInfo.execId[dataInfo.execIdSize]=0;     
    SocketUtils::readFromStream(sockfd, dataInfo.execId, dataInfo.execIdSize);
    
    // Reads ckpNumber from the stream
    dataInfo.ckpNumber = (int)SocketUtils::readUint32(sockfd);
    // Reads fragmentNumber from the stream (OK)
    dataInfo.fragmentNumber = (int)SocketUtils::readUint32(sockfd);
      
    // Reads the file data from the stream to 'data'
    dataInfo.dataSize = (long)SocketUtils::readUint32(sockfd);
    dataInfo.data = new char[dataInfo.dataSize]; 
    SocketUtils::readFromStream(sockfd, dataInfo.data, dataInfo.dataSize);
   
    // Saves data to filesystem -----------------------
    std::ostringstream ckpFileName;
    ckpFileName << outputDir << ckpReposId << "." << dataInfo.execId << "_" << dataInfo.ckpNumber << "_" << dataInfo.fragmentNumber << ".ckp";
    int outFile = creat(ckpFileName.str().c_str(),0666); // change to 0600
    write(outFile, dataInfo.data, dataInfo.dataSize);
    close(outFile);
    // ------------------------------------------------
    
    std::cout << "Saved " << dataInfo.dataSize << " bytes to " << ckpFileName.str() << std::endl;                 
    int writeCompleted = 1;
    SocketUtils::writeUint32(sockfd, writeCompleted);

    // Notifies the ckpReposManager about the newly stored checkpoint
    int freeSpace = FileUtils::getAvailableDiskSpace(outputDir.c_str());
    ckpReposStub.notifyCkpStored(ckpReposId, dataInfo.execId, dataInfo.ckpNumber, dataInfo.fragmentNumber, freeSpace);
       
    delete[] dataInfo.data;
  }
  
  // Recover checkpointing data --> ckpNumber | dataSize data
  else if (methodType == 2) {

    // Reads the executionId from the stream to 'data'
    long execIdSize = (long)SocketUtils::readUint32(sockfd);        
    char *execId = new char[execIdSize+1];
    execId[execIdSize]=0;      
    SocketUtils::readFromStream(sockfd, execId, execIdSize);
    // Reads the ckpNumber
    long ckpNumber = SocketUtils::readUint32(sockfd);
    // Reads fragmentNumber from the stream (OK)
    long fragmentNumber = SocketUtils::readUint32(sockfd);
    
    // Open the correct file and put in buffer --------
    std::ostringstream ckpFileName;
    ckpFileName << outputDir << ckpReposId << "." << execId << "_" << ckpNumber << "_" << fragmentNumber << ".ckp";
    int inFile = open(ckpFileName.str().c_str(), O_RDONLY);
    if (inFile == -1) 
      std::cerr << "Couldn't open file " << ckpFileName.str() << "." << std::endl;
    struct stat *fileStat = new struct stat;
    fstat(inFile, fileStat);
    long dataSize = (long)fileStat->st_size;
    delete fileStat;
    char *data = new char[dataSize];
    read(inFile, data, dataSize);
    // -------------------------------------------------
    
    std::cout << "Returning Data from " << ckpFileName.str() << std::endl;
        
    // Writes the number of bytes of the file
    SocketUtils::writeUint32(sockfd, (long)dataSize);
    // Transfer data to repository
    SocketUtils::writeToStream(sockfd, data, dataSize);
    
    delete[] data;
  }
}

//------------------------------------------------------------------------------

void CRDataServer::setupServer() {
  
  // Open a TCP socket
  this->sockfd = socket(AF_INET, SOCK_STREAM, 0);
  if ( sockfd < 0 )
    std::cerr << "CRDataServer::startServer --> cannot open stream socket." << std::endl;

  // Binds the server address
  struct sockaddr_in serverAddress;
  bzero((char *) &serverAddress, sizeof(serverAddress));
  serverAddress.sin_family      = AF_INET;
  serverAddress.sin_addr.s_addr = htonl(INADDR_ANY);  
  serverAddress.sin_port        = htons(port);
  
  bool bound = false;
  while (bound == false && port < maxPort) {  
    if ( bind(sockfd, (struct sockaddr *)&serverAddress, sizeof(serverAddress)) == 0 )
      bound = true;
    else
      serverAddress.sin_port = htons(++port);
  }
            
  int availableSpace = FileUtils::getAvailableDiskSpace(this->outputDir.c_str());  
  int tempCkpReposId = ckpReposStub.registerCkpRepos(this->ipAddress, this->port, availableSpace);
  std::cerr << "ckpRepository: Listening on ip:" << ipAddress << " port:" << port << std::endl;      
  
  if (tempCkpReposId >= 0 ) {        
    // removes old checkpoint data from output directory
    ostringstream rmStream;
    rmStream << "rm " << outputDir << "*.ckp 2> /dev/null"; 
    system( rmStream.str().c_str() );            
    
    this->ckpReposId = tempCkpReposId;
    std::cout << "ckpRepository started with id " << ckpReposId  << ". Removing old checkpoint data." << std::endl;
  }
  else{
    this->ckpReposId = -tempCkpReposId;
    std::cout << "ckpRepository started with id " << ckpReposId  << ". Keeping old checkpoint data." << std::endl;
  }
  
  std::cout << "ckpRepository: saving data at '" << outputDir << "'." << std::endl;
   
  listen(sockfd, 128);          
}

//----------------------------------------------------------------------------

void CRDataServer::startServer() {
    
  while(true) {
    
    // Accepts the connection from a client
    struct sockaddr_in clientAddress;
    socklen_t clientAddressLength = sizeof(clientAddress);
    int newsockfd = 
      accept (sockfd, (struct sockaddr *)&clientAddress, &clientAddressLength);
    
    if (newsockfd < 0)
      std::cerr << "CRDataServer::startServer --> error accepting connection." << std::endl;
    
    // Forks a new process for concurrent processing
    int pidMid = fork();
    if (pidMid == 0) {  //mid
      int pidBotton = fork();
      if (pidBotton == 0) {  //son
        close (sockfd); // close original socket
        this->readStream(newsockfd);
        exit(0);
      }
      else 
	    exit(0);
    }
    else
      wait(NULL);

    // Parent process: close the new socket
    close (newsockfd); 
  }
}

//------------------------------------------------------------------------------
