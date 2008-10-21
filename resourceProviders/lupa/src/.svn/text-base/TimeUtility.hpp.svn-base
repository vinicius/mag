#ifndef TimeUtility_HPP
#define TimeUtility_HPP

#include <time.h>

/**
 * An abstraction of the OS's way of getting the current time.
 *
 * This class has no associated state. So all of its methods can be called
 * without instantiating it.
 *
 * @author Tiago Motta Jorge
 */
class TimeUtility {
public:
   static int getCurrentHours();
   static int getCurrentMinutes();
   static int getCurrentSeconds();

   /**
    * 0 -> sunday .. 6 -> saturday
    */
   static int getCurrentDayOfWeek();

   /**
    * 0 .. 365
    */
   static int getCurrentDayOfYear();

   /**
    * Year less 1900
    */
   static int getCurrentYear();

   static int decreaseDayOfWeek(int day);
   static int decreaseDayOfYear(int day);

private:
   TimeUtility();
   static struct tm *getUpdatedCurrentTime();
};

#endif
