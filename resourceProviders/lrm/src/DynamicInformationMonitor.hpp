#ifndef DynamicInformationMonitor_HPP
#define DynamicInformationMonitor_HPP


#include "NodeStaticInformation.hpp"
#include "CpuUsage.hpp"

using namespace std;

#include <string>

/**

  DynamicInformationMonitor - Collects system's dynamic information.

  DynamicInformationMonitor queries the underlying OS for dynamic
  machine information, such as idle percentage of CPU,
  disk and memory. Currently, it only works in Linux, mainly
  due to the fact that there is no standardized
  way of getting this kind of information across different
  platforms.


  @author Andrei Goldchleger

*/

  class DynamicInformationMonitor {

   private:

     long freeRAM;    /**< free RAM space */
     long freeSwap;   /**< free swap space */
     long fsFree;     /**< free disk space */
     long filesFree;  /**< free File nodes in filesystem */
     long fsTotal;    /**< total available disk space(for the user executing the LRM) */
     long filesTotal; /**< total filenodes in filesystem */

     float currCpuUsage;  /**< holds current averaged CPU utilization */

     long prevFreeRAM;    /**< RAM availability at previous measurement */
     long prevFreeSwap;   /**< swap memory availability at previous measurement */
     long prevFsFree;     /**< free disk space at previous measurement */
     long prevFilesFree;  /**< previously available filenodes */
     long prevFsTotal;    /**< previously available disk space */
     long prevFilesTotal; /**< previously available filenodes in the filesystem */
     float prevCpuUsage;  /**< previously averaged CPU utilization */

     float threshold;     /**< percentage that makes a change significant */

     NodeStaticInformation & lsi; /**< reference to static info (to calculate percentages) */
     CpuUsage & cpuUsage; /**< Reference to a CpuUsage measuring object */


     /**
       Updates dynamic information.
     */
     void update();

     /**
       Tests if a significant change happened.
     */
     void testChange();

   public:

     /**
       Builds a DynamicInformationMonitor object

       @param _lsi - Reference to a LocalStaticInfo object(needed in order to calculate
       percentages of available resources).
       @param threshold - percentage of change that, when applied to subjects being
       measured(such as CPU), makes a given change significant (that is, one that
       should be reported to other parts of the system)
     */
     DynamicInformationMonitor(NodeStaticInformation & _lsi, float threshold);

     /**
      Reports if a significant change in resource availability has ocurred.

      A significant change is considered to be any variation in resource
      availability (CPU,Phys Memory, Swap) that exceeds the threshold
      (the one set at construction time).

     */
     bool hadSignificantChange();


     /**
       @return free RAM
     */
     long  getFreeRAM() const{ return freeRAM; }

     /**
       @return free swap
     */
     long  getFreeSwap() const{ return freeSwap; }

     /**
       @return available disk space
     */
     long  getFsFree() const{ return fsFree; }

     /**
       @return available filenodes
     */
     long  getFilesFree() const{ return filesFree; }

     /**
       @return total available disk space (for the user executing the LRM)
     */
     long  getFsTotal() const{ return fsTotal; }

     /**
       @return total available filenodes
     */
     long  getFilesTotal() const{ return filesTotal; }

     /**
       @return total available CPU usage
     */
     const float getCpuUsage() const{ return currCpuUsage; }

  };//class

#endif // DynamicInformationMonitor_HPP
