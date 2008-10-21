#ifndef ArsmStub_HPP
#define ArsmStub_HPP
#include <string>
class MessagePacket;
using std::string;

  //IMPORTANT: This class is not multithread-safe. Users of this class must ensure that
  // 'state' is safely isolated when concurrently accessed
  class ArsmStub{

    private:

      struct lua_State * state;

    public:
      ArsmStub(lua_State * aState, const string & arsmIor);
      ArsmStub(); 
      MessagePacket initiateContext(MessagePacket msggss);
      void finalizeContext(string id);
      MessagePacket selfTest(MessagePacket msgGSS);
  };

#endif//ArsmStub_HPP


