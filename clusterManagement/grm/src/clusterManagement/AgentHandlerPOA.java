package clusterManagement;

/**
 *	Generated from IDL interface "AgentHandler"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public abstract class AgentHandlerPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, clusterManagement.AgentHandlerOperations
{
	static private final java.util.Hashtable m_opsHash = new java.util.Hashtable();
	static
	{
		m_opsHash.put ( "restartExecution", new java.lang.Integer(0));
		m_opsHash.put ( "getLastCheckpointNumber", new java.lang.Integer(1));
		m_opsHash.put ( "unregisterAgent", new java.lang.Integer(2));
		m_opsHash.put ( "setKeepAliveInterval", new java.lang.Integer(3));
		m_opsHash.put ( "requestOutputFiles", new java.lang.Integer(4));
		m_opsHash.put ( "requestExecution", new java.lang.Integer(5));
		m_opsHash.put ( "setSampleInterval", new java.lang.Integer(6));
		m_opsHash.put ( "restoreExecution", new java.lang.Integer(7));
		m_opsHash.put ( "isAvailable", new java.lang.Integer(8));
		m_opsHash.put ( "releaseNode", new java.lang.Integer(9));
		m_opsHash.put ( "getStatus", new java.lang.Integer(10));
		m_opsHash.put ( "killProcess", new java.lang.Integer(11));
		m_opsHash.put ( "deleteAllAgents", new java.lang.Integer(12));
	}
	private String[] ids = {"IDL:clusterManagement/AgentHandler:1.0","IDL:resourceProviders/Lrm:1.0"};
	public clusterManagement.AgentHandler _this()
	{
		return clusterManagement.AgentHandlerHelper.narrow(_this_object());
	}
	public clusterManagement.AgentHandler _this(org.omg.CORBA.ORB orb)
	{
		return clusterManagement.AgentHandlerHelper.narrow(_this_object(orb));
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
			case 0: // restartExecution
			{
				java.lang.String _arg0=_input.read_string();
				java.lang.String _arg1=_input.read_string();
				_out = handler.createReply();
				_out.write_long(restartExecution(_arg0,_arg1));
				break;
			}
			case 1: // getLastCheckpointNumber
			{
				java.lang.String _arg0=_input.read_string();
				_out = handler.createReply();
				_out.write_long(getLastCheckpointNumber(_arg0));
				break;
			}
			case 2: // unregisterAgent
			{
				java.lang.String _arg0=_input.read_string();
				_out = handler.createReply();
				unregisterAgent(_arg0);
				break;
			}
			case 3: // setKeepAliveInterval
			{
				int _arg0=_input.read_long();
				_out = handler.createReply();
				setKeepAliveInterval(_arg0);
				break;
			}
			case 4: // requestOutputFiles
			{
				java.lang.String _arg0=_input.read_string();
				_out = handler.createReply();
				dataTypes.FileSequenceHelper.write(_out,requestOutputFiles(_arg0));
				break;
			}
			case 5: // requestExecution
			{
				dataTypes.ApplicationExecutionInformation _arg0=dataTypes.ApplicationExecutionInformationHelper.read(_input);
				dataTypes.ProcessExecutionInformation _arg1=dataTypes.ProcessExecutionInformationHelper.read(_input);
				_out = handler.createReply();
				_out.write_long(requestExecution(_arg0,_arg1));
				break;
			}
			case 6: // setSampleInterval
			{
				int _arg0=_input.read_long();
				_out = handler.createReply();
				setSampleInterval(_arg0);
				break;
			}
			case 7: // restoreExecution
			{
				dataTypes.ApplicationExecutionInformation _arg0=dataTypes.ApplicationExecutionInformationHelper.read(_input);
				dataTypes.ProcessExecutionInformation _arg1=dataTypes.ProcessExecutionInformationHelper.read(_input);
				_out = handler.createReply();
				restoreExecution(_arg0,_arg1);
				break;
			}
			case 8: // isAvailable
			{
				_out = handler.createReply();
				_out.write_boolean(isAvailable());
				break;
			}
			case 9: // releaseNode
			{
				_out = handler.createReply();
				releaseNode();
				break;
			}
			case 10: // getStatus
			{
				java.lang.String _arg0=_input.read_string();
				_out = handler.createReply();
				_out.write_string(getStatus(_arg0));
				break;
			}
			case 11: // killProcess
			{
				java.lang.String _arg0=_input.read_string();
				_out = handler.createReply();
				killProcess(_arg0);
				break;
			}
			case 12: // deleteAllAgents
			{
				_out = handler.createReply();
				deleteAllAgents();
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
