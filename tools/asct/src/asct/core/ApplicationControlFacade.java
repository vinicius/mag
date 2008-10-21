/**
 * @(#)AsctFacade.java		Dec 20, 2005
 *
 * Copyleft
 */
package asct.core;

import clusterManagement.ApplicationNotFoundException;
import clusterManagement.ApplicationRegistrationException;
import clusterManagement.BinaryCreationException;
import clusterManagement.BinaryNotFoundException;
import clusterManagement.DirectoryCreationException;
import clusterManagement.DirectoryNotEmptyException;
import clusterManagement.DirectoryNotFoundException;
import clusterManagement.InvalidPathNameException;
import clusterManagement.SecurityException;
import asct.core.corba.AsctImpl;
import asct.core.corba.OrbHolder;
import asct.core.repository.ApplicationRepositoryManager;
import asct.core.submission.ExecutionRequestManager;
import asct.shared.ExecutionRequestData;
import asct.shared.ExecutionRequestStatus;
import asct.shared.IExecutionListener;
import dataTypes.ContentDescription;
import dataTypes.ExecutionRequestId;

/**
 * Client API to InteGrade.
 * 
 * @version 1.0 Dec 20, 2005
 * @author Eduardo Guerra and Eudenia Xavier
 */
public class ApplicationControlFacade {

	/** Manages the execution requestes. */
	private ExecutionRequestManager execManager;

	/** Manages the interaction with the application repository. */
	private ApplicationRepositoryManager appRepManager;

	/**
	 * Constructor.
	 * */
	public ApplicationControlFacade(String localDirectory) {
		OrbHolder orbHolder = new OrbHolder();
		orbHolder.initStubs();

		execManager = new ExecutionRequestManager(orbHolder, localDirectory);
		
		appRepManager = new ApplicationRepositoryManager(
				orbHolder.getApplicationRepositoryStubWrapper());
		
		orbHolder.setAsctImpl(new AsctImpl(execManager));
		
		new Thread(orbHolder).start();
		
		while (orbHolder.getIsRunning() == false) {
			try { Thread.sleep(100);} 
			catch (InterruptedException e) {}
		}
	}


	/** ************************** Submission **************************** */

	/**
	 * @param data all data needed for submition
	 * @return new request status
	 * @throws SecurityException 
	 */
	public ExecutionRequestStatus executeApplication(
				final ExecutionRequestData data) 
			throws ApplicationNotFoundException, DirectoryNotFoundException, 
				InvalidPathNameException, SecurityException {
		data.getApplication().setDescription(
				appRepManager.getAppDescription(data.getApplication()));
		
		return execManager.executeApplication(data);
	}

	/**
	 * @param data all data needed for submition
	 * @param binaryFileName name of the binary to be submited
	 * @return new request status
	 */
	public ExecutionRequestStatus executeBinary(
				final ExecutionRequestData data, final String binaryFileName) {
		return execManager.executeBinary(data, binaryFileName);
	}

	/**
	 * @param requestId id of the request to be killed
	 */
	public void killApplication(final String requestId) {
		execManager.killApplication(requestId);
	}

	/**
	 * 
	 * @param execReqId id of the request to get results
	 */
	public void getApplicationResults(final String execReqId) {
		execManager.getApplicationResults(execReqId);
	}
	
	/** ******************** Application Repository ************************ */

	/**
	 * @return contents of the application repository root directory
	 * @throws SecurityException 
	 * */
	public ContentDescription[] listRootDirectoryContents() 
			throws DirectoryNotFoundException, InvalidPathNameException, SecurityException{
		return appRepManager.listRootDirectoryContents();
	}

	
	/**
	 * @param directoryPath the relative path from the repository root 
	 * @return contents of the directory in the application repository
	 * @throws SecurityException 
	 */
	 public ContentDescription[] listDirectoryContents(
			 final String directoryPath) 
	 		throws DirectoryNotFoundException, InvalidPathNameException, SecurityException { 
		 return appRepManager.listDirectoryContents(directoryPath);
	 }
	 
	 /**
	  * @param directoryPath the relative path from the repository root 
	 * @throws SecurityException 
	  */
	 public void createDirectory(final String directoryPath) 
	 		throws DirectoryCreationException, InvalidPathNameException, SecurityException {
		 appRepManager.createDirectory(directoryPath);
	 }
	 
	 /**
	  * @param directoryPath the relative path from the repository root 
	 * @throws SecurityException 
	  */
	 public void removeDirectory(final String directoryPath)
	 			throws DirectoryNotFoundException, DirectoryNotEmptyException,
	 				InvalidPathNameException, SecurityException {
		 appRepManager.removeDirectory(directoryPath);
	 }

	 /**
	  * @param basePath the relative path from the repository root 
	  * @param applicationName the name of the application
	 * @throws SecurityException 
	  */
	 public void registerApplication(final String basePath, 
			 		final String applicationName) 
	 			throws ApplicationRegistrationException, 
					DirectoryCreationException, InvalidPathNameException, SecurityException {
		 appRepManager.registerApplication(basePath, applicationName);
	 }
	 
	 /**
	  * @param basePath the relative path from the repository root 
	  * @param applicationName the name of the application
	 * @throws SecurityException 
	  */
	 public void unregisterApplication(final String basePath, 
		 			final String applicationName) 
	 			throws ApplicationNotFoundException, DirectoryNotFoundException,
	 				DirectoryNotEmptyException, InvalidPathNameException, SecurityException {
		 appRepManager.unregisterApplication(basePath, applicationName);
	 }
	 
	/**
	  * @param localFilePath the local path of the file to be uploaded 
	  * @param remoteBasePath the relative path from the repository root 
	  * @param applicationName the name of the application
	  * @param platform os name plus hardware (eg. Linux_i686)
	 * @throws SecurityException 
	 */
	public void uploadBinary(final String localFilePath, 
				final String remoteBasePath, final String applicationName, 
				final String platform) 
			throws BinaryCreationException, ApplicationNotFoundException,
					DirectoryNotFoundException, InvalidPathNameException, SecurityException {
		appRepManager.uploadBinary(localFilePath, remoteBasePath, 
				applicationName, platform);
	}

	/**
	  * @param basePath the relative path from the repository root 
	  * @param applicationName the name of the application
	  * @param fileName the name of the file to be deleted
	 * @throws SecurityException 
	 */
	public void deleteBinary(final String basePath, 
				final String applicationName, final String fileName) 
			throws ApplicationNotFoundException, DirectoryNotFoundException, 
					BinaryNotFoundException, InvalidPathNameException, SecurityException {
		appRepManager.deleteBinary(basePath, applicationName, fileName);
	}

	/** ************************** Outros ********************************** */

	/**
	 * 
	 * @param listener a listener to execution requests
	 */
	public void registerExecutionStateListener(
				final IExecutionListener listener) {
		execManager.registerExecutionStateListener(listener);
	}

}
