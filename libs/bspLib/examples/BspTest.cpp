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


  /**
    Our basic test application.
  */
  int main(){

   cerr << "++++++++++++++++++++++++++++++++++++++++++++++++++++++" << endl;

    int i = 0;

    try{
       bsp_begin(0);
       cerr << "Bom dia: meu bsp_pid eh: " <<  bsp_pid() << endl;

       i = bspAllSums(bsp_pid());

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





