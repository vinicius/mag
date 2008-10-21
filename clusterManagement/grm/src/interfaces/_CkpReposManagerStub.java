package interfaces;


/**
 *	Generated from IDL interface "CkpReposManager"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class _CkpReposManagerStub
	extends org.omg.CORBA.portable.ObjectImpl
	implements interfaces.CkpReposManager
{
	private String[] ids = {"IDL:interfaces/CkpReposManager:1.0"};
	public String[] _ids()
	{
		return ids;
	}

	public final static java.lang.Class _opsClass = interfaces.CkpReposManagerOperations.class;
	public void notifyCkpStored(int ckpReposId, java.lang.String executionId, int ckpNumber, int fragmentNumber, int availableSpace)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "notifyCkpStored", true);
				_os.write_long(ckpReposId);
				_os.write_string(executionId);
				_os.write_long(ckpNumber);
				_os.write_long(fragmentNumber);
				_os.write_long(availableSpace);
				_is = _invoke(_os);
				return;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "notifyCkpStored", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			CkpReposManagerOperations _localServant = (CkpReposManagerOperations)_so.servant;
			try
			{
			_localServant.notifyCkpStored(ckpReposId,executionId,ckpNumber,fragmentNumber,availableSpace);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return;
		}

		}

	}

	public subtypes.CheckpointRemovalList getCheckpointRemovalList(int ckpReposId)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "getCheckpointRemovalList", true);
				_os.write_long(ckpReposId);
				_is = _invoke(_os);
				subtypes.CheckpointRemovalList _result = subtypes.CheckpointRemovalListHelper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "getCheckpointRemovalList", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			CkpReposManagerOperations _localServant = (CkpReposManagerOperations)_so.servant;
			subtypes.CheckpointRemovalList _result;			try
			{
			_result = _localServant.getCheckpointRemovalList(ckpReposId);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public types.CkpInfo getLastCkpInfo(java.lang.String executionId)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "getLastCkpInfo", true);
				_os.write_string(executionId);
				_is = _invoke(_os);
				types.CkpInfo _result = types.CkpInfoHelper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "getLastCkpInfo", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			CkpReposManagerOperations _localServant = (CkpReposManagerOperations)_so.servant;
			types.CkpInfo _result;			try
			{
			_result = _localServant.getLastCkpInfo(executionId);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public int updateRepositoryStatus(int ckpReposId, int availableSpace)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "updateRepositoryStatus", true);
				_os.write_long(ckpReposId);
				_os.write_long(availableSpace);
				_is = _invoke(_os);
				int _result = _is.read_long();
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "updateRepositoryStatus", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			CkpReposManagerOperations _localServant = (CkpReposManagerOperations)_so.servant;
			int _result;			try
			{
			_result = _localServant.updateRepositoryStatus(ckpReposId,availableSpace);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public int registerCkpRepos(java.lang.String ipAddress, short port, int availableSpace)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "registerCkpRepos", true);
				_os.write_string(ipAddress);
				_os.write_short(port);
				_os.write_long(availableSpace);
				_is = _invoke(_os);
				int _result = _is.read_long();
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "registerCkpRepos", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			CkpReposManagerOperations _localServant = (CkpReposManagerOperations)_so.servant;
			int _result;			try
			{
			_result = _localServant.registerCkpRepos(ipAddress,port,availableSpace);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public subtypes.CkpReposAddressList getCkpRepos(java.lang.String executionId, java.lang.String ipAddress, int ckpNumber, java.lang.String[] checksumList, int ckpSize)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "getCkpRepos", true);
				_os.write_string(executionId);
				_os.write_string(ipAddress);
				_os.write_long(ckpNumber);
				types.ChecksumListHelper.write(_os,checksumList);
				_os.write_long(ckpSize);
				_is = _invoke(_os);
				subtypes.CkpReposAddressList _result = subtypes.CkpReposAddressListHelper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "getCkpRepos", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			CkpReposManagerOperations _localServant = (CkpReposManagerOperations)_so.servant;
			subtypes.CkpReposAddressList _result;			try
			{
			_result = _localServant.getCkpRepos(executionId,ipAddress,ckpNumber,checksumList,ckpSize);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public void setCkpReposMode(java.lang.String executionId, int mode, int nNodes, int nExtra)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "setCkpReposMode", true);
				_os.write_string(executionId);
				_os.write_long(mode);
				_os.write_long(nNodes);
				_os.write_long(nExtra);
				_is = _invoke(_os);
				return;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "setCkpReposMode", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			CkpReposManagerOperations _localServant = (CkpReposManagerOperations)_so.servant;
			try
			{
			_localServant.setCkpReposMode(executionId,mode,nNodes,nExtra);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return;
		}

		}

	}

}
