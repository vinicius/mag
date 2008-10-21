package clusterManagement;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "ApplicationRepository"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class ApplicationRepositoryPOATie
	extends ApplicationRepositoryPOA
{
	private ApplicationRepositoryOperations _delegate;

	private POA _poa;
	public ApplicationRepositoryPOATie(ApplicationRepositoryOperations delegate)
	{
		_delegate = delegate;
	}
	public ApplicationRepositoryPOATie(ApplicationRepositoryOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public clusterManagement.ApplicationRepository _this()
	{
		return clusterManagement.ApplicationRepositoryHelper.narrow(_this_object());
	}
	public clusterManagement.ApplicationRepository _this(org.omg.CORBA.ORB orb)
	{
		return clusterManagement.ApplicationRepositoryHelper.narrow(_this_object(orb));
	}
	public ApplicationRepositoryOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(ApplicationRepositoryOperations delegate)
	{
		_delegate = delegate;
	}
	public POA _default_POA()
	{
		if (_poa != null)
		{
			return _poa;
		}
		else
		{
			return super._default_POA();
		}
	}
	public void deleteApplicationBinary(java.lang.String basePath, java.lang.String applicationName, java.lang.String binaryName) throws clusterManagement.InvalidPathNameException,clusterManagement.ApplicationNotFoundException,clusterManagement.SecurityException,clusterManagement.DirectoryNotFoundException,clusterManagement.BinaryNotFoundException
	{
_delegate.deleteApplicationBinary(basePath,applicationName,binaryName);
	}

	public void uploadApplicationBinary(dataTypes.BinaryDescription binaryDescription, byte[] binaryCode) throws clusterManagement.InvalidPathNameException,clusterManagement.ApplicationNotFoundException,clusterManagement.SecurityException,clusterManagement.BinaryCreationException,clusterManagement.DirectoryNotFoundException
	{
_delegate.uploadApplicationBinary(binaryDescription,binaryCode);
	}

	public dataTypes.ApplicationDescription getApplicationDescription(java.lang.String basePath, java.lang.String applicationName) throws clusterManagement.InvalidPathNameException,clusterManagement.ApplicationNotFoundException,clusterManagement.SecurityException,clusterManagement.DirectoryNotFoundException
	{
		return _delegate.getApplicationDescription(basePath,applicationName);
	}

	public byte[] getApplicationBinary(java.lang.String basePath, java.lang.String applicationName, java.lang.String binaryName) throws clusterManagement.InvalidPathNameException,clusterManagement.ApplicationNotFoundException,clusterManagement.SecurityException,clusterManagement.DirectoryNotFoundException,clusterManagement.BinaryNotFoundException,clusterManagement.FileIOException
	{
		return _delegate.getApplicationBinary(basePath,applicationName,binaryName);
	}

	public void unregisterApplication(java.lang.String basePath, java.lang.String applicationName) throws clusterManagement.InvalidPathNameException,clusterManagement.ApplicationNotFoundException,clusterManagement.SecurityException,clusterManagement.DirectoryNotFoundException,clusterManagement.DirectoryNotEmptyException
	{
_delegate.unregisterApplication(basePath,applicationName);
	}

	public dataTypes.ContentDescription[] listDirectoryContents(java.lang.String directoryName) throws clusterManagement.InvalidPathNameException,clusterManagement.SecurityException,clusterManagement.DirectoryNotFoundException
	{
		return _delegate.listDirectoryContents(directoryName);
	}

	public void createDirectory(java.lang.String directoryName) throws clusterManagement.InvalidPathNameException,clusterManagement.DirectoryCreationException,clusterManagement.SecurityException
	{
_delegate.createDirectory(directoryName);
	}

	public void registerApplication(java.lang.String basePath, java.lang.String applicationName) throws clusterManagement.InvalidPathNameException,clusterManagement.DirectoryCreationException,clusterManagement.ApplicationRegistrationException,clusterManagement.SecurityException
	{
_delegate.registerApplication(basePath,applicationName);
	}

	public byte[] getRemoteApplicationBinary(java.lang.String basePath, java.lang.String applicationName, java.lang.String binaryName, java.lang.String applicationRepositoryIor) throws clusterManagement.InvalidPathNameException,clusterManagement.ApplicationNotFoundException,clusterManagement.SecurityException,clusterManagement.DirectoryNotFoundException,clusterManagement.BinaryNotFoundException,clusterManagement.FileIOException
	{
		return _delegate.getRemoteApplicationBinary(basePath,applicationName,binaryName,applicationRepositoryIor);
	}

	public void removeDirectory(java.lang.String directoryName) throws clusterManagement.InvalidPathNameException,clusterManagement.SecurityException,clusterManagement.DirectoryNotFoundException,clusterManagement.DirectoryNotEmptyException
	{
_delegate.removeDirectory(directoryName);
	}

}
