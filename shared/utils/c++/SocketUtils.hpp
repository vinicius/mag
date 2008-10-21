// SocketUtils.cpp

#ifndef SOCKET_UTILS_HPP
#define SOCKET_UTILS_HPP

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>

#include <iostream>

class SocketUtils {
public:

    //---------------------------------------------------------------------------
    static void writeUint32(int sockfd, long data) {

        uint32_t dataNl = htonl( (uint32_t)data );
        if (write(sockfd, &dataNl, sizeof(uint32_t)) <= 0) 
        std::cerr << "SocketUtils::writeUint32 --> " 
		          << "error while writing to stream (" << data << ")." << std::endl;
    
    }

    //---------------------------------------------------------------------------
    static long readUint32(int sockfd) {

        uint32_t dataSizeNl;
        if (read(sockfd, &dataSizeNl, sizeof(uint32_t)) < (signed)sizeof(uint32_t)) 
        std::cerr << "SocketUtils::readUint32 --> " 
		          << "error while reading uint32 from stream." << std::endl;
        return (long) ntohl( dataSizeNl );  
        
    }

    //---------------------------------------------------------------------------
    static long readFromStream(int sockfd, void *buffer, int dataSize) {

        char *data = (char *)buffer;
    
        // Read checkpoint data from the stream
        int nleft = dataSize;
        while ( nleft > 0 ) {
      
            int nread = read(sockfd, data, nleft);    
            if (nread <= 0) { // ERROR!!!
                std::cerr << "SocketUtils::readFromStream --> error reading from stream. " 
		                  << "Read " << dataSize - nleft + nread << " of "
		                  << dataSize << " bytes." << std::endl;
	           return dataSize - nleft + nread;
            }      
      
            nleft -= nread;
            data  += nread;
        }

        return dataSize;
    
  }
  
  //---------------------------------------------------------------------------
  static long writeToStream(int sockfd, const void *buffer, int dataSize) {

        char *data = (char *)buffer;
    
        // Writes checkpoint data to the stream
        int nleft = dataSize;
        while ( nleft > 0 ) {
      
            int nwritten = write(sockfd, data, nleft);    
            if (nwritten <= 0) { // ERROR!!!
                std::cerr << "SocketUtils::writeToStream --> error writing to stream. " 
		                  << "Wrote " << dataSize - nleft + nwritten << " of " 
		                  << dataSize << " bytes." << std::endl;
	       return dataSize - nleft + nwritten;
        }      
      
        nleft -= nwritten;
        data  += nwritten;
    }
    
    return dataSize;
  }

  //---------------------------------------------------------------------------
};

#endif
