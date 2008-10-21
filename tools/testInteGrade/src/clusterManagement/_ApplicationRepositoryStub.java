package clusterManagement;


/**
 *	Generated from IDL interface "ApplicationRepository"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class _ApplicationRepositoryStub
	extends org.omg.CORBA.portable.ObjectImpl
	implements clusterManagement.ApplicationRepository
{
	private String[] ids = {"IDL:clusterManagement/ApplicationRepository:1.0"};
	public String[] _ids()
	{
		return ids;
	}

	public final static java.lang.Class _opsClass = clusterManagement.ApplicationRepositoryOperations.class;
	public void deleteApplicationBinary(java.lang.String basePath, java.lang.String applicationName, java.lang.String binaryName) throws clusterManagement.InvalidPathNameException,clusterManagement.ApplicationNotFoundException,clusterManagement.SecurityException,clusterManagement.DirectoryNotFoundException,clusterManagement.BinaryNotFoundException
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "deleteApplicationBinary", true);
				_os.write_string(basePath);
				_os.write_string(applicationName);
				_os.write_string(binaryName);
				_is = _invoke(_os);
				return;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:clusterManagement/InvalidPathNameException:1.0"))
				{
					throw clusterManagement.InvalidPathNameExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/ApplicationNotFoundException:1.0"))
				{
					throw clusterManagement.ApplicationNotFoundExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/SecurityException:1.0"))
				{
					throw clusterManagement.SecurityExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/DirectoryNotFoundException:1.0"))
				{
					throw clusterManagement.DirectoryNotFoundExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/BinaryNotFoundException:1.0"))
				{
					throw clusterManagement.BinaryNotFoundExceptionHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "deleteApplicationBinary", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			ApplicationRepositoryOperations _localServant = (ApplicationRepositoryOperations)_so.servant;
			try
			{
			_localServant.deleteApplicationBinary(basePath,applicationName,binaryName);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return;
		}

		}

	}

	public void uploadApplicationBinary(dataTypes.BinaryDescription binaryDescription, byte[] binaryCode) throws clusterManagement.InvalidPathNameException,clusterManagement.ApplicationNotFoundException,clusterManagement.SecurityException,clusterManagement.BinaryCreationException,clusterManagement.DirectoryNotFoundException
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "uploadApplicationBinary", true);
				dataTypes.BinaryDescriptionHelper.write(_os,binaryDescription);
				dataTypes.BinaryHelper.write(_os,binaryCode);
				_is = _invoke(_os);
				return;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:clusterManagement/InvalidPathNameException:1.0"))
				{
					throw clusterManagement.InvalidPathNameExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/ApplicationNotFoundException:1.0"))
				{
					throw clusterManagement.ApplicationNotFoundExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/SecurityException:1.0"))
				{
					throw clusterManagement.SecurityExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/BinaryCreationException:1.0"))
				{
					throw clusterManagement.BinaryCreationExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/DirectoryNotFoundException:1.0"))
				{
					throw clusterManagement.DirectoryNotFoundExceptionHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "uploadApplicationBinary", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			ApplicationRepositoryOperations _localServant = (ApplicationRepositoryOperations)_so.servant;
			try
			{
			_localServant.uploadApplicationBinary(binaryDescription,binaryCode);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return;
		}

		}

	}

	public dataTypes.ApplicationDescription getApplicationDescription(java.lang.String basePath, java.lang.String applicationName) throws clusterManagement.InvalidPathNameException,clusterManagement.ApplicationNotFoundException,clusterManagement.SecurityException,clusterManagement.DirectoryNotFoundException
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "getApplicationDescription", true);
				_os.write_string(basePath);
				_os.write_string(applicationName);
				_is = _invoke(_os);
				dataTypes.ApplicationDescription _result = dataTypes.ApplicationDescriptionHelper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:clusterManagement/InvalidPathNameException:1.0"))
				{
					throw clusterManagement.InvalidPathNameExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/ApplicationNotFoundException:1.0"))
				{
					throw clusterManagement.ApplicationNotFoundExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/SecurityException:1.0"))
				{
					throw clusterManagement.SecurityExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/DirectoryNotFoundException:1.0"))
				{
					throw clusterManagement.DirectoryNotFoundExceptionHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "getApplicationDescription", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			ApplicationRepositoryOperations _localServant = (ApplicationRepositoryOperations)_so.servant;
			dataTypes.ApplicationDescription _result;			try
			{
			_result = _localServant.getApplicationDescription(basePath,applicationName);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public byte[] getApplicationBinary(java.lang.String basePath, java.lang.String applicationName, java.lang.String binaryName) throws clusterManagement.InvalidPathNameException,clusterManagement.ApplicationNotFoundException,clusterManagement.SecurityException,clusterManagement.DirectoryNotFoundException,clusterManagement.BinaryNotFoundException,clusterManagement.FileIOException
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "getApplicationBinary", true);
				_os.write_string(basePath);
				_os.write_string(applicationName);
				_os.write_string(binaryName);
				_is = _invoke(_os);
				byte[] _result = dataTypes.BinaryHelper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:clusterManagement/InvalidPathNameException:1.0"))
				{
					throw clusterManagement.InvalidPathNameExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/ApplicationNotFoundException:1.0"))
				{
					throw clusterManagement.ApplicationNotFoundExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/SecurityException:1.0"))
				{
					throw clusterManagement.SecurityExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/DirectoryNotFoundException:1.0"))
				{
					throw clusterManagement.DirectoryNotFoundExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/BinaryNotFoundException:1.0"))
				{
					throw clusterManagement.BinaryNotFoundExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/FileIOException:1.0"))
				{
					throw clusterManagement.FileIOExceptionHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "getApplicationBinary", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			ApplicationRepositoryOperations _localServant = (ApplicationRepositoryOperations)_so.servant;
			byte[] _result;			try
			{
			_result = _localServant.getApplicationBinary(basePath,applicationName,binaryName);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public void unregisterApplication(java.lang.String basePath, java.lang.String applicationName) throws clusterManagement.InvalidPathNameException,clusterManagement.ApplicationNotFoundException,clusterManagement.SecurityException,clusterManagement.DirectoryNotFoundException,clusterManagement.DirectoryNotEmptyException
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "unregisterApplication", true);
				_os.write_string(basePath);
				_os.write_string(applicationName);
				_is = _invoke(_os);
				return;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:clusterManagement/InvalidPathNameException:1.0"))
				{
					throw clusterManagement.InvalidPathNameExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/ApplicationNotFoundException:1.0"))
				{
					throw clusterManagement.ApplicationNotFoundExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/SecurityException:1.0"))
				{
					throw clusterManagement.SecurityExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/DirectoryNotFoundException:1.0"))
				{
					throw clusterManagement.DirectoryNotFoundExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/DirectoryNotEmptyException:1.0"))
				{
					throw clusterManagement.DirectoryNotEmptyExceptionHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "unregisterApplication", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			ApplicationRepositoryOperations _localServant = (ApplicationRepositoryOperations)_so.servant;
			try
			{
			_localServant.unregisterApplication(basePath,applicationName);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return;
		}

		}

	}

	public dataTypes.ContentDescription[] listDirectoryContents(java.lang.String directoryName) throws clusterManagement.InvalidPathNameException,clusterManagement.SecurityException,clusterManagement.DirectoryNotFoundException
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "listDirectoryContents", true);
				_os.write_string(directoryName);
				_is = _invoke(_os);
				dataTypes.ContentDescription[] _result = dataTypes.ContentDescriptionSequenceHelper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:clusterManagement/InvalidPathNameException:1.0"))
				{
					throw clusterManagement.InvalidPathNameExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/SecurityException:1.0"))
				{
					throw clusterManagement.SecurityExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/DirectoryNotFoundException:1.0"))
				{
					throw clusterManagement.DirectoryNotFoundExceptionHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "listDirectoryContents", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			ApplicationRepositoryOperations _localServant = (ApplicationRepositoryOperations)_so.servant;
			dataTypes.ContentDescription[] _result;			try
			{
			_result = _localServant.listDirectoryContents(directoryName);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public void createDirectory(java.lang.String directoryName) throws clusterManagement.InvalidPathNameException,clusterManagement.DirectoryCreationException,clusterManagement.SecurityException
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "createDirectory", true);
				_os.write_string(directoryName);
				_is = _invoke(_os);
				return;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:clusterManagement/InvalidPathNameException:1.0"))
				{
					throw clusterManagement.InvalidPathNameExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/DirectoryCreationException:1.0"))
				{
					throw clusterManagement.DirectoryCreationExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/SecurityException:1.0"))
				{
					throw clusterManagement.SecurityExceptionHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "createDirectory", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			ApplicationRepositoryOperations _localServant = (ApplicationRepositoryOperations)_so.servant;
			try
			{
			_localServant.createDirectory(directoryName);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return;
		}

		}

	}

	public void registerApplication(java.lang.String basePath, java.lang.String applicationName) throws clusterManagement.InvalidPathNameException,clusterManagement.DirectoryCreationException,clusterManagement.ApplicationRegistrationException,clusterManagement.SecurityException
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "registerApplication", true);
				_os.write_string(basePath);
				_os.write_string(applicationName);
				_is = _invoke(_os);
				return;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:clusterManagement/InvalidPathNameException:1.0"))
				{
					throw clusterManagement.InvalidPathNameExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/DirectoryCreationException:1.0"))
				{
					throw clusterManagement.DirectoryCreationExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/ApplicationRegistrationException:1.0"))
				{
					throw clusterManagement.ApplicationRegistrationExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/SecurityException:1.0"))
				{
					throw clusterManagement.SecurityExceptionHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "registerApplication", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			ApplicationRepositoryOperations _localServant = (ApplicationRepositoryOperations)_so.servant;
			try
			{
			_localServant.registerApplication(basePath,applicationName);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return;
		}

		}

	}

	public byte[] getRemoteApplicationBinary(java.lang.String basePath, java.lang.String applicationName, java.lang.String binaryName, java.lang.String applicationRepositoryIor) throws clusterManagement.InvalidPathNameException,clusterManagement.ApplicationNotFoundException,clusterManagement.SecurityException,clusterManagement.DirectoryNotFoundException,clusterManagement.BinaryNotFoundException,clusterManagement.FileIOException
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "getRemoteApplicationBinary", true);
				_os.write_string(basePath);
				_os.write_string(applicationName);
				_os.write_string(binaryName);
				_os.write_string(applicationRepositoryIor);
				_is = _invoke(_os);
				byte[] _result = dataTypes.BinaryHelper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:clusterManagement/InvalidPathNameException:1.0"))
				{
					throw clusterManagement.InvalidPathNameExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/ApplicationNotFoundException:1.0"))
				{
					throw clusterManagement.ApplicationNotFoundExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/SecurityException:1.0"))
				{
					throw clusterManagement.SecurityExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/DirectoryNotFoundException:1.0"))
				{
					throw clusterManagement.DirectoryNotFoundExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/BinaryNotFoundException:1.0"))
				{
					throw clusterManagement.BinaryNotFoundExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/FileIOException:1.0"))
				{
					throw clusterManagement.FileIOExceptionHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "getRemoteApplicationBinary", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			ApplicationRepositoryOperations _localServant = (ApplicationRepositoryOperations)_so.servant;
			byte[] _result;			try
			{
			_result = _localServant.getRemoteApplicationBinary(basePath,applicationName,binaryName,applicationRepositoryIor);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public void removeDirectory(java.lang.String directoryName) throws clusterManagement.InvalidPathNameException,clusterManagement.SecurityException,clusterManagement.DirectoryNotFoundException,clusterManagement.DirectoryNotEmptyException
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "removeDirectory", true);
				_os.write_string(directoryName);
				_is = _invoke(_os);
				return;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:clusterManagement/InvalidPathNameException:1.0"))
				{
					throw clusterManagement.InvalidPathNameExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/SecurityException:1.0"))
				{
					throw clusterManagement.SecurityExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/DirectoryNotFoundException:1.0"))
				{
					throw clusterManagement.DirectoryNotFoundExceptionHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:clusterManagement/DirectoryNotEmptyException:1.0"))
				{
					throw clusterManagement.DirectoryNotEmptyExceptionHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "removeDirectory", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			ApplicationRepositoryOperations _localServant = (ApplicationRepositoryOperations)_so.servant;
			try
			{
			_localServant.removeDirectory(directoryName);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return;
		}

		}

	}

}
