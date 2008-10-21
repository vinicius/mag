package asct.core.corba;

import clusterManagement.*;
import clusterManagement.SecurityException;
import dataTypes.*;

public class ApplicationRepositoryStubWrapper extends Thread 
					implements ApplicationRepositoryOperations {
	
	/**  */
	protected ApplicationRepository applicationRepository_;

	/**
	 * Constructor.
	 * */
	public ApplicationRepositoryStubWrapper(ApplicationRepository applicationRepository) {
		applicationRepository_ = applicationRepository;
	}

	public ContentDescription[] listDirectoryContents(String directoryName)
			throws DirectoryNotFoundException, InvalidPathNameException, SecurityException {
	
			return applicationRepository_.listDirectoryContents(directoryName);
	}

	public void createDirectory(String directoryName)
			throws DirectoryCreationException, InvalidPathNameException, SecurityException {
		applicationRepository_.createDirectory(directoryName);
	}
	
	public void removeDirectory(String directoryName)
			throws DirectoryNotFoundException, DirectoryNotEmptyException,
				InvalidPathNameException, SecurityException {
		applicationRepository_.removeDirectory(directoryName); 
	}
	
	public void registerApplication(String basePath, String applicationName)
			throws ApplicationRegistrationException, DirectoryCreationException, 
				InvalidPathNameException, SecurityException {
		applicationRepository_.registerApplication(basePath, applicationName);

	}

	public void unregisterApplication(String basePath, String applicationName)
			throws ApplicationNotFoundException, DirectoryNotFoundException,
				DirectoryNotEmptyException, InvalidPathNameException, SecurityException {
		applicationRepository_.unregisterApplication(basePath, applicationName);

	}
	
	public void uploadApplicationBinary(BinaryDescription binaryDescription, byte[] binaryCode)
			throws BinaryCreationException, ApplicationNotFoundException,
				DirectoryNotFoundException, InvalidPathNameException, SecurityException {
		applicationRepository_.uploadApplicationBinary(binaryDescription, binaryCode);
		
	}
	
	public void deleteApplicationBinary(String basePath, String applicationName,
			String binaryName) throws ApplicationNotFoundException,
			DirectoryNotFoundException, BinaryNotFoundException,
			InvalidPathNameException, SecurityException {
		applicationRepository_.deleteApplicationBinary(basePath, applicationName, binaryName);

	}
	
	public ApplicationDescription getApplicationDescription(String basePath,
			String applicationName) throws ApplicationNotFoundException,
			DirectoryNotFoundException, InvalidPathNameException, SecurityException {
		return applicationRepository_.getApplicationDescription(basePath, applicationName);
	}

	public byte[] getApplicationBinary(String basePath, String applicationName,
			String binaryName) throws InvalidPathNameException, ApplicationNotFoundException, DirectoryNotFoundException, BinaryNotFoundException, FileIOException, SecurityException {
		return applicationRepository_.getApplicationBinary(basePath,applicationName,binaryName);
	}

	public byte[] getRemoteApplicationBinary(String basePath, 
				String applicationName, String binaryName, 
				String applicationRepositoryIor) throws InvalidPathNameException, ApplicationNotFoundException, DirectoryNotFoundException, BinaryNotFoundException, FileIOException, SecurityException {
		return applicationRepository_.getRemoteApplicationBinary(basePath, 
				applicationName, binaryName, 
				applicationRepositoryIor);
	}
}
