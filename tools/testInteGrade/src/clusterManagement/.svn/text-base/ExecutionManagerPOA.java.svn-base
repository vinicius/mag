package clusterManagement;

/**
 *	Generated from IDL interface "ExecutionManager"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public abstract class ExecutionManagerPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, clusterManagement.ExecutionManagerOperations
{
	static private final java.util.Hashtable m_opsHash = new java.util.Hashtable();
	static
	{
		m_opsHash.put ( "setExecutionScheduled", new java.lang.Integer(0));
		m_opsHash.put ( "setProcessExecutionFinished", new java.lang.Integer(1));
		m_opsHash.put ( "registerBspProcess", new java.lang.Integer(2));
		m_opsHash.put ( "setProcessExecutionStarted", new java.lang.Integer(3));
	}
	private String[] ids = {"IDL:clusterManagement/ExecutionManager:1.0"};
	public clusterManagement.ExecutionManager _this()
	{
		return clusterManagement.ExecutionManagerHelper.narrow(_this_object());
	}
	public clusterManagement.ExecutionManager _this(org.omg.CORBA.ORB orb)
	{
		return clusterManagement.ExecutionManagerHelper.narrow(_this_object(orb));
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
			case 0: // setExecutionScheduled
			{
				dataTypes.ApplicationExecutionInformation _arg0=dataTypes.ApplicationExecutionInformationHelper.read(_input);
				dataTypes.ProcessExecutionInformation[] _arg1=dataTypes.ProcessExecutionInformationSequenceHelper.read(_input);
				_out = handler.createReply();
				setExecutionScheduled(_arg0,_arg1);
				break;
			}
			case 1: // setProcessExecutionFinished
			{
				int _arg0=_input.read_long();
				dataTypes.ExecutionRequestId _arg1=dataTypes.ExecutionRequestIdHelper.read(_input);
				int _arg2=_input.read_long();
				_out = handler.createReply();
				_out.write_long(setProcessExecutionFinished(_arg0,_arg1,_arg2));
				break;
			}
			case 2: // registerBspProcess
			{
				dataTypes.ExecutionRequestId _arg0=dataTypes.ExecutionRequestIdHelper.read(_input);
				java.lang.String _arg1=_input.read_string();
				_out = handler.createReply();
				dataTypes.BspProcessZeroInformationHelper.write(_out,registerBspProcess(_arg0,_arg1));
				break;
			}
			case 3: // setProcessExecutionStarted
			{
				java.lang.String _arg0=_input.read_string();
				java.lang.String _arg1=_input.read_string();
				int _arg2=_input.read_long();
				dataTypes.ExecutionRequestId _arg3=dataTypes.ExecutionRequestIdHelper.read(_input);
				_out = handler.createReply();
				setProcessExecutionStarted(_arg0,_arg1,_arg2,_arg3);
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
