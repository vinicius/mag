#include "CpuUsage.hpp"
#include <fstream>
#include <unistd.h>
#include <sstream>
#include <iostream>//FIXME: REMOVE


CpuUsage * CpuUsage::singleInstance = 0;

void * CpuUsage::cpuLoopWrapper(void * ptr){
    CpuUsage * cpuUsage = (CpuUsage *) ptr;
    cpuUsage->cpuLoop();
    return NULL;
}

void CpuUsage::cpuLoop(){

    while(true){
         //(1) read "/proc/stat"
         //(2) wait for a while (currently 2 sec)
         //(3) read "/proc/stat"
         //(4) calculate diff of (3) and (1)

         string cpu; //Dummy, value discarded
         ifstream ifs("/proc/stat",ios::in);
         ifs >> cpu;
         ifs >> us;
         ifs >> usLow;
         ifs >> sy;
         ifs >> id;
         ifs.close();
         currTotal = us + usLow + sy + id;
         currUsed  = us + usLow + sy;

         //Mutex to avoid read vs. write problems
         pthread_mutex_lock( &mutex );
         util = (currUsed - prevUsed);
         util *= 100;
         util /= (currTotal - prevTotal);
         prevTotal = currTotal;
         prevUsed = currUsed;
         utilInInterval += util;
         numLoops++;
         pthread_mutex_unlock( &mutex );

         sleep(CPU_MEASURE_INTERVAL);
    }
}

float CpuUsage::utilization(){

    float cpuUsage;
    pthread_mutex_lock( &mutex );
    if(numLoops == 0)//Avoids division by zero in the begginig of execution
        cpuUsage = 0;
    else
        cpuUsage = (float) utilInInterval / numLoops;
    utilInInterval = 0;
    numLoops = 0;
    pthread_mutex_unlock( &mutex );

    return cpuUsage;
}


CpuUsage::CpuUsage():prevTotal(0),
                     prevUsed(0),
                     util(0.0),
                     utilInInterval(0.0),
                     numLoops(0){

    pthread_mutex_init(&mutex, NULL);
    pthread_create(&thread, NULL,(void * (*)(void *))
                   cpuLoopWrapper ,(void *) this );
    pthread_detach(thread);
}

CpuUsage & CpuUsage::getInstance(){

    if(singleInstance == NULL){
        singleInstance = new CpuUsage();
        return * singleInstance;
    }
    else
        return * singleInstance;
}




