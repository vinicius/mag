#include <iostream>
#include <unistd.h>

#include "Lupa.hpp"

int main(void) {
   Lupa lupa;
   string prompt = "lupa> ";
   char option;

   while (true) {
      std::cout << prompt;
      std::cin >> option;

      switch (option) {
      case 'q':
         return 0;
      case 'a':
         std::cout << lupa.canRunGridApplication() << std::endl;
         break;
      default:
         break;
      }
   }

   return 0;
}
