#include "CheckpointTimer.hpp"
#include <iostream>
#include <ctime>

using namespace std;

//-------------------------------------------------------------------------
CheckpointTimer::CheckpointTimer(int ckpInterval) {

  struct timeval lastCkpTimeval;
  ckpIntervalTime = (double)ckpInterval;
  gettimeofday(&lastCkpTimeval, NULL);  
  double bef  = (double)lastCkpTimeval.tv_sec;
  double befu = (double)lastCkpTimeval.tv_usec;
  lastCkpTime = (bef + befu/1000000.);
}

CheckpointTimer::~CheckpointTimer() {}

bool CheckpointTimer::testFinishedInterval() {

  struct timeval currCkpTimeval;
  gettimeofday(&currCkpTimeval, NULL);  
  double bef  = (double)currCkpTimeval.tv_sec;
  double befu = (double)currCkpTimeval.tv_usec;
  double currCkpTime = (bef + befu/1000000.);
  
  if ( (currCkpTime - lastCkpTime) > ckpIntervalTime ) {
    lastCkpTime = currCkpTime;
    return 1;
  }

  return 0;
  
}

