#ifndef BasicData_HPP
#define BasicData_HPP

#include <iostream>

class DayData;

using namespace std;

/**
 * This is the basic ADT that is used all over the Lupa component. It is an
 * abstraction of a 48 hour collected info. The operational methods of this ADT
 * are simply a delegation to the currentDayData DayData object.
 *
 * @author Tiago Motta Jorge
 */
class BasicData {
   /**
    * Used to send BasicDatas over a stream.
    *
    * Output format:
    *
    * -- BasicData begin --
    * <DayData format for the lastDayData field>
    * <DayData format for the currentDayData field>
    * -- BasicData end --
    */
   friend ostream &operator<<(ostream &outStream, const BasicData &basicData);

   /**
    * Used to get a BasicData from a stream. The format to be read is the same as
    * mentioned above, but without the 'begin' and 'end' markers. Those markers
    * are intended to be used by a client class, so it can find a BasicData
    * object in a stream.
    *
    * It is up to the client class to 'eat' the markers.
    */
   friend istream &operator>>(istream &inStream, BasicData &basicData);

public:
   BasicData();

   BasicData(const DayData &lastDayData, const DayData &currentDayData);

   /**
    * Creates a BasicData with an empty currentDayData and the lastDayData being
    * a copy of the parameter DayData.
    */
   BasicData(const DayData &lastDayData);

   /**
    * Look at the doc of the DayData constructor to see what each paramater
    * means.
    */
   BasicData(int collectInterval, int dayOfWeek, int dayOfYear, int year);

   /**
    * Copy constructor.
    */
   BasicData(const BasicData &other);

   /**
    * Overloaded attribution operator.
    */
   const BasicData &operator=(const BasicData &other);

   ~BasicData();

   void insertMeasure(double measure,
      short hours, short minutes, short seconds);

   double getMeasure(short hours, short minutes, short seconds) const;

   int getDayOfWeek() const;
   int getDayOfYear() const;
   int getYear() const;

   void setLastDayData(const DayData &lastDayData);
   void setCurrentDayData(const DayData &currentDayData);

   DayData getCurrentDayData();

   bool isAtToday() const;
   bool isFromYesterday() const;

   /**
    * Debug only.
    */
   void printData() const;

private:
   DayData *lastDayData;
   DayData *currentDayData;
};

#endif
