#ifndef DayData_HPP
#define DayData_HPP

#define INVALID_DATA -1

#include <iostream>

using namespace std;

/**
 * Represents the measurements made in a day. It's a smart ADT that
 * calculates its size by the collectInterval that is passed to its constructor.
 * It also gives a measure given its time and a measure can also be inserted
 * with the time it was collected.
 *
 * @author Tiago Motta Jorge
 */
class DayData {
   /**
    * Used to send DayDatas over a stream.
    * 
    * Output format:
    *
    * -- DayData begin --
    * dayOfWeek
    * dayOfYear
    * year
    * arraySize 
    * collectInterval
    * measure[0]
    * .
    * .
    * .
    * measure[n]
    * -- DayData end --
    */
   friend ostream &operator<<(ostream &outStream, const DayData &dayData);

   /**
    * Used to get a DayData from a stream. The format to be read is the same as
    * mentioned above, but without the 'begin' and 'end' markers. Those markers
    * are intended to be used by a client class, so it can find a DayData
    * object in a stream.
    *
    * It is up to the client class to 'eat' the markers.
    *
    * The passed dayData will be realocated, so it can manage the stream's DayData 
    * contents.
    */
   friend istream &operator>>(istream &inStream, DayData &dayData);

public:
   /**
    * Constructs a DayData object with the appropriate size to store the number
    * of measures that can be made with the interval passed in the argument.
    *
    * @param collectInterval - the collect interval in seconds.
    * @param dayOfWeek - the day of the week that this data was collected. Its
    *                    code is the same used by the TimeUtility class.
    * @param dayOfYear - the day of the year that this data was collected. Its
    *                    code is the same used by the TimeUtility class.
    * @param year      - the year that this data was collected. Its
    *                    code is the same used by the TimeUtility class.
    */
   DayData(int collectInterval, int dayOfWeek, int dayOfYear, int year);
   
   /**
    * Copy constructor.
    */
   DayData(const DayData &other);
   
   /**
    * Overloaded attribution operator.
    */
   const DayData &operator=(const DayData &other);

   ~DayData();   

   void insertMeasure(double measure, 
      short hours, short minutes, short seconds);

   double getMeasure(short hours, short minutes, short seconds) const;

   int getDayOfWeek() const;
   int getDayOfYear() const;
   int getYear() const;
   int getCollectInterval() const;

   bool isAtToday() const;
   bool isFromYesterday() const;

   /**
    * Debug only.
    */
   void printData() const;

private:
   int calculateSizeBasedOn(int collectInterval);
   int convertTimeToPosition(short hours, short minutes, short seconds) const;
   
   double *measures;     /**< Array of Measure's */
   int dayOfWeek;        /**< Tells the week's day of collection: 0 -> sunday .. 6 -> saturday */ 
   int dayOfYear;        /**< 0 .. 365 */ 
   int year;             /**< year less 1900 */ 
   int arraySize;      
   int collectInterval;
};

#endif
