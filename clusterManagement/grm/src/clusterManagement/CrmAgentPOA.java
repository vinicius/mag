package clusterManagement;

/**
 *	Generated from IDL interface "CrmAgent"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public abstract class CrmAgentPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, clusterManagement.CrmAgentOperations
{
	static private final java.util.Hashtable m_opsHash = new java.util.Hashtable();
	static
	{
		m_opsHash.put ( "requestRemoteExecution", new java.lang.Integer(0));
	}
	private String[] ids = {"IDL:clusterManagement/CrmAgent:1.0"};
	public clusterManagement.CrmAgent _this()
	{
		return clusterManagement.CrmAgentHelper.narrow(_this_object());
	}
	public clusterManagement.CrmAgent _this(org.omg.CORBA.ORB orb)
	{
		return clusterManagement.CrmAgentHelper.narrow(_this_object(orb));
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
			case 0: // requestRemoteExecution
			{
				dataTypes.ApplicationExecutionInformation _arg0=dataTypes.ApplicationExecutionInformationHelper.read(_input);
				dataTypes.ProcessExecutionInformation[] _arg1=dataTypes.ProcessExecutionInformationSequenceHelper.read(_input);
				java.lang.String[] _arg2=dataTypes.FileNameSequenceHelper.read(_input);
				_out = handler.createReply();
				requestRemoteExecution(_arg0,_arg1,_arg2);
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
