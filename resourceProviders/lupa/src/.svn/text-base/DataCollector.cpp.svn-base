#include <pthread.h>
#include <unistd.h>
#include <stdlib.h>
#include "DataCollector.hpp"
#include "GlobalDataBank.hpp"
#include "BasicData.hpp"
#include "DayData.hpp"

DataCollector::DataCollector(GlobalDataBank *gdt, int cInterval)
   : collectInterval(cInterval), globalDataBank(gdt) {

   killThread = false;
   currentData = NULL;
}

DataCollector::~DataCollector() {
   stopCollecting();

   if (currentData != NULL) {
      delete currentData;
   }
}

void DataCollector::startCollecting() {
   pthread_t collectingThread;

   pthread_create(&collectingThread, NULL, 
      collectData, 
      (void *) this);
}

void DataCollector::stopCollecting() {
   killThread = true;
}

BasicData DataCollector::getCurrentData() {
   return *currentData;
}

void DataCollector::setCurrentData(BasicData *data) {
   currentData = data;
}

void DataCollector::refreshCurrentData() {
   BasicData *tempData = NULL;

   /* First Lupa execution or lupa.persisted file not found */ 
   if (currentData == NULL) {
      createNewCurrentData();
      return;
   } 

   /* Still collecting current day's data */
   if (currentData->isAtToday()) {
      return;
   }
       
   /* Day changed, so save the old data */
   globalDataBank->insertData(*currentData);

   /* If it isn't a so old data, complete the BasicData info for last 24 hours. */
   if (currentData->isFromYesterday()) {
      tempData = currentData;
      currentData = new BasicData(currentData->getCurrentDayData());

      delete tempData;
      tempData = NULL;
   } else {
      /* It can be a very old data... */ 
      createNewCurrentData();
   }
}

void DataCollector::createNewCurrentData() {

   if (currentData != NULL) {
      delete currentData;
   }

   currentData = new BasicData(
      collectInterval, 
      TimeUtility::getCurrentDayOfWeek(),
      TimeUtility::getCurrentDayOfYear(),
      TimeUtility::getCurrentYear()
   );
}

void *collectData(void *dCollector) {
   DataCollector *dataCollector = (DataCollector *) dCollector;

   dataCollector->refreshCurrentData();

   while (true) {

      if (dataCollector->killThread) {
         pthread_exit(NULL);
      }

      dataCollector->refreshCurrentData();

      dataCollector->currentData->insertMeasure(
         dataCollector->systemAnalyser.getCpuUsage(),
         TimeUtility::getCurrentHours(), 
         TimeUtility::getCurrentMinutes(),
         TimeUtility::getCurrentSeconds()
      );

      sleep(dataCollector->collectInterval);
   }

   return NULL;
}
