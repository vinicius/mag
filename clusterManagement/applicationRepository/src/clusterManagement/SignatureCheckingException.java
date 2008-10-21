package clusterManagement;

/**
 *	Generated from IDL definition of exception "SignatureCheckingException"
 *	@author JacORB IDL compiler 
 */

public final class SignatureCheckingException
	extends org.omg.CORBA.UserException
{
	public SignatureCheckingException()
	{
		super(clusterManagement.SignatureCheckingExceptionHelper.id());
	}

	public java.lang.String myMessage = "";
	public SignatureCheckingException(java.lang.String _reason,java.lang.String myMessage)
	{
		super(clusterManagement.SignatureCheckingExceptionHelper.id()+ " " + _reason);
		this.myMessage = myMessage;
	}
	public SignatureCheckingException(java.lang.String myMessage)
	{
		super(clusterManagement.SignatureCheckingExceptionHelper.id());
		this.myMessage = myMessage;
	}
}
