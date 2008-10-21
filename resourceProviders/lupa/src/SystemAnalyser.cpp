#include "SystemAnalyser.hpp"
#include "LinuxCpuInstantAnalyser.hpp"

SystemAnalyser::SystemAnalyser()
   : cpuInstantAnalyser(new LinuxCpuInstantAnalyser()) {
}

SystemAnalyser::~SystemAnalyser() {
   delete cpuInstantAnalyser;
}

float SystemAnalyser::getCpuUsage() {
   return cpuInstantAnalyser->getCpuUsage();
}

float SystemAnalyser::getCpuIdleUsage() {
   return cpuInstantAnalyser->getCpuIdleUsage();
}
