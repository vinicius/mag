#include "IDADecoder.h"

#include <cassert>
#include <iostream>
#include <iomanip>
using namespace std;

IDADecoderIdentity::IDADecoderIdentity()
{
}

IDADecoderIdentity::~IDADecoderIdentity()
{
}

// TODO: someone has to delete data!
unsigned char *IDADecoderIdentity::decodeData(enc_t **data, int dataSize, int *sliceNumbers, int nSlices, int nExtra, IDAAuxVectors *idaAuxVectors) {
    
    if (idaAuxVectors->getAuxVectors() == 0)    
        idaAuxVectors->generateIdentityG(nSlices, nExtra);                  
    enc_t **auxInverse = idaAuxVectors->calculateInverse(sliceNumbers, nSlices);
  
    int nSegments = (dataSize/nSlices==0) ? dataSize/nSlices : dataSize/nSlices+1;    
    unsigned char *decodedData = new unsigned char[dataSize];
  
    int *posVector = new int[nSlices];
    for (int i=0; i<nSlices; i++) {
        int usedSum = 0;
        int usedPos = -1;
        for (int j=0; j<nSlices; j++)
            if (auxInverse[i][j] > 0) {
                usedSum += auxInverse[i][j];
                usedPos = j;
            }
        if (usedSum == 1) posVector[i] = usedPos;
        else posVector[i] = -1;
        
    }
  
    // IdaDecoderIdentity   
    for (int segment=0; segment<nSegments; segment++) { 
        unsigned char *decodedDataTemp = decodedData + segment*nSlices;
        for (int slicePos=0; slicePos<nSlices; slicePos++) {
            if (posVector[slicePos] > 0)
               decodedDataTemp[slicePos] = data[posVector[slicePos]][segment];
            else {
                enc_t d = 0;
                for (int k=0; k<nSlices; k++)            
                    d = psum(d, pmul(auxInverse[slicePos][k], data[k][segment])); // %p ?
                decodedDataTemp[slicePos] = d; // %p ?
            }
        }
    }
        
    delete[] posVector;
       
    return decodedData;
}

