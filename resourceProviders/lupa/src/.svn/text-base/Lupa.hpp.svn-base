#ifndef Lupa_HPP
#define Lupa_HPP

#include "UsagePredictor.hpp"
#include "GlobalDataBank.hpp"
#include "DataCollector.hpp"
#include "PersistenceManager.hpp"

/**
 * Lupa - Local Usage Pattern Analyser
 *
 * This class is a facade to a complex system. It makes a prediction based on
 * its current collected data to tell wether or not a grid application can run
 * without disturbing the host.
 *
 * @author Tiago Motta Jorge
 */
class Lupa {
public:
   /**
    * When a Lupa is instantiated, it starts collecting data immediately.
    */
   Lupa();

   /**
    * When a Lupa is deleted, it stops collecting data.
    */
   ~Lupa();
   
   /**
    * The paramaters passed to this method tells how much of the resources of
    * this node the application will need to run.
    *
    * @param withThisCpu - use a value between 0.0 .. 100.0, indicating how much
    * cpu the application incoming from the grid will need.
    */
   bool canRunGridApplication(int withThisRam = 32, float withThisCpu = 50.0, 
      double withThisDisk = 0);

   void incomingGridProcess();

private:
   void startCollectingData();
   void stopCollectingData();

   GlobalDataBank globalDataBank;
   UsagePredictor usagePredictor;
   DataCollector dataCollector;
   PersistenceManager persistenceManager;
};

#endif
