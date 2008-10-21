#ifndef _IDAIMPL_H_
#define _IDAIMPL_H_

class IDAImpl
{
    
    class IDAEncoder *idaEncoder;
    class IDADecoder *idaDecoder;    
    class IDAAuxVectors *idaAuxVectors;    
    
    void createTables ();
        
    
public:

    unsigned char **encodeData(unsigned char *data, int dataSize, int nSlices, int nExtra);
    unsigned char *decodeData(unsigned char **data, int dataSize, int *sliceNumbers, int nSlices, int nExtra);
    
	IDAImpl();
	virtual ~IDAImpl();
};

#endif //_IDAIMPL_H_
