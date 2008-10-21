#ifndef CpuInstantAnalyser_HPP
#define CpuInstantAnalyser_HPP

/**
 * CpuInstantAnalyser - Utility abstract class to measure instant cpu usage.
 *
 * This class was designed to measure the cpu usage in a instant way, by the
 * will of the client. It is meant that the platform dependent implementation
 * of this class will be instantiated.
 *
 * @author Tiago Motta Jorge
 */
class CpuInstantAnalyser {
public:
   CpuInstantAnalyser();

   /**
    * Returns the usage of the cpu in percentage. Note: the first measure isn't
    * accurate. From the second call ahead, the value becomes good. It's up to
    * the caller to define the sampling interval.
    */
   virtual float getCpuUsage();

   /**
    * Returns the idle usage of the cpu in percentage. The note for the
    * getCpuUsage() method also holds for this one.
    */
   virtual float getCpuIdleUsage();

private:
   /**
    * Gathers system info and put it into the arguments. This method is
    * highly OS dependent.
    *
    * @param usage - will have the user + nice + system usage time.
    * @param totalUsage - will have usage + idle time.
    */
   virtual void setUsageValues(long *usage, long *totalUsage) = 0;

   float previousUsage;       /**< Contains the previous (user + nice + system) time  */
   float previousTotalUsage;  /**< Contains the (previousUsage + previous idle) time  */
};

#endif
