#include "DynamicInformationMonitor.hpp"

#include <sys/sysinfo.h>
#include <sys/vfs.h>
#include <iostream>
#include <sstream>
#include <cmath>

using namespace std;

DynamicInformationMonitor::DynamicInformationMonitor(NodeStaticInformation & _lsi,float _threshold):
                                                                threshold(_threshold),
                                                                lsi(_lsi),
                                                                cpuUsage(CpuUsage::getInstance()){
    update();
    update(); //Initializes prev and current
}

void DynamicInformationMonitor::update(){

  // Works with Linux 2.3+ kernels
  struct sysinfo sysInfo;
    if(sysinfo(&sysInfo)){
     cerr << "DynamicInformationMonitor::Error on call to sysinfo" << endl;
     exit(1);
  }

  prevFreeRAM = freeRAM;
  freeRAM =  ((sysInfo.freeram + sysInfo.bufferram) * sysInfo.mem_unit) / 1024;
  prevFreeSwap = freeSwap;
  freeSwap = (sysInfo.freeswap * sysInfo.mem_unit) / 1024;

  //Get disk information using statfs
  //FIXME: Don't considers large file systems (more than 32bit)
  struct statfs vfs;

  if(statfs(".", &vfs)){
    cerr << "DynamicInformationMonitor::Error on call to statfs" << endl;
    exit(1);
  }

  prevFsFree = fsFree;
  fsFree = (vfs.f_bavail / 1024) * (vfs.f_bsize / 1024) + 1;
  prevFilesFree = filesFree;
  filesFree = vfs.f_ffree;

  prevFsTotal = fsTotal;
  fsTotal = ((vfs.f_blocks - (vfs.f_bfree - vfs.f_bavail)) / 1024) * (vfs.f_bsize / 1024) + 1;

  prevFilesTotal = filesTotal;
  filesTotal = vfs.f_files;
  prevCpuUsage = currCpuUsage;
  currCpuUsage = cpuUsage.utilization();
}

void DynamicInformationMonitor::testChange(){

    cout << "DynamicInformationMonitor::testChange " << endl << endl;
    cout << "freeram:      " << freeRAM << endl;
    cout << "prevFreeRAM:      " << prevFreeRAM << endl;
    cout << "freeswap:     " << freeSwap << endl;
    cout << "prevFreeSwap:     " << prevFreeSwap << endl;
    cout << "currCpuUsage:     " <<  currCpuUsage << endl;
    cout << "prevCpuUsage:     " <<prevCpuUsage  << endl;

    if((fabs(((double) freeRAM - prevFreeRAM) ) * 100 ) / lsi.getTotalRAM()  > threshold)
        cout << "RAM changed" << endl ;
    if((fabs(((double) freeSwap - prevFreeSwap) ) * 100 ) / lsi.getTotalSwap() > threshold)
        cout << "SWAP changed" << endl ;
    if((fabs(((double) currCpuUsage - prevCpuUsage )) ) > threshold)
        cout << "CPU USAGE changed" << endl ;
}

bool DynamicInformationMonitor::hadSignificantChange(){

    update();

    if((fabs(((double) freeRAM - prevFreeRAM) ) * 100 ) / lsi.getTotalRAM()  > threshold)
        return true;
    else if((fabs(((double) freeSwap - prevFreeSwap )) * 100 ) / lsi.getTotalSwap() > threshold)
        return true;
    else if((fabs(((double) currCpuUsage - prevCpuUsage ))) > threshold)
        return true;
    return false;
}



