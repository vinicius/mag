package clusterManagement;

/**
 *	Generated from IDL interface "ExecutionManagementAgent"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public abstract class ExecutionManagementAgentPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, clusterManagement.ExecutionManagementAgentOperations
{
	static private final java.util.Hashtable m_opsHash = new java.util.Hashtable();
	static
	{
		m_opsHash.put ( "setExecutionScheduled", new java.lang.Integer(0));
		m_opsHash.put ( "setExecutionRefused", new java.lang.Integer(1));
	}
	private String[] ids = {"IDL:clusterManagement/ExecutionManagementAgent:1.0"};
	public clusterManagement.ExecutionManagementAgent _this()
	{
		return clusterManagement.ExecutionManagementAgentHelper.narrow(_this_object());
	}
	public clusterManagement.ExecutionManagementAgent _this(org.omg.CORBA.ORB orb)
	{
		return clusterManagement.ExecutionManagementAgentHelper.narrow(_this_object(orb));
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
			case 1: // setExecutionRefused
			{
				dataTypes.ApplicationExecutionInformation _arg0=dataTypes.ApplicationExecutionInformationHelper.read(_input);
				dataTypes.ProcessExecutionInformation[] _arg1=dataTypes.ProcessExecutionInformationSequenceHelper.read(_input);
				_out = handler.createReply();
				setExecutionRefused(_arg0,_arg1);
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
