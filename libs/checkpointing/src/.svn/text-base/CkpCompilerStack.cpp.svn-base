#include "CheckpointingLib.hpp"
#include "CkpCompilerStack.hpp"
#include "StackMemoryPositions.hpp"
#include "DataConverters.hpp"

#include "ida/IDAImpl.h"

#include <cassert>
#include <iostream>
#include <sstream>
#include <vector>

using namespace std;

#define CKPT_POINTERCHUNK 4001

CkpCompilerStack::CkpCompilerStack(int initialStackCapacity, HeapController *heap, CkpStore *ckpStore) {

  lastCkpNumber = -1;
  this->heapController = heap;
  this->ckpStore = ckpStore;
  ckpStackData   = new void *[initialStackCapacity];
  ckpStackTemp   = new void *[initialStackCapacity];
  ckpStackFunc   = new void *[initialStackCapacity];
  ckpStackNbytes = new ckp_datasize_t[initialStackCapacity];
  ckpStackType   = new long[initialStackCapacity];
  ckpStackNsegments = 0;

  architecture = DataConverters::getProcessorArchitecture();
  
  //assert(sizeof(char)==1); // Is used to assign the machine architecture  
  ckpPushData(&architecture,    sizeof(ckp_arch_t),    CKPT_ARCH,    0);
  ckpPushData(&stackEndDataPos, sizeof(ckp_datapos_t), CKPT_DATAPOS, 0);

}

CkpCompilerStack::~CkpCompilerStack() {

    // TODO: Free memory
}

//-------------------------------------------------------------------------
int CkpCompilerStack::ckpPushData(void *data, long nbytes, unsigned long type, void (*func)(void *str)) {
    //  ckpStackTemp[ckpStackNsegments]   = data;

    if (isBuiltIn(type) || 
        isPrimitivePointer(type) || 
        type == CKPT_MEMORYCHUNK ||
        type == CKPT_POINTERCHUNK || 
        type == CKPT_POINTER_STRUCT) 
    {        
      ckpStackData[ckpStackNsegments]   = data;
      ckpStackNbytes[ckpStackNsegments] = (ckp_datasize_t)nbytes;
      ckpStackType[ckpStackNsegments]   = type;
      ckpStackFunc[ckpStackNsegments]   = (void *)func;
      return ++ckpStackNsegments;
    } 
    else if (type == CKPT_STRUCT) {
      (*func)(data);
    }

    return -1;        
}

int CkpCompilerStack::ckpPopData(int nVar) {
    ckpStackNsegments -= nVar;
	return ckpStackNsegments;
}

//--------------------------------------------------------------------------

