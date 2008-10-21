#include "BspLib.hpp"

#include <stdio.h>


#include <time.h>/*FIXME REMOVE: For benchmarkin' only*/



/**
  \fn int bspAllSums(int x)
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


  /**
    \fn main
    Our basic test application.
  */
  int main(){

    int i = 0;
    time_t ltime;

   printf("++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");


       bsp_begin(0);
       printf("Bom dia: meu bsp_pid eh: %d\n", bsp_pid());

       i = bspAllSums(bsp_pid());

       printf("******************** %d *******************\n", i);

       time( &ltime );
       printf("Tempo: %s\n",ctime( &ltime ));





   return 0;

  }





