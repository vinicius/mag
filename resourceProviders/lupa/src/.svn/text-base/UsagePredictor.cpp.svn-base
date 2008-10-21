#include <stdlib.h>
#include "UsagePredictor.hpp"
#include "DataBank.hpp"
#include "GlobalDataBank.hpp"
#include "PredictionStrategy.hpp"
#include "BasicData.hpp"

UsagePredictor::UsagePredictor(GlobalDataBank *gdt) 
   : globalDataBank(gdt) {

   predictionStrategy = NULL;
   predictionDataBank = NULL;
}

UsagePredictor::~UsagePredictor() {

   if (predictionDataBank != NULL) {
      delete predictionDataBank;
   }
}

bool UsagePredictor::canRunGridApplication(int withThisRam, float withThisCpu, 
   double withThisDisk) {

   globalDataBank->setPredictionStrategyAndDataOf(this);

   if (predictionDataBank->size() == 0) {
      cerr << "Lupa: Insufficient data to predict... Allowing requests." << endl;
      return true;
   }

   return predictionStrategy->canRunGridApplication(this, withThisRam,
      withThisCpu, withThisDisk);
}

void UsagePredictor::setWeekPredictionStrategy() {
   predictionStrategy = &weekPredictionStrategy;
}

void UsagePredictor::predictWithThisData(DataBank *dataBank) {

   if (predictionDataBank != NULL) {
      delete predictionDataBank;
   }

   predictionDataBank = dataBank;
}

DataBank *UsagePredictor::getPredictionDataBank() {
   return predictionDataBank;
}
