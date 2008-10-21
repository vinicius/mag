#include "Condition.hpp"

#include<iostream>

using namespace std;

  //-------------------------------------------------------------------
  Condition::Condition():
                        cond(false){

    pthread_mutex_init(&condLock, NULL);
    pthread_cond_init(&condCond, NULL);

  }

  //-------------------------------------------------------------------
  void Condition::wait(){


    pthread_mutex_lock(& condLock);
      while (! cond)
        pthread_cond_wait(& condCond, & condLock);
      cond = false;
    pthread_mutex_unlock(& condLock);

  }

  //-------------------------------------------------------------------
  void Condition::signal(){

    pthread_mutex_lock(& condLock);
      cond = true;
      pthread_cond_signal(& condCond);
    pthread_mutex_unlock(& condLock);

  }
