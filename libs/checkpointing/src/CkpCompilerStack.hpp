#ifndef CKP_COMPILER_STACK
#define CKP_COMPILER_STACK

#include "HeapController.hpp"
#include "CkpStore.hpp"
#include <map>

// TODO: the stack should adjust its size automatically;

class CkpCompilerStack {

private:  
    HeapController *heapController;   
    CkpStore *ckpStore;
    int lastCkpNumber;
    
    ckp_datapos_t stackEndDataPos;
    char architecture;

    // Holds application data
    void** ckpStackData;
    void** ckpStackTemp; // Holds pointer data while the stack is being saved
    void** ckpStackFunc;
    ckp_datasize_t*  ckpStackNbytes;
    long*  ckpStackType;
    long   ckpStackNsegments;

public:
  CkpCompilerStack(int initialStackCapacity, HeapController *, CkpStore *);
  ~CkpCompilerStack();

  void setCkpStore(CkpStore *ckpStore) {
    if (this->ckpStore) delete this->ckpStore;
    this->ckpStore = ckpStore;
  };

  int ckpPushData(void *data, long nbytes, unsigned long type, void (*func)(void *));
  int ckpPopData(int);	
  int ckpSaveStackData(int ckpNumber);	
  
  void setLastCkpNumber(int ckpNumber) {lastCkpNumber = ckpNumber;}
};

#endif // CKP_LIB
