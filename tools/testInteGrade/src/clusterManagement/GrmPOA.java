package clusterManagement;

/**
 *	Generated from IDL interface "Grm"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public abstract class GrmPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, clusterManagement.GrmOperations
{
	static private final java.util.Hashtable m_opsHash = new java.util.Hashtable();
	static
	{
		m_opsHash.put ( "updateLrmInformation", new java.lang.Integer(0));
		m_opsHash.put ( "setSampleInterval", new java.lang.Integer(1));
		m_opsHash.put ( "updateGrmInformation", new java.lang.Integer(2));
		m_opsHash.put ( "registerGrm", new java.lang.Integer(3));
		m_opsHash.put ( "requestRemoteExecution", new java.lang.Integer(4));
		m_opsHash.put ( "registerLrm", new java.lang.Integer(5));
		m_opsHash.put ( "isAvailable", new java.lang.Integer(6));
		m_opsHash.put ( "setKeepAliveInterval", new java.lang.Integer(7));
	}
	private String[] ids = {"IDL:clusterManagement/Grm:1.0"};
	public clusterManagement.Grm _this()
	{
		return clusterManagement.GrmHelper.narrow(_this_object());
	}
	public clusterManagement.Grm _this(org.omg.CORBA.ORB orb)
	{
		return clusterManagement.GrmHelper.narrow(_this_object(orb));
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
			case 0: // updateLrmInformation
			{
				java.lang.String _arg0=_input.read_string();
				dataTypes.NodeDynamicInformation _arg1=dataTypes.NodeDynamicInformationHelper.read(_input);
				_out = handler.createReply();
				updateLrmInformation(_arg0,_arg1);
				break;
			}
			case 1: // setSampleInterval
			{
				int _arg0=_input.read_long();
				_out = handler.createReply();
				setSampleInterval(_arg0);
				break;
			}
			case 2: // updateGrmInformation
			{
				java.lang.String _arg0=_input.read_string();
				dataTypes.SubtreeInformation _arg1=dataTypes.SubtreeInformationHelper.read(_input);
				_out = handler.createReply();
				updateGrmInformation(_arg0,_arg1);
				break;
			}
			case 3: // registerGrm
			{
				java.lang.String _arg0=_input.read_string();
				dataTypes.SubtreeInformation _arg1=dataTypes.SubtreeInformationHelper.read(_input);
				_out = handler.createReply();
				registerGrm(_arg0,_arg1);
				break;
			}
			case 4: // requestRemoteExecution
			{
				dataTypes.ApplicationExecutionInformation _arg0=dataTypes.ApplicationExecutionInformationHelper.read(_input);
				dataTypes.ProcessExecutionInformation[] _arg1=dataTypes.ProcessExecutionInformationSequenceHelper.read(_input);
				_out = handler.createReply();
				_out.write_boolean(requestRemoteExecution(_arg0,_arg1));
				break;
			}
			case 5: // registerLrm
			{
				java.lang.String _arg0=_input.read_string();
				dataTypes.NodeStaticInformation _arg1=dataTypes.NodeStaticInformationHelper.read(_input);
				_out = handler.createReply();
				registerLrm(_arg0,_arg1);
				break;
			}
			case 6: // isAvailable
			{
				_out = handler.createReply();
				_out.write_boolean(isAvailable());
				break;
			}
			case 7: // setKeepAliveInterval
			{
				int _arg0=_input.read_long();
				_out = handler.createReply();
				setKeepAliveInterval(_arg0);
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
