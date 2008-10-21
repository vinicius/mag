#include <iostream>
#include <string>
#include "DataBank.hpp"
#include "BasicData.hpp"

using namespace std;

ostream &operator<<(ostream &outStream, const DataBank &dataBank) {
   outStream << "-- DataBank begin --" << endl;
   outStream << dataBank.basicDataList.size() << endl;

   for (BasicDataListIterator i = dataBank.begin(); i != dataBank.end(); i++) {
      outStream << *i;
   }

   outStream << "-- DataBank end --" << endl;

   return outStream;
}

istream &operator>>(istream &inStream, DataBank &dataBank) {
   BasicData tempBasicData;
   int numberOfBasicDatas;
   string trash;

   dataBank.clear();

   inStream >> numberOfBasicDatas;

   while (numberOfBasicDatas--) {

      // eats the '-- BasicData begin --' marker
      inStream >> trash >> trash >> trash >> trash;

      inStream >> tempBasicData;
      dataBank.insertData(tempBasicData);

      // eats the '-- BasicData end --' marker
      inStream >> trash >> trash >> trash >> trash;
   }

   return inStream;
}

void DataBank::printData() const {

   for (BasicDataListIterator i = begin(); i != end(); i++) {
      i->printData();
   }
}

void DataBank::insertData(const BasicData &basicData) {
   basicDataList.push_back(basicData); 
}

void DataBank::clear() {
   basicDataList.clear();
}

BasicDataListIterator DataBank::begin() const {
   return basicDataList.begin();
}

BasicDataListIterator DataBank::end() const {
   return basicDataList.end();
}

int DataBank::size() const {
   return basicDataList.size();
}
