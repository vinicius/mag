#ifndef BSP_LIB_CKP
#define BSP_LIB_CKP

#ifdef __cplusplus
extern "C"{
#endif
    
  void bsp_begin_ckp(int nproc);
  
  int bsp_sync_ckp();
  
  void bsp_end_ckp();

  /** Must behave as if the other computer is of the same architecture 
   *  Necessary to convert the data representation and adjust the offset */
  /** void bsp_pushregister2 (const void *ident, int size, long typeValue); */

  /** Must behave as if the other computer is of the same architecture 
   *  Necessary to convert the data representation and adjust the offset */
  /** void bsp_get2(int pid, void * src, int offset, void * dst, int nBytes); */
      
#ifdef __cplusplus
} /* extern "C" */
#endif

#endif /*BSP_LIB_CKP*/


