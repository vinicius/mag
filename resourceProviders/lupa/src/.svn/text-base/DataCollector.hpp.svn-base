#ifndef DataCollector_HPP
#define DataCollector_HPP

#include "TimeUtility.hpp"
#include "SystemAnalyser.hpp"

class GlobalDataBank;
class BasicData;

/**
 * Implements a data collector. When a peace of data is completely collected, 
 * often at the end of a day, it stores the brand new data into a GlobalDataBank. 
 * Its function is to collect data and send it to the GlobalDataBank. Its up to the
 * GlobalDataBank to know what to do with the data just received, like labeling
 * it as junk or good data info.
 *
 * @author Tiago Motta Jorge
 */
class DataCollector {
   /**
    * This is the function that is used to collect the data. It is used by the
    * collector thread.
    */
   friend void *collectData(void *dataCollector);

public:
   /**
    * @param collectInteval - the collect interval in seconds.
    */
   DataCollector(GlobalDataBank *globalDataBank, int collectInterval); 
   ~DataCollector(); 

   /**
    * Returns immediately. It starts up a thread that does the collecting job.
    */
   void startCollecting();

   /**
    * Stops the collecting job by killing its thread. Calling this method causes
    * the DataCollector to automatically save its current collected data in the
    * GlobalDataBank.
    *
    * Note: it is up to the GlobalDataBank to select the best data sent by the a
    * client, like the DataCollector.
    */
   void stopCollecting();

   /**
    * Returns a copy of the current collected BasicData.
    */ 
   BasicData getCurrentData();

   void setCurrentData(BasicData *data);

private:
   void refreshCurrentData();
   void createNewCurrentData();
   int decreaseDay(int day);
   
   bool killThread;
   int collectInterval;
   BasicData *currentData;
   GlobalDataBank *globalDataBank;
   SystemAnalyser systemAnalyser;
};

#endif
