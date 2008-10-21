#include "CkpRepositoryStub.hpp"
#include "../../../shared/utils/c++/SocketUtils.hpp"
#include "../../../shared/utils/c++/StringUtils.hpp"

#include <unistd.h>
#include <fcntl.h>
#include <cassert>

using namespace std;

//===========================================================================
// CkpRepositoryStub
//===========================================================================

int CkpRepositoryStub::connectToServer(string ipAddress, short port) {

  // Fills serverAddress with the server address and port
  struct sockaddr_in serverAddress;
  bzero((char *) &serverAddress, sizeof(serverAddress));
  serverAddress.sin_family      = AF_INET;
  serverAddress.sin_addr.s_addr = inet_addr(ipAddress.c_str());  
  serverAddress.sin_port        = htons(port);
 
  // Opens a TCP socket
  int sockfd = socket(AF_INET, SOCK_STREAM, 0);
  if ( sockfd < 0 ) {
    std::cerr << "CkpRepositoryStore::connectToServer --> cannot open stream socket." << std::endl;
    return -1;
  }

  // Connects to server
  int connectFlag = connect (sockfd, (struct sockaddr *)&serverAddress, sizeof(serverAddress));
  if ( connectFlag < 0 ) {
    std::cerr << "CkpRepositoryStore::connectToServer --> cannot connect to server." << std::endl;
    return -1;
  }
   
  return sockfd;
}

//---------------------------------------------------------------------------
  
int CkpRepositoryStub::transferData(string & ipAddress, short portNumber, void *data, long nbytes, int ckpNumber, int fragmentNumber) {

  int sockfd = this->connectToServer(ipAddress, portNumber);  
  if (sockfd < 0) return -1;
  assert(ckpNumber >= 0);
  
  // sending a new checkpoint
  SocketUtils::writeUint32(sockfd, (long)1); // Indicates that a store will be performed

  // Transfer the executionId
  SocketUtils::writeUint32(sockfd, (long)execId.length());
  SocketUtils::writeToStream(sockfd, execId.c_str(), execId.length());
  // Transfer the ckpNumber
  SocketUtils::writeUint32(sockfd, (long)ckpNumber);
  // Transfer the fragmentNumber
  SocketUtils::writeUint32(sockfd, (long)fragmentNumber);      
  // Transfer data to repository
  SocketUtils::writeUint32(sockfd, (long)nbytes);
  SocketUtils::writeToStream(sockfd, data, nbytes);

  // get the number of bytes written
  long writeCompleted = SocketUtils::readUint32(sockfd);
  
  close (sockfd);

  return writeCompleted;
}

//---------------------------------------------------------------------------

int CkpRepositoryStub::readData(string & ipAddress, short portNumber, void * & data, long & dataSize, int ckpNumber, int fragmentNumber) {

  int sockfd = this->connectToServer(ipAddress, portNumber);
  if (sockfd < 0) return -1;
    
  // Asking for checkpoint 'CkpNumber' 
  SocketUtils::writeUint32(sockfd, (long)2);
  
  // Transfer the executionId
  SocketUtils::writeUint32(sockfd, (long)execId.length());
  SocketUtils::writeToStream(sockfd, execId.c_str(), execId.length());
  // Transfer the ckpNumber
  SocketUtils::writeUint32(sockfd, (long)ckpNumber);
  // Transfer the fragmentNumber
  SocketUtils::writeUint32(sockfd, (long)fragmentNumber);        

  // Reads the file data from the stream to 'data'
  long tmpSize = SocketUtils::readUint32(sockfd);
  if (dataSize < 0) {
    dataSize = tmpSize;      
    data = malloc(dataSize);
  }

  SocketUtils::readFromStream(sockfd, data, dataSize);
  close (sockfd);  
  std::cerr << "Finished recovering data!!!" << std::endl;
  
  return tmpSize;
}

//---------------------------------------------------------------------------

void CkpRepositoryStub::getDataSize(string & ipAddress, short portNumber, long & dataSize, int ckpNumber) {

  sockfdTemp = this->connectToServer(ipAddress, portNumber);  
  // Asking for checkpoint 'CkpNumber' 
  SocketUtils::writeUint32(sockfdTemp, (long)2);
  SocketUtils::writeUint32(sockfdTemp, (long)execId.length());
  SocketUtils::writeToStream(sockfdTemp, execId.c_str(), execId.length());
  SocketUtils::writeUint32(sockfdTemp, (long)ckpNumber);

  // Reads the file data from the stream to 'data'
  dataSize = SocketUtils::readUint32(sockfdTemp);      
}

//---------------------------------------------------------------------------

void CkpRepositoryStub::getDataBody(string & ipAddress, short portNumber, void * data, long & dataSize, int ckpNumber) {

  std::cerr << "Getting " << dataSize << " bytes." << std::endl;

  SocketUtils::readFromStream(sockfdTemp, data, dataSize);
  close (sockfdTemp);  
  sockfdTemp = 0;
  
  std::cerr << "Finished recovering data!!!" << std::endl;
}

//---------------------------------------------------------------------------
