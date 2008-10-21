#ifndef LrmSkeleton_HPP
#define LrmSkeleton_HPP

#include <string>

class ExecutionSpecs;
class LrmImpl;
class Config;
struct lua_State;

using std::string;

  //IMPORTANT: This class is not multithread-safe. Users of this class must ensure that
  // 'state' is safely isolated when concurrently accessed
  class LrmSkeleton{

    private:

      //Fields--------------------------------------------------------------------------
      struct lua_State * serverSideState; /**< lua_sstate representing the server side*/
      LrmImpl * lrmImpl_;
      string lrmIor_; //FIXME: Needed?
      static LrmSkeleton * singleInstance_; /**< singleton */



      //Methods-------------------------------------------------------------------------

      LrmSkeleton(LrmImpl * lrmImpl, const Config & config);

      //     Wrappers to servant method implementation(Required by Lua)
      //     Needed because O2 must call a LrmImpl method when a remote call
      //     arrives. The problem is that we MUST register a function pointer
      //     in Lua RT in order to lua call LrmImpl. In C++, the only way to
      //     do that is via a class method (We cannot pass a pointer to a member
      //     function). So, the instance (LrmImpl is a singleton) is aceesed
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
      static int requestExecutionWrapper(struct lua_State * state);

      //TODO: COMMENT ME
      static int requestOutputFilesWrapper(struct lua_State * state);

      //TODO: COMMENT ME
      static int killProcessWrapper(struct lua_State * state);
      
      /**
        Wrapper for servant implementation method.

        @param state - A Lua state sent by lua RT containing any parameters
        received by the remote call.
      */
      static int getLastCheckpointNumberWrapper(struct lua_State * state);
      
      /**
        Wrapper for servant implementation method.

        @param state - A Lua state sent by lua RT containing any parameters
        received by the remote call.
      */
      static int restartExecutionWrapper(struct lua_State * state);

    public:

      static LrmSkeleton & init(LrmImpl * lrmImpl, const Config & config);
      static LrmSkeleton & singleInstance(){ return *LrmSkeleton::singleInstance_; }

      const string & lrmIor() const{ return lrmIor_; }


  };

#endif//LrmSkeleton_HPP

