#include "IDAEncoder.h"

#include <cassert>
#include <iostream>
using namespace std;


IDAEncoderRabin::IDAEncoderRabin()
{
}

IDAEncoderRabin::~IDAEncoderRabin()
{
}

/**
 * TODO: Current Impl considers dataSize and nSlices are exactly divisible
 * */
enc_t **IDAEncoderRabin::encodeData(unsigned char *data, int dataSize, int nSlices, int nExtra, IDAAuxVectors *idaAuxVectors) {
    
    enc_t **auxVectors = idaAuxVectors->generateVectors(nSlices+nExtra, nSlices);    
        
    int nSegments = (dataSize%nSlices == 0) ? dataSize/nSlices : dataSize/nSlices+1;
    
    enc_t **encData = new enc_t *[nSlices + nExtra];
    for (int i=0; i<nSlices + nExtra; i++)
        encData[i] = new enc_t[nSegments]; // Aumentar em 1 todos dataSize/nSlices
        
    for (int i=0; i<nSlices + nExtra; i++) // encData[i]
        for (int j=0; j<nSegments; j++) { // c(i,k) = a(i).data(j*m)
            enc_t encDataTemp = 0;
            for (int k=0; k<nSlices ; k++) {
                encDataTemp = psum(encDataTemp, pmul(auxVectors[i][k],data[(j*nSlices)+k])); // %p ?
            }
            encData[i][j] = encDataTemp; // %p ?   
        }     
    
    return encData;
}
