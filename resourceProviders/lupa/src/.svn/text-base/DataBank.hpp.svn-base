#ifndef DataBank_HPP
#define DataBank_HPP

#include <iostream>
#include <list>

class BasicData;

using namespace std;

typedef std::list<BasicData> BasicDataList;
typedef std::list<BasicData>::const_iterator BasicDataListIterator; 

/**
 * This class represents a collection of BasicData objects. Many DataBanks will be
 * managed by the GlobalDataBank. The groupment of BasicDatas into DataBanks is
 * performed by the ClusteringStrategy of the GlobalDataBank. A DataBank can be
 * viewed as a cluster made by the associated ClusteringStrategy.
 *
 * @author Tiago Motta Jorge
 */
class DataBank {
   /**
    * Used to send DataBanks over a stream.
    *
    * Output format:
    *
    * -- DataBank begin --
    * <number of BasicDatas in this data bank>
    * <BasicData[1] format>
    * .
    * .
    * .
    * <BasicData[n] format>
    * -- DataBank end --
    */
   friend ostream &operator<<(ostream &outStream, const DataBank &dataBank);

   /**
    * Used to get a DataBank from a stream. The format to be read is the same as
    * mentioned above, but without the 'begin' and 'end' markers. Those markers
    * are intended to be used by a client class, so it can find a DataBank
    * object in a stream.
    *
    * It is up to the client class to 'eat' the markers.
    */
   friend istream &operator>>(istream &inStream, DataBank &dataBank);

public:
   void insertData(const BasicData &basicData);

   /**
    * Removes all DayDatas from this DataBank.
    */
   void clear();
   
   /**
    * Returns a const iterator to run over the DayData objects within this DataBank.
    * This iterator starts at the first DayData object within its DataBank.
    */
   BasicDataListIterator begin() const; 

   /**
    * Returns a const iterator at the first empty block of this DataBank. It can be
    * used to know when there are no more DayDatas.
    */
   BasicDataListIterator end() const; 

   /**
    * Returns the number of BasicDatas into this DataBank's collection.
    */
   int size() const;

   /**
    * Debug only.
    */
   void printData() const;

private:
   BasicDataList basicDataList;
};

#endif
