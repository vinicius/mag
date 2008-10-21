package clusterManagement;

/**
 *	Generated from IDL definition of exception "ContextFinalizationException"
 *	@author JacORB IDL compiler 
 */

public final class ContextFinalizationException
	extends org.omg.CORBA.UserException
{
	public ContextFinalizationException()
	{
		super(clusterManagement.ContextFinalizationExceptionHelper.id());
	}

	public java.lang.String myMessage = "";
	public ContextFinalizationException(java.lang.String _reason,java.lang.String myMessage)
	{
		super(clusterManagement.ContextFinalizationExceptionHelper.id()+ " " + _reason);
		this.myMessage = myMessage;
	}
	public ContextFinalizationException(java.lang.String myMessage)
	{
		super(clusterManagement.ContextFinalizationExceptionHelper.id());
		this.myMessage = myMessage;
	}
}
