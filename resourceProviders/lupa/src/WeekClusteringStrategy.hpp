#ifndef WeekClusteringStrategy_HPP
#define WeekClusteringStrategy_HPP

#include "ClusteringStrategy.hpp"
#include "GlobalDataBank.hpp"

/**
 * This class implements a simple Week clustering algorithm. Each day of the
 * week has one and only one DayData.
 *
 * @author Tiago Motta Jorge
 */
class WeekClusteringStrategy : public ClusteringStrategy {
public:
   WeekClusteringStrategy(GlobalDataBank *globalDataBank);

   virtual void buildClusters();

   virtual void insertData(const BasicData &basicData);

   virtual void setPredictionStrategyAndDataOf(UsagePredictor *up);

private:
   DataBankListIterator getDataBankIteratorAt(int thisDayOfWeek);

   GlobalDataBank *globalDataBank;
};

#endif 
