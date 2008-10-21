
#ifndef CpuUsage_HPP
#define CpuUsage_HPP

#include <pthread.h>
#include <string>

using namespace std;

/**
  CpuUsage - Utility class to measure Cpu Usage.

  This class was designed to constantly measure CPU utilization
  on Linux systems. Basically, it starts a thread that periodically
  gathers information from the OS (via /proc), and reports the last
  average CPU utilization when queried.

  CpuUsage is a singleton, since ther is no reason to have more than
  one CPU measurer in our system.

  @author Andrei Goldchleger

*/

class CpuUsage {

    private:
      static const int CPU_MEASURE_INTERVAL = 10; /**< time between CPU usage measurements*/

      //TODO:Extracted variables from 2k. Don't have a clue about them :-)
      unsigned long us;
      unsigned long usLow;
      unsigned long sy;
      unsigned long id;
      unsigned long currTotal;
      unsigned long currUsed;
      unsigned long prevTotal;
      unsigned long prevUsed;
      double util;             /**< CPU utilization in current interval */

      double utilInInterval;   /**< diference bettween current and last measurement */
      int numLoops;

      pthread_t thread;        /**< thread that keeps collecting CPU availability */
      pthread_mutex_t mutex;   /**< protects CPU utilization from race conditions */

      /**
        CPU Measurement function (an infinite loop).

      */
      void cpuLoop();
      
      /**
        cpuLoop wrapper. Needed because pthreads require a function pointer
      */
      static void * cpuLoopWrapper(void * ptr);
      
      static CpuUsage * singleInstance; /**< CpuUsage singleton */
      
      /**
        Constructor.
      */
      CpuUsage();

    public:

       /**
         Returns average CPU utilization.

         Returns a float in the range [0..100] representing the
         average CPU utilization since last call.
       */
       float utilization();
       
       /**
         Returns a reference to CpuUsage's singleton.
       */
       static CpuUsage & getInstance();
};

#endif //CpuUsage_HPP

