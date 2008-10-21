package clusterManagement;


/**
 *	Generated from IDL interface "Arsm"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class _ArsmStub
	extends org.omg.CORBA.portable.ObjectImpl
	implements clusterManagement.Arsm
{
	private String[] ids = {"IDL:clusterManagement/Arsm:1.0"};
	public String[] _ids()
	{
		return ids;
	}

	public final static java.lang.Class _opsClass = clusterManagement.ArsmOperations.class;
	public byte[] initiateContext(byte[] contextStream) throws clusterManagement.ContextInitiationException
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "initiateContext", true);
				dataTypes.StreamHelper.write(_os,contextStream);
				_is = _invoke(_os);
				byte[] _result = dataTypes.StreamHelper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:clusterManagement/ContextInitiationException:1.0"))
				{
					throw clusterManagement.ContextInitiationExceptionHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "initiateContext", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			ArsmOperations _localServant = (ArsmOperations)_so.servant;
			byte[] _result;			try
			{
			_result = _localServant.initiateContext(contextStream);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public void finalizeContext(byte[] contextStream) throws clusterManagement.ContextFinalizationException
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "finalizeContext", true);
				dataTypes.StreamHelper.write(_os,contextStream);
				_is = _invoke(_os);
				return;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:clusterManagement/ContextFinalizationException:1.0"))
				{
					throw clusterManagement.ContextFinalizationExceptionHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "finalizeContext", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			ArsmOperations _localServant = (ArsmOperations)_so.servant;
			try
			{
			_localServant.finalizeContext(contextStream);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return;
		}

		}

	}

	public byte[] selfTest(byte[] signedMessage) throws clusterManagement.SelfTestException
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "selfTest", true);
				dataTypes.StreamHelper.write(_os,signedMessage);
				_is = _invoke(_os);
				byte[] _result = dataTypes.StreamHelper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:clusterManagement/SelfTestException:1.0"))
				{
					throw clusterManagement.SelfTestExceptionHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "selfTest", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			ArsmOperations _localServant = (ArsmOperations)_so.servant;
			byte[] _result;			try
			{
			_result = _localServant.selfTest(signedMessage);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public byte[] requestSignature(byte[] messageStream) throws clusterManagement.SignatureRequestException
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "requestSignature", true);
				dataTypes.StreamHelper.write(_os,messageStream);
				_is = _invoke(_os);
				byte[] _result = dataTypes.StreamHelper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:clusterManagement/SignatureRequestException:1.0"))
				{
					throw clusterManagement.SignatureRequestExceptionHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "requestSignature", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			ArsmOperations _localServant = (ArsmOperations)_so.servant;
			byte[] _result;			try
			{
			_result = _localServant.requestSignature(messageStream);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public byte[] checkSignature(byte[] messageStream) throws clusterManagement.SignatureCheckingException
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "checkSignature", true);
				dataTypes.StreamHelper.write(_os,messageStream);
				_is = _invoke(_os);
				byte[] _result = dataTypes.StreamHelper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:clusterManagement/SignatureCheckingException:1.0"))
				{
					throw clusterManagement.SignatureCheckingExceptionHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "checkSignature", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			ArsmOperations _localServant = (ArsmOperations)_so.servant;
			byte[] _result;			try
			{
			_result = _localServant.checkSignature(messageStream);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

}
