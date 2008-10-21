#ifndef AsctStub_HPP
#define AsctStub_HPP

#include <string>


struct lua_State;
class BspInfo;

using std::string;


  class ExecutionManagerStub{

    private:

      struct lua_State * state;

    public:

      ExecutionManagerStub(const string & orbPath,
                           const string & resourceManagementIdlPath,
                           const string & aAsctIor);
      BspInfo *registerBspNode(const string & appId, const string & nodeId, 
                               const string & bspProxyIor);
      
      //FIXME: Lua_close in ~AsctStub?

  };

#endif//AsctStub_HPP


