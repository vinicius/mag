package clusterManagement;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "Crm"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class CrmPOATie
	extends CrmPOA
{
	private CrmOperations _delegate;

	private POA _poa;
	public CrmPOATie(CrmOperations delegate)
	{
		_delegate = delegate;
	}
	public CrmPOATie(CrmOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public clusterManagement.Crm _this()
	{
		return clusterManagement.CrmHelper.narrow(_this_object());
	}
	public clusterManagement.Crm _this(org.omg.CORBA.ORB orb)
	{
		return clusterManagement.CrmHelper.narrow(_this_object(orb));
	}
	public CrmOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(CrmOperations delegate)
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
	public int requestExecution(dataTypes.ApplicationExecutionInformation applicationExecutionInformation, dataTypes.ProcessExecutionInformation processExecutionInformation, java.lang.String[] lrmIors)
	{
		return _delegate.requestExecution(applicationExecutionInformation,processExecutionInformation,lrmIors);
	}

}
