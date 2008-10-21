#include "WeekPredictionStrategy.hpp"
#include "UsagePredictor.hpp"
#include "DataBank.hpp"
#include "TimeUtility.hpp"
#include "BasicData.hpp"
#include "DayData.hpp"

bool WeekPredictionStrategy::canRunGridApplication(UsagePredictor *predictor,
   int withThisRam, float withThisCpu, double withThisDisk) {

   DataBank *data = predictor->getPredictionDataBank();
   int currentHours = TimeUtility::getCurrentHours();
   int currentMinutes = TimeUtility::getCurrentMinutes();
   int currentSeconds = TimeUtility::getCurrentSeconds();
   double predictedCpuUsage = 0.0;  
   double measure = 0.0;
   int count = 0;
   

   for (BasicDataListIterator i = data->begin(); i != data->end(); i++) {
      measure = i->getMeasure(currentHours, currentMinutes, currentSeconds);
      
      if (measure != INVALID_DATA) {
         predictedCpuUsage += measure;
         count++;
      }
   } 

   #ifdef DEBUG
   cout << "count: " << count << endl;
   cout << "predicted: " << predictedCpuUsage << endl;
   cout <<  (100.0 - (predictedCpuUsage / count)) << endl;
   cout << "withThisCpu: " << withThisCpu << endl;
   #endif

   return (count != 0) && ((100.0 - (predictedCpuUsage / count)) >= withThisCpu);
}
