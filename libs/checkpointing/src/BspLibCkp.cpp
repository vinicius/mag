#include "BspLibCkp.hpp"

#include "CheckpointingLib.hpp"
#include "BspLib.hpp"

#include <fstream>
#include <iostream>

using namespace std;

int globalCkp = 0; /* Register for checkpointing */

//-----------------------------------------------------------------------

void bsp_begin_ckp(int nproc) {

  bsp_begin(nproc);
  bsp_pushregister2(&globalCkp, sizeof(int), CKPT_INT);
  bsp_sync();
}

//-----------------------------------------------------------------------

int bsp_sync_ckp() {

  int j;

  if (bsp_pid() == 0) {    
    if (testCheckpointTimer()) globalCkp = 1;    
    /** IMPROVEMENT: Can be optimized to put new value only when globalCkp changes */
    for (j=0; j<bsp_nprocs(); j++)
      bsp_put(j, &globalCkp, &globalCkp, 0, sizeof(int));
  }

  bsp_sync();
  /** A new checkpoint will be generated */
  if (globalCkp) {
    globalCkp = 0;
    return 1;
  }
  return 0;
}

//-----------------------------------------------------------------------

void bsp_end_ckp() {
   
  /** Necessary because the reinitialization mechanism supposes all processes
   *  finish at the same time */
  bsp_sync();
}

//-----------------------------------------------------------------------
