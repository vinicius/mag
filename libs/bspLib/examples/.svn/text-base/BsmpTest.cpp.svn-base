//extern "C"{
#include "BspLib.hpp"
//}
#include <cstdio>
#include <iostream>

#include <ctime>//FIXME REMOVE: For benchmarkin' only

using namespace std;

/**
  Perform allsums, that is, at the end of the computation, task <i>i</i>
  will output the values stored at tasks 1,2,..., i.
  
  @param x - The initial vatue to be stored in this task.
*/
int bspAllSums(int x){

    int  i, left, right;
    bsp_pushregister(&left, sizeof(int));
    bsp_sync();


    right = x;
    for (i = 1; i < bsp_nprocs(); i*=2) {

      if ( bsp_pid() + i < bsp_nprocs())
        bsp_put(bsp_pid() + i, &right, &left, 0, sizeof(int));
      bsp_sync();
      if (bsp_pid() >=i) right = left + right;
    }
    bsp_popregister(&left);
    return right;
  }

int bbspAllSums(int x){

    int  i, left, right, tag_size, global_idx, status;
    //bsp_pushregister(&left, sizeof(int));
    tag_size = sizeof(int);
    bsp_set_tagsize(&tag_size);
    bsp_sync();


    right = x;
    for (i = 1; i < bsp_nprocs(); i*=2) {

      if ( bsp_pid() + i < bsp_nprocs()){
       global_idx = (bsp_pid() * bsp_nprocs()) + i;
        bsp_send(bsp_pid() + i, &global_idx, &right, sizeof(int));
      }
      bsp_sync();
      if (bsp_pid() >=i) {
      	bsp_get_tag(&status,NULL); //I'm not sure
           if (status!=sizeof(int))
              return -1;
           bsp_move(&left,sizeof(int));
      	right = left + right;
      }
    }
    bsp_set_tagsize(&tag_size); // I'm not sure
    return right;
  }

  /**
    Our basic test application.
  */
  int main(){

   cerr << "++++++++++++++++++++++++++++++++++++++++++++++++++++++" << endl;

    int i = 0;

    try{
       bsp_begin(0);
       cerr << "Bom dia: meu bsp_pid eh: " <<  bsp_pid() << endl;

       i = bbspAllSums(bsp_pid()); 

       cerr << "********************" << i << "*******************"  << endl;
       time_t ltime;
       time( &ltime );
       cerr << "Tempo: " << ctime( &ltime ) << endl;
       
       //bsp_end();
    }
    catch(...){
      cerr << "MAIN::alguem lancou excecao"  << endl;
    }

   return 0;

  }





