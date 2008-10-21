#include "HeapController.hpp"

using namespace std;

HeapController::HeapController() {
  MemoryChunkData memChunk;
  memChunk.size     = 0;    
  memChunk.savedFlag = false;
  nullPointer = NULL;
  memoryMap.insert(make_pair(nullPointer, memChunk));
}

HeapController::~HeapController() {
  memoryMap.clear();
}  

void HeapController::addAllocatedMemory(void *memAddress, long memSize) {
  MemoryChunkData memChunk;
  memChunk.size     = memSize;    
  memChunk.savedFlag = false;
  memoryMap.insert(make_pair(memAddress, memChunk));
}

void HeapController::removeAllocatedMemory(void *memAddress) {
  memoryMap.erase(memAddress); 
}

MemoryChunkData& HeapController::getMemoryData(void *memAddress) {
  std::map<void*, MemoryChunkData>::iterator memMapI = memoryMap.find(memAddress);
  if (memMapI == memoryMap.end()) 
    return (*memoryMap.find(nullPointer)).second;
  else
    return (*memMapI).second;
}

void HeapController::resetSavedFlag(void) {
  std::map<void*, MemoryChunkData>::iterator memMapI = memoryMap.begin();
  while (memMapI != memoryMap.end()) {
    (*memMapI).second.savedFlag = false;
    memMapI++;
  }
}

 