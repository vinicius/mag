package clusterManagement;


/**
 *	Generated from IDL interface "ExecutionManager"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class _ExecutionManagerStub
	extends org.omg.CORBA.portable.ObjectImpl
	implements clusterManagement.ExecutionManager
{
	private String[] ids = {"IDL:clusterManagement/ExecutionManager:1.0"};
	public String[] _ids()
	{
		return ids;
	}

	public final static java.lang.Class _opsClass = clusterManagement.ExecutionManagerOperations.class;
	public void setExecutionScheduled(dataTypes.ApplicationExecutionInformation applicationExecutionInformation, dataTypes.ProcessExecutionInformation[] processExecutionInformationSequence)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "setExecutionScheduled", true);
				dataTypes.ApplicationExecutionInformationHelper.write(_os,applicationExecutionInformation);
				dataTypes.ProcessExecutionInformationSequenceHelper.write(_os,processExecutionInformationSequence);
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
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "setExecutionScheduled", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			ExecutionManagerOperations _localServant = (ExecutionManagerOperations)_so.servant;
			try
			{
			_localServant.setExecutionScheduled(applicationExecutionInformation,processExecutionInformationSequence);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return;
		}

		}

	}

	public int setProcessExecutionFinished(int restartId, dataTypes.ExecutionRequestId executionRequestId, int status)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "setProcessExecutionFinished", true);
				_os.write_long(restartId);
				dataTypes.ExecutionRequestIdHelper.write(_os,executionRequestId);
				_os.write_long(status);
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
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "setProcessExecutionFinished", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			ExecutionManagerOperations _localServant = (ExecutionManagerOperations)_so.servant;
			int _result;			try
			{
			_result = _localServant.setProcessExecutionFinished(restartId,executionRequestId,status);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public dataTypes.BspProcessZeroInformation registerBspProcess(dataTypes.ExecutionRequestId executionRequestId, java.lang.String bspProxyIor)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "registerBspProcess", true);
				dataTypes.ExecutionRequestIdHelper.write(_os,executionRequestId);
				_os.write_string(bspProxyIor);
				_is = _invoke(_os);
				dataTypes.BspProcessZeroInformation _result = dataTypes.BspProcessZeroInformationHelper.read(_is);
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
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "registerBspProcess", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			ExecutionManagerOperations _localServant = (ExecutionManagerOperations)_so.servant;
			dataTypes.BspProcessZeroInformation _result;			try
			{
			_result = _localServant.registerBspProcess(executionRequestId,bspProxyIor);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public void setProcessExecutionStarted(java.lang.String lrmIor, java.lang.String executionId, int restartId, dataTypes.ExecutionRequestId executionRequestId)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "setProcessExecutionStarted", true);
				_os.write_string(lrmIor);
				_os.write_string(executionId);
				_os.write_long(restartId);
				dataTypes.ExecutionRequestIdHelper.write(_os,executionRequestId);
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
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "setProcessExecutionStarted", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			ExecutionManagerOperations _localServant = (ExecutionManagerOperations)_so.servant;
			try
			{
			_localServant.setProcessExecutionStarted(lrmIor,executionId,restartId,executionRequestId);
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
