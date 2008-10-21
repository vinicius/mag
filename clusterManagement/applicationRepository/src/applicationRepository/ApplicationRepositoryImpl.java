package applicationRepository;

import clusterManagement.ApplicationRepository;

import clusterManagement.ApplicationRepositoryHelper;
import clusterManagement.ApplicationRepositoryOperations;
import clusterManagement.ApplicationRepositoryPOA;
import clusterManagement.ApplicationNotFoundException;
import clusterManagement.ApplicationRegistrationException;
import clusterManagement.BinaryCreationException;
import clusterManagement.BinaryNotFoundException;
import clusterManagement.DirectoryCreationException;
import clusterManagement.DirectoryNotEmptyException;
import clusterManagement.DirectoryNotFoundException;
import clusterManagement.FileIOException;
import clusterManagement.InvalidPathNameException;
import clusterManagement.SecurityException;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Vector;
import java.io.BufferedReader;
import java.lang.reflect.Array;
import java.util.StringTokenizer;

import dataTypes.ApplicationDescription;
import dataTypes.BinaryDescription;
import dataTypes.ContentDescription;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
//import org.omg.CORBA.Object;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

/**
 * Created on Jun 9, 2004
 * Last changes on Feb 03, 2005
 */

/**
 * @author vidal
 */
