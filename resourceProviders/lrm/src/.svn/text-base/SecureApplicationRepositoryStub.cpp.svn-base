#include "SecureApplicationRepositoryStub.hpp"

SecureApplicationRepositoryStub::SecureApplicationRepositoryStub(lua_State * aState,
                                                                  const string & aAppReposIor,
 						  		   ArscImpl & arsc):ApplicationRepositoryStub(aState,aAppReposIor)
{
	arsc_ = &arsc;
	printf("Secure APP \n");
}


string SecureApplicationRepositoryStub::getApplicationBinary(const string & basePath, 
            const string & applicationName, const string & binaryName) 
{
    string signedBasePath=arsc_->requestSignature(basePath);
    string signedApplicationName=arsc_->requestSignature(applicationName);
    string signedBinaryName=arsc_->requestSignature(binaryName);    
    string signedApplication = ApplicationRepositoryStub::getApplicationBinary(signedBasePath, signedApplicationName, signedBinaryName);
    string app = arsc_->checkSignature(signedApplication);  
	return app;
}

string SecureApplicationRepositoryStub::getRemoteApplicationBinary( const string & basePath, 
            const string & applicationName, const string & binaryName, const string & applicationRepositoryIor){
	string signedBasePath=arsc_->requestSignature(basePath);
    string signedApplicationName=arsc_->requestSignature(applicationName);
    string signedBinaryName=arsc_->requestSignature(binaryName);    
    string signedApplicationRepositoryIor=arsc_->requestSignature(applicationRepositoryIor);
    string signedApplication = ApplicationRepositoryStub::getRemoteApplicationBinary(signedBasePath, signedApplicationName, signedBinaryName,signedApplicationRepositoryIor);
    string app = arsc_->checkSignature(signedApplication);  
	 return app;            	
            	
            	
            	
            	
            	
            	
}
      
	