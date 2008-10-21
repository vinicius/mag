#include "CpuInstantAnalyser.hpp"
#include "LinuxCpuInstantAnalyser.hpp"
#include <iostream>
#include <unistd.h>

using namespace std;

int main(void) {
   CpuInstantAnalyser *cia = new LinuxCpuInstantAnalyser();

   while (1) {
      cout << "-------------------" << endl;
      cout << cia->getCpuIdleUsage() << endl;

      sleep(1);
   }

   return 0;
}
