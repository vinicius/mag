#include <pthread.h>
#include <unistd.h>
#include <fstream>
#include <string>
#include <stdio.h>
#include "PersistenceManager.hpp"
#include "GlobalDataBank.hpp"
#include "DataCollector.hpp"
#include "BasicData.hpp"

using namespace std;

PersistenceManager::PersistenceManager(GlobalDataBank *gdb, DataCollector *dc)
   : globalDataBank(gdb), dataCollector(dc) {

   startPersistenceThread();
}

void PersistenceManager::persistData() {
   makeBackup();

   ofstream ofs("lupa.persisted", ios::out); 

   ofs << dataCollector->getCurrentData();
   ofs << *globalDataBank;
   
   ofs.close();
}

void PersistenceManager::loadData() {
   ifstream ifs("lupa.persisted", ios::in);
   BasicData *restoredData = new BasicData();
   string trash;

   if (!ifs) {
      cout << "Lupa: Creating new Lupa data base..." << endl;
   } else {
      // eats the '-- BasicData begin --' marker
      ifs >> trash >> trash >> trash >> trash;

      ifs >> *restoredData;

      // eats the '-- BasicData end --' marker
      ifs >> trash >> trash >> trash >> trash;

      // eats the '-- GlobalDataBank begin --' marker
      ifs >> trash >> trash >> trash >> trash;
      ifs >> *globalDataBank;

      ifs.close();

      dataCollector->setCurrentData(restoredData);

      cout << "Lupa: Lupa data base loaded ok!" << endl;
   }
}

void PersistenceManager::makeBackup() {
   ifstream in("lupa.persisted");

   if (in) {
      ofstream out("lupa.backup");
      out << in.rdbuf();
      out.close();
   }

   in.close();
}

void *persistLupaData(void *pManager) {
   PersistenceManager *persistenceManager = (PersistenceManager *) pManager;

   while (true) {
      sleep(PERSIST_INTERVAL);
      persistenceManager->persistData();
   }

   return NULL;
}

void PersistenceManager::startPersistenceThread() {
   pthread_t backupThread;

   pthread_create(&backupThread, NULL, persistLupaData, (void *) this);
}
