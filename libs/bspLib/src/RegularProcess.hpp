#ifndef RegularProcess_HPP
#define RegularProcess_HPP

#include "BaseProcess.hpp"

#include <string>
#include <map>

class BspProxyStubPool;
class DrmaManager;
class BsmpManager;



  class RegularProcess: public BaseProcess{

    private:

      std::string processZeroIor_;
      int processId_;

    public:

      RegularProcess(BspProxyStubPool * stubPool,
                                 DrmaManager * drmaManager,
                                 BsmpManager * bsmpManager,
                                 const std::string & processZeroIor,
                                 int processId);

      void registerOtherProcessIors(map<int, std::string> processInfo);

      void bspLocalSynch();

      void bspSynchDone();
      
      void bspBegin();
      
      

  };

#endif//RegularProcess_HPP







