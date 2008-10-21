#ifndef CKP_STACK_MEMORY_POSITIONS_HPP
#define CKP_STACK_MEMORY_POSITIONS_HPP

#include <map>
#include "CheckpointingLib.hpp"

//----------------------------------------------------------------

class StackMemoryPositions {
  std::map<void*, ckp_datapos_t> memoryMap;
  
 public:
  void addMemoryAddress(void *memAddress, ckp_datapos_t bufferPos);
  ckp_datapos_t getMemoryPosition(void *memAddress);
};

//----------------------------------------------------------------

#endif // CKP_STACK_MEMORY_POSITIONS_HPP
