package clusterManagement;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "Arsm"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class ArsmPOATie
	extends ArsmPOA
{
	private ArsmOperations _delegate;

	private POA _poa;
	public ArsmPOATie(ArsmOperations delegate)
	{
		_delegate = delegate;
	}
	public ArsmPOATie(ArsmOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public clusterManagement.Arsm _this()
	{
		return clusterManagement.ArsmHelper.narrow(_this_object());
	}
	public clusterManagement.Arsm _this(org.omg.CORBA.ORB orb)
	{
		return clusterManagement.ArsmHelper.narrow(_this_object(orb));
	}
	public ArsmOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(ArsmOperations delegate)
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
	public byte[] initiateContext(byte[] contextStream) throws clusterManagement.ContextInitiationException
	{
		return _delegate.initiateContext(contextStream);
	}

	public void finalizeContext(byte[] contextStream) throws clusterManagement.ContextFinalizationException
	{
_delegate.finalizeContext(contextStream);
	}

	public byte[] selfTest(byte[] signedMessage) throws clusterManagement.SelfTestException
	{
		return _delegate.selfTest(signedMessage);
	}

	public byte[] requestSignature(byte[] messageStream) throws clusterManagement.SignatureRequestException
	{
		return _delegate.requestSignature(messageStream);
	}

	public byte[] checkSignature(byte[] messageStream) throws clusterManagement.SignatureCheckingException
	{
		return _delegate.checkSignature(messageStream);
	}

}
