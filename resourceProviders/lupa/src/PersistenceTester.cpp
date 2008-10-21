#include <fstream>
#include <string>
#include "DayData.hpp"
#include "BasicData.hpp"
#include "TimeUtility.hpp"
#include "DataBank.hpp"
#include "GlobalDataBank.hpp"

using namespace std;

int main(void) {
   DayData dayData3(14400, 5, 300, 104);
   DayData dayData2(7200, 3, 301, 104);
   DayData dayData(
      3600, 
      TimeUtility::getCurrentDayOfWeek(), 
      TimeUtility::getCurrentDayOfYear(),
      TimeUtility::getCurrentYear()
   );
   BasicData basicData(dayData, dayData2);
   BasicData basicData2(dayData3, dayData3);
   DataBank dataBank;
   DataBank dataBank2;
   GlobalDataBank globalDataBank;
   GlobalDataBank globalDataBank2;
   string trash;

// GlobalDataBank output/input test
   dataBank.insertData(basicData);
   dataBank.insertData(basicData2);

   globalDataBank.insertDataBank(dataBank);
   globalDataBank.insertDataBank(dataBank);

   ofstream ofs("globalDataBank.persisted", ios::out);

   ofs << globalDataBank;
   ofs.close();

   ifstream ifs("globalDataBank.persisted", ios::in);

   // eats the '-- DataBank begin --' marker
   ifs >> trash >> trash >> trash >> trash;

   ifs >> globalDataBank2;
   ifs.close();

   globalDataBank2.printData();

/*
// DataBank output/input test
   dataBank.insertData(basicData);
   dataBank.insertData(basicData2);

   ofstream ofs("dataBank.persisted", ios::out);

   ofs << dataBank;
   ofs.close();

   ifstream ifs("dataBank.persisted", ios::in);

   // eats the '-- DataBank begin --' marker
   ifs >> trash >> trash >> trash >> trash;

   ifs >> dataBank2;
   ifs.close();

   dataBank2.printData();
*/

/*
// BasicData output/input test

   ofstream ofs("basicData.persisted", ios::out);

   ofs << basicData;
   ofs.close();

   ifstream ifs("basicData.persisted", ios::in);

   // eats the '-- BasicData begin --' marker
   ifs >> trash >> trash >> trash >> trash;

   ifs >> basicData2;
   ifs.close();

   basicData2.printData();
*/ 

/* 
// DayData output/input test
   ofstream ofs("dayData.persisted", ios::out);
   
   ofs << dayData;
   ofs.close();

   ifstream ifs("dayData.persisted", ios::in);

   // eats the '-- DayData begin --' marker
   ifs >> trash >> trash >> trash >> trash;

   ifs >> dayData2;
   ifs.close();

   dayData2.printData();
*/
   
   return 0;
}
