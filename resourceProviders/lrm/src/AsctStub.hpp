#ifndef AsctStub_HPP
#define AsctStub_HPP

#include <string>
//#include <vector>

struct lua_State;

using std::string;
//using std::vector;

  //IMPORTANT: This class is not multithread-safe. Users of this class must ensure that
  // 'state' is safely isolated when concurrently accessed
  class AsctStub{

    private:

      struct lua_State * state;
      string asctIor_;

    public:

      const string & asctIor() {return asctIor_;}

      AsctStub(lua_State * aState, const string & aAsctIor);

      void setExecutionAccepted(const string & lrmIor,
                                const string & executionId,
                                const string & applicationId,
                                const string & processId);
				//const string & replicaId); // Vinicius

      void getInputFiles(const string & applicationId,
                         const string & processId,
			 //const string & replicaId, // Vinicius
                         const string & destinationPath);

      void setExecutionFinished(const string & applicationId,
                                const string & processId);
				//const string & replicaId); // Vinicius
  };

#endif//AsctStub_HPP


