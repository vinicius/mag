package clusterManagement;

/**
 *	Generated from IDL interface "Arsm"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface ArsmOperations
{
	/* constants */
	/* operations  */
	byte[] initiateContext(byte[] contextStream) throws clusterManagement.ContextInitiationException;
	void finalizeContext(byte[] contextStream) throws clusterManagement.ContextFinalizationException;
	byte[] checkSignature(byte[] messageStream) throws clusterManagement.SignatureCheckingException;
	byte[] requestSignature(byte[] messageStream) throws clusterManagement.SignatureRequestException;
	byte[] selfTest(byte[] signedMessage) throws clusterManagement.SelfTestException;
}