public class ApplicationRepositoryImpl implements
		ApplicationRepositoryOperations {

	public static String rootName; // aplication repository root directory name

	private ORB orb;

	/**
	 * @author vidal default constructor
	 */
	public ApplicationRepositoryImpl(ORB orb) {
		this.orb = orb;
		ApplicationRepositoryImpl.rootName = ".." + File.separator
				+ "repository_root" + File.separator;
	}// constructor

	/**
	 * @author vidal
	 * @param String
	 *            dir_name : a name of a directory
	 * @throws InvalidPathNameException
	 * @raises DirectoryCreationException if it cannot create the directory
	 *         Creates the directory with the provided dirname, if it doesn't
	 *         exist
	 */
	public void createDirectory(String directoryName)
			throws DirectoryCreationException, InvalidPathNameException {
		FileUtils.checkPathName(directoryName);
		directoryName = rootName + File.separator + directoryName;
		FileUtils.createDirectory(directoryName);
	}

	/**
	 * @author vidal
	 * @param String
	 *            dir_name: a name of a directory
	 * @throws InvalidPathNameException
	 * @raises DirectoryNotFoundException if the dirname does not exist
	 * @raises DirectoryNotEmptyException if the directory is not empty Removes
	 *         the directory with the provided dirname
	 */
	public void removeDirectory(String directoryName)
			throws DirectoryNotFoundException, DirectoryNotEmptyException,
			InvalidPathNameException {
		FileUtils.checkPathName(directoryName);
		directoryName = rootName + File.separator + directoryName;
		FileUtils.removeDirectory(directoryName);
	}// removeDirectory method

	/**
	 * @author vidal
	 * @param basePath
	 *            the application parent directory
	 * @param applicationName
	 *            the name of the application
	 * @throws InvalidPathNameException
	 * @raises DirectoryCreationException if it cannot create the directory
	 * @raises ApplicationRegistrationException if it cannot create the
	 *         application file Registers an application in the repository.
	 *         Namely, creates a directory, at the repository root, if it
	 *         doesn't exist, and creates an application file (this file cannot
	 *         to exist yet)
	 */
	public void registerApplication(String basePath, String applicationName)
			throws DirectoryCreationException,
			ApplicationRegistrationException, InvalidPathNameException {
		FileUtils.checkPathName(basePath);
		FileUtils.checkPathName(applicationName);
		String fullApplicationPath = rootName + File.separator + basePath
				+ File.separator + applicationName;

		// TODO Synchronize
		FileUtils.createDirectory(fullApplicationPath);

		File file = new File(fullApplicationPath + File.separator
				+ FileUtils.getApplicationDescriptionName());
		try {
			/* (1) writes the application file */
			FileOutputStream appfos = new FileOutputStream(file);
			if (appfos == null) {
				ApplicationRegistrationException are = new ApplicationRegistrationException(
						"couldn't create the aplication description file !");
				throw are;
			}

			/* (2) updates the properties application file */
			Properties properties;
			properties = new Properties();

			// update the two first properties
			String numberOfBinaries = "0";
			properties.setProperty("base_path", basePath);
			properties.setProperty("application_name", applicationName);
			properties.setProperty("number_of_binaries", numberOfBinaries);
			properties.store(appfos, "application properties");
			appfos.close();
		} catch (IOException ioe) {
			ApplicationRegistrationException are = new ApplicationRegistrationException(
					ioe.getMessage()
							+ ": writing the application description file !");
			throw are;
		}
	}// registerApplication method

	/**
	 * @author vidal
	 * @param dirname
	 *            the name of the direrctory (absolute pathname)
	 * @param app_name
	 *            the name of the application
	 * @throws DirectoryNotFoundException
	 * @throws ApplicationNotFoundException
	 * @throws InvalidPathNameException
	 * @returns the serialized application Retrieves an application description
	 *          struct
	 */
	public ApplicationDescription getApplicationDescription(String basePath,
			String applicationName) throws DirectoryNotFoundException,
			ApplicationNotFoundException, InvalidPathNameException {
		FileUtils.checkPathName(basePath);
		FileUtils.checkPathName(applicationName);

		// creates application description file
		ApplicationDescription appDesc = new ApplicationDescription();
		String fullApplicationPath = rootName + File.separator + basePath
				+ File.separator + applicationName;

		File applicationDescriptionFile = new File(fullApplicationPath
				+ File.separator + FileUtils.getApplicationDescriptionName());

		if (!applicationDescriptionFile.exists()
				|| applicationDescriptionFile.isDirectory()) {
			ApplicationNotFoundException anfe = new ApplicationNotFoundException(
					" :: application description file not found !");
			throw anfe;
		}

		Properties properties = new Properties();
		// reads properties from input stream
		FileInputStream fis;
		try {
			fis = new FileInputStream(applicationDescriptionFile);
			properties.load(fis);
			fis.close();
			appDesc.basePath = properties.getProperty("base_path");
			appDesc.applicationName = properties
					.getProperty("application_name");
			appDesc.numberOfBinaries = properties
					.getProperty("number_of_binaries");
			int nofbin = Integer.parseInt(properties
					.getProperty("number_of_binaries"));
			appDesc.binaryIds = new String[nofbin];

			Object[] keys = properties.keySet().toArray();
			for (int i = 0, j = 0; i < keys.length; i++) {
				String key = keys[i].toString();
				if (key.substring(key.lastIndexOf("_")).equals(
						FileUtils.getBinarySuffixName())) {
					appDesc.binaryIds[j] = properties.getProperty(key);
					j++;
				}
			}
		} catch (IOException e) {
			ApplicationNotFoundException anfe = new ApplicationNotFoundException(
					e.getMessage()
							+ ": reading the application description file !");
			throw anfe;
		}
		return appDesc;
	}// getAppDescription method

	/**
	 * @author vidal
	 * @param binaryDescription
	 * @param applicationBinary
	 *            serialized application to be registered
	 * @throws InvalidPathNameException
	 * @raises DirectoryNotFoundException if it cannot create the directory
	 * @raises ApplicationNotFoundException if it cannot find the application
	 * @raises BinaryCreationException if it cannot create the binary code file
	 *         Adds a binary code file to an application in the repository
	 */
	public void uploadApplicationBinary(BinaryDescription binaryDesc,
			byte[] applicationBinary) throws DirectoryNotFoundException,
			ApplicationNotFoundException, BinaryCreationException,
			InvalidPathNameException {

		// checks the parameters
		if (binaryDesc == null || applicationBinary == null) {
			BinaryCreationException fce = new BinaryCreationException(
					"appDesc parameter null !");
			throw fce;
		}
		FileUtils.checkPathName(binaryDesc.basePath);
		FileUtils.checkPathName(binaryDesc.applicationName);
		FileUtils.checkPathName(binaryDesc.binaryName);

		String fullPathApplicationName = rootName + File.separator
				+ binaryDesc.basePath + File.separator
				+ binaryDesc.applicationName;
		String fullPathBinaryName = fullPathApplicationName + File.separator
				+ binaryDesc.binaryName;

		// checks if binary application exists
		File applicationDirectory = new File(fullPathApplicationName);
		if (!applicationDirectory.exists()
				|| !applicationDirectory.isDirectory()) {
			ApplicationNotFoundException anf = new ApplicationNotFoundException();
			throw anf;
		}

		// TODO: Remove this exception?
		// checks if binary application exists
		File binaryFile = new File(fullPathBinaryName);
		if (binaryFile.exists()) {
			BinaryCreationException bce = new BinaryCreationException(
					"binary file already exists !");
			throw bce;
		}

		/* (1) writes the application binary code file */
		FileOutputStream binaryFileOutputStream;
		try {
			binaryFileOutputStream = new FileOutputStream(binaryFile);
			if (binaryFileOutputStream == null) {
				BinaryCreationException bce = new BinaryCreationException(
						"couldn't create the binary file !");
				throw bce;
			}
			binaryFileOutputStream.write(applicationBinary);
			binaryFileOutputStream.close();
		} catch (IOException e) {
			BinaryCreationException bce = new BinaryCreationException(e
					.getMessage()
					+ ": writing the binary code file !");
			throw bce;
		}

		/* (2) updates the properties application file */
		Properties properties = new Properties();

		// reads properties from input stream
		String applicationDescriptionFile = fullPathApplicationName
				+ File.separator + FileUtils.getApplicationDescriptionName();
		FileInputStream applicationDescriptionInputStream;
		try {
			applicationDescriptionInputStream = new FileInputStream(
					applicationDescriptionFile);
			properties.load(applicationDescriptionInputStream);
			applicationDescriptionInputStream.close();
		} catch (IOException e) {
			BinaryCreationException bce = new BinaryCreationException(e
					.getMessage()
					+ ": reading the application description file !");
			throw bce;
		}

		// update the properties
		int numberOfBinaries = Integer.parseInt(properties
				.getProperty("number_of_binaries")) + 1;
		properties.setProperty("number_of_binaries", String
				.valueOf(numberOfBinaries));

		properties.setProperty(binaryDesc.binaryName
				+ FileUtils.getBinarySuffixName(), binaryDesc.binaryName);

		FileOutputStream applicationDescriptionOutputStream;
		try {
			applicationDescriptionOutputStream = new FileOutputStream(
					applicationDescriptionFile);
			properties.store(applicationDescriptionOutputStream,
					"application properties");
			applicationDescriptionOutputStream.close();
		} catch (IOException e) {
			BinaryCreationException bce = new BinaryCreationException(e
					.getMessage()
					+ ": updating the application description file !");
			throw bce;
		}
	}// uploadApplicationBinary method

	/**
	 * @author vidal Retrieves a binary code file from the application
	 *         repository
	 * @param dir_name
	 *            the name of the directory (absolute pathname)
	 * @throws InvalidPathNameException
	 * @throws BinaryNotFoundException
	 * @throws FileIOException
	 * @returns the serialized application
	 */
	public byte[] getApplicationBinary(String basePath, String applicationName,
			String binaryName) throws InvalidPathNameException,
			BinaryNotFoundException, FileIOException {

		FileUtils.checkPathName(basePath);
		FileUtils.checkPathName(applicationName);
		FileUtils.checkPathName(binaryName);

		String fullPathBinaryName = rootName + File.separator + basePath
				+ File.separator + applicationName + File.separator
				+ binaryName;

		File binaryFile = new File(fullPathBinaryName);
		/* reads the application binary code file */
		FileInputStream binaryInputStream;
		byte[] applicationBinary = null;
		try {
			binaryInputStream = new FileInputStream(binaryFile);
			applicationBinary = new byte[(int) binaryFile.length()];
			binaryInputStream.read(applicationBinary);
			binaryInputStream.close();
		} catch (FileNotFoundException e) {
			BinaryNotFoundException bnf = new BinaryNotFoundException();
			throw bnf;

		} catch (IOException e) {
			FileIOException fioe = new FileIOException();
			throw fioe;
		}
		return applicationBinary;
	}// getApplicationBinary method

	/**
	 * @author hammurabi Retrieves a binary code file from a remotely identified
	 *         application repository
	 * @throws InvalidPathNameException
	 * @throws
	 * @throws BinaryNotFoundException
	 * @throws DirectoryNotFoundException
	 * @throws ApplicationNotFoundException
	 * @throws FileIOException
	 * @throws SecurityException 
	 */
	public byte[] getRemoteApplicationBinary(String basePath,
			String applicationName, String binaryName,
			String remoteRepositoryIor) throws InvalidPathNameException,
			ApplicationNotFoundException, DirectoryNotFoundException,
			BinaryNotFoundException, FileIOException, SecurityException {

		FileUtils.checkPathName(basePath);
		FileUtils.checkPathName(applicationName);
		FileUtils.checkPathName(binaryName);
		ApplicationRepository remoteRepository = ApplicationRepositoryHelper
				.narrow(orb.string_to_object(remoteRepositoryIor));
		System.out.println("Returning remote application.");
		return remoteRepository.getApplicationBinary(basePath, applicationName,	binaryName);
	}

	/**
	 * @author vidal
	 * @param dirName
	 *            the name of the directory (absolute pathname)
	 * @param binaryName
	 *            the name of the binary application
	 * @param platform
	 *            the name of the platform to compose the binaryname
	 * @throws InvalidPathNameException
	 * @raises DirectoryNotFoundException if the dirname does not exist
	 * @raises ApplicationNotFoundException if the application properties file
	 *         does not exist
	 * @raises BinaryNotFoundException if the binary code file does not exist
	 *         Deletes an application binary stored on the ApplicationRepository
	 */
	public void deleteApplicationBinary(String basePath,
			String applicationName, String binaryName)
			throws DirectoryNotFoundException, ApplicationNotFoundException,
			BinaryNotFoundException, InvalidPathNameException {

		FileUtils.checkPathName(basePath);
		FileUtils.checkPathName(applicationName);
		FileUtils.checkPathName(binaryName);

		String fullPathApplicationName = rootName + File.separator + basePath
				+ File.separator + applicationName;
		String fullPathBinaryName = fullPathApplicationName + File.separator
				+ binaryName;

		// checks if the application directory exists
		File directoryApplication = new File(fullPathApplicationName);
		if (!directoryApplication.exists()) {
			DirectoryNotFoundException dnfe = new DirectoryNotFoundException();
			throw dnfe;
		}

		// checks if application description file exists

		String applicationDescriptionName = fullPathApplicationName
				+ File.separator + FileUtils.getApplicationDescriptionName();
		File applicationDescriptionFile = new File(applicationDescriptionName);
		if (!applicationDescriptionFile.exists()) {
			ApplicationNotFoundException anfe = new ApplicationNotFoundException();
			throw anfe;
		}

		// checks if binary application exists
		File binaryFile = new File(fullPathBinaryName);
		if (!binaryFile.exists()) {
			BinaryNotFoundException bnfe = new BinaryNotFoundException();
			throw bnfe;
		}

		/* (1) deletes the application binary code file */
		if (!binaryFile.delete()) {
			BinaryNotFoundException bnfe = new BinaryNotFoundException(
					"deleting the binary file !");
			throw bnfe;
		}

		/* (2) updates the properties application file */
		Properties properties;
		properties = new Properties();

		try {
			// reads properties from input stream
			FileInputStream applicationDescriptionInputStream = new FileInputStream(
					applicationDescriptionFile);
			properties.load(applicationDescriptionInputStream);
			applicationDescriptionInputStream.close();

			// update the number_of_binaries property
			int numberOfBinaries = Integer.parseInt(properties
					.getProperty("number_of_binaries")) - 1;
			properties.setProperty("number_of_binaries", String
					.valueOf(numberOfBinaries));

			// remove the specified property
			if (properties.containsKey(binaryFile.getName()
					+ FileUtils.getBinarySuffixName())) {
				properties.remove(binaryFile.getName()
						+ FileUtils.getBinarySuffixName());
			}

			FileOutputStream applicationDescriptionOutputStream = new FileOutputStream(
					applicationDescriptionFile);
			properties.store(applicationDescriptionOutputStream,
					"application properties");
			applicationDescriptionOutputStream.close();
		} catch (IOException e) {
			BinaryNotFoundException bnfe = new BinaryNotFoundException(e
					.getMessage()
					+ ": updating application description file !");
			throw bnfe;
		}
	}// deleteApplicationBinary method

	/**
	 * @author vidal
	 * @param basePath
	 *            the name of the direrctory (absolute pathname)
	 * @param applicationName
	 *            the name of the file
	 * @throws DirectoryNotFoundException
	 *             if the dirname does not exist
	 * @throws ApplicationNotFoundException
	 *             if the filename does not exist
	 * @throws DirectoryNotEmptyException
	 *             if the directory is not empty Unregisters an application
	 *             registered on the ApplicationRepository
	 * @throws InvalidPathNameException
	 */
	public void unregisterApplication(String basePath, String applicationName)
			throws DirectoryNotFoundException, ApplicationNotFoundException,
			DirectoryNotEmptyException, InvalidPathNameException {

		FileUtils.checkPathName(basePath);
		FileUtils.checkPathName(applicationName);

		String fullPathApplicationName = rootName + File.separator + basePath
				+ File.separator + applicationName;

		// checks if directory exists
		File directoryApplication = new File(fullPathApplicationName);
		if (!directoryApplication.exists()) {
			DirectoryNotFoundException dnfe = new DirectoryNotFoundException();
			throw dnfe;
		}
		
		// checks if dirfile is empty, i.e., exists only the application
		// description file
		if (directoryApplication.list().length > 1) {
			DirectoryNotEmptyException dnee = new DirectoryNotEmptyException(
					"there is at least one associated binary !");
			throw dnee;
		}
		
		/* (1) deletes the application description file */
		String fullPathApplicationDescription = rootName + File.separator + basePath
			+ File.separator + applicationName + File.separator + FileUtils.getApplicationDescriptionName();

		File descFile = new File(fullPathApplicationDescription);
		if (!descFile.delete()) {
			ApplicationNotFoundException anfe = new ApplicationNotFoundException(
					"deleting application description file !");
			throw anfe;
		}			
		
		/* (1) delets the application directory */
		FileUtils.removeDirectory(fullPathApplicationName);
	}// unregisterApplication method

	/**
	 * TODO correct this // Returns the directories and files names under the
	 * provided dirname //
	 * 
	 * @param dir_name
	 *            the name of a directory //
	 * @return an array of ContentDescription structures //
	 * @throws DirectoryNotFoundException
	 *             if the dirname does not exist //
	 * @throws InvalidPathNameException
	 */
	public ContentDescription[] listDirectoryContents(String directoryName)
			throws DirectoryNotFoundException, InvalidPathNameException {
		FileUtils.checkPathName(directoryName);
		
		String fullPathDirectoryName = rootName + File.separator + directoryName;
		File directory = new File(fullPathDirectoryName);
		
		if (!directory.exists()) {
			DirectoryNotFoundException dnfe = new DirectoryNotFoundException();
			throw dnfe;
		}

		if (!directory.isDirectory()) {
			DirectoryNotFoundException dnfe = new DirectoryNotFoundException();
			throw dnfe;
		}

		ContentDescription[] contents = (ContentDescription[]) FileUtils.readDirectoryContents(directory);
		return contents;

	}// listDirectoryContents method

	// /*
	// Returns the number of directories and files names under the provided
	// dirname
	// @param dir_name the name of a directory
	// @return an array of ContentDescription structures
	// @raises DirectoryNotFoundException if the dirname does not exist
	// */
	public int getNumberOfEntries(String dir_name)
			throws DirectoryNotFoundException {
		if (dir_name == null) {
			DirectoryNotFoundException dnfe = new DirectoryNotFoundException();
			throw dnfe;
		}

		File dir = new File(dir_name);
		if (!dir.exists()) {
			DirectoryNotFoundException dnfe = new DirectoryNotFoundException();
			throw dnfe;
		}

		File[] contentDir = dir.listFiles();
		return contentDir.length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clusterManagement.ApplicationRepositoryOperations#getRootName()
	 */
	public String getRootName() {
		return ApplicationRepositoryImpl.rootName;
	}

	/**
	 * Tests if the provided dirname represents a directory auxiliary method
	 * 
	 * @param dirname
	 *            the name of a directory
	 * @raises DirectoryNotFoundException if the directory with the dirname
	 *         provides not exists
	 * @returns true if dirname is a directory. false otherwise
	 */
	/*
	 * public boolean isDirectory( String dirname) throws
	 * DirectoryNotFoundException { File dir = new File(dirname) ; if (!
	 * dir.exists()) { DirectoryNotFoundException dnfe = new
	 * DirectoryNotFoundException(); throw dnfe; } return dir.isDirectory(); }
	 */
}

/*
 * // auxiliary classes class JustFileFilter implements FileFilter { //*
 * implements the FilenameFilter.accept method public boolean accept(File file) {
 * 
 * if (file.exists() &&file.isFile() ) return true; return false; } }
 * 
 * class DirectoryFilter implements FileFilter { //* implements the
 * FilenameFilter.accept method public boolean accept(File dir) { if
 * (dir.exists() && dir.isDirectory() ) return true; return false; } }
 */