package clusterManagement;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "ExecutionManager"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class ExecutionManagerPOATie
	extends ExecutionManagerPOA
{
	private ExecutionManagerOperations _delegate;

	private POA _poa;
	public ExecutionManagerPOATie(ExecutionManagerOperations delegate)
	{
		_delegate = delegate;
	}
	public ExecutionManagerPOATie(ExecutionManagerOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public clusterManagement.ExecutionManager _this()
	{
		return clusterManagement.ExecutionManagerHelper.narrow(_this_object());
	}
	public clusterManagement.ExecutionManager _this(org.omg.CORBA.ORB orb)
	{
		return clusterManagement.ExecutionManagerHelper.narrow(_this_object(orb));
	}
	public ExecutionManagerOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(ExecutionManagerOperations delegate)
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

	public int setProcessExecutionFinished(int restartId, dataTypes.ExecutionRequestId executionRequestId, int status)
	{
		return _delegate.setProcessExecutionFinished(restartId,executionRequestId,status);
	}

	public dataTypes.BspProcessZeroInformation registerBspProcess(dataTypes.ExecutionRequestId executionRequestId, java.lang.String bspProxyIor)
	{
		return _delegate.registerBspProcess(executionRequestId,bspProxyIor);
	}

	public void setProcessExecutionStarted(java.lang.String lrmIor, java.lang.String executionId, int restartId, dataTypes.ExecutionRequestId executionRequestId)
	{
_delegate.setProcessExecutionStarted(lrmIor,executionId,restartId,executionRequestId);
	}

}
