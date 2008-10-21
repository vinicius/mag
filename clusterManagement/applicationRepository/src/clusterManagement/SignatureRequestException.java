package clusterManagement;

/**
 *	Generated from IDL definition of exception "SignatureRequestException"
 *	@author JacORB IDL compiler 
 */

public final class SignatureRequestException
	extends org.omg.CORBA.UserException
{
	public SignatureRequestException()
	{
		super(clusterManagement.SignatureRequestExceptionHelper.id());
	}

	public java.lang.String myMessage = "";
	public SignatureRequestException(java.lang.String _reason,java.lang.String myMessage)
	{
		super(clusterManagement.SignatureRequestExceptionHelper.id()+ " " + _reason);
		this.myMessage = myMessage;
	}
	public SignatureRequestException(java.lang.String myMessage)
	{
		super(clusterManagement.SignatureRequestExceptionHelper.id());
		this.myMessage = myMessage;
	}
}
