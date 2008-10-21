#include "BspLib.hpp"
#include "BspCentral.hpp"
#include <iostream>


  BspCentral * bspCentral = new BspCentral();
  //----------------------------------------------------------------------------------
  void bsp_begin(int maxProcs){
    try{
      bspCentral->bspBegin();
    }
    catch(...){
      cerr << "Exception thrown while calling bsp_begin. Please check the output for additional"
           << " error messages" << endl;
    }
  }

  //----------------------------------------------------------------------------------
  int bsp_pid(){
    try{
      return bspCentral->bspPid();
    }
    catch(...){
      cerr << "Exception thrown while calling bsp_pid. Please check the output for additional"
           << " error messages" << endl;
      return -1;
    }
  }

  //----------------------------------------------------------------------------------
  void bsp_pushregister (const void * ident, int size){
    try{
      bspCentral->bspPushregister( ident, size);
    }
    catch(...){
      cerr << "Exception thrown while calling bsp_pushregister. Please check the output for additional"
           << " error messages" << endl;
    }
  }
  //----------------------------------------------------------------------------------
  void bsp_pushregister2 (const void *ident, int size, long typeValue) {
    try{
      bspCentral->bspPushregister( ident, size, typeValue);
    }
    catch(...){
      cerr << "Exception thrown while calling bsp_pushregister. Please check the output for additional"
           << " error messages" << endl;
    }
  }
  

  //----------------------------------------------------------------------------------
  void bsp_push_reg (const void *ident, int size){
    try{
      bspCentral->bspPushregister( ident, size);
    }
    catch(...){
      cerr << "Exception thrown while calling bsp_push_reg. Please check the output for additional"
           << " error messages" << endl;
    }
  }

  //----------------------------------------------------------------------------------
  void bsp_pop_reg(const void *ident){
    try{
      bspCentral->bspPopregister(ident);
    }
    catch(...){
      cerr << "Exception thrown while calling bsp_pop_reg. Please check the output for additional"
           << " error messages" << endl;
    }
  }

  //----------------------------------------------------------------------------------
  void bsp_popregister(const void *ident){
    try{
      bspCentral->bspPopregister( ident);
    }
    catch(...){
      cerr << "Exception thrown while calling bsp_popregister. Please check the output for additional"
           << " error messages" << endl;
    }
  }

  //----------------------------------------------------------------------------------
  void bsp_put(int pid, const void *src, void *dst, int offset, int nbytes){
    try{
      bspCentral->bspPut(pid, (void *) src, dst, offset, nbytes);
    }
    catch(...){
      cerr << "Exception thrown while calling bsp_put. Please check the output for additional"
           << " error messages" << endl;
    }
  }

  //----------------------------------------------------------------------------------
  void bsp_sync(){
    try{
      bspCentral->bspSync();
    }
    catch(...){
      cerr << "Exception thrown while calling bsp_sync. Please check the output for additional"
           << " error messages" << endl;
    }
  }

  //----------------------------------------------------------------------------------

  int bsp_nprocs(){
    try{
      return bspCentral->bspNprocs();
    }
    catch(...){
      cerr << "Exception thrown while calling bsp_nprocs. Please check the output for additional"
           << " error messages" << endl;
      return -1;           
    }
  }

  //----------------------------------------------------------------------------------
  void bsp_get(int pid,const void * src, int offset, void * dst, int nbytes){
    try{
      bspCentral->bspGet(pid, src, offset, dst, nbytes);
    }
    catch(...){
      cerr << "Exception thrown while calling bsp_get. Please check the output for additional"
           << " error messages" << endl;
    }
  }

  // BSMP functions
  //---------------------------------------------------------------------------------
  void bsp_send(int pid,const void *tag, const void * payload,int payload_bytes){
    try{
      bspCentral->bspSend(pid, tag, payload, payload_bytes);
    }
    catch(...){
      cerr << "Exception thrown while calling bsp_send. Please check the output for additional"
           << " error messages" << endl;
    }
  }

  //---------------------------------------------------------------------------------
  void bsp_move(void *payload,int reception_bytes){
    try{
      bspCentral->bspMove(payload, reception_bytes);
    }
    catch(...){
      cerr << "Exception thrown while calling bsp_move. Please check the output for additional"
           << " error messages" << endl;
    }
  }

  //---------------------------------------------------------------------------------
  void  bsp_qsize(int *packets, int *accum_nbytes){
    try{
      bspCentral->bspQSize(packets, accum_nbytes);
    }
    catch(...){
      cerr << "Exception thrown while calling bsp_qsize. Please check the output for additional"
           << " error messages" << endl;
    }
  }

  //---------------------------------------------------------------------------------
  void bsp_set_tagsize(int *tag_bytes){
    try{
      bspCentral->bspSetTagSize(tag_bytes);
    }
    catch(...){
      cerr << "Exception thrown while calling bsp_set_tagsize. Please check the output for additional"
           << " error messages" << endl;
    }
  }

  //---------------------------------------------------------------------------------
  void bsp_get_tag(int *status, void *tag){
    try{
      bspCentral->bspGetTag(status, tag);
    }
    catch(...){
      cerr << "Exception thrown while calling bsp_get_tag. Please check the output for additional"
           << " error messages" << endl;
    }
  }






