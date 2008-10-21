#include <stdlib.h>
#include <sys/time.h>
#include <time.h>

#include "TimeUtility.hpp"

TimeUtility::TimeUtility() {
}

int TimeUtility::getCurrentHours() {
   struct tm *currentTime = getUpdatedCurrentTime();

   return currentTime->tm_hour;
}

int TimeUtility::getCurrentMinutes() {
   struct tm *currentTime = getUpdatedCurrentTime();

   return currentTime->tm_min;
}

int TimeUtility::getCurrentSeconds() {
   struct tm *currentTime = getUpdatedCurrentTime();
   
   return currentTime->tm_sec;
}

int TimeUtility::getCurrentDayOfWeek() {
   struct tm *currentTime = getUpdatedCurrentTime();

   return currentTime->tm_wday;
}

int TimeUtility::getCurrentDayOfYear() {
   struct tm *currentTime = getUpdatedCurrentTime();

   return currentTime->tm_yday;
}

int TimeUtility::getCurrentYear() {
   struct tm *currentTime = getUpdatedCurrentTime();

   return currentTime->tm_year;
}

int TimeUtility::decreaseDayOfWeek(int day) {

   if (day == 0) {
      return 6;
   } else {
      return day - 1;
   }
}

int TimeUtility::decreaseDayOfYear(int day) {

   if (day == 0) {
      return 365;
   } else {
      return day - 1;
   }
}

struct tm *TimeUtility::getUpdatedCurrentTime() {
   struct timeval t;
   
   gettimeofday(&t, NULL);

   return localtime(&(t.tv_sec));
}
