package tools;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "Asct"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class AsctPOATie
	extends AsctPOA
{
	private AsctOperations _delegate;

	private POA _poa;
	public AsctPOATie(AsctOperations delegate)
	{
		_delegate = delegate;
	}
	public AsctPOATie(AsctOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public tools.Asct _this()
	{
		return tools.AsctHelper.narrow(_this_object());
	}
	public tools.Asct _this(org.omg.CORBA.ORB orb)
	{
		return tools.AsctHelper.narrow(_this_object(orb));
	}
	public AsctOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(AsctOperations delegate)
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
	public dataTypes.FileStruct[] getInputFiles(dataTypes.ExecutionRequestId executionRequestId)
	{
		return _delegate.getInputFiles(executionRequestId);
	}

	public void setExecutionFinished(dataTypes.ExecutionRequestId executionRequestId)
	{
_delegate.setExecutionFinished(executionRequestId);
	}

	public void setExecutionAccepted(dataTypes.RequestAcceptanceInformation requestAcceptanceInformation)
	{
_delegate.setExecutionAccepted(requestAcceptanceInformation);
	}

	public void setExecutionRefused(dataTypes.ExecutionRequestId executionRequestId)
	{
_delegate.setExecutionRefused(executionRequestId);
	}

}
