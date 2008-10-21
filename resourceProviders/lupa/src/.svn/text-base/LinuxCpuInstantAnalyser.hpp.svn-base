#ifndef LinuxCpuInstantAnalyser_HPP
#define LinuxCpuInstantAnalyser_HPP

#include "CpuInstantAnalyser.hpp"

/**
 * LinuxCpuInstantAnalyser - Utility class to measure instant cpu usage in the
 * Linux OS.
 *
 * @author Tiago Motta Jorge
 */
class LinuxCpuInstantAnalyser : public CpuInstantAnalyser {
private:
   /**
    * This methods reads from the /proc/stat.
    */
   virtual void setUsageValues(long *usage, long *totalUsage);
};

#endif
