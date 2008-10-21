package clusterManagement;

/**
 *	Generated from IDL interface "ApplicationRepository"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public abstract class ApplicationRepositoryPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, clusterManagement.ApplicationRepositoryOperations
{
	static private final java.util.Hashtable m_opsHash = new java.util.Hashtable();
	static
	{
		m_opsHash.put ( "deleteApplicationBinary", new java.lang.Integer(0));
		m_opsHash.put ( "uploadApplicationBinary", new java.lang.Integer(1));
		m_opsHash.put ( "getApplicationDescription", new java.lang.Integer(2));
		m_opsHash.put ( "getApplicationBinary", new java.lang.Integer(3));
		m_opsHash.put ( "unregisterApplication", new java.lang.Integer(4));
		m_opsHash.put ( "listDirectoryContents", new java.lang.Integer(5));
		m_opsHash.put ( "createDirectory", new java.lang.Integer(6));
		m_opsHash.put ( "registerApplication", new java.lang.Integer(7));
		m_opsHash.put ( "getRemoteApplicationBinary", new java.lang.Integer(8));
		m_opsHash.put ( "removeDirectory", new java.lang.Integer(9));
	}
	private String[] ids = {"IDL:clusterManagement/ApplicationRepository:1.0"};
	public clusterManagement.ApplicationRepository _this()
	{
		return clusterManagement.ApplicationRepositoryHelper.narrow(_this_object());
	}
	public clusterManagement.ApplicationRepository _this(org.omg.CORBA.ORB orb)
	{
		return clusterManagement.ApplicationRepositoryHelper.narrow(_this_object(orb));
	}
	public org.omg.CORBA.portable.OutputStream _invoke(String method, org.omg.CORBA.portable.InputStream _input, org.omg.CORBA.portable.ResponseHandler handler)
		throws org.omg.CORBA.SystemException
	{
		org.omg.CORBA.portable.OutputStream _out = null;
		// do something
		// quick lookup of operation
		java.lang.Integer opsIndex = (java.lang.Integer)m_opsHash.get ( method );
		if ( null == opsIndex )
			throw new org.omg.CORBA.BAD_OPERATION(method + " not found");
		switch ( opsIndex.intValue() )
		{
			case 0: // deleteApplicationBinary
			{
			try
			{
				java.lang.String _arg0=_input.read_string();
				java.lang.String _arg1=_input.read_string();
				java.lang.String _arg2=_input.read_string();
				_out = handler.createReply();
				deleteApplicationBinary(_arg0,_arg1,_arg2);
			}
			catch(clusterManagement.InvalidPathNameException _ex0)
			{
				_out = handler.createExceptionReply();
				clusterManagement.InvalidPathNameExceptionHelper.write(_out, _ex0);
			}
			catch(clusterManagement.ApplicationNotFoundException _ex1)
			{
				_out = handler.createExceptionReply();
				clusterManagement.ApplicationNotFoundExceptionHelper.write(_out, _ex1);
			}
			catch(clusterManagement.SecurityException _ex2)
			{
				_out = handler.createExceptionReply();
				clusterManagement.SecurityExceptionHelper.write(_out, _ex2);
			}
			catch(clusterManagement.DirectoryNotFoundException _ex3)
			{
				_out = handler.createExceptionReply();
				clusterManagement.DirectoryNotFoundExceptionHelper.write(_out, _ex3);
			}
			catch(clusterManagement.BinaryNotFoundException _ex4)
			{
				_out = handler.createExceptionReply();
				clusterManagement.BinaryNotFoundExceptionHelper.write(_out, _ex4);
			}
				break;
			}
			case 1: // uploadApplicationBinary
			{
			try
			{
				dataTypes.BinaryDescription _arg0=dataTypes.BinaryDescriptionHelper.read(_input);
				byte[] _arg1=dataTypes.BinaryHelper.read(_input);
				_out = handler.createReply();
				uploadApplicationBinary(_arg0,_arg1);
			}
			catch(clusterManagement.InvalidPathNameException _ex0)
			{
				_out = handler.createExceptionReply();
				clusterManagement.InvalidPathNameExceptionHelper.write(_out, _ex0);
			}
			catch(clusterManagement.ApplicationNotFoundException _ex1)
			{
				_out = handler.createExceptionReply();
				clusterManagement.ApplicationNotFoundExceptionHelper.write(_out, _ex1);
			}
			catch(clusterManagement.SecurityException _ex2)
			{
				_out = handler.createExceptionReply();
				clusterManagement.SecurityExceptionHelper.write(_out, _ex2);
			}
			catch(clusterManagement.BinaryCreationException _ex3)
			{
				_out = handler.createExceptionReply();
				clusterManagement.BinaryCreationExceptionHelper.write(_out, _ex3);
			}
			catch(clusterManagement.DirectoryNotFoundException _ex4)
			{
				_out = handler.createExceptionReply();
				clusterManagement.DirectoryNotFoundExceptionHelper.write(_out, _ex4);
			}
				break;
			}
			case 2: // getApplicationDescription
			{
			try
			{
				java.lang.String _arg0=_input.read_string();
				java.lang.String _arg1=_input.read_string();
				_out = handler.createReply();
				dataTypes.ApplicationDescriptionHelper.write(_out,getApplicationDescription(_arg0,_arg1));
			}
			catch(clusterManagement.InvalidPathNameException _ex0)
			{
				_out = handler.createExceptionReply();
				clusterManagement.InvalidPathNameExceptionHelper.write(_out, _ex0);
			}
			catch(clusterManagement.ApplicationNotFoundException _ex1)
			{
				_out = handler.createExceptionReply();
				clusterManagement.ApplicationNotFoundExceptionHelper.write(_out, _ex1);
			}
			catch(clusterManagement.SecurityException _ex2)
			{
				_out = handler.createExceptionReply();
				clusterManagement.SecurityExceptionHelper.write(_out, _ex2);
			}
			catch(clusterManagement.DirectoryNotFoundException _ex3)
			{
				_out = handler.createExceptionReply();
				clusterManagement.DirectoryNotFoundExceptionHelper.write(_out, _ex3);
			}
				break;
			}
			case 3: // getApplicationBinary
			{
			try
			{
				java.lang.String _arg0=_input.read_string();
				java.lang.String _arg1=_input.read_string();
				java.lang.String _arg2=_input.read_string();
				_out = handler.createReply();
				dataTypes.BinaryHelper.write(_out,getApplicationBinary(_arg0,_arg1,_arg2));
			}
			catch(clusterManagement.InvalidPathNameException _ex0)
			{
				_out = handler.createExceptionReply();
				clusterManagement.InvalidPathNameExceptionHelper.write(_out, _ex0);
			}
			catch(clusterManagement.ApplicationNotFoundException _ex1)
			{
				_out = handler.createExceptionReply();
				clusterManagement.ApplicationNotFoundExceptionHelper.write(_out, _ex1);
			}
			catch(clusterManagement.SecurityException _ex2)
			{
				_out = handler.createExceptionReply();
				clusterManagement.SecurityExceptionHelper.write(_out, _ex2);
			}
			catch(clusterManagement.DirectoryNotFoundException _ex3)
			{
				_out = handler.createExceptionReply();
				clusterManagement.DirectoryNotFoundExceptionHelper.write(_out, _ex3);
			}
			catch(clusterManagement.BinaryNotFoundException _ex4)
			{
				_out = handler.createExceptionReply();
				clusterManagement.BinaryNotFoundExceptionHelper.write(_out, _ex4);
			}
			catch(clusterManagement.FileIOException _ex5)
			{
				_out = handler.createExceptionReply();
				clusterManagement.FileIOExceptionHelper.write(_out, _ex5);
			}
				break;
			}
			case 4: // unregisterApplication
			{
			try
			{
				java.lang.String _arg0=_input.read_string();
				java.lang.String _arg1=_input.read_string();
				_out = handler.createReply();
				unregisterApplication(_arg0,_arg1);
			}
			catch(clusterManagement.InvalidPathNameException _ex0)
			{
				_out = handler.createExceptionReply();
				clusterManagement.InvalidPathNameExceptionHelper.write(_out, _ex0);
			}
			catch(clusterManagement.ApplicationNotFoundException _ex1)
			{
				_out = handler.createExceptionReply();
				clusterManagement.ApplicationNotFoundExceptionHelper.write(_out, _ex1);
			}
			catch(clusterManagement.SecurityException _ex2)
			{
				_out = handler.createExceptionReply();
				clusterManagement.SecurityExceptionHelper.write(_out, _ex2);
			}
			catch(clusterManagement.DirectoryNotFoundException _ex3)
			{
				_out = handler.createExceptionReply();
				clusterManagement.DirectoryNotFoundExceptionHelper.write(_out, _ex3);
			}
			catch(clusterManagement.DirectoryNotEmptyException _ex4)
			{
				_out = handler.createExceptionReply();
				clusterManagement.DirectoryNotEmptyExceptionHelper.write(_out, _ex4);
			}
				break;
			}
			case 5: // listDirectoryContents
			{
			try
			{
				java.lang.String _arg0=_input.read_string();
				_out = handler.createReply();
				dataTypes.ContentDescriptionSequenceHelper.write(_out,listDirectoryContents(_arg0));
			}
			catch(clusterManagement.InvalidPathNameException _ex0)
			{
				_out = handler.createExceptionReply();
				clusterManagement.InvalidPathNameExceptionHelper.write(_out, _ex0);
			}
			catch(clusterManagement.SecurityException _ex1)
			{
				_out = handler.createExceptionReply();
				clusterManagement.SecurityExceptionHelper.write(_out, _ex1);
			}
			catch(clusterManagement.DirectoryNotFoundException _ex2)
			{
				_out = handler.createExceptionReply();
				clusterManagement.DirectoryNotFoundExceptionHelper.write(_out, _ex2);
			}
				break;
			}
			case 6: // createDirectory
			{
			try
			{
				java.lang.String _arg0=_input.read_string();
				_out = handler.createReply();
				createDirectory(_arg0);
			}
			catch(clusterManagement.InvalidPathNameException _ex0)
			{
				_out = handler.createExceptionReply();
				clusterManagement.InvalidPathNameExceptionHelper.write(_out, _ex0);
			}
			catch(clusterManagement.DirectoryCreationException _ex1)
			{
				_out = handler.createExceptionReply();
				clusterManagement.DirectoryCreationExceptionHelper.write(_out, _ex1);
			}
			catch(clusterManagement.SecurityException _ex2)
			{
				_out = handler.createExceptionReply();
				clusterManagement.SecurityExceptionHelper.write(_out, _ex2);
			}
				break;
			}
			case 7: // registerApplication
			{
			try
			{
				java.lang.String _arg0=_input.read_string();
				java.lang.String _arg1=_input.read_string();
				_out = handler.createReply();
				registerApplication(_arg0,_arg1);
			}
			catch(clusterManagement.InvalidPathNameException _ex0)
			{
				_out = handler.createExceptionReply();
				clusterManagement.InvalidPathNameExceptionHelper.write(_out, _ex0);
			}
			catch(clusterManagement.DirectoryCreationException _ex1)
			{
				_out = handler.createExceptionReply();
				clusterManagement.DirectoryCreationExceptionHelper.write(_out, _ex1);
			}
			catch(clusterManagement.ApplicationRegistrationException _ex2)
			{
				_out = handler.createExceptionReply();
				clusterManagement.ApplicationRegistrationExceptionHelper.write(_out, _ex2);
			}
			catch(clusterManagement.SecurityException _ex3)
			{
				_out = handler.createExceptionReply();
				clusterManagement.SecurityExceptionHelper.write(_out, _ex3);
			}
				break;
			}
			case 8: // getRemoteApplicationBinary
			{
			try
			{
				java.lang.String _arg0=_input.read_string();
				java.lang.String _arg1=_input.read_string();
				java.lang.String _arg2=_input.read_string();
				java.lang.String _arg3=_input.read_string();
				_out = handler.createReply();
				dataTypes.BinaryHelper.write(_out,getRemoteApplicationBinary(_arg0,_arg1,_arg2,_arg3));
			}
			catch(clusterManagement.InvalidPathNameException _ex0)
			{
				_out = handler.createExceptionReply();
				clusterManagement.InvalidPathNameExceptionHelper.write(_out, _ex0);
			}
			catch(clusterManagement.ApplicationNotFoundException _ex1)
			{
				_out = handler.createExceptionReply();
				clusterManagement.ApplicationNotFoundExceptionHelper.write(_out, _ex1);
			}
			catch(clusterManagement.SecurityException _ex2)
			{
				_out = handler.createExceptionReply();
				clusterManagement.SecurityExceptionHelper.write(_out, _ex2);
			}
			catch(clusterManagement.DirectoryNotFoundException _ex3)
			{
				_out = handler.createExceptionReply();
				clusterManagement.DirectoryNotFoundExceptionHelper.write(_out, _ex3);
			}
			catch(clusterManagement.BinaryNotFoundException _ex4)
			{
				_out = handler.createExceptionReply();
				clusterManagement.BinaryNotFoundExceptionHelper.write(_out, _ex4);
			}
			catch(clusterManagement.FileIOException _ex5)
			{
				_out = handler.createExceptionReply();
				clusterManagement.FileIOExceptionHelper.write(_out, _ex5);
			}
				break;
			}
			case 9: // removeDirectory
			{
			try
			{
				java.lang.String _arg0=_input.read_string();
				_out = handler.createReply();
				removeDirectory(_arg0);
			}
			catch(clusterManagement.InvalidPathNameException _ex0)
			{
				_out = handler.createExceptionReply();
				clusterManagement.InvalidPathNameExceptionHelper.write(_out, _ex0);
			}
			catch(clusterManagement.SecurityException _ex1)
			{
				_out = handler.createExceptionReply();
				clusterManagement.SecurityExceptionHelper.write(_out, _ex1);
			}
			catch(clusterManagement.DirectoryNotFoundException _ex2)
			{
				_out = handler.createExceptionReply();
				clusterManagement.DirectoryNotFoundExceptionHelper.write(_out, _ex2);
			}
			catch(clusterManagement.DirectoryNotEmptyException _ex3)
			{
				_out = handler.createExceptionReply();
				clusterManagement.DirectoryNotEmptyExceptionHelper.write(_out, _ex3);
			}
				break;
			}
		}
		return _out;
	}

	public String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] obj_id)
	{
		return ids;
	}
}
