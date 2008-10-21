package clusterManagement;

/**
 *	Generated from IDL interface "Arsm"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public abstract class ArsmPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, clusterManagement.ArsmOperations
{
	static private final java.util.Hashtable m_opsHash = new java.util.Hashtable();
	static
	{
		m_opsHash.put ( "initiateContext", new java.lang.Integer(0));
		m_opsHash.put ( "finalizeContext", new java.lang.Integer(1));
		m_opsHash.put ( "selfTest", new java.lang.Integer(2));
		m_opsHash.put ( "requestSignature", new java.lang.Integer(3));
		m_opsHash.put ( "checkSignature", new java.lang.Integer(4));
	}
	private String[] ids = {"IDL:clusterManagement/Arsm:1.0"};
	public clusterManagement.Arsm _this()
	{
		return clusterManagement.ArsmHelper.narrow(_this_object());
	}
	public clusterManagement.Arsm _this(org.omg.CORBA.ORB orb)
	{
		return clusterManagement.ArsmHelper.narrow(_this_object(orb));
	}
	public org.omg.CORBA.portable.OutputStream _invoke(String method, org.omg.CORBA.portable.InputStream _input, org.omg.CORBA.portable.ResponseHandler handler)
		throws org.omg.CORBA.SystemException
	{
		org.omg.CORBA.portable.OutputStream _out = null;
		// do something
		// quick lookup of operation
		java.lang.Integer opsIndex = (java.lang.Integer)m_opsHash.get ( method );
		if ( null == opsIndex )
			throw new org.omg.CORBA.BAD_OPERATION(method + " not found");
		switch ( opsIndex.intValue() )
		{
			case 0: // initiateContext
			{
			try
			{
				byte[] _arg0=dataTypes.StreamHelper.read(_input);
				_out = handler.createReply();
				dataTypes.StreamHelper.write(_out,initiateContext(_arg0));
			}
			catch(clusterManagement.ContextInitiationException _ex0)
			{
				_out = handler.createExceptionReply();
				clusterManagement.ContextInitiationExceptionHelper.write(_out, _ex0);
			}
				break;
			}
			case 1: // finalizeContext
			{
			try
			{
				byte[] _arg0=dataTypes.StreamHelper.read(_input);
				_out = handler.createReply();
				finalizeContext(_arg0);
			}
			catch(clusterManagement.ContextFinalizationException _ex0)
			{
				_out = handler.createExceptionReply();
				clusterManagement.ContextFinalizationExceptionHelper.write(_out, _ex0);
			}
				break;
			}
			case 2: // selfTest
			{
			try
			{
				byte[] _arg0=dataTypes.StreamHelper.read(_input);
				_out = handler.createReply();
				dataTypes.StreamHelper.write(_out,selfTest(_arg0));
			}
			catch(clusterManagement.SelfTestException _ex0)
			{
				_out = handler.createExceptionReply();
				clusterManagement.SelfTestExceptionHelper.write(_out, _ex0);
			}
				break;
			}
			case 3: // requestSignature
			{
			try
			{
				byte[] _arg0=dataTypes.StreamHelper.read(_input);
				_out = handler.createReply();
				dataTypes.StreamHelper.write(_out,requestSignature(_arg0));
			}
			catch(clusterManagement.SignatureRequestException _ex0)
			{
				_out = handler.createExceptionReply();
				clusterManagement.SignatureRequestExceptionHelper.write(_out, _ex0);
			}
				break;
			}
			case 4: // checkSignature
			{
			try
			{
				byte[] _arg0=dataTypes.StreamHelper.read(_input);
				_out = handler.createReply();
				dataTypes.StreamHelper.write(_out,checkSignature(_arg0));
			}
			catch(clusterManagement.SignatureCheckingException _ex0)
			{
				_out = handler.createExceptionReply();
				clusterManagement.SignatureCheckingExceptionHelper.write(_out, _ex0);
			}
				break;
			}
		}
		return _out;
	}

	public String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] obj_id)
	{
		return ids;
	}
}
