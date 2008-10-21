
#ifndef _IDADECODER_H_
#define _IDADECODER_H_

#include "IDADefinitions.h"
#include "IDAAuxVectors.h"

class IDADecoder
{

public:
	IDADecoder(){}
	virtual ~IDADecoder(){}
    
    virtual unsigned char *decodeData(enc_t **data, int dataSize, int *sliceNumbers, int nSlices, int nExtra, IDAAuxVectors *idaAuxVectors)=0;
};

class IDADecoderRabin : public IDADecoder {

    class IDAAuxVectors *idaAuxVectors;

public:
    IDADecoderRabin();
    ~IDADecoderRabin();
    
    unsigned char *decodeData(enc_t **data, int dataSize, int *sliceNumbers, int nSlices, int nExtra, IDAAuxVectors *idaAuxVectors);
};

class IDADecoderIdentity : public IDADecoder {

    class IDAAuxVectors *idaAuxVectors;

public:
    IDADecoderIdentity();
    ~IDADecoderIdentity();
    
    unsigned char *decodeData(enc_t **data, int dataSize, int *sliceNumbers, int nSlices, int nExtra, IDAAuxVectors *idaAuxVectors);
};


#endif /*_IDAENCODER_H_*/
