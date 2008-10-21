#ifndef PredictionStrategy_HPP
#define PredictionStrategy_HPP

class UsagePredictor;

/**
 * Encapsulates the prediction algorithm.
 *
 * @author Tiago Motta Jorge
 */
class PredictionStrategy {
public:
   
   virtual bool canRunGridApplication(UsagePredictor *predictor, 
      int withThisRam, float withThisCpu, double withThisDisk) = 0;
};

#endif
