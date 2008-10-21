#include <stdlib.h>
#include "WeekClusteringStrategy.hpp"
#include "BasicData.hpp"
#include "DataBank.hpp"
#include "GlobalDataBank.hpp"
#include "UsagePredictor.hpp"
#include "TimeUtility.hpp"

WeekClusteringStrategy::WeekClusteringStrategy(GlobalDataBank *globalDataBank) {
   this->globalDataBank = globalDataBank;
}

void WeekClusteringStrategy::buildClusters() {
   DataBank allData = globalDataBank->getUnclassifiedData(); 

   globalDataBank->clear();

   for (BasicDataListIterator i = allData.begin(); i != allData.end(); i++) {
      insertData(*i);
   }
}

void WeekClusteringStrategy::insertData(const BasicData &basicData) {
   DataBankListIterator i = getDataBankIteratorAt(basicData.getDayOfWeek());
   DataBank *newDataBank = NULL;

   if (i != NULL) {
      i->insertData(basicData);
   } else {
      newDataBank = new DataBank();
      newDataBank->insertData(basicData);
      globalDataBank->insertDataBank(*newDataBank); 

      delete newDataBank;
   }
} 

void WeekClusteringStrategy::setPredictionStrategyAndDataOf(
   UsagePredictor *up) {

   int currentDay = TimeUtility::getCurrentDayOfWeek();
   DataBank *newDataBank = new DataBank();
   DataBank tempDataBank;
   DataBankListIterator tempDataBankIterator;

   up->setWeekPredictionStrategy();   

   for (int i = TimeUtility::decreaseDayOfWeek(currentDay), count = 3; count > 0; count--) {
      tempDataBankIterator = getDataBankIteratorAt(i);

      if (tempDataBankIterator != NULL) { 
         tempDataBank = *tempDataBankIterator;
         newDataBank->insertData(*(tempDataBank.begin()));
      }

      i = TimeUtility::decreaseDayOfWeek(i);
   }

   up->predictWithThisData(newDataBank);
}

DataBankListIterator WeekClusteringStrategy::getDataBankIteratorAt(
   int thisDayOfWeek) {

   for (DataBankListIterator i = globalDataBank->begin(); i != globalDataBank->end(); i++) {

      if (i->begin()->getDayOfWeek() == thisDayOfWeek) {
         return i;
      }
   }

   return NULL;
}