int CkpCompilerStack::ckpSaveStackData(int ckpNumber) {
   
    /** Contains the index of all pointers in CkpStack */
    std::vector<int> pointerIndexList;   // TODO: Replace by a list
    /** Contains the position in CkpStack of the data reference by 
     *  the pointers with the same index contained in pointerIndexList */
    std::vector<ckp_datapos_t> pointerDataPosList; // TODO: Replace by a list
    
    StackMemoryPositions stackMemPos;
    std::vector<int> stckPointerIndexList;   // TODO: Replace by a list

    // TODO: Needs to exclude globalVars
    //unsigned long stckBeginning = (unsigned long)ckpStackData[3];
    //unsigned long stckEnd = (unsigned long)ckpStackData[ckpStackNsegments-1];

    unsigned long stckBeginning;
    unsigned long stckEnd;
    {
      int i=2;
      while ( ckpStackType[i]%(2*CKPT_GLOBAL) > CKPT_GLOBAL ) i++; 
      stckBeginning = (unsigned long)ckpStackData[i];
      stckEnd       = (unsigned long)ckpStackData[i];

      for (; i<ckpStackNsegments; i++) 
	if (ckpStackType[i]%(2*CKPT_GLOBAL) < CKPT_GLOBAL) {
	  if ((unsigned long)ckpStackData[i] < stckBeginning )
	    stckBeginning = (unsigned long)ckpStackData[i];
	  else if ((unsigned long)ckpStackData[i] > stckEnd )
	    stckEnd = (unsigned long)ckpStackData[i];
	}
    }

    int ckpStackNsegmentsBefore = ckpStackNsegments;
    long nBytesTotal = 0;

    /** Dereference pointers */
    for (int i=0; i<ckpStackNsegments; i++) {
	    
        if (isBuiltIn(ckpStackType[i])) {
          nBytesTotal += ckpStackNbytes[i];
        }
        else if (ckpStackType[i] == CKPT_MEMORYCHUNK ) {
	  //	  std::cout << "size=" << ckpStackNbytes[i] << std::endl;
	  nBytesTotal += sizeof(ckp_datasize_t); // Chunk size goes before chunk data     
          nBytesTotal += ckpStackNbytes[i];
        }        
        else if (ckpStackType[i] == CKPT_POINTERCHUNK) {
	  //std::cout << "CKPT_POINTER_CHUNK" << std::endl;
	  nBytesTotal += sizeof(ckp_datasize_t); // Chunk size goes before chunk data     
          // BytesTotal += ckpStackNbytes[i]; // Is added by the pointer themselves
        }        
        else if (isPrimitiveSinglePointer(ckpStackType[i]) || ckpStackType[i] == CKPT_POINTER_STRUCT) {
          nBytesTotal += sizeof(ckp_datapos_t); // Saves the position of the pointed data

          unsigned long address = *(unsigned long *)ckpStackData[i];
          /** Pointer to the stack */
	  if ( (stckEnd       <= address && address <= stckBeginning) ||
	       (stckBeginning <= address && address <= stckEnd      ) ){
	    //cout << "Inside Stack1!!! " << i << endl;
	    stckPointerIndexList.push_back(i);
	  }
          /** Pointer to a heap memory area */
          else { 
            pointerIndexList.push_back(i);
            MemoryChunkData& memChunkData = heapController->getMemoryData(*(void **)ckpStackData[i]); 
            /** Invalid or NULL pointer */
            if (memChunkData.size == 0) 
              pointerDataPosList.push_back(-1);
            /** Pointed memory area already saved */
            else if (memChunkData.savedFlag == true)
              pointerDataPosList.push_back(memChunkData.dataPosition);
            /** Pointed memory area not saved */
            else { 

	      //std::cout << "Saving Pointer Data!!! size=" << memChunkData.size << std::endl;

              memChunkData.savedFlag    = true;  
              memChunkData.dataPosition = ckpStackNsegments;
              pointerDataPosList.push_back(ckpStackNsegments);
              if (ckpStackFunc[i] == 0) {
                /** The last level of indirection was reached */
                if ( (ckpStackType[i] % CKPT_POINTER) / CKPT_POINTER_LEVEL == 0)
                  ckpPushData(*(void **)ckpStackData[i], memChunkData.size, CKPT_MEMORYCHUNK, 0);
	      }
              else { // CKPT_POINTER_STRUCT
                if ( (ckpStackType[i] % CKPT_POINTER) / CKPT_POINTER_LEVEL == 0) {
                  void (*func)(void *) = (void (*)(void *))ckpStackFunc[i];
                  (*func)(*(void **)ckpStackData[i]);                           
                }
		
	      }
	    }
	  }
	}
	/** Multi-level pointer. Need to follow each address here */                  
	else if (isPrimitivePointer(ckpStackType[i])) {

          unsigned long address = *(unsigned long *)ckpStackData[i];
          /** Pointer to the stack */
          if ( (stckEnd <= address && address <= stckBeginning) ||
               (stckBeginning <= address && address <= stckEnd) ) {
	    //	    cout << "Inside Stack2!!! " << i << endl;
            stckPointerIndexList.push_back(i);
	  }
          /** Pointer to a heap memory area */
          else { 
	  
            pointerIndexList.push_back(i);
	    nBytesTotal += sizeof(ckp_datapos_t); // Saves the position of the pointed data
            MemoryChunkData& memChunkData = heapController->getMemoryData(*(void **)ckpStackData[i]); 

	    //std::cout << "MultiLevel Pointer!!! size=" << memChunkData.size << std::endl;

            /** Invalid or NULL pointer */
            if (memChunkData.size == 0) 
              pointerDataPosList.push_back(-1);
            /** Pointed memory area already saved */
            else if (memChunkData.savedFlag == true)
              pointerDataPosList.push_back(memChunkData.dataPosition);
            /** Pointed memory area not saved */
            else { 
              memChunkData.savedFlag    = true;  
              memChunkData.dataPosition = ckpStackNsegments;
	      pointerDataPosList.push_back(ckpStackNsegments);

	      unsigned long tempSize = memChunkData.size;
	      int dataPosChunkSize = (memChunkData.size/sizeof(void *)) * sizeof(ckp_datapos_t);
	      ckpPushData(0, dataPosChunkSize, CKPT_POINTERCHUNK, 0);
	      for (int p = 0; tempSize >= sizeof(void *); p += sizeof(void *)) {
		ckpPushData((void *)((*(char **)ckpStackData[i])+p), ckpStackNbytes[i],
			    ckpStackType[i] - CKPT_POINTER_LEVEL, 0);                
		tempSize -= sizeof(void *);
	      }
	      if (tempSize != 0) {
		std::cout << "Error when saving a multi-level pointer..." << std::endl;
		return -1; 
	      }                
	    }
	  }                    
	} // if (isPrimitiveSinglePointer(ckpStackType[i])) {
	
    } // for (int i=0; i<ckpStackNsegments; i++)

    cout << "ckpSaveStackData: Phase 1 completed" << endl;

    char *saveBuffer = new char[nBytesTotal];
    ckp_datapos_t *dataPosition = new ckp_datapos_t[ckpStackNsegments];
    bool pointerToStack = !(stckPointerIndexList.empty());
    
    /** Copies the data into saveBuffer and sets dataPosition with the
     *  location of the data from CkpStack in the saveBuffer */
    char *saveBufferBeginning = saveBuffer;    

    for (int i=0; i<ckpStackNsegments; i++) {
      dataPosition[i] = (ckp_datapos_t)(saveBuffer - saveBufferBeginning);          
      if (isPrimitivePointer(ckpStackType[i]) || ckpStackType[i] == CKPT_POINTER_STRUCT)
	saveBuffer += sizeof(ckp_datapos_t); // Will be updated later
      else if (ckpStackType[i] == CKPT_POINTERCHUNK) {
	//	std::cout << "CKPT_POINTER_CHUNK size=" << ckpStackNbytes[i] << " i=" << i << std::endl;
	memcpy(saveBuffer, (void *)&(ckpStackNbytes[i]), sizeof(ckp_datasize_t));
	saveBuffer += sizeof(ckp_datasize_t); // Saves the chunk size
	// saveBuffer += ckpStackNbytes[i]; // Are updated by the pointer themselves
      }
      else if (ckpStackType[i] == CKPT_MEMORYCHUNK) {
        memcpy(saveBuffer, (void *)&(ckpStackNbytes[i]), sizeof(ckp_datasize_t));
        saveBuffer += sizeof(ckp_datasize_t); // Saves the chunk size
        memcpy(saveBuffer, ckpStackData[i], ckpStackNbytes[i]);
        saveBuffer += ckpStackNbytes[i]; // Saves the data            
      }
      else { // CKPT_BUILTIN
        if (pointerToStack) stackMemPos.addMemoryAddress(ckpStackData[i], dataPosition[i]);
        memcpy(saveBuffer, ckpStackData[i], ckpStackNbytes[i]);
        saveBuffer += ckpStackNbytes[i];      
      }
    }
    saveBuffer = saveBufferBeginning;
    
    /** Updates the pointers in saveBuffer with the new positions in 
     *  'saveBuffer' */
    for (unsigned int i=0; i<pointerIndexList.size(); i++) {
      int currIndex = pointerIndexList[i];
      int pointerDataPos = pointerDataPosList[i];

      if (pointerDataPos >= 0) {
        ckp_datapos_t dataPos = dataPosition[pointerDataPos];       
        saveBuffer += dataPosition[currIndex];
        memcpy(saveBuffer, &dataPos, sizeof(ckp_datapos_t));
        saveBuffer = saveBufferBeginning;
      }
      else { // NULL or invalid pointer
        saveBuffer += dataPosition[currIndex];
        ckp_datapos_t minusOne =  -1;
        memcpy(saveBuffer, &minusOne, sizeof(ckp_datapos_t));
        saveBuffer = saveBufferBeginning;        
      }
    }
    for (unsigned int i=0; i<stckPointerIndexList.size(); i++) {
      int currIndex = stckPointerIndexList[i];
      ckp_datapos_t dataPos  = stackMemPos.getMemoryPosition(*(void **)ckpStackData[currIndex]);
      saveBuffer   += dataPosition[currIndex];
      memcpy(saveBuffer, &dataPos, sizeof(ckp_datapos_t));
      saveBuffer = saveBufferBeginning;
    }
    
    /** The beginning of the stack contains the architecture, followed by 
     *  the position where stack data finishes */
    stackEndDataPos = dataPosition[ckpStackNsegmentsBefore-1];
    memcpy(saveBuffer+sizeof(char), &stackEndDataPos, sizeof(ckp_datapos_t));

    cerr << "CkpCompilerStack: calling save data."<< endl;
    
    //*************** IDA Encoding **********************************//
//    bool enableIda = true;
//    if (enableIda) {
//        // encodeData(unsigned char *data, int dataSize, int nSlices, int nExtra)
//        IDAImpl idaImpl;
//        int nSlices = 3;
//        int nExtra  = 0;
//        unsigned char **encData = idaImpl.encodeData((unsigned char *)saveBuffer, nBytesTotal, nSlices, nExtra);
//        int sliceSize = (nBytesTotal%nSlices == 0) ? nBytesTotal/nSlices : nBytesTotal/nSlices+1;
//        if (ckpNumber >= 0) lastCkpNumber = ckpNumber;
//        else ++lastCkpNumber;             
//
//        ckpStore->saveCkpData(encData, sliceSize, lastCkpNumber);        
//        for (int i=0; i<nSlices+nExtra; i++)
//            delete[] encData[i];            
//        delete[] encData;            
//    }
    
    // Saves the data to file '<ckpNumber>.ckp'
    std::ostringstream ckpFile;
    int returnValue;
    if (ckpNumber >= 0) lastCkpNumber = ckpNumber;
    else ++lastCkpNumber;     
    ckpStore->saveCkpData(saveBuffer, nBytesTotal, lastCkpNumber);
    
    //delete[] saveBuffer;
    delete[] dataPosition;
    heapController->resetSavedFlag();
        
    // Restore the stackSize to the same value as before  
    ckpStackNsegments = ckpStackNsegmentsBefore;  
    return returnValue;
}

//--------------------------------------------------------------------------
