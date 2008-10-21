package resourceProviders;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "Lrm"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class LrmPOATie
	extends LrmPOA
{
	private LrmOperations _delegate;

	private POA _poa;
	public LrmPOATie(LrmOperations delegate)
	{
		_delegate = delegate;
	}
	public LrmPOATie(LrmOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public resourceProviders.Lrm _this()
	{
		return resourceProviders.LrmHelper.narrow(_this_object());
	}
	public resourceProviders.Lrm _this(org.omg.CORBA.ORB orb)
	{
		return resourceProviders.LrmHelper.narrow(_this_object(orb));
	}
	public LrmOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(LrmOperations delegate)
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

	public void setKeepAliveInterval(int seconds)
	{
_delegate.setKeepAliveInterval(seconds);
	}

	public int requestExecution(dataTypes.ApplicationExecutionInformation applicationExecutionInformation, dataTypes.ProcessExecutionInformation processExecutionInformation)
	{
		return _delegate.requestExecution(applicationExecutionInformation,processExecutionInformation);
	}

	public dataTypes.FileStruct[] requestOutputFiles(java.lang.String executionId)
	{
		return _delegate.requestOutputFiles(executionId);
	}

	public void setSampleInterval(int seconds)
	{
_delegate.setSampleInterval(seconds);
	}

	public boolean isAvailable()
	{
		return _delegate.isAvailable();
	}

	public void killProcess(java.lang.String executionId)
	{
_delegate.killProcess(executionId);
	}

	public java.lang.String getStatus(java.lang.String executionId)
	{
		return _delegate.getStatus(executionId);
	}

}
