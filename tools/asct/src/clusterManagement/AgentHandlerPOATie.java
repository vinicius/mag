package clusterManagement;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "AgentHandler"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class AgentHandlerPOATie
	extends AgentHandlerPOA
{
	private AgentHandlerOperations _delegate;

	private POA _poa;
	public AgentHandlerPOATie(AgentHandlerOperations delegate)
	{
		_delegate = delegate;
	}
	public AgentHandlerPOATie(AgentHandlerOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public clusterManagement.AgentHandler _this()
	{
		return clusterManagement.AgentHandlerHelper.narrow(_this_object());
	}
	public clusterManagement.AgentHandler _this(org.omg.CORBA.ORB orb)
	{
		return clusterManagement.AgentHandlerHelper.narrow(_this_object(orb));
	}
	public AgentHandlerOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(AgentHandlerOperations delegate)
	{
		_delegate = delegate;
	}
	public POA _default_POA()
	{
		if (_poa != null)
		{
			return _poa;
		}
		else
		{
			return super._default_POA();
		}
	}
	public int restartExecution(java.lang.String executionId, java.lang.String processArguments)
	{
		return _delegate.restartExecution(executionId,processArguments);
	}

	public int getLastCheckpointNumber(java.lang.String executionId)
	{
		return _delegate.getLastCheckpointNumber(executionId);
	}

	public void unregisterAgent(java.lang.String executionId)
	{
_delegate.unregisterAgent(executionId);
	}

	public void setKeepAliveInterval(int seconds)
	{
_delegate.setKeepAliveInterval(seconds);
	}

	public dataTypes.FileStruct[] requestOutputFiles(java.lang.String executionId)
	{
		return _delegate.requestOutputFiles(executionId);
	}

	public int requestExecution(dataTypes.ApplicationExecutionInformation applicationExecutionInformation, dataTypes.ProcessExecutionInformation processExecutionInformation)
	{
		return _delegate.requestExecution(applicationExecutionInformation,processExecutionInformation);
	}

	public void setSampleInterval(int seconds)
	{
_delegate.setSampleInterval(seconds);
	}

	public void restoreExecution(dataTypes.ApplicationExecutionInformation applicationExecutionInformation, dataTypes.ProcessExecutionInformation processExecutionInformation)
	{
_delegate.restoreExecution(applicationExecutionInformation,processExecutionInformation);
	}

	public boolean isAvailable()
	{
		return _delegate.isAvailable();
	}

	public void releaseNode()
	{
_delegate.releaseNode();
	}

	public java.lang.String getStatus(java.lang.String executionId)
	{
		return _delegate.getStatus(executionId);
	}

	public void killProcess(java.lang.String executionId)
	{
_delegate.killProcess(executionId);
	}

	public void deleteAllAgents()
	{
_delegate.deleteAllAgents();
	}

}
