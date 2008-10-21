package clusterManagement;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "CrmAgent"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class CrmAgentPOATie
	extends CrmAgentPOA
{
	private CrmAgentOperations _delegate;

	private POA _poa;
	public CrmAgentPOATie(CrmAgentOperations delegate)
	{
		_delegate = delegate;
	}
	public CrmAgentPOATie(CrmAgentOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public clusterManagement.CrmAgent _this()
	{
		return clusterManagement.CrmAgentHelper.narrow(_this_object());
	}
	public clusterManagement.CrmAgent _this(org.omg.CORBA.ORB orb)
	{
		return clusterManagement.CrmAgentHelper.narrow(_this_object(orb));
	}
	public CrmAgentOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(CrmAgentOperations delegate)
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
	public void requestRemoteExecution(dataTypes.ApplicationExecutionInformation applicationExecutionInformation, dataTypes.ProcessExecutionInformation[] processExecutionInformationSequence, java.lang.String[] lrmIors)
	{
_delegate.requestRemoteExecution(applicationExecutionInformation,processExecutionInformationSequence,lrmIors);
	}

}
