#ifndef BSPLIB_HPP
#define BSPLIB_HPP

#ifdef __cplusplus
extern "C"{
#endif

  /**
    Starts the parallel portion of a BSP program

    @param maxProcs - the number of tasks to be spawned. This
    is currently unused. Instead, use the configuration file to
    specify the number of tasks.
  */
  void bsp_begin(int maxProcs);

  /**
    Returns the BSP PID of the current task
  */
  int bsp_pid();


  /**
    Returns the number of total tasks of the BSP application.
  */
  int bsp_nprocs();

  /**
    Barrier synchronization. Waits until all other tasks reach the
    barrier.
  */
  void bsp_sync();

  /**
    Register a memory address as accessible for remote writes/reads.

    @param ident - pointer to the memory area
    @param size - size (in bytes) if the memory area being registered.
  */
  void bsp_pushregister (const void *ident, int size);

  void bsp_pushregister2 (const void *ident, int size, long typeValue);

  /**
    Removes the registration that allows a memory area to be remotely accessed.
    If there are more than one registration of the same address, the last one is
    removed.

    @param ident - the memory address to be deregistered
  */
  void bsp_popregister(const void *ident);

  /**
    Performs a write at a remote memory address.

    @param pid - the BSP PID of the remote task on which the write will be performed
    @param src - the memory address from which contents will be copied
    @param dst - the LOCAL destination memory address. Will be mapped to a global
    memory address.
    @param offset - will start copying from address dst + offset
    @param nbytes - the number of bytes that will be copied
  */
  void bsp_put(int pid, const void *src, void *dst, int offset, int nbytes);


  /**
    Allows the local task to read from memory from a remote process.

    @param pid - The BSP process ID from where memory contents will be read
    @param src - Memory address from where data will be copied
    @param offset - Offset that will be added to the source address to determine
                    the starting position from where data will be copied
    @param dst - The destination address to were data will be written
    @param nBytes - Number of bytes to be read
  */
  void bsp_get(int pid, const void * src, int offset, void * dst, int nBytes);


  /**
    Alias to bsp_pushregister. Register a memory address as accessible
    for remote writes/reads.

    @param ident - pointer to the memory area
    @param size - size (in bytes) if the memory area being registered.
  */
  void bsp_push_reg (const void *ident, int size);

    /**
    Alias to bsp_popregister. Removes the registration that allows a memory area
    to be remotely accessed. If there are more than one registration of the same
    address, the last one is removed.

    @param ident - the memory address to be deregistered
  */
  void bsp_pop_reg (const void *ident);
  
  /**
   * Alias to bsp_send. Sends data for processing.
   * 
   * @param pid - The BSP process ID
   * @param tag - 
   * @param payload - 
   * @param payload_bytes -
   */
  void bsp_send(int pid,const void *tag, const void * payload,int payload_bytes);
  
  /**
   * 
   * 
   * @param payload
   * @param reception_bytes
   */
  void bsp_move(void *payload,int reception_bytes);

  /**
   * 
   * @param packets
   * @param accum_nbytes
   * 
   */	
  void  bsp_qsize(int *packets, int *accum_nbytes);
  
  /**
   * 
   * @param tag_bytes
   * 
   */
  void bsp_set_tagsize(int *tag_bytes);
  
  
  /**
   * 
   * @param status
   * @param tag
   */ 
  void bsp_get_tag(int *status, void *tag);
  
  

#ifdef __cplusplus
} /* extern "C"*/
#endif

#endif/*BSPLIB_HPP*/

