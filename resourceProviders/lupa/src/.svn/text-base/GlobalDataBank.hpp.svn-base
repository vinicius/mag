#ifndef GlobalDataBank_HPP
#define GlobalDataBank_HPP

#include <iostream>
#include <list>

class BasicData;
class DataBank;
class ClusteringStrategy;
class UsagePredictor;

using namespace std;

typedef std::list<DataBank> DataBankList;
typedef std::list<DataBank>::iterator DataBankListIterator;

/**
 * Represents all the information collected. Its composed by DataBanks. This
 * class uses the Strategy pattern. It's up to a ClusteringStrategy to manage
 * the DataBanks within this class.
 *
 * @author Tiago Motta Jorge
 */
class GlobalDataBank {
   /**
    * Used to send an entire GlobalDataBank over a stream.
    *
    * Output format:
    *
    * -- GlobalDataBank begin --
    * <number of DataBanks in this data bank>
    * <DataBank[1] format>
    * .
    * .
    * .
    * <DataBank[n] format>
    * -- GlobalDataBank end --
    */
   friend ostream &operator<<(ostream &outStream, 
      GlobalDataBank &globalDataBank);

   /**
    * Used to get a GlobalDataBank from a stream. The format to be read is 
    * the same as mentioned above, but without the 'begin' and 'end' markers. 
    * Those markers are intended to be used by a client class, so it can find 
    * a GlobalDataBank object in a stream.
    *
    * It is up to the client class to 'eat' the markers.
    */
   friend istream &operator>>(istream &inStream, 
      GlobalDataBank &globalDataBank);

public:
   GlobalDataBank();
   ~GlobalDataBank();

   /**
    * Inserts a BasicData into the GlobalDataBank. This task is delegated to the
    * current ClusteringStrategy.
    */
   void insertData(const BasicData &basicData);

   void insertDataBank(const DataBank &dataBank);

   /**
    * Delegates to its clustering strategy. The clusteringStrategy will set the
    * predictionStrategy at the UsagePredictor and send the data it needs to
    * perform the prediction.
    */
   void setPredictionStrategyAndDataOf(UsagePredictor *up);

   /**
    * Removes all DataBanks from this GlobalDataBank.
    */
   void clear();

   /**
    * Returns an iterator to run over the DataBank objects within this
    * GlobalDataBank. This iterator starts at the first DataBank object 
    * within its GlobalDataBank.
    */
   DataBankListIterator begin();

   /**
    * Returns an iterator at the first empty block of this GlobalDataBank. 
    * It can be used to know when there are no more DataBanks.
    */
   DataBankListIterator end();

   /**
    * Returns a DataBank composed by all BasicDatas collected. In other words,
    * returns a copy of all BasicDatas within all DataBanks within this 
    * GlobalDataBank in a single DataBank. This method can be used by
    * clients that wants to rebuild the groups.
    */
   DataBank getUnclassifiedData();

   /**
    * Debug only.
    */
   void printData();

private:
   ClusteringStrategy *clusteringStrategy;
   DataBankList dataBankList;
};

#endif
