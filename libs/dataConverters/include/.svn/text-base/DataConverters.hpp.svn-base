#include <vector>
#include <iostream>

#include "CheckpointingLib.hpp"

#ifndef DATACONVERTERS_HPP
#define DATACONVERTERS_HPP

#define dataconverter_t long (*)(void *, const void *)
#define dataconverter_t1(x) long (*x)(void *, const void *)
#define datasizes_t void (*)(ckp_datasize_t *, ckp_datasize_t *)
#define datasizes_t1(x) void (*x)(ckp_datasize_t *, ckp_datasize_t *)

class DataConverters {

  int dstArch;  
  int srcArch;  

  std::vector<dataconverter_t> *dataConverter;
  std::vector<std::vector<dataconverter_t> *> dataConverterVector;

  std::vector<datasizes_t> *dataSizes;
  std::vector<std::vector<datasizes_t> *> dataSizesVector;
  
  void initializeDataConverter(int srcArch);

public:

  DataConverters(int arch) : dstArch(arch) {
    dataConverterVector.resize(N_ARCH+1, NULL);
    dataSizesVector.resize(N_ARCH+1, NULL);
    dataConverter = NULL;
    dataSizes = NULL;
  }
  
  ~DataConverters();
  
  // TODO: This should be done transparently at the contructor
  static ckp_arch_t getProcessorArchitecture();
  
  ckp_arch_t getArchitecture() { return dstArch; }

  void setDataConverter (int srcArch) {

    if (this->srcArch == srcArch)
      return;
    
    this->srcArch = srcArch;
    if (dataConverterVector[srcArch] == NULL) 
      initializeDataConverter(srcArch);

    dataConverter = dataConverterVector[srcArch];
    dataSizes     = dataSizesVector[srcArch];

  }

  /**  */
  void getSizes (ckp_datasize_t *dstSize, ckp_datasize_t *srcSize, unsigned long type) {
    
    (*(*dataSizes)[type])(dstSize, srcSize);
    
  }
  
  /** nbytes represents the number of bytes from the source */
  int readData (void *dst, const void *src, unsigned long type) {
    
    return (*(*dataConverter)[type])(dst, src);
    
  }
  
  /** nbytes represents the number of bytes from the source */
  int readDataVector (void *dst, const void *src, int nbytes, unsigned long type) {

    if (srcArch == dstArch) {
      memcpy (dst, src, nbytes);
      return nbytes;
    }
    
    int bytesRead = 0;
    dataconverter_t1(conv) = (*dataConverter)[type]; 

    ckp_datasize_t srcSize, dstSize; 
    (*(*dataSizes)[type])(&dstSize, &srcSize);
 
    for ( int i=0; bytesRead < nbytes ; i++)
      bytesRead += (*conv)((char *)dst + i*dstSize, (char *)src + i*srcSize);
    
    return bytesRead;
  }
  
};

#endif // DATACONVERTERS_HPP
