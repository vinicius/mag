#ifndef _SECUREAPPLICATIONREPOSITORYSTUB_HPP_
#define _SECUREAPPLICATIONREPOSITORYSTUB_HPP_
#include <iostream>
#include <fstream>

#include "../../../shared/arsc/C++/src/ArscImpl.hpp"
#include "../../../shared/arsc/C++/src/MessagePacket.hpp"
#include "ApplicationRepositoryStub.hpp"


class SecureApplicationRepositoryStub: public ApplicationRepositoryStub
{
private:
	ArscImpl * arsc_;
public:
	SecureApplicationRepositoryStub(lua_State * aState,
					const string & aAppReposIor, 
					ArscImpl & arsc);
	string getApplicationBinary(const string & basePath, 
            const string & applicationName, const string & binaryName);
    string getRemoteApplicationBinary( const string & basePath, 
            const string & applicationName, const string & binaryName, const string & applicationRepositoryIor);
      
	
};

#endif //_SECUREAPPLICATIONREPOSITORYSTUB_HPP_
