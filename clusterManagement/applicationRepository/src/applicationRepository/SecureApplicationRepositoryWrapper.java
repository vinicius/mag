package applicationRepository;

import java.io.File;
import java.io.IOException;

import arsc.ArscImpl;
import arsm.DigestException;
import clusterManagement.ApplicationNotFoundException;
import clusterManagement.ApplicationRegistrationException;
import clusterManagement.ApplicationRepositoryOperations;
import clusterManagement.BinaryCreationException;
import clusterManagement.BinaryNotFoundException;
import clusterManagement.ContextInitiationException;
import clusterManagement.DirectoryCreationException;
import clusterManagement.DirectoryNotEmptyException;
import clusterManagement.DirectoryNotFoundException;
import clusterManagement.FileChecksumException;
import clusterManagement.FileIOException;
import clusterManagement.InvalidPathNameException;
import clusterManagement.SecurityException;
import clusterManagement.SignatureCheckingException;
import clusterManagement.SignatureRequestException;
import dataTypes.ApplicationDescription;
import dataTypes.BinaryDescription;
import dataTypes.ContentDescription;
import dataTypes.kindOfItens;

public class SecureApplicationRepositoryWrapper implements
		ApplicationRepositoryOperations {
	ApplicationRepositoryImpl applicationRepositoryImpl=null;
	private  ArscImpl arscApp=null; 

	public SecureApplicationRepositoryWrapper(ApplicationRepositoryImpl impl) {
		applicationRepositoryImpl = impl;
		arscApp=new ArscImpl();
		try {
			arscApp.initContext();
		} catch (ContextInitiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createDirectory(String directoryName)
			throws SecurityException, DirectoryCreationException, InvalidPathNameException {
			try {
				directoryName=internalDirectory(directoryName );
			} catch (SignatureCheckingException e) {
				e.printStackTrace();
				throw new SecurityException(e.toString());
			}
			applicationRepositoryImpl.createDirectory(directoryName);
	}

	public void removeDirectory(String directoryName)
			throws SecurityException, DirectoryNotFoundException, DirectoryNotEmptyException, InvalidPathNameException {
			try {
				String stringTargetId = ArscImpl.getId(directoryName);
				directoryName=internalDirectory(directoryName );
				String externalDirectoryName = externalDirectory(directoryName,stringTargetId);
				if(externalDirectoryName.equals(File.separator + FileUtils.getPublicDirectory()))
				{
					throw new SecurityException("Directory `/Public' cannot be removed.");
				}
			} catch (SignatureCheckingException e) {
				e.printStackTrace();
				throw new SecurityException(e.toString());
			}
				applicationRepositoryImpl.removeDirectory(directoryName);
	}

	private String internalDirectory(String directoryName ) throws SecurityException, SignatureCheckingException
	{
		String stringTargetId = ArscImpl.getId(directoryName); 
		stringTargetId = stringTargetId.substring(0,stringTargetId.indexOf(File.separator));
		try {
			directoryName = arscApp.checkForeignSignature(directoryName);
		} catch (SignatureCheckingException e) {
			e.printStackTrace();
			throw new SecurityException(e.toString());
		}
		directoryName = stringTargetId  + File.separator +  directoryName;
		return directoryName;
	}
	private String externalDirectory(String directoryName,String stringTargetId)
	{
		stringTargetId = stringTargetId.substring(0,stringTargetId.indexOf(File.separator));
		return directoryName.substring(stringTargetId.length() + File.separator.length());
	}
	public ContentDescription[] listDirectoryContents(String directoryName)
		throws SecurityException, DirectoryNotFoundException, InvalidPathNameException {
		String targetId = arscApp.getId(directoryName);
		try {
			directoryName=internalDirectory(directoryName);
		} catch (SignatureCheckingException e) {
			e.printStackTrace();
			throw new SecurityException(e.toString());
		}
		File dir = new File(applicationRepositoryImpl.getRootName() + File.separator +  directoryName);
		if (! dir.exists()) {
			dir.mkdir();
			try{
				Runtime.getRuntime().exec("/bin/ln -s " + ".." + File.separator + FileUtils.getPublicDirectory() + File.separator + " " +  applicationRepositoryImpl.getRootName() + directoryName);
			}catch(IOException ioe){
				ioe.printStackTrace();
			}
		}
		String filename = null;
		kindOfItens kind = null;
		ContentDescription[] contents = applicationRepositoryImpl.listDirectoryContents(directoryName);
		
		for(int i =0 ; i < contents.length;i++)
		{
			
			filename=externalDirectory(contents[i].fileName,targetId);
			kind = contents[i].kind;
			try {
				
				contents[i].fileName = arscApp.requestForeignSignature(targetId,filename);
			} catch (SignatureRequestException e) {
				e.printStackTrace();
				throw new SecurityException(e.toString());
			}
		//	contents[i].kind = new String(arscApp.requestForeignSignature(targetId, kind.getBytes()));
		}
		return contents;
		
	}

	public void registerApplication(String directoryName, String applicationName)
			throws SecurityException, DirectoryCreationException, ApplicationRegistrationException, InvalidPathNameException{
		try {
			directoryName=internalDirectory(directoryName);
		} catch (SignatureCheckingException e) {
			e.printStackTrace();
			throw new SecurityException(e.toString());
		}
		try {
			applicationName = arscApp.checkForeignSignature(applicationName);
		} catch (SignatureCheckingException e) {
			e.printStackTrace();
			throw new SecurityException(e.toString());
		}
		applicationRepositoryImpl.registerApplication(directoryName,applicationName);

	}

	public void unregisterApplication(String directoryName,
			String applicationName) throws SecurityException, ApplicationNotFoundException,
			DirectoryNotFoundException, DirectoryNotEmptyException, InvalidPathNameException {
		try {
			directoryName=internalDirectory(directoryName);
		} catch (SignatureCheckingException e) {
			e.printStackTrace();
			throw new SecurityException(e.toString());
		}
		try {
			applicationName = arscApp.checkForeignSignature(applicationName);
		} catch (SignatureCheckingException e) {
			e.printStackTrace();
			throw new SecurityException(e.toString());
		}
		applicationRepositoryImpl.unregisterApplication(directoryName,applicationName);
	}

	public ApplicationDescription getApplicationDescription(String basePath,
			String applicationName) throws SecurityException, ApplicationNotFoundException,
			DirectoryNotFoundException, InvalidPathNameException {
		String targetId = arscApp.getId(basePath);
		try {
			basePath=internalDirectory(basePath);
		} catch (SignatureCheckingException e) {
			e.printStackTrace();
			throw new SecurityException(e.toString());
		}
		try {
			applicationName = arscApp.checkForeignSignature(applicationName);
		} catch (SignatureCheckingException e) {
			e.printStackTrace();
			throw new SecurityException(e.toString());
		}
		ApplicationDescription returnApplicationDescription = applicationRepositoryImpl.getApplicationDescription(basePath,applicationName);
		try {
			returnApplicationDescription.applicationName = arscApp.requestForeignSignature(targetId,returnApplicationDescription.applicationName);
		} catch (SignatureRequestException e) {
			e.printStackTrace();
			throw new SecurityException(e.toString());
		}
		try {
			returnApplicationDescription.basePath = arscApp.requestForeignSignature(targetId,returnApplicationDescription.basePath);
		} catch (SignatureRequestException e) {
			e.printStackTrace();
			throw new SecurityException(e.toString());
		}
		for (int i = 0; i < returnApplicationDescription.binaryIds.length; i++) {
			try {
				returnApplicationDescription.binaryIds[i] = arscApp.requestForeignSignature(targetId,returnApplicationDescription.binaryIds[i]);
			} catch (SignatureRequestException e) {
				e.printStackTrace();
				throw new SecurityException(e.toString());
			}
		}
		try {
			returnApplicationDescription.numberOfBinaries = arscApp.requestForeignSignature(targetId,returnApplicationDescription.numberOfBinaries);
		} catch (SignatureRequestException e) {
			e.printStackTrace();
			throw new SecurityException(e.toString());
		}
		return returnApplicationDescription;
	}

	public void uploadApplicationBinary(BinaryDescription binaryDescription,
			byte[] applicationBinary) throws SecurityException, ApplicationNotFoundException,
			BinaryCreationException, DirectoryNotFoundException, InvalidPathNameException {
		
		try {
			applicationBinary = arscApp.checkForeignSignature(applicationBinary);
		} catch (SignatureCheckingException e) {
			e.printStackTrace();
			throw new SecurityException(e.toString());
		}//check ASCT signature
    	byte[] mdApp;
		try {
			mdApp = ArscImpl.messageDigest(applicationBinary);
		} catch (DigestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			BinaryCreationException bce = new BinaryCreationException("Error in digest function.");
			throw bce;
		} // app messagedigest
    	byte[] appDigest = new byte[applicationBinary.length+mdApp.length];
    	System.arraycopy(mdApp,0,appDigest,0,mdApp.length);
    	System.arraycopy(applicationBinary,0,appDigest,mdApp.length,applicationBinary.length);
    	applicationBinary=appDigest;
    	String stringTargetId = ArscImpl.getId(binaryDescription.applicationName); 
    	try {
			binaryDescription.basePath = arscApp.checkForeignSignature(binaryDescription.basePath);
		} catch (SignatureCheckingException e) {
			e.printStackTrace();
			throw new SecurityException(e.toString());
		}
    	try {
			binaryDescription.applicationName = arscApp.checkForeignSignature(binaryDescription.applicationName);
		} catch (SignatureCheckingException e) {
			e.printStackTrace();
			throw new SecurityException(e.toString());
		}
    	try {
			binaryDescription.binaryName = arscApp.checkForeignSignature(binaryDescription.binaryName);
		} catch (SignatureCheckingException e) {
			e.printStackTrace();
			throw new SecurityException(e.toString());
		}
    	try {
			binaryDescription.description = arscApp.checkForeignSignature(binaryDescription.description);
		} catch (SignatureCheckingException e) {
			e.printStackTrace();
			throw new SecurityException(e.toString());
		}
    	stringTargetId = stringTargetId.substring(0,stringTargetId.indexOf(File.separator));
	binaryDescription.basePath =  stringTargetId  + File.separator +  binaryDescription.basePath;
	applicationRepositoryImpl.uploadApplicationBinary(binaryDescription,applicationBinary);
	}

	public void deleteApplicationBinary(String basePath, String applicationName, String binaryName)
			throws SecurityException, ApplicationNotFoundException, DirectoryNotFoundException,
			BinaryNotFoundException, InvalidPathNameException {
			try {
				basePath=internalDirectory(basePath);
			} catch (SignatureCheckingException e) {
				e.printStackTrace();
				throw new SecurityException(e.toString());
			}
			try {
				applicationName = arscApp.checkForeignSignature(applicationName);
			} catch (SignatureCheckingException e) {
				e.printStackTrace();
				throw new SecurityException(e.toString());
			}
			try {
				binaryName = arscApp.checkForeignSignature(binaryName);
			} catch (SignatureCheckingException e) {
				e.printStackTrace();
				throw new SecurityException(e.toString());
			}
			applicationRepositoryImpl.deleteApplicationBinary(basePath, applicationName, binaryName);
	}

	public byte[] getApplicationBinary(String basePath, String applicationName, String binaryName) throws SecurityException, InvalidPathNameException, BinaryNotFoundException, FileIOException{
		byte[]  targetId = ArscImpl.getId(binaryName.getBytes()); // Target Client Identification
		try {
			basePath = arscApp.checkForeignSignature(basePath);
		} catch (SignatureCheckingException e) {
			e.printStackTrace();
			throw new SecurityException(e.toString());
		}// check signature
		try {
			applicationName = arscApp.checkForeignSignature(applicationName);
		} catch (SignatureCheckingException e) {
			e.printStackTrace();
			throw new SecurityException(e.toString());
		}// check signature
		try {
			binaryName = arscApp.checkForeignSignature(binaryName);
		} catch (SignatureCheckingException e) {
			e.printStackTrace();
			throw new SecurityException(e.toString());
		}// check signature
		byte[] applicationBinary = applicationRepositoryImpl.getApplicationBinary(basePath, applicationName, binaryName);
		try {
			applicationBinary=arscApp.verifyDigest(applicationBinary);
		} catch (DigestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FileIOException fioe = new FileIOException("File is corrupted in repository file system.");
			throw fioe;
		}
		
			try {
				applicationBinary = arscApp.requestForeignSignature(targetId,applicationBinary);
			} catch (SignatureRequestException e) {
				e.printStackTrace();
				throw new SecurityException(e.toString());
			}
			
	 return applicationBinary;
	 
	}

	public byte[] getRemoteApplicationBinary(String basePath, String applicationName, String binaryName,
			String applicationRepositoryIor) throws SecurityException, InvalidPathNameException, ApplicationNotFoundException, DirectoryNotFoundException, BinaryNotFoundException, FileIOException {
		// TODO: Security in remote case
		byte[]  targetId = ArscImpl.getId(binaryName.getBytes()); // Target Client Identification
		byte[] returnApplicationBinary = applicationRepositoryImpl.getRemoteApplicationBinary(basePath, applicationName, binaryName,
				applicationRepositoryIor);
		try {
			returnApplicationBinary = arscApp.requestForeignSignature(targetId,returnApplicationBinary);
		} catch (SignatureRequestException e) {
			e.printStackTrace();
			throw new SecurityException(e.toString());
		} 
		return returnApplicationBinary;	
		}

}
