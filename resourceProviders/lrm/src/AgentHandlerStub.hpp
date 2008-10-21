#ifndef AgentHandlerStub_HPP
#define AgentHandlerStub_HPP
#include <assert.h>
#include <string>

#include "ExecutionSpecs.hpp"

struct lua_State;

using std::string;

  //IMPORTANT: This class is not multithread-safe. Users of this class must ensure that
  // 'state' is safely isolated when concurrently accessed
  class AgentHandlerStub{

    private:

      struct lua_State * state;

    public:

      AgentHandlerStub(lua_State * aState, const string & ahIor);
//      void remoteExecutionRequest(const string & appId, ExecutionSpecs & execSpecs);
      void remoteExecutionRequest(ExecutionSpecs & execSpecs);

  };

#endif//AgentHandlerStub_HPP
