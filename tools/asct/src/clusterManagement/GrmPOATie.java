package clusterManagement;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "Grm"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class GrmPOATie
	extends GrmPOA
{
	private GrmOperations _delegate;

	private POA _poa;
	public GrmPOATie(GrmOperations delegate)
	{
		_delegate = delegate;
	}
	public GrmPOATie(GrmOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public clusterManagement.Grm _this()
	{
		return clusterManagement.GrmHelper.narrow(_this_object());
	}
	public clusterManagement.Grm _this(org.omg.CORBA.ORB orb)
	{
		return clusterManagement.GrmHelper.narrow(_this_object(orb));
	}
	public GrmOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(GrmOperations delegate)
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
	public void registerGrm(java.lang.String childGrmIor, dataTypes.SubtreeInformation subtreeInformation)
	{
_delegate.registerGrm(childGrmIor,subtreeInformation);
	}

	public void setKeepAliveInterval(int seconds)
	{
_delegate.setKeepAliveInterval(seconds);
	}

	public void registerLrm(java.lang.String lrmIor, dataTypes.NodeStaticInformation nodeStaticInformation)
	{
_delegate.registerLrm(lrmIor,nodeStaticInformation);
	}

	public boolean requestRemoteExecution(dataTypes.ApplicationExecutionInformation applicationExecutionInformation, dataTypes.ProcessExecutionInformation[] processExecutionInformationSequence)
	{
		return _delegate.requestRemoteExecution(applicationExecutionInformation,processExecutionInformationSequence);
	}

	public void setSampleInterval(int seconds)
	{
_delegate.setSampleInterval(seconds);
	}

	public boolean isAvailable()
	{
		return _delegate.isAvailable();
	}

	public java.lang.String migrationLocationRequest(java.lang.String[] locations)
	{
		return _delegate.migrationLocationRequest(locations);
	}

	public void updateLrmInformation(java.lang.String lrmIor, dataTypes.NodeDynamicInformation nodeDynamicInformation)
	{
_delegate.updateLrmInformation(lrmIor,nodeDynamicInformation);
	}

	public void setLocation(java.lang.String location, java.lang.String ior)
	{
_delegate.setLocation(location,ior);
	}

	public void updateGrmInformation(java.lang.String childGrmIor, dataTypes.SubtreeInformation subtreeInformation)
	{
_delegate.updateGrmInformation(childGrmIor,subtreeInformation);
	}

}
