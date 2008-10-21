#include "StackMemoryPositions.hpp"

using namespace std;

void StackMemoryPositions::addMemoryAddress(void *memAddress, ckp_datapos_t bufferPos) {
  memoryMap.insert(make_pair(memAddress, bufferPos));
}

ckp_datapos_t StackMemoryPositions::getMemoryPosition(void *memAddress) {
  std::map<void*, ckp_datapos_t>::iterator memMapI = memoryMap.find(memAddress);
  if (memMapI == memoryMap.end()) return -1;
  return (*memMapI).second;
}
