package asct.ui;

import java.io.File;
import java.util.StringTokenizer;

import asct.core.ApplicationControlFacade;
import asct.shared.AbstractGridApplication;
import asct.shared.BspGridApplication;
import asct.shared.ExecutionRequestData;
import asct.shared.ExecutionRequestStatus;
import asct.shared.IExecutionListener;
import asct.shared.ParametricCopyHolder;
import asct.shared.ParametricGridApplication;
import asct.shared.SequencialGridApplication;
import clusterManagement.ApplicationNotFoundException;
import clusterManagement.ApplicationRegistrationException;
import clusterManagement.BinaryCreationException;
import clusterManagement.BinaryNotFoundException;
import clusterManagement.DirectoryCreationException;
import clusterManagement.DirectoryNotEmptyException;
import clusterManagement.DirectoryNotFoundException;
import clusterManagement.InvalidPathNameException;
import clusterManagement.SecurityException;
import dataTypes.ApplicationDescription;
import dataTypes.ApplicationType;
import dataTypes.ContentDescription;

/**
 * A singleton controller responsible to:
 *  -> Create and remove: diretories, binaries, and applications within the ApplicationRepository through the ApplicationControlFacade; 
 *  -> Excecute and manage the execution os applications within the ApplicationRepository through the ApplicationControlFacade;
 * 
 * @version 1.0 Apr 26, 2006
 * @author Ricardo Luiz de Andrade Abrantes 
 */
public class ASCTController {

	/** singleton instance */
	private static ASCTController instance = null;
	/** ApplicationControlFacade */
	private ApplicationControlFacade facade = null;
	
	/** 
	 * Instance constructor 
	 */
	private ASCTController(String localDirectory) {
		facade = new ApplicationControlFacade(localDirectory);
	}

	/** 
	 * Gets a factory (singleton) instance
	 * TODO: The call to the ASCTController should not use those two static values (false,"/tmp").
	 * Instead, is should receive it from the caller of the getInstance() method.
	 * TODO: ASCT should preserve the output directory contents
     */
	public static synchronized ASCTController getInstance(){
		if (instance == null){
			cleanOutputDirectory();
			instance = new ASCTController(getOutputDirectory());
		}
		return instance;
	}
	
	private static void cleanOutputDirectory(){
		File outputDirectory = new File(getOutputDirectory());
		if (outputDirectory.isDirectory()) {
			String[] children = outputDirectory.list();
			for (int i=0; i<children.length; i++) {
				boolean success = deleteDir(new File(outputDirectory, children[i]));
                if (!success) {
                		System.out.println("Error cleaning the output directory");
                }
            }
		}
	}
    // Deletes all files and subdirectories under dir.
    // Returns true if all deletions were successful.
    // If a deletion fails, the method stops attempting to delete and returns false.
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // The directory is now empty so delete it
        return dir.delete();
    }
	public static String getOutputDirectory(){
		return new String(System.getProperty("user.home") + "/output/");
	}
	public void registerExecutionStateListener(IExecutionListener listener){
		facade.registerExecutionStateListener(listener);
	}
	
	public ContentDescription[] listRootDirectoryContents() 
	throws DirectoryNotFoundException, InvalidPathNameException, SecurityException{
		return facade.listRootDirectoryContents();
	}
	
	public ContentDescription[] listDirectoryContents(final String directoryPath) 
	 		throws DirectoryNotFoundException, InvalidPathNameException, SecurityException{
		return facade.listDirectoryContents(directoryPath);
	}
	/**
	  * @param directoryPath the relative path from the repository root 
	 * @throws SecurityException 
	  */
	 public void createDirectory(final String directoryPath) 
	 		throws DirectoryCreationException, InvalidPathNameException, SecurityException {
		facade.createDirectory(directoryPath);
	 }
	 
	 /**
	  * @param directoryPath the relative path from the repository root 
	 * @throws SecurityException 
	  */
	 public void removeDirectory(final String directoryPath)
	 			throws DirectoryNotFoundException, DirectoryNotEmptyException,
	 				InvalidPathNameException, SecurityException {
		 facade.removeDirectory(directoryPath);
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
		 facade.registerApplication(basePath, applicationName);
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
		 facade.unregisterApplication(basePath, applicationName);
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
		facade.uploadBinary(localFilePath, remoteBasePath, 
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
		facade.deleteBinary(basePath, applicationName, fileName);
	}

	/**
	 * Request the execution of an application
	 * @return new request status
	 * @throws SecurityException 
	 */
	public ExecutionRequestStatus executeApplication(String basePath, String applicationName, String applicationArguments, String applicationContraints, String applicationPreferences,ApplicationType applicationType, String binaryNames,String[] inputFiles, String[] outputFiles,ParametricCopyHolder[] parametricCopies, int numberOfTasks, boolean forceDifferentMachines) 
	throws ApplicationNotFoundException, DirectoryNotFoundException, InvalidPathNameException, SecurityException{
		ExecutionRequestStatus requestStatus = null;	
		StringTokenizer tokens = new StringTokenizer(binaryNames);
		int numberOfBinaries= tokens.countTokens();
		ExecutionRequestData data = new ExecutionRequestData();
		
		String binaryIds[] = new String[numberOfBinaries];
		for (int count = 0; count < numberOfBinaries; count++){		
			binaryIds[count] = tokens.nextToken();
		}
		
		AbstractGridApplication application;
		ApplicationDescription applicationDescrption = new ApplicationDescription(basePath,applicationName,(new Integer(numberOfBinaries)).toString(),binaryIds);
		if(applicationType.value() == ApplicationType.bsp.value()){
			application = new BspGridApplication(applicationName,basePath);
		}
		else if(applicationType.value() == ApplicationType.parametric.value()){
			application = new ParametricGridApplication(applicationName,basePath);
		}
		else {
			application = new SequencialGridApplication(applicationName,basePath);
		}
		application.setDescription(applicationDescrption);
		
		data.setApplication(application);
		data.setArguments(applicationArguments);
		data.setInputFiles(inputFiles);
		data.setOutputFileNames(outputFiles);
		data.setNumberOfTasks(numberOfTasks);
		data.setNumberOfCopies(parametricCopies.length);
		data.setParametricCopies(parametricCopies);
		data.setForceDifferentMachines(forceDifferentMachines);
		data.setConstraints(applicationContraints);
		data.setPreferences(applicationPreferences);
		requestStatus = facade.executeApplication(data);
		
		return requestStatus;
		
	}
	/**
	 * Kills the execution of an application
	 * @param requestId id of the request to be killed
	 */
	public void killApplication(final String requestId) {
		facade.killApplication(requestId);
	}
	
	/**
	 * Gets the results of an execution
	 * @param execReqId id of the request to get results
	 */
	public void getApplicationResults(String execReqId) {
		facade.getApplicationResults(execReqId);
	}
}
