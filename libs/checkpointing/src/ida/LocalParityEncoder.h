#ifndef _LOCALPARITYENCODER_H_
#define _LOCALPARITYENCODER_H_

#include "IDADefinitions.h"

class LocalParityEncoder
{
public:
    LocalParityEncoder(){}
    virtual ~LocalParityEncoder(){}
    
    enc_t **encodeData(unsigned char *data, int dataSize, int nSlices);
    unsigned char *decodeData(enc_t **data, int dataSize, int *sliceNumbers, int nSlices);
    
};

#endif
