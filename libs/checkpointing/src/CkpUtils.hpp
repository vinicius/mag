#ifndef CKPUTILS_HPP_
#define CKPUTILS_HPP_

#include <string>
using namespace std;

class CkpUtils {
    
public:
	CkpUtils();
    virtual ~CkpUtils();
    
    static string getChecksum(unsigned char *data, int dataSize);
    
    static string *createSliceChecksumList(unsigned char **sliceData, int nSlices, int sliceSizes, 
                                    unsigned char *data, int dataSize);    
    
};

#endif /*CKPUTILS_HPP_*/
