#ifndef NodeStaticInformation_HPP
#define NodeStaticInformation_HPP

#include <string>

using namespace std;

/**

  NodeStaticInformation - Collects system's static information.

  NodeStaticInformation queries the underlying OS for static
  machine information, such as OS version, CPU class
  and so on. Currently, it only works in Linux, mainly
  due to the fact that there is no standardized
  way of getting this kind of information across different
  platforms.


  @author Andrei Goldchleger

*/

  class NodeStaticInformation {

    private:

     string hostName;        
     string osName;
     string osVersion;
     string processorName;
     int processorMhz;
     long totalRAM;
     long totalSwap;


    public:

      NodeStaticInformation();

      /**
        Reads node data
      */
      void init();

      /**
        Returns the machine's host name
      */
      const char * getHostName() const{return hostName.c_str(); }

      /**
        Returns the machine's OS name(e.g. linux)
      */
      const char * getOsName() const{return osName.c_str(); }

      /**
        Returns the machine's OS version
      */
      const char * getOsVersion() const{return osVersion.c_str(); }

      /**
        Returns the machine's CPU name
      */
      const char * getProcessorName() const{return processorName.c_str(); }

      /**
        Returns the CPU clock
      */
      int getProcessorMhz() const{return processorMhz; }

      /**
        Returns the total amount of RAM, in bytes
      */
      long getTotalRAM() const{return totalRAM; }

      /**
        Returns the total amount of swap memory, in bytes
      */
      long getTotalSwap() const{return totalSwap; }

  };

#endif // NodeStaticInformation_HPP

