package core.wrappers;

/**
 Interface AppReposWrapper - Interface of ApplicationRepository wrapper
 
 @author Rafael Fernandes Lopes
 */
public interface AppReposWrapper {
	/**
	 Retrieve bytecode of an application from the ApplicationRepository
	 
	 @param applicationId - Id of application that must be retrieved
	 @return serialized bytecode of null if appId was not found
	 */
	public byte [] getApplication(String basePath, String applicationName, String binaryName);
}
