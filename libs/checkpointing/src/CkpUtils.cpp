#include "CkpUtils.hpp"
#include <sstream>
#include <iostream>

CkpUtils::CkpUtils()
{
}

CkpUtils::~CkpUtils()
{
}

string CkpUtils::getChecksum(unsigned char *data, int dataSize) 
{
    int checksumInt = 0;
    for (int i=0; i<dataSize; i++)
        checksumInt += data[i];            

    ostringstream strChecksum;
    strChecksum << checksumInt;
    return strChecksum.str();    
}
    
string *CkpUtils::createSliceChecksumList
(unsigned char **sliceData, int nSlices, int sliceSizes, unsigned char *data, int dataSize)
{
    string *checksumList = new string[nSlices+1];

    for (int slice=0; slice < nSlices; slice++)
        checksumList[slice] = getChecksum(sliceData[slice], sliceSizes);
    checksumList[nSlices] = getChecksum(data, dataSize);
    
    for (int i=0; i<nSlices+1; i++) {
        std::cerr << checksumList[i] << " ";
    }    
    std::cerr << std::endl;       
    
    return checksumList;    
}
