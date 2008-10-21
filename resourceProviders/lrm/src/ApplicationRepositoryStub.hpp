#ifndef ApplicationRepositoryStub_HPP
#define ApplicationRepositoryStub_HPP

#include <string>

struct lua_State;

using std::string;

  //IMPORTANT: This class is not multithread-safe. Users of this class must ensure that
  // 'state' is safely isolated when concurrently accessed
  class ApplicationRepositoryStub{

    private:

      struct lua_State * state;

    public:

      ApplicationRepositoryStub(lua_State * aState, const string & aAppReposIor);
      virtual ~ApplicationRepositoryStub(){} 

      virtual string getApplicationBinary(const string & basePath, 
            const string & applicationName, const string & binaryName);

      virtual string getRemoteApplicationBinary( const string & basePath, 
            const string & applicationName, const string & binaryName, const string & applicationRepositoryIor);
      
      //downloadApplication(const string & appId);
      //virtual string downloadRemoteApplication(const string & appId, const string & appReposId);
      string registerApplication(const string & appPath);

  };

#endif//ApplicationRepositoryStub_HPP


