/**
 * 
 */
package asct.core.corba;

import arsc.ArscImpl;
import clusterManagement.ApplicationNotFoundException;
import clusterManagement.ApplicationRegistrationException;
import clusterManagement.ApplicationRepository;
import clusterManagement.BinaryCreationException;
import clusterManagement.BinaryNotFoundException;
import clusterManagement.DirectoryCreationException;
import clusterManagement.DirectoryNotEmptyException;
import clusterManagement.DirectoryNotFoundException;
import clusterManagement.FileIOException;
import clusterManagement.InvalidPathNameException;
import clusterManagement.SecurityException;
import clusterManagement.SignatureCheckingException;
import clusterManagement.SignatureRequestException;
import dataTypes.ApplicationDescription;
import dataTypes.BinaryDescription;
import dataTypes.ContentDescription;

/**
 * @author Braga
 *
 */
public class SecureApplicationRepositoryWrapper extends
		ApplicationRepositoryStubWrapper {
    private ArscImpl arsc_=null;	
    
    public SecureApplicationRepositoryWrapper(ApplicationRepository applicationRepository, ArscImpl arsc)
    {
    	  super(applicationRepository);
      applicationRepository_ = applicationRepository;
      arsc_=arsc;
    }

	public ContentDescription[] listDirectoryContents(String directoryName)
			throws DirectoryNotFoundException, InvalidPathNameException, SecurityException {
		try {
			directoryName = arsc_.requestSignature(directoryName);
		} catch (SignatureRequestException e) {
			throw new SecurityException(e.toString());
		}
		ContentDescription[] returnContents = applicationRepository_.listDirectoryContents(directoryName);
    		for(int i =0 ; i < returnContents.length;i++)
		{
			try {
				returnContents[i].fileName = arsc_.checkSignature(returnContents[i].fileName);
			} catch (SignatureCheckingException e) {
				throw new SecurityException(e.toString());
			}
			//returnContents[i].kind = arsc_.checkSignature(returnContents[i].kind);
		}
		return returnContents;
	}

	public void createDirectory(String directoryName)
			throws DirectoryCreationException, InvalidPathNameException, SecurityException {
		try {
			directoryName=arsc_.requestSignature(directoryName);
		} catch (SignatureRequestException e) {
			throw new SecurityException(e.toString());
		} 
		applicationRepository_.createDirectory(directoryName);
	}

	public void removeDirectory(String directoryName)
			throws DirectoryNotFoundException, DirectoryNotEmptyException,
			InvalidPathNameException, SecurityException {
		try {
			directoryName=arsc_.requestSignature(directoryName);
		} catch (SignatureRequestException e) {
			throw new SecurityException(e.toString());
		}
		applicationRepository_.removeDirectory(directoryName);
	}

	public void registerApplication(String basePath, String applicationName)
			throws ApplicationRegistrationException,
			DirectoryCreationException, InvalidPathNameException, SecurityException {
		try{
			basePath=arsc_.requestSignature(basePath);
			applicationName=arsc_.requestSignature(applicationName);
		}catch(SignatureRequestException e)
		{
			throw new SecurityException(e.toString());
		}
		applicationRepository_.registerApplication(basePath, applicationName);

	}

	public void unregisterApplication(String basePath, String applicationName)
			throws ApplicationNotFoundException, DirectoryNotFoundException,
			DirectoryNotEmptyException, InvalidPathNameException, SecurityException {
		try {
			basePath=arsc_.requestSignature(basePath);
			applicationName=arsc_.requestSignature(applicationName);
		} catch (SignatureRequestException e) {
			throw new SecurityException(e.toString());
		}

		applicationRepository_.unregisterApplication(basePath, applicationName);

	}

	public void uploadApplicationBinary(BinaryDescription binaryDescription, byte[] binaryCode)
			throws BinaryCreationException, ApplicationNotFoundException,
			DirectoryNotFoundException, InvalidPathNameException, SecurityException {
		try{
			binaryCode = arsc_.requestSignature(binaryCode);
			binaryDescription.applicationName=arsc_.requestSignature(binaryDescription.applicationName);
			binaryDescription.basePath=arsc_.requestSignature(binaryDescription.basePath);
			binaryDescription.binaryName=arsc_.requestSignature(binaryDescription.binaryName);
			binaryDescription.description=arsc_.requestSignature(binaryDescription.description);
			} catch (SignatureRequestException e) {
				throw new SecurityException(e.toString());
			}
		applicationRepository_.uploadApplicationBinary(binaryDescription, binaryCode);
	}

	public void deleteApplicationBinary(String basePath, String applicationName, String binaryName) throws ApplicationNotFoundException,
			DirectoryNotFoundException, BinaryNotFoundException,
			InvalidPathNameException, SecurityException {
		try{
		basePath=arsc_.requestSignature(basePath);
		applicationName=arsc_.requestSignature(applicationName);
		binaryName=arsc_.requestSignature(binaryName);
		} catch (SignatureRequestException e) {
			throw new SecurityException(e.toString());
		}
		applicationRepository_.deleteApplicationBinary(basePath, applicationName, binaryName);
	}

	public ApplicationDescription getApplicationDescription(String basePath,
			String applicationName) throws ApplicationNotFoundException,
			DirectoryNotFoundException, InvalidPathNameException, SecurityException {
		try{
		basePath=arsc_.requestSignature(basePath);
		applicationName=arsc_.requestSignature(applicationName);
		} catch (SignatureRequestException e) {
			throw new SecurityException(e.toString());
		}
		ApplicationDescription returnApplicationDescription = applicationRepository_.getApplicationDescription(basePath, applicationName); 
		try {
			returnApplicationDescription.applicationName=arsc_.checkSignature(returnApplicationDescription.applicationName);
			returnApplicationDescription.basePath=arsc_.checkSignature(returnApplicationDescription.basePath);
		} catch (SignatureCheckingException e) {
			// TODO Auto-generated catch block
			throw new SecurityException(e.toString());
		}
		
			for(int i=0;i<returnApplicationDescription.binaryIds.length;i++)
			{
				try {
					returnApplicationDescription.binaryIds[i]=arsc_.checkSignature(returnApplicationDescription.binaryIds[i]);
					
			} catch (SignatureCheckingException e) {
				// TODO Auto-generated catch block
				throw new SecurityException(e.toString());
			}
			}
			try {
				returnApplicationDescription.numberOfBinaries=arsc_.checkSignature(returnApplicationDescription.numberOfBinaries);
			} catch (SignatureCheckingException e) {
				throw new SecurityException(e.toString());
			}
			return returnApplicationDescription;
	}

	public byte[] getApplicationBinary(String basePath, String applicationName,
			String binaryName) throws InvalidPathNameException, ApplicationNotFoundException, DirectoryNotFoundException, BinaryNotFoundException, FileIOException, SecurityException {
		try{
		basePath=arsc_.requestSignature(basePath);
		applicationName=arsc_.requestSignature(applicationName);
		binaryName=arsc_.requestSignature(binaryName);
			} catch (SignatureRequestException e) {
				// TODO Auto-generated catch block
				throw new SecurityException(e.toString());
			}
		byte[] returnApplicationBinary=	applicationRepository_.getApplicationBinary(basePath,applicationName,binaryName);
		try{
		returnApplicationBinary = arsc_.checkSignature(returnApplicationBinary);
		} catch (SignatureCheckingException e) {
			// TODO Auto-generated catch block
			throw new SecurityException(e.toString());
		}
		return returnApplicationBinary;
	}

	public byte[] getRemoteApplicationBinary(String basePath, 
				String applicationName, String binaryName, 
				String applicationRepositoryIor) throws InvalidPathNameException, ApplicationNotFoundException, DirectoryNotFoundException, BinaryNotFoundException, FileIOException, SecurityException {
		
		try{
		basePath=arsc_.requestSignature(basePath);
		applicationName=arsc_.requestSignature(applicationName);
		binaryName=arsc_.requestSignature(binaryName);
		applicationRepositoryIor=arsc_.requestSignature(applicationRepositoryIor);
		} catch (SignatureRequestException e) {
			// TODO Auto-generated catch block
			throw new SecurityException(e.toString());
		}
		byte[] returnApplicationRepository = applicationRepository_.getRemoteApplicationBinary(basePath, applicationName, binaryName,  applicationRepositoryIor);
		return returnApplicationRepository;
	}
	
}
