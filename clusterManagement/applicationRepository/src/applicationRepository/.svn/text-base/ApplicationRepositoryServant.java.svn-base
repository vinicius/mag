package applicationRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import org.omg.CORBA.ORB;

import clusterManagement.ApplicationNotFoundException;
import clusterManagement.ApplicationRegistrationException;
import clusterManagement.ApplicationRepositoryOperations;
import clusterManagement.ApplicationRepositoryPOA;
import clusterManagement.BinaryCreationException;
import clusterManagement.BinaryNotFoundException;
import clusterManagement.DirectoryCreationException;
import clusterManagement.DirectoryNotEmptyException;
import clusterManagement.DirectoryNotFoundException;
import clusterManagement.FileIOException;
import clusterManagement.InvalidPathNameException;
import clusterManagement.SecurityException;
import dataTypes.ApplicationDescription;
import dataTypes.BinaryDescription;
import dataTypes.ContentDescription;

public class ApplicationRepositoryServant extends ApplicationRepositoryPOA {
	private ApplicationRepositoryOperations applicationRepository= null;
	
	public ApplicationRepositoryServant( ORB orb)
	{
		BufferedReader configFile = null;
		boolean secure=false;
		try{
			configFile = new BufferedReader(
					(new InputStreamReader
							(new FileInputStream(new File("apprepos.conf")))));		
			String line="";
			while((line = configFile.readLine()) != null){
				StringTokenizer st = new StringTokenizer(line);
				if (st.hasMoreTokens()) {
					String configOption = st.nextToken();
					if(configOption.equals("secureAppRepos")){
						if (st.hasMoreTokens())
							secure=st.nextToken().contentEquals(new StringBuffer("true"));
					}
				}
				
			}
			
			
		}catch(FileNotFoundException fnfe){
			System.err.println("AppRepos::ApplicationRepositoryServant->> Someone asked for an inexistent file");
			fnfe.printStackTrace();
			System.exit(-1);
		}catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(-1);
		}
		
		if(secure)
		{
			applicationRepository = new SecureApplicationRepositoryWrapper(new ApplicationRepositoryImpl(orb));
		}else
		{
			applicationRepository = new ApplicationRepositoryImpl(orb);
		}
	}
	
	public void createDirectory(String directoryName)
	throws  DirectoryCreationException, InvalidPathNameException, SecurityException {
		applicationRepository.createDirectory(directoryName);
	}
	
	public void removeDirectory(String directoryName)
	throws SecurityException, DirectoryNotFoundException, DirectoryNotEmptyException, InvalidPathNameException {
		applicationRepository.removeDirectory(directoryName);
	}
	
	public ContentDescription[] listDirectoryContents(String directoryName)
	throws SecurityException, DirectoryNotFoundException, InvalidPathNameException {
		
		return applicationRepository.listDirectoryContents(directoryName);
	}
	
	public void registerApplication(String directoryName, String applicationName)
	throws SecurityException, DirectoryCreationException, ApplicationRegistrationException, InvalidPathNameException {
		
		applicationRepository.registerApplication(directoryName, applicationName);
	}
	
	public void unregisterApplication(String directoryName,
			String applicationName) throws SecurityException, ApplicationNotFoundException,
			DirectoryNotFoundException, DirectoryNotEmptyException, InvalidPathNameException {
		
		applicationRepository.unregisterApplication(directoryName, applicationName);
	}
	
	public ApplicationDescription getApplicationDescription(String basePath,
			String applicationName) throws SecurityException, ApplicationNotFoundException,
			DirectoryNotFoundException, InvalidPathNameException {
		return applicationRepository.getApplicationDescription(basePath,applicationName);
	}
	
	public void uploadApplicationBinary(BinaryDescription binaryDescription,
			byte[] binaryCode) throws SecurityException, ApplicationNotFoundException,
			BinaryCreationException, DirectoryNotFoundException, InvalidPathNameException {
		
		applicationRepository.uploadApplicationBinary(binaryDescription, binaryCode);
		
	}
	
	public void deleteApplicationBinary(String basePath, String applicationName, String binaryName)
	throws SecurityException, ApplicationNotFoundException, DirectoryNotFoundException,
	BinaryNotFoundException, InvalidPathNameException {
		applicationRepository.deleteApplicationBinary( basePath, applicationName, binaryName);
	}
	
	public byte[] getApplicationBinary(String basePath, String applicationName, String binaryName) throws SecurityException, InvalidPathNameException, ApplicationNotFoundException, DirectoryNotFoundException, BinaryNotFoundException, FileIOException {
		return applicationRepository.getApplicationBinary( basePath, applicationName, binaryName);
	}
	
	public byte[] getRemoteApplicationBinary(String basePath, String applicationName, String binaryName,
			String applicationRepositoryIor) throws SecurityException, InvalidPathNameException, ApplicationNotFoundException, DirectoryNotFoundException, BinaryNotFoundException, FileIOException {
		return applicationRepository.getRemoteApplicationBinary(basePath, applicationName, binaryName,
				applicationRepositoryIor);
	}
	
}
