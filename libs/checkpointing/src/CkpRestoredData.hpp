#ifndef CKPRESTOREDDATA_HPP
#define CKPRESTOREDDATA_HPP

#include "CheckpointingLib.hpp"
#include "DataConverters.hpp"
#include "CkpStore.hpp"
#include <map>
#include <vector>
#include <list>
#include <utility>

class CkpRestoredData{
protected:
  
    struct ckpGetDataStruct {
      void *data;
      long nbytes;
      unsigned long type;
      void (*func)(void *);
      ckp_datapos_t dataPos;
    };

    std::vector<ckpGetDataStruct *> structPointerStack;

    DataConverters *dataConverters;

    /** Contains the data chunk already read */
    typedef std::map<ckp_datapos_t, void *> DataChunksRead;
    typedef std::map<ckp_datapos_t, void *>::const_iterator DataChunksReadI; // iterator
    DataChunksRead dataChunksRead;

    bool ckpHeapData;
    bool ckpOnlyAdd;
    bool structSkipRecoverFlag;
    
    /**  */
    typedef std::map<ckp_datapos_t, void *> StackDataAddresses;
    StackDataAddresses stackDataAddresses;
    ckp_datapos_t stackEndPos;
    
    /**  */    
    typedef std::list< std::pair<void *, ckp_datapos_t> > StackDataPointersList;    
    StackDataPointersList stackDataPointersList;

    /** Buffer holding the restored data */
    void* ckpRestoreData;
    /** Pointer to the next byte to be restored */
    char* ckpRestoreCurrent;
    long  ckpRestoreNbytes; // Number of bytes recovered from file
    long  ckpRestoredBytes;
    long  ckpRestoredStackBytes;

public:

    CkpRestoredData();
    virtual ~CkpRestoredData();
    
    /** Recover checkpoint data from file <ckpNumber>.ckp */
    int ckpRestoreCkpData(CkpStore *ckpStore, int ckpNumber);
    /** FIXME: Add Comment */
    int ckpGetData(void *data, long nbytes, unsigned long type, void (*func)(void *)); 
    
//    static int ckpGetData(CkpRestoredData *restoredData,
//                          void *data, 
//                          long nbytes,
//                          unsigned long type, 
//                          void (*func)(void *)) {
//
//        return restoredData->getData(data, nbytes, type, func); 
//    }
};

#endif // CKPRESTOREDDATA_HPP
