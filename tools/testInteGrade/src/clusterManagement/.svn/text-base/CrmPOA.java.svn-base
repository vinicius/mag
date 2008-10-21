package clusterManagement;

/**
 *	Generated from IDL interface "Crm"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public abstract class CrmPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, clusterManagement.CrmOperations
{
	static private final java.util.Hashtable m_opsHash = new java.util.Hashtable();
	static
	{
		m_opsHash.put ( "requestExecution", new java.lang.Integer(0));
	}
	private String[] ids = {"IDL:clusterManagement/Crm:1.0"};
	public clusterManagement.Crm _this()
	{
		return clusterManagement.CrmHelper.narrow(_this_object());
	}
	public clusterManagement.Crm _this(org.omg.CORBA.ORB orb)
	{
		return clusterManagement.CrmHelper.narrow(_this_object(orb));
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
			case 0: // requestExecution
			{
				dataTypes.ApplicationExecutionInformation _arg0=dataTypes.ApplicationExecutionInformationHelper.read(_input);
				dataTypes.ProcessExecutionInformation _arg1=dataTypes.ProcessExecutionInformationHelper.read(_input);
				java.lang.String[] _arg2=dataTypes.FileNameSequenceHelper.read(_input);
				_out = handler.createReply();
				_out.write_long(requestExecution(_arg0,_arg1,_arg2));
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
