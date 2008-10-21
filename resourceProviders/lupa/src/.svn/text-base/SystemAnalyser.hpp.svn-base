#ifndef SystemAnalyser_HPP
#define SystemAnalyser_HPP

class CpuInstantAnalyser;

/**
 * This class is a Facade for the OS specific ways of getting hardware usage
 * measurements, like CPU usage, memory, disk etc.
 *
 * @author Tiago Motta Jorge
 */
class SystemAnalyser {
public:
   SystemAnalyser();

   ~SystemAnalyser();

   /**
    * Returns the usage of the cpu in percentage. Note: the first measure isn't
    * accurate. From the second call ahead, the value becomes good. It's up to
    * the caller to define the sampling interval.
    */
   float getCpuUsage();

   /**
    * Returns the idle usage of the cpu in percentage. Note: the first 
    * measure isn't accurate. From the second call ahead, the value 
    * becomes good. It's up to the caller to define the sampling interval.
    */
   float getCpuIdleUsage();
private:
   CpuInstantAnalyser *cpuInstantAnalyser;  /**< Cpu measurement's responsible. */
};

#endif
