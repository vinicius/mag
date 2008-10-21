#include "NodeStaticInformation.hpp"
#include <sys/utsname.h>
#include <sys/sysinfo.h>
#include <iostream>
#include <fstream>
#include <string>

//FIXME: To die on function's errors does not seem to be a good thing...

 
NodeStaticInformation::NodeStaticInformation() {
    init();
}

void NodeStaticInformation::init() {

    //Initializes with info obtained from uname
    struct utsname unameInfo;
    if( uname(&unameInfo) ){
        cerr << "LocStaticInfo::Error on call to uname" << endl;
        exit(1);
    }

    hostName = string(unameInfo.nodename);
    osName = string(unameInfo.sysname);
    osVersion = string(unameInfo.release);
    processorName = string(unameInfo.machine);

    //Initializes with information obtained from sysinfo(man 2 sysinfo)
    //FIXME: Only work in 2.3+ kernels
    struct sysinfo sysInfo;
    if(sysinfo(&sysInfo)){
        cerr << "LocStaticInfo::Error on call to sysinfo" << endl;
        exit(1);
    }
    totalRAM =  (sysInfo.totalram * sysInfo.mem_unit) / 1024;
    totalSwap = (sysInfo.totalswap * sysInfo.mem_unit) / 1024;

    //FIXME: Only one processor for now, but it is easy to extend
    string token; 
    ifstream ifs("/proc/cpuinfo",ios::in);
    while(true){
        ifs >> token;
        if(token.compare("MHz") == 0)
            break;
    }
    ifs >> token; //skips ':'
    ifs >> processorMhz;
    ifs.close();
}

