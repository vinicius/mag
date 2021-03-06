#ifndef ExecutionManagerStub_HPP
#define ExecutionManagerStub_HPP

#include <string>
//#include <vector>

struct lua_State;

using std::string;
//using std::vector;

  //IMPORTANT: This class is not multithread-safe. Users of this class must ensure that
  // 'state' is safely isolated when concurrently accessed
  class ExecutionManagerStub{

  private:
    struct lua_State * state;

  public:
    ExecutionManagerStub(lua_State * aState, const string & execManagerIor);

    void setProcessExecutionStarted(const string & applicationId, 
                                    const string & processId,
				    //const string & replicaId, // Vinicius
                                    const string & lrmIor, 
                                    const int & restartId, 
                                    const string & executionId);
  
    int setProcessExecutionFinished(const string & applicationId, 
                                    const string & processId,
				    //const string & replicaId, // Vinicius
                                    const int & restartId,
                                    int exitStatus);
  };

#endif // ExecutionManagerStub_HPP


