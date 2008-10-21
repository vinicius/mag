#include <iostream>
#include "BasicData.hpp"

int main(void) {
   BasicData *basicDataOld = new BasicData(300, 3, 300, 104);
   BasicData *basicData = new BasicData(*basicDataOld);

   delete basicDataOld;
   delete basicData;

   return 0;
}
