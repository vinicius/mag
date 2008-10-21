#include "SystemAnalyser.hpp"
#include <iostream>
#include <unistd.h>

using namespace std;

int main(void) {
   SystemAnalyser sa;

   while (1) {
      cout << "-------------------" << endl;
      cout << sa.getCpuUsage() << endl;

      sleep(1);
   }

   return 0;
}
