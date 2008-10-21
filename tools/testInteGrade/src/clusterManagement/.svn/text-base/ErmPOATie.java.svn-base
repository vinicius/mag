package clusterManagement;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "Erm"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class ErmPOATie
	extends ErmPOA
{
	private ErmOperations _delegate;

	private POA _poa;
	public ErmPOATie(ErmOperations delegate)
	{
		_delegate = delegate;
	}
	public ErmPOATie(ErmOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public clusterManagement.Erm _this()
	{
		return clusterManagement.ErmHelper.narrow(_this_object());
	}
	public clusterManagement.Erm _this(org.omg.CORBA.ORB orb)
	{
		return clusterManagement.ErmHelper.narrow(_this_object(orb));
	}
	public ErmOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(ErmOperations delegate)
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

	public dataTypes.FileStruct[] getInputFiles(dataTypes.ExecutionRequestId executionRequestId)
	{
		return _delegate.getInputFiles(executionRequestId);
	}

	public int getLastCheckpointNumber(java.lang.String executionId)
	{
		return _delegate.getLastCheckpointNumber(executionId);
	}

	public void setKeepAliveInterval(int seconds)
	{
_delegate.setKeepAliveInterval(seconds);
	}

	public int setProcessExecutionFinished(java.lang.String lrmIor, int restartId, dataTypes.ExecutionRequestId executionRequestId, int status)
	{
		return _delegate.setProcessExecutionFinished(lrmIor,restartId,executionRequestId,status);
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

	public boolean isAvailable()
	{
		return _delegate.isAvailable();
	}

	public java.lang.String getStatus(java.lang.String executionId)
	{
		return _delegate.getStatus(executionId);
	}

	public void killProcess(java.lang.String executionId)
	{
_delegate.killProcess(executionId);
	}

	public void setExecutionRefused(dataTypes.ExecutionRequestId executionRequestId)
	{
_delegate.setExecutionRefused(executionRequestId);
	}

	public void setExecutionAccepted(dataTypes.RequestAcceptanceInformation requestAcceptanceInformation)
	{
_delegate.setExecutionAccepted(requestAcceptanceInformation);
	}

	public void setProcessExecutionStarted(java.lang.String lrmIor, java.lang.String executionId, int restartId, dataTypes.ExecutionRequestId executionRequestId)
	{
_delegate.setProcessExecutionStarted(lrmIor,executionId,restartId,executionRequestId);
	}

	public void setExecutionFinished(dataTypes.ExecutionRequestId executionRequestId)
	{
_delegate.setExecutionFinished(executionRequestId);
	}

}
