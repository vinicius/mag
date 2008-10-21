package tools;

/**
 *	Generated from IDL interface "Asct"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public abstract class AsctPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, tools.AsctOperations
{
	static private final java.util.Hashtable m_opsHash = new java.util.Hashtable();
	static
	{
		m_opsHash.put ( "getInputFiles", new java.lang.Integer(0));
		m_opsHash.put ( "setExecutionFinished", new java.lang.Integer(1));
		m_opsHash.put ( "setExecutionAccepted", new java.lang.Integer(2));
		m_opsHash.put ( "setExecutionRefused", new java.lang.Integer(3));
	}
	private String[] ids = {"IDL:tools/Asct:1.0"};
	public tools.Asct _this()
	{
		return tools.AsctHelper.narrow(_this_object());
	}
	public tools.Asct _this(org.omg.CORBA.ORB orb)
	{
		return tools.AsctHelper.narrow(_this_object(orb));
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
			case 0: // getInputFiles
			{
				dataTypes.ExecutionRequestId _arg0=dataTypes.ExecutionRequestIdHelper.read(_input);
				_out = handler.createReply();
				dataTypes.FileSequenceHelper.write(_out,getInputFiles(_arg0));
				break;
			}
			case 1: // setExecutionFinished
			{
				dataTypes.ExecutionRequestId _arg0=dataTypes.ExecutionRequestIdHelper.read(_input);
				_out = handler.createReply();
				setExecutionFinished(_arg0);
				break;
			}
			case 2: // setExecutionAccepted
			{
				dataTypes.RequestAcceptanceInformation _arg0=dataTypes.RequestAcceptanceInformationHelper.read(_input);
				_out = handler.createReply();
				setExecutionAccepted(_arg0);
				break;
			}
			case 3: // setExecutionRefused
			{
				dataTypes.ExecutionRequestId _arg0=dataTypes.ExecutionRequestIdHelper.read(_input);
				_out = handler.createReply();
				setExecutionRefused(_arg0);
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
