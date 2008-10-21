#include "LocalParityEncoder.h"
#include <cstring>

#include <iostream>
using namespace std;
//------------------------------------------------------------------------------
enc_t **LocalParityEncoder::encodeData(unsigned char *data, int dataSize, int nSlices) {

    int sliceSize = (dataSize/nSlices==0) ? dataSize/nSlices : dataSize/nSlices+1;    

    enc_t **encData = new enc_t *[nSlices + 1];
    for (int i=0; i<nSlices + 1; i++)
        encData[i] = data+(i*sliceSize);
    encData[nSlices] = new enc_t[sliceSize];
                  
        for (int i=0; i<sliceSize; i++) {
            encData[nSlices][i] = encData[0][i];
            for(int k=1; k<nSlices; k++)
                encData[nSlices][i] = encData[nSlices][i]^encData[k][i];
        }
                
        
    //        for (int i=0; i<sliceSize/4; i++) {
    //	  encData[nSlices][i] = encData[0][i];
    //	  unsigned long *ckpDataLong = (unsigned long *)encData[i];
    //	  for(int k=1; k<nProcesses; k++) {
    //	    encData[nSlices][i] = encData[nSlices][i]^ckpDataLong[k];
    //	  }
    //	}
            
    return encData;
}

//------------------------------------------------------------------------------

unsigned char *LocalParityEncoder::decodeData(enc_t **data, int dataSize, int *sliceNumbers, int nSlices) {

    unsigned char *ckpData = new unsigned char[dataSize];

    int sliceSize = (dataSize/nSlices==0) ? dataSize/nSlices : dataSize/nSlices+1;    

    for (int i=0, slice=0; i < nSlices; i++, slice++) {    
      if (sliceNumbers[i] == slice)
        memcpy(ckpData+sliceSize*slice, data[slice], sliceSize);
      else {
        for (int pos=0; pos<sliceSize; pos++) {
          ckpData[slice*sliceSize + pos] = data[nSlices][pos];  
          for(int k=0; k<slice; k++) 
            ckpData[slice*sliceSize + pos] = ckpData[slice*sliceSize+pos]^ckpData[k*sliceSize+pos];
          for(int k=slice+1; k<nSlices; k++) 
            ckpData[slice*sliceSize + pos] = ckpData[slice*sliceSize+pos]^ckpData[k*sliceSize+pos];
        }        
        i--;
      }          
    }       
    return ckpData;
}

//------------------------------------------------------------------------------
