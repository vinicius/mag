extern "C" {
#include <sys/time.h>
  //#include <fcntl.h>
  //#include <stdlib.h>
  //#include <unistd.h>
  //#include <stdio.h>
}

#ifndef CKP_TIMER
#define CKP_TIMER

class CheckpointTimer {

  double lastCkpTime;
  double ckpIntervalTime;
  
public:

  CheckpointTimer(int ckpInterval = 60);
  ~CheckpointTimer();

  void ckpInterval(int ckpInterval) {ckpIntervalTime = (double)ckpInterval;}
  int  ckpInterval() {return (int)ckpIntervalTime;}

  bool testFinishedInterval();
};

#endif
