#include <iostream>
#include <unistd.h>
#include "DayData.hpp"
#include "BasicData.hpp"
#include "TimeUtility.hpp"
#include "SystemAnalyser.hpp"
#include "DataBank.hpp"

int main(void) {
   SystemAnalyser sa;
   DayData data(
      2, 
      TimeUtility::getCurrentDayOfWeek(), 
      TimeUtility::getCurrentDayOfYear(),
      TimeUtility::getCurrentYear()
   );
   DayData *data2;
   DataBank dataBank;

   for (int i = 0; i < 20; i++) {
     data.insertMeasure(sa.getCpuIdleUsage(), 
        TimeUtility::getCurrentHours(), 
        TimeUtility::getCurrentMinutes(), 
        TimeUtility::getCurrentSeconds()
     );

     sleep(1);
   }
   
   data2 = new DayData(data);

   dataBank.insertData(*(new BasicData(data)));
   dataBank.insertData(*(new BasicData(*data2)));
   
   delete data2;

   dataBank.printData();

   return 0;
}
