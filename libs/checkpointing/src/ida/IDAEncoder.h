#ifndef _IDAENCODER_H_
#define _IDAENCODER_H_

#include "IDADefinitions.h"
#include "IDAAuxVectors.h"

class IDAEncoder
{
public:
	IDAEncoder(){}
	virtual ~IDAEncoder(){}
    
    virtual enc_t **encodeData(unsigned char *data, int dataSize, int nSlices, int nExtra, IDAAuxVectors *idaAuxVectors)=0;
};

//------------------------------------------------------------------------------

class IDAEncoderRabin : public IDAEncoder {

    class IDAAuxVectors *idaAuxVectors;

public:

    IDAEncoderRabin();
    ~IDAEncoderRabin();

    enc_t **encodeData(unsigned char *data, int dataSize, int nSlices, int nExtra, IDAAuxVectors *idaAuxVectors);    
};

//------------------------------------------------------------------------------

class IDAEncoderIdentity : public IDAEncoder {

    class IDAAuxVectors *idaAuxVectors;

public:

    IDAEncoderIdentity();
    ~IDAEncoderIdentity();

    enc_t **encodeData(unsigned char *data, int dataSize, int nSlices, int nExtra, IDAAuxVectors *idaAuxVectors);    
};

//------------------------------------------------------------------------------

#endif /*_IDAENCODER_H_*/
