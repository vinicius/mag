package clusterManagement;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "ExecutionManagementAgent"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class ExecutionManagementAgentPOATie
	extends ExecutionManagementAgentPOA
{
	private ExecutionManagementAgentOperations _delegate;

	private POA _poa;
	public ExecutionManagementAgentPOATie(ExecutionManagementAgentOperations delegate)
	{
		_delegate = delegate;
	}
	public ExecutionManagementAgentPOATie(ExecutionManagementAgentOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public clusterManagement.ExecutionManagementAgent _this()
	{
		return clusterManagement.ExecutionManagementAgentHelper.narrow(_this_object());
	}
	public clusterManagement.ExecutionManagementAgent _this(org.omg.CORBA.ORB orb)
	{
		return clusterManagement.ExecutionManagementAgentHelper.narrow(_this_object(orb));
	}
	public ExecutionManagementAgentOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(ExecutionManagementAgentOperations delegate)
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
	public void setExecutionScheduled(dataTypes.ApplicationExecutionInformation applicationExecutionInformation, dataTypes.ProcessExecutionInformation[] processExecutionInformationSequence)
	{
_delegate.setExecutionScheduled(applicationExecutionInformation,processExecutionInformationSequence);
	}

	public void setExecutionRefused(dataTypes.ApplicationExecutionInformation applicationExecutionInformation, dataTypes.ProcessExecutionInformation[] processExecutionInformationSequence)
	{
_delegate.setExecutionRefused(applicationExecutionInformation,processExecutionInformationSequence);
	}

}
