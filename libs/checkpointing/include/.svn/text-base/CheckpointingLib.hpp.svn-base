#ifndef CKP_LIB
#define CKP_LIB

#include <stddef.h>
  
#ifdef __cplusplus
extern "C"{
#endif

#define CKPT_VOID 0

/** CKPT_BUILTIN [1,99]
 *  signed data types [1,49]
 *  unsigned data types [51, 99] */
#define isBuiltIn(x) ( 0 < (x) && (x) < 100)
#define CKPT_BOOL    1 // bool (1)
#define CKPT_CHAR    2 // char (8)
#define CKPT_WCHAR   3 // wchar_t (16)
#define CKPT_SHORT   4 // short (16)
#define CKPT_INT     5 // int (16/32)
#define CKPT_LONG    6 // long (32)
#define CKPT_FLOAT   7 // float (32)
#define CKPT_DOUBLE  8 // double (64)
#define CKPT_LDOUBLE 9 // long double (64)

#define CKPT_FPOS_T  10   // Used only for experiments
#define CKPT_TIMEVAL 11   // Used only for experiments
#define CKPT_FPOS_T_SIZE  sizeof(fpos_t)
#define CKPT_TIMEVAL_SIZE sizeof(timeval)

#define CKPT_SIGNED     0
#define CKPT_UNSIGNED  20
#define CKPT_GLOBAL    50

#define CKPT_BOOL_SIZE    sizeof(bool)
#define CKPT_CHAR_SIZE    sizeof(char)
#define CKPT_WCHAR_SIZE   sizeof(wchar_t)
#define CKPT_SHORT_SIZE   sizeof(short)
#define CKPT_INT_SIZE     sizeof(int)
#define CKPT_LONG_SIZE    sizeof(long)
#define CKPT_FLOAT_SIZE   sizeof(float)
#define CKPT_DOUBLE_SIZE  sizeof(double)
#define CKPT_LDOUBLE_SIZE sizeof(long double)

#define CKPT_BOOL_X8664_SIZE     1
#define CKPT_CHAR_X8664_SIZE     1
#define CKPT_WCHAR_X8664_SIZE    2
#define CKPT_SHORT_X8664_SIZE    2
#define CKPT_INT_X8664_SIZE      4
#define CKPT_LONG_X8664_SIZE     8
#define CKPT_FLOAT_X8664_SIZE    4
#define CKPT_DOUBLE_X8664_SIZE   8
#define CKPT_LDOUBLE_X8664_SIZE 16
#define CKPT_FPOS_T_X8664_SIZE   4

#define CKPT_BOOL_X86_SIZE     1
#define CKPT_CHAR_X86_SIZE     1
#define CKPT_WCHAR_X86_SIZE    2
#define CKPT_SHORT_X86_SIZE    2
#define CKPT_INT_X86_SIZE      4
#define CKPT_LONG_X86_SIZE     4
#define CKPT_FLOAT_X86_SIZE    4
#define CKPT_DOUBLE_X86_SIZE   8
#define CKPT_LDOUBLE_X86_SIZE 12
#define CKPT_FPOS_T_X86_SIZE   4

#define CKPT_BOOL_PPCG4_SIZE    4
#define CKPT_CHAR_PPCG4_SIZE    1
#define CKPT_WCHAR_PPCG4_SIZE   2
#define CKPT_SHORT_PPCG4_SIZE   2
#define CKPT_INT_PPCG4_SIZE     4
#define CKPT_LONG_PPCG4_SIZE    4
#define CKPT_FLOAT_PPCG4_SIZE   4
#define CKPT_DOUBLE_PPCG4_SIZE  8
#define CKPT_LDOUBLE_PPCG4_SIZE 8 // Valid for <= gcc3.1
#define CKPT_FPOS_T_PPCG4_SIZE  8 // Valid for Mac OS X

// #define CKPT_ARRAY 1
 
/** CKPT_POINTER [1000, 1999] 
 *  The last two digits indicates the basic data type referenced 
 *  The hundreds digit indicates the pointer level, starting from 1 */
#define isPrimitivePointer(x) ( 1000 <= (x) && (x) < 2000)
#define isPrimitiveSinglePointer(x) ( 1000 <= (x) && (x) < 1100)
#define CKPT_POINTER 1000
#define CKPT_POINTER_LEVEL 100

#define CKPT_STRUCT 2000

/** CKPT_POINTER_STRUCT [2000, 2999] 
 *  The hundreds digit indicates the pointer level, starting from 1 */
#define CKPT_POINTER_STRUCT 3000

/** CKPT_MEMORYCHUNK */
#define CKPT_MEMORYCHUNK 4000

//-------------------------------------------------------

#define CKPT_DATAPOS CKPT_INT
typedef int ckp_datapos_t;

#define CKPT_DATASIZE CKPT_INT
typedef int ckp_datasize_t;

#define CKPT_ARCH CKPT_CHAR
typedef char ckp_arch_t;

//#define I386    3
//#define I486    4
//#define I586    5
//#define I686    6
#define X86    1
#define X86_64 2
#define PPCG4  3

#define N_ARCH 3

  /**
     Tests if a full interval has elapsed from the last taken checkpoint. 
     If true, it returns 1 and restarts the timer. Otherwise, it returns 0;
  */
  int testCheckpointTimer();

  /**
     Saves the data contained in 'data' to the file 'filename'.

     @param filename - The name of a file to save the data 
     @param data - The data to be saved
     @param nbytes - Numbers of bytes to save  
  */
  int saveCheckpoint(const char *filename, void *data, long nbytes);

  /**
     Saves the data contained in 'data' to the file 'filename'.

     @param filename - The name of a file to save the data 
     @param data - A vector containing byte sequences to be saved
     @param nbytes - Numbers of bytes of each byte sequence from 'data'
     @param nsegments - Numbers of byte sequences in the vector 'data'
  */
  //int saveCheckpointVector(const char *filename, void **data, long *nbytes, long nsegments, int append);


  /**
     Saves the checkpoint from file 'filename'

     @param filename - The name of the file to recover the data from
     @param data - A pointer to a buffer where data will be placed
     @param bufferSize - Size of the buffer  
  */
  
  int recoverCheckpoint(const char *filename, void **data, long *bufferSize);

//===========================================================================

  /* The following functions are used by the checkpoint compiler 
   * They will probably be changed in the near future
  */			 
  extern int ckp_restoring;

  /**
   * Start checkpointing process.
   * Should be called at the beginning of application execution.
   * 
   * Tries to read file 'ckp.conf' from the execution directory.
   * 'ckp.conf' contains the checkpointing storage method and interval 
   **/ 
  void initCheckpointing(int argc, char **argv);
  
  void set_ckp_store(int ckpSaveFlag, int delay);
 
  int ckp_push_data(void *data, long nbytes, unsigned long type, void (*func)(void *));
  int ckp_pop_data(void);	
  int ckp_npop_data(int nVar);
  int ckp_get_data(void *data, long nbytes, unsigned long type, void (*func)(void *));
  
  int  ckp_restore_data(int ckpNumber);
  int  ckp_save_stack_data(int ckpNumber = -1);

  void *ckp_malloc(size_t size);
  void *ckp_calloc(size_t nmenb, size_t size);
  void ckp_free(void *ptr);
  void *ckp_realloc(void *ptr, size_t size);
	
#ifdef __cplusplus
} /* extern "C" */
#endif

#endif /*CKP_LIB*/
