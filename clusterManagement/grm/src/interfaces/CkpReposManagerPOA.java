package interfaces;

/**
 *	Generated from IDL interface "CkpReposManager"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public abstract class CkpReposManagerPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, interfaces.CkpReposManagerOperations
{
	static private final java.util.Hashtable m_opsHash = new java.util.Hashtable();
	static
	{
		m_opsHash.put ( "notifyCkpStored", new java.lang.Integer(0));
		m_opsHash.put ( "getCheckpointRemovalList", new java.lang.Integer(1));
		m_opsHash.put ( "getLastCkpInfo", new java.lang.Integer(2));
		m_opsHash.put ( "updateRepositoryStatus", new java.lang.Integer(3));
		m_opsHash.put ( "registerCkpRepos", new java.lang.Integer(4));
		m_opsHash.put ( "getCkpRepos", new java.lang.Integer(5));
		m_opsHash.put ( "setCkpReposMode", new java.lang.Integer(6));
	}
	private String[] ids = {"IDL:interfaces/CkpReposManager:1.0"};
	public interfaces.CkpReposManager _this()
	{
		return interfaces.CkpReposManagerHelper.narrow(_this_object());
	}
	public interfaces.CkpReposManager _this(org.omg.CORBA.ORB orb)
	{
		return interfaces.CkpReposManagerHelper.narrow(_this_object(orb));
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
			case 0: // notifyCkpStored
			{
				int _arg0=_input.read_long();
				java.lang.String _arg1=_input.read_string();
				int _arg2=_input.read_long();
				int _arg3=_input.read_long();
				int _arg4=_input.read_long();
				_out = handler.createReply();
				notifyCkpStored(_arg0,_arg1,_arg2,_arg3,_arg4);
				break;
			}
			case 1: // getCheckpointRemovalList
			{
				int _arg0=_input.read_long();
				_out = handler.createReply();
				subtypes.CheckpointRemovalListHelper.write(_out,getCheckpointRemovalList(_arg0));
				break;
			}
			case 2: // getLastCkpInfo
			{
				java.lang.String _arg0=_input.read_string();
				_out = handler.createReply();
				types.CkpInfoHelper.write(_out,getLastCkpInfo(_arg0));
				break;
			}
			case 3: // updateRepositoryStatus
			{
				int _arg0=_input.read_long();
				int _arg1=_input.read_long();
				_out = handler.createReply();
				_out.write_long(updateRepositoryStatus(_arg0,_arg1));
				break;
			}
			case 4: // registerCkpRepos
			{
				java.lang.String _arg0=_input.read_string();
				short _arg1=_input.read_short();
				int _arg2=_input.read_long();
				_out = handler.createReply();
				_out.write_long(registerCkpRepos(_arg0,_arg1,_arg2));
				break;
			}
			case 5: // getCkpRepos
			{
				java.lang.String _arg0=_input.read_string();
				java.lang.String _arg1=_input.read_string();
				int _arg2=_input.read_long();
				java.lang.String[] _arg3=types.ChecksumListHelper.read(_input);
				int _arg4=_input.read_long();
				_out = handler.createReply();
				subtypes.CkpReposAddressListHelper.write(_out,getCkpRepos(_arg0,_arg1,_arg2,_arg3,_arg4));
				break;
			}
			case 6: // setCkpReposMode
			{
				java.lang.String _arg0=_input.read_string();
				int _arg1=_input.read_long();
				int _arg2=_input.read_long();
				int _arg3=_input.read_long();
				_out = handler.createReply();
				setCkpReposMode(_arg0,_arg1,_arg2,_arg3);
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
