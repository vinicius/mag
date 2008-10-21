#ifndef CKP_HEAP_CONTROLLER
#define CKP_HEAP_CONTROLLER

#include <map>

//----------------------------------------------------------------

struct MemoryChunkData {
  long size;    
  int  dataPosition;
  bool savedFlag;
};

class HeapController {
  std::map<void*, MemoryChunkData> memoryMap;
  void *nullPointer;
  
 public:
  HeapController();
  ~HeapController();
  
  void addAllocatedMemory(void *memAddress, long memSize);
  void removeAllocatedMemory(void *memAddress);
  void resetSavedFlag(void);
  MemoryChunkData& getMemoryData(void *memAddress);
};

//----------------------------------------------------------------

#endif // CKP_HEAP_CONTROLLER
