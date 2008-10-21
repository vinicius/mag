#ifndef ArsmSkeleton_HPP
#define ArsmSkeleton_HPP

#include <string>

class ExecutionSpecs;
class ArscImpl;
class Config;
struct lua_State;

using std::string;

  //IMPORTANT: This class is not multithread-safe. Users of this class must ensure that
  // 'state' is safely isolated when concurrently accessed
  class ArscSkeleton{

    private:

      //Fields--------------------------------------------------------------------------
      struct lua_State * serverSideState; /**< lua_sstate representing the server side*/
      ArscImpl * arscImpl_;
      string arscIor_; //FIXME: Needed?
      static ArscSkeleton * singleInstance_; /**< singleton */



      //Methods-------------------------------------------------------------------------

      ArscSkeleton(ArscImpl * lsmImpl, const Config & config);

      //     Wrappers to servant method implementation(Required by Lua)
      //     Needed because O2 must call a ArscImpl method when a remote call
      //     arrives. The problem is that we MUST register a function pointer
      //     in Lua RT in order to lua call ArscImpl. In C++, the only way to
      //     do that is via a class method (We cannot pass a pointer to a member
      //     function). So, the instance (ArscImpl is a singleton) is aceesed
      //     via this class method.
      
            /**
        Wrapper for initServerSide(). Needed because of the way pthreads are launched.
      */
      static void * serverSideSetup(void * ptr);


      /**
        Wrapper for servant implementation method.

        @param state - A Lua state sent by lua RT containing any parameters
         received by the remote call.
      */
      static int setSampleIntervalWrapper(struct lua_State * state);

      /**
        Wrapper for servant implementation method.

        @param state - A Lua state sent by lua RT containing any parameters
        received by the remote call.
      */
      static int setKeepAliveIntervalWrapper(struct lua_State * state);

      /**
        Wrapper for servant implementation method.

        @param state - A Lua state sent by lua RT containing any parameters
        received by the remote call.
      */
      static int remoteExecutionRequestWrapper(struct lua_State * state);

      //TODO: COMMENT ME
      static int requestOutputFilesWrapper(struct lua_State * state);

      //TODO: COMMENT ME
      static int killWrapper(struct lua_State * state);

    public:

      static ArscSkeleton & init(ArscImpl * lsmImpl, const Config & config);
      static ArscSkeleton & singleInstance(){ return *ArscSkeleton::singleInstance_; }

      const string & lsmIor() const{ return arscIor_; }


  };

#endif//LsmSkeleton_HPP

