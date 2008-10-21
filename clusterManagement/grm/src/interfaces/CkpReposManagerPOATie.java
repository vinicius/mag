package interfaces;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "CkpReposManager"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class CkpReposManagerPOATie
	extends CkpReposManagerPOA
{
	private CkpReposManagerOperations _delegate;

	private POA _poa;
	public CkpReposManagerPOATie(CkpReposManagerOperations delegate)
	{
		_delegate = delegate;
	}
	public CkpReposManagerPOATie(CkpReposManagerOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public interfaces.CkpReposManager _this()
	{
		return interfaces.CkpReposManagerHelper.narrow(_this_object());
	}
	public interfaces.CkpReposManager _this(org.omg.CORBA.ORB orb)
	{
		return interfaces.CkpReposManagerHelper.narrow(_this_object(orb));
	}
	public CkpReposManagerOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(CkpReposManagerOperations delegate)
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
	public void notifyCkpStored(int ckpReposId, java.lang.String executionId, int ckpNumber, int fragmentNumber, int availableSpace)
	{
_delegate.notifyCkpStored(ckpReposId,executionId,ckpNumber,fragmentNumber,availableSpace);
	}

	public subtypes.CheckpointRemovalList getCheckpointRemovalList(int ckpReposId)
	{
		return _delegate.getCheckpointRemovalList(ckpReposId);
	}

	public types.CkpInfo getLastCkpInfo(java.lang.String executionId)
	{
		return _delegate.getLastCkpInfo(executionId);
	}

	public int updateRepositoryStatus(int ckpReposId, int availableSpace)
	{
		return _delegate.updateRepositoryStatus(ckpReposId,availableSpace);
	}

	public int registerCkpRepos(java.lang.String ipAddress, short port, int availableSpace)
	{
		return _delegate.registerCkpRepos(ipAddress,port,availableSpace);
	}

	public subtypes.CkpReposAddressList getCkpRepos(java.lang.String executionId, java.lang.String ipAddress, int ckpNumber, java.lang.String[] checksumList, int ckpSize)
	{
		return _delegate.getCkpRepos(executionId,ipAddress,ckpNumber,checksumList,ckpSize);
	}

	public void setCkpReposMode(java.lang.String executionId, int mode, int nNodes, int nExtra)
	{
_delegate.setCkpReposMode(executionId,mode,nNodes,nExtra);
	}

}
