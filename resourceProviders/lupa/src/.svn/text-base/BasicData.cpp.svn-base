#include <iostream>
#include <string>
#include "BasicData.hpp"
#include "DayData.hpp"
#include "TimeUtility.hpp"

#define DEFAULT_COLLECT_INTERVAL 300
#define DEFAULT_YESTERDAY_DAY_OF_WEEK 0
#define DEFAULT_DAY_OF_WEEK 1
#define DEFAULT_DAY_OF_YEAR 0
#define DEFAULT_YEAR 1900

using namespace std;

ostream &operator<<(ostream &outStream, const BasicData &basicData) {
   outStream << "-- BasicData begin --" << endl;

   outStream << *(basicData.lastDayData);
   outStream << *(basicData.currentDayData);

   outStream << "-- BasicData end --" << endl;

   return outStream;
}

istream &operator>>(istream &inStream, BasicData &basicData) {
   string trash;

   /* eats the '-- DayData begin --' marker. */
   inStream >> trash >> trash >> trash >> trash;

   inStream >> *(basicData.lastDayData);

   /* eats the '-- DayData end --' marker. */
   inStream >> trash >> trash >> trash >> trash;

   /* eats the '-- DayData begin --' marker. */
   inStream >> trash >> trash >> trash >> trash;

   inStream >> *(basicData.currentDayData);

   /* eats the '-- DayData end --' marker. */
   inStream >> trash >> trash >> trash >> trash;

   return inStream;
}

BasicData::BasicData() 
   : lastDayData(new DayData(
        DEFAULT_COLLECT_INTERVAL, DEFAULT_YESTERDAY_DAY_OF_WEEK,
        DEFAULT_DAY_OF_YEAR, DEFAULT_YEAR)), 

     currentDayData(new DayData(
        DEFAULT_COLLECT_INTERVAL, DEFAULT_DAY_OF_WEEK,
        DEFAULT_DAY_OF_YEAR, DEFAULT_YEAR)) { 
}

BasicData::BasicData(const DayData &lastDayData, 
   const DayData &currentDayData) 

   : lastDayData(new DayData(lastDayData)), 
     currentDayData(new DayData(currentDayData)) {
}

BasicData::BasicData(const DayData &lastDayData) {
   int year = (lastDayData.getDayOfYear() == 365) 
      ? lastDayData.getYear() + 1
      : lastDayData.getYear();

   this->lastDayData = new DayData(lastDayData);

   currentDayData = new DayData(
      lastDayData.getCollectInterval(),
      (lastDayData.getDayOfWeek() + 1) % 7,
      (lastDayData.getDayOfYear() + 1) % 366,
      year
   );
}

BasicData::BasicData(int collectInterval, int dayOfWeek, int dayOfYear, int year) 
   : lastDayData(new DayData(
        collectInterval, 
        TimeUtility::decreaseDayOfWeek(dayOfWeek), 
        TimeUtility::decreaseDayOfYear(dayOfYear),
        year)
     ),

     currentDayData(new DayData(collectInterval, dayOfWeek, dayOfYear, year)) {
}

BasicData::BasicData(const BasicData &other) 
   : lastDayData(new DayData(*(other.lastDayData))),
     currentDayData(new DayData(*(other.currentDayData))) {
}

const BasicData &BasicData::operator=(const BasicData &other) {
   delete lastDayData;
   delete currentDayData;

   lastDayData = new DayData(*(other.lastDayData));
   currentDayData = new DayData(*(other.currentDayData));

   return *this;
}

BasicData::~BasicData() {
   delete lastDayData;
   delete currentDayData;
}

void BasicData::insertMeasure(double measure,
   short hours, short minutes, short seconds) {

   currentDayData->insertMeasure(measure, hours, minutes, seconds);
}

double BasicData::getMeasure(short hours, short minutes, short seconds) const {
   return currentDayData->getMeasure(hours, minutes, seconds);
}

int BasicData::getDayOfWeek() const {
   return currentDayData->getDayOfWeek();
}

int BasicData::getDayOfYear() const {
   return currentDayData->getDayOfYear();
}

int BasicData::getYear() const {
   return currentDayData->getYear();
}

void BasicData::setLastDayData(const DayData &lastDayData) {
   delete this->lastDayData;

   this->lastDayData = new DayData(lastDayData);
}

void BasicData::setCurrentDayData(const DayData &currentDayData) {
   delete this->currentDayData;

   this->currentDayData = new DayData(currentDayData);
}

DayData BasicData::getCurrentDayData() {
   return *currentDayData;
}

bool BasicData::isAtToday() const {
   return currentDayData->isAtToday();
}

bool BasicData::isFromYesterday() const {
   return currentDayData->isFromYesterday();
}

void BasicData::printData() const {
   lastDayData->printData();
   currentDayData->printData();
}
