#include "LinuxCpuInstantAnalyser.hpp"
#include <fstream>
#include <string>

using namespace std;

void LinuxCpuInstantAnalyser::setUsageValues(long *usage, long *totalUsage) {
   string trash;
   long user, nice, system, idle;
   ifstream ifs("/proc/stat", ios::in);

   ifs >> trash;

   ifs >> user;
   ifs >> nice;
   ifs >> system;
   ifs >> idle;

   ifs.close();

   *usage = user + nice + system;
   *totalUsage = *usage + idle;
}
