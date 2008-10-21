#include "CpuInstantAnalyser.hpp"

CpuInstantAnalyser::CpuInstantAnalyser() {
   previousUsage = 0;
   previousTotalUsage = 0;
}

float CpuInstantAnalyser::getCpuUsage() {
   long usage, totalUsage;
   double result;

   setUsageValues(&usage, &totalUsage);

   result = 100.0 * (usage - previousUsage) / (totalUsage - previousTotalUsage);

   previousUsage = usage;
   previousTotalUsage = totalUsage;

   return result;
}

float CpuInstantAnalyser::getCpuIdleUsage() {
   return 100.0 - getCpuUsage();
}
