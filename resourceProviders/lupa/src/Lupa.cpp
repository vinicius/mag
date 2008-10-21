#include "Lupa.hpp"

#define COLLECT_INTERVAL 300

Lupa::Lupa() 
   : usagePredictor(&globalDataBank), 
     dataCollector(&globalDataBank, COLLECT_INTERVAL),
     persistenceManager(&globalDataBank, &dataCollector) {

   persistenceManager.loadData();
   startCollectingData();
}

Lupa::~Lupa() {
   stopCollectingData();
   persistenceManager.persistData();
}

void Lupa::startCollectingData() {
   dataCollector.startCollecting();
}

void Lupa::stopCollectingData() {
   dataCollector.stopCollecting();
}

bool Lupa::canRunGridApplication(int withThisRam, float withThisCpu, 
   double withThisDisk) {
   
   return usagePredictor.canRunGridApplication(withThisRam, withThisCpu, 
      withThisDisk);
}

void Lupa::incomingGridProcess() {
}
