#include "IDADecoder.h"

#include <cassert>
#include <iostream>
using namespace std;

IDADecoderRabin::IDADecoderRabin()
{
}

IDADecoderRabin::~IDADecoderRabin()
{
}

unsigned char *IDADecoderRabin::decodeData(enc_t **data, int dataSize, int *sliceNumbers, int nSlices, int nExtra, IDAAuxVectors *idaAuxVectors) {

    if (idaAuxVectors->getAuxVectors() == 0)    
        idaAuxVectors->generateVectors(nSlices+nExtra, nSlices); 
    enc_t **auxInverse = idaAuxVectors->calculateInverse(sliceNumbers, nSlices);
    
    int nSegments = (dataSize/nSlices==0) ? dataSize/nSlices : dataSize/nSlices+1;    
    unsigned char *decodedData = new unsigned char[dataSize];
    
    for (int i=0; i<nSegments; i++) { 
        unsigned char *decodedDataTemp = decodedData + i*nSlices;
        for (int j=0; j<nSlices; j++) {
            enc_t d = 0;
            for (int k=0; k<nSlices; k++)            
                 d = psum(d, pmul(auxInverse[j][k], data[k][i])); // %p ?
            decodedDataTemp[j] = d; // %p ?
        }
    }
       
    return decodedData;
}
