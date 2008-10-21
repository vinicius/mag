#include <iostream>
#include "DayData.hpp"
#include "TimeUtility.hpp"

using namespace std;

ostream &operator<<(ostream &outStream, const DayData &dayData) {
   outStream << "-- DayData begin --" << endl;

   outStream << dayData.dayOfWeek << endl;
   outStream << dayData.dayOfYear << endl;
   outStream << dayData.year << endl;
   outStream << dayData.arraySize << endl;
   outStream << dayData.collectInterval << endl;

   for (int i = 0; i < dayData.arraySize; i++) {
      outStream << dayData.measures[i] << endl;
   }

   outStream << "-- DayData end --" << endl;

   return outStream;
}

istream &operator>>(istream &inStream, DayData &dayData) {
   delete [] dayData.measures;

   inStream >> dayData.dayOfWeek;
   inStream >> dayData.dayOfYear;
   inStream >> dayData.year;
   inStream >> dayData.arraySize;
   inStream >> dayData.collectInterval;

   dayData.measures = new double[dayData.arraySize];

   for (int i = 0; i < dayData.arraySize; i++) {
      inStream >> dayData.measures[i];
   }

   return inStream;
}

void DayData::printData() const {
   cout << "dayOfWeek = " << dayOfWeek << endl;
   cout << "dayOfYear = " << dayOfYear << endl;
   cout << "year = " << year << endl;
   cout << "arraySize = " << arraySize << endl;
   cout << "collectInterval = " << collectInterval << endl;
 
   for (int i = 0; i < arraySize; i++) {
      cout << "measures[" << i << "] = " << measures[i] << endl;
   }
} 

DayData::DayData(int cInterval, int dOfWeek, int dOfYear, int aYear) {
   collectInterval = cInterval;
   arraySize = calculateSizeBasedOn(collectInterval);
   measures = new double[arraySize]; 
   dayOfWeek = dOfWeek;
   dayOfYear = dOfYear;
   year = aYear;

   for (int i = 0; i < arraySize; i++) {
      measures[i] = INVALID_DATA;
   }
}

DayData::DayData(const DayData &other) {
   collectInterval = other.collectInterval;
   arraySize = other.arraySize;
   measures = new double[arraySize];
   dayOfWeek = other.dayOfWeek;
   dayOfYear = other.dayOfYear;
   year = other.year;
   
   for (int i = 0; i < arraySize; i++) {
      measures[i] = other.measures[i];
   }
}

const DayData &DayData::operator=(const DayData &other) {
   
   if (collectInterval != other.collectInterval) {
      delete [] measures;
      collectInterval = other.collectInterval;
      arraySize = other.arraySize;
      measures = new double[arraySize];
   }

   dayOfWeek = other.dayOfWeek;
   dayOfYear = other.dayOfYear;
   year = other.year;

   for (int i = 0; i < arraySize; i++) {
      measures[i] = other.measures[i];
   }
   
   return *this;
}

DayData::~DayData() {
   delete [] measures;
}

void DayData::insertMeasure(double measure, short hours,
   short minutes, short seconds) {
   
   double consistentMeasure = 0.0;
   int positionToInsert = convertTimeToPosition(hours, minutes, seconds);

   consistentMeasure = (measure >= 0.0) ? measure : 0.0;
   consistentMeasure = (consistentMeasure <= 100.0) ? consistentMeasure : 100.0;

   measures[positionToInsert] = consistentMeasure;
}

double DayData::getMeasure(short hours, short minutes, short seconds) const {
   int positionToLook = convertTimeToPosition(hours, minutes, seconds);

   return measures[positionToLook];
}

int DayData::getDayOfWeek() const {
   return dayOfWeek;
}

int DayData::getDayOfYear() const {
   return dayOfYear;
}

int DayData::getYear() const {
   return year;
}

int DayData::getCollectInterval() const {
   return collectInterval;
}

bool DayData::isAtToday() const {
   int currentYear = TimeUtility::getCurrentYear();
   int currentDayOfYear = TimeUtility::getCurrentDayOfYear();
   int currentDayOfWeek = TimeUtility::getCurrentDayOfWeek();

   return (year == currentYear) 
      && (dayOfYear == currentDayOfYear)
      && (dayOfWeek == currentDayOfWeek);
}

bool DayData::isFromYesterday() const {
   int currentDayOfYear = TimeUtility::getCurrentDayOfYear();

   return (dayOfYear == TimeUtility::decreaseDayOfYear(currentDayOfYear));
}

int DayData::calculateSizeBasedOn(int collectInterval) {
   int aDaySeconds = 86400;  /**< number of seconds in a day = 24 * 60 * 60 */

   return aDaySeconds / collectInterval;
}

int DayData::convertTimeToPosition(short hours, short minutes, short seconds)
   const {

   int consistentHours;
   int consistentMinutes;
   int consistentSeconds;

   consistentHours = ((hours >= 0) && (hours < 60)) ? hours : 0;
   consistentMinutes = ((minutes >= 0) && (minutes < 60)) ? minutes : 0;
   consistentSeconds = ((seconds >= 0) && (seconds < 60)) ? seconds : 0;
   
   return ((consistentHours * 60 * 60) + (consistentMinutes * 60) 
           + consistentSeconds) / collectInterval;
}
