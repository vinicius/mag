#ifndef GrmStub_HPP
#define GrmStub_HPP

#include <string>

class NodeStaticInformation;
class DynamicInformationMonitor;
class ExecutionSpecs;
struct lua_State;

using std::string;

  //IMPORTANT: This class is not multithread-safe. Users of this class must ensure that
  // 'state' is safely isolated when concurrently accessed
  class GrmStub {

    private:

      struct lua_State * state;
      string grmIor;

    public:

      GrmStub(lua_State * aState, const string & grmIor);
      void registerLrm(const string & lrmIor, const NodeStaticInformation  & lsi);
      void updateLrmInformation(const string & lrmIor, const DynamicInformationMonitor  & ldi);
  };

#endif//GrmStub_HPP


