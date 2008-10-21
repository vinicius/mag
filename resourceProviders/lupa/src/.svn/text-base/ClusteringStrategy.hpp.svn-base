#ifndef ClusteringStrategy_HPP
#define ClusteringStrategy_HPP

class BasicData;
class GlobalDataBank;
class UsagePredictor;

/**
 * Responsible for managing the DataBanks within the GlobalDataBank.
 *
 * @author Tiago Motta Jorge
 */
class ClusteringStrategy {
public:
   /**
    * Uses all the data contained in the GlobalDataBank and classify then into
    * clusters. It is used to reorganize the BasicDatas into DataBanks within 
    * the GlobalDataBank.
    */
   virtual void buildClusters() = 0;

   virtual void insertData(const BasicData &basicData) = 0;

   virtual void setPredictionStrategyAndDataOf(UsagePredictor *up) = 0;
};

#endif
