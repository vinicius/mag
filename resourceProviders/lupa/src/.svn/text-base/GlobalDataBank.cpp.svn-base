#include <iostream>
#include <string>
#include "GlobalDataBank.hpp"
#include "BasicData.hpp"
#include "DataBank.hpp"
#include "WeekClusteringStrategy.hpp"
#include "UsagePredictor.hpp"

using namespace std;

ostream &operator<<(ostream &outStream, GlobalDataBank &globalDataBank) {
   outStream << "-- GlobalDataBank begin --" << endl;
   outStream << globalDataBank.dataBankList.size() << endl;

   for (DataBankListIterator i = globalDataBank.begin(); i != globalDataBank.end(); i++) {
      outStream << *i;
   }

   outStream << "-- GlobalDataBank end --" << endl;

   return outStream;
}

istream &operator>>(istream &inStream, GlobalDataBank &globalDataBank) {
   DataBank tempDataBank;
   int numberOfDataBanks;
   string trash;

   globalDataBank.clear();

   inStream >> numberOfDataBanks;

   while (numberOfDataBanks--) {

      // eats the '-- DataBank begin --' marker
      inStream >> trash >> trash >> trash >> trash;

      inStream >> tempDataBank;
      globalDataBank.insertDataBank(tempDataBank);

      // eats the '-- DataBank end --' marker
      inStream >> trash >> trash >> trash >> trash;
   }

   return inStream;
}

GlobalDataBank::GlobalDataBank()
   : clusteringStrategy(new WeekClusteringStrategy(this)) {
}

GlobalDataBank::~GlobalDataBank() {
   delete clusteringStrategy;
}

void GlobalDataBank::insertData(const BasicData &basicData) {
   clusteringStrategy->insertData(basicData);
}

void GlobalDataBank::insertDataBank(const DataBank &dataBank) {
   dataBankList.push_back(dataBank);
}

void GlobalDataBank::setPredictionStrategyAndDataOf(UsagePredictor *up) {
   clusteringStrategy->setPredictionStrategyAndDataOf(up);
}

void GlobalDataBank::clear() {
   dataBankList.clear();
}

DataBankListIterator GlobalDataBank::begin() {
   return dataBankList.begin();
}

DataBankListIterator GlobalDataBank::end() {
   return dataBankList.end();
}

DataBank GlobalDataBank::getUnclassifiedData() {
   DataBank resultDataBank;

   for (DataBankListIterator i = begin(); i != end(); i++) {

      for (BasicDataListIterator j = i->begin(); j != i->end(); j++) {
         resultDataBank.insertData(*j);
      }
   }

   return resultDataBank;
}

void GlobalDataBank::printData() {

   for (DataBankListIterator i = begin(); i != end(); i++) {
      i->printData();
   }
}
