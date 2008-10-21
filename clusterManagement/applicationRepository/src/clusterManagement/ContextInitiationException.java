package clusterManagement;

/**
 *	Generated from IDL definition of exception "ContextInitiationException"
 *	@author JacORB IDL compiler 
 */

public final class ContextInitiationException
	extends org.omg.CORBA.UserException
{
	public ContextInitiationException()
	{
		super(clusterManagement.ContextInitiationExceptionHelper.id());
	}

	public java.lang.String myMessage = "";
	public ContextInitiationException(java.lang.String _reason,java.lang.String myMessage)
	{
		super(clusterManagement.ContextInitiationExceptionHelper.id()+ " " + _reason);
		this.myMessage = myMessage;
	}
	public ContextInitiationException(java.lang.String myMessage)
	{
		super(clusterManagement.ContextInitiationExceptionHelper.id());
		this.myMessage = myMessage;
	}
}
