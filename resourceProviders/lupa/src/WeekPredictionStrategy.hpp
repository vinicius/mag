#ifndef WeekPredictionStrategy_HPP
#define WeekPredictionStrategy_HPP

#include "PredictionStrategy.hpp"

class WeekPredictionStrategy : public PredictionStrategy {
public:
   virtual bool canRunGridApplication(UsagePredictor *predictor, 
      int withThisRam, float withThisCpu, double withThisDisk);
};

#endif
