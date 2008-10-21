#ifndef _IDAAUXVECTORS_H_
#define _IDAAUXVECTORS_H_

#include "IDADefinitions.h"

class IDAAuxVectors
{
    
    enc_t **auxVectors;
    
    enc_t **calculateInverseJordan(int *slices, int nSlices);
            
    enc_t **generateVectorsTest(int nLines, int size);
    enc_t **generateVectorsQuadratic(int nLines, int size);
    
public:
	IDAAuxVectors();
	virtual ~IDAAuxVectors();

    enc_t **getAuxVectors() {return auxVectors;}
    enc_t **generateIdentityG(int nSlices, int nExtra);
    enc_t **calculateInverse(int *slices, int nSlices);
    enc_t **generateVectors(int nLines, int size);
};

#endif /*_IDAAUXVECTORS_H_*/
