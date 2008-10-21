#ifndef UsagePredictor_HPP
#define UsagePredictor_HPP

#include "WeekPredictionStrategy.hpp"

class GlobalDataBank;
class PredictionStrategy;
class DataBank;

/**
 * This class performs the prediction of the node usage. It delegates its
 * prediction algorithm to the PredictionStrategy. The ClusteringStrategy at the
 * GlobalDataBank is the responsible for setting the PredictionStrategy. It also
 * sends the data needed to perform the prediction.
 * 
 * @author Tiago Motta Jorge
 */
class UsagePredictor {
public:
   UsagePredictor(GlobalDataBank *globalDataBank);
   ~UsagePredictor();

   bool canRunGridApplication(int withThisRam, float withThisCpu,
      double withThisDisk);

   void setWeekPredictionStrategy();

   void predictWithThisData(DataBank *dataBank);

   DataBank *getPredictionDataBank();

private:
   GlobalDataBank *globalDataBank;
   DataBank *predictionDataBank;
   PredictionStrategy *predictionStrategy;
   WeekPredictionStrategy weekPredictionStrategy;
};

#endif
