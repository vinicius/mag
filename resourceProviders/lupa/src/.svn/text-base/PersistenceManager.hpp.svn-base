#ifndef PersistenceManager_HPP
#define PersistenceManager_HPP

#define PERSIST_INTERVAL 600 /**< persist thread interval in seconds */

class GlobalDataBank;
class DataCollector;

/**
 * Responsible for persisting and reloading the Lupa's data.
 *
 * @author Tiago Motta Jorge
 */
class PersistenceManager {
public:
   PersistenceManager(GlobalDataBank *gdb, DataCollector *dc);

   /**
    * Persists the Lupa's current GlobalDataBank and the current data being
    * collected by the Lupa's DataCollector.
    */
   void persistData();

   void loadData();

private:
   void makeBackup();
   void startPersistenceThread();

   GlobalDataBank *globalDataBank;
   DataCollector *dataCollector;
};

#endif
