#include <iostream>
#include <iomanip>
#include <cstdlib>
#include <vector>
#include <cassert>
#include <cmath>

#include <sys/time.h>
#include <sys/times.h>

#include "IDAEncoder.h"
#include "IDAImpl.h"
#include "LocalParityEncoder.h"
#include "IDADecoder.h"
#include "IDADefinitions.h"
#include "IDAAuxVectors.h"

using namespace std;

//int p = 257;
//int p = 1000000000;

void testEncoding (IDAImpl & idaImpl, int size, int nSlices, int nExtras, int sliceNumbers[]);

int main(int argc, char **argv) {
              
    IDAImpl idaImpl;
                    
    int nSlices = 4;
    int nExtras = 2;    
    int sliceNumbers[] = {0,1,2,3};
    testEncoding(idaImpl, 120, nSlices, nExtras, sliceNumbers);

    // missing 3
    sliceNumbers[0] = 0;
    sliceNumbers[1] = 1;
    sliceNumbers[2] = 2;    
    sliceNumbers[3] = 4;    
    testEncoding(idaImpl, 120, nSlices, nExtras, sliceNumbers);

    // missing 2,3
    sliceNumbers[0] = 0;
    sliceNumbers[1] = 1;
    sliceNumbers[2] = 4;    
    sliceNumbers[3] = 5;    
    testEncoding(idaImpl, 120, nSlices, nExtras, sliceNumbers);

    // missing 0,1
    sliceNumbers[0] = 2;
    sliceNumbers[1] = 3;
    sliceNumbers[2] = 4;    
    sliceNumbers[3] = 5;    
    testEncoding(idaImpl, 120, nSlices, nExtras, sliceNumbers);

    // missing 0,3
    sliceNumbers[0] = 1;
    sliceNumbers[1] = 2;
    sliceNumbers[2] = 4;    
    sliceNumbers[3] = 5;    
    testEncoding(idaImpl, 120, nSlices, nExtras, sliceNumbers);

    
//    int sliceNumbers[] = {0,1,2,3,4,5,8,9};    
//    int sliceNumbers[][9] = {{0,1,2,3,4,5,6,7,9}, 
//                             {0,1,2,3,4,5,6,8,9}, 
//                             {0,1,2,3,4,5,7,8,9}, 
//                             {0,1,2,3,4,6,7,8,9}, // 6x4
//                             {0,1,2,3,5,6,7,8,9}}; 

//    int nSlices[] = {9,8,7,6,5};
//    int nExtras[] = {1,2,3,4,5};
                  
//    int sizes[] = {10,20,50,100,200};

//    for (int size=3; size<4; size++) 
//      for (int exp=1; exp<3; exp++) 
//        testEncoding(idaImpl, sizes[size], nSlices[exp], nExtras[exp], sliceNumbers[exp]);

    return 0;
}

void testEncoding (IDAImpl & idaImpl, int size, int nSlices, int nExtras, int sliceNumbers[]) {

    LocalParityEncoder localParityEncoder;

    int dataRandSize = size;
    unsigned char *dataRand = new unsigned char[dataRandSize];
    for (int i=0; i<dataRandSize; i++)
        dataRand[i] = rand()%256; 

    enc_t **dataRandEnc = idaImpl.encodeData(dataRand, dataRandSize, nSlices, nExtras);
    
    enc_t **data1 = new enc_t *[nSlices];
    for (int i=0; i<nSlices; i++)
        data1[i] = dataRandEnc[sliceNumbers[i]];    
                
    unsigned char *dataRandFinal = idaImpl.decodeData(data1, dataRandSize, sliceNumbers, nSlices, nExtras);
    
    //---------------------------------------------------------
    unsigned char *charData = (unsigned char *)dataRand;
    int checksumBefore=0;
    int checksumAfter=0;
    
    for (int i=0; i < dataRandSize; i++) {
        checksumBefore += charData[i];    
        checksumAfter += dataRandFinal[i];    
    }        
    
    std::cerr << checksumBefore << " " << checksumAfter <<  std::endl;
    //---------------------------------------------------------           
       
    for (int i=0; i<dataRandSize; i++)
        assert(dataRand[i] == dataRandFinal[i]);               

    delete[] data1;                            
    for (int slice=0; slice < nSlices+nExtras; slice++)
        delete[] dataRandEnc[slice];
    delete[] dataRandEnc;    
    delete[] dataRandFinal;    
    delete[] dataRand;    

    printf ("------------------------------------------------------------\n");    
}
