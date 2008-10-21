package tools;


/**
 *	Generated from IDL interface "Asct"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class _AsctStub
	extends org.omg.CORBA.portable.ObjectImpl
	implements tools.Asct
{
	private String[] ids = {"IDL:tools/Asct:1.0"};
	public String[] _ids()
	{
		return ids;
	}

	public final static java.lang.Class _opsClass = tools.AsctOperations.class;
	public dataTypes.FileStruct[] getInputFiles(dataTypes.ExecutionRequestId executionRequestId)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "getInputFiles", true);
				dataTypes.ExecutionRequestIdHelper.write(_os,executionRequestId);
				_is = _invoke(_os);
				dataTypes.FileStruct[] _result = dataTypes.FileSequenceHelper.read(_is);
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
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "getInputFiles", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			AsctOperations _localServant = (AsctOperations)_so.servant;
			dataTypes.FileStruct[] _result;			try
			{
			_result = _localServant.getInputFiles(executionRequestId);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public void setExecutionFinished(dataTypes.ExecutionRequestId executionRequestId)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "setExecutionFinished", true);
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
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "setExecutionFinished", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			AsctOperations _localServant = (AsctOperations)_so.servant;
			try
			{
			_localServant.setExecutionFinished(executionRequestId);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return;
		}

		}

	}

	public void setExecutionAccepted(dataTypes.RequestAcceptanceInformation requestAcceptanceInformation)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "setExecutionAccepted", true);
				dataTypes.RequestAcceptanceInformationHelper.write(_os,requestAcceptanceInformation);
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
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "setExecutionAccepted", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			AsctOperations _localServant = (AsctOperations)_so.servant;
			try
			{
			_localServant.setExecutionAccepted(requestAcceptanceInformation);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return;
		}

		}

	}

	public void setExecutionRefused(dataTypes.ExecutionRequestId executionRequestId)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "setExecutionRefused", true);
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
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "setExecutionRefused", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			AsctOperations _localServant = (AsctOperations)_so.servant;
			try
			{
			_localServant.setExecutionRefused(executionRequestId);
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
