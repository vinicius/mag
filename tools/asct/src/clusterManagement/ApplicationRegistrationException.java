package clusterManagement;

/**
 *	Generated from IDL definition of exception "ApplicationRegistrationException"
 *	@author JacORB IDL compiler 
 */

public final class ApplicationRegistrationException
	extends org.omg.CORBA.UserException
{
	public ApplicationRegistrationException()
	{
		super(clusterManagement.ApplicationRegistrationExceptionHelper.id());
	}

	public java.lang.String myMessage = "";
	public ApplicationRegistrationException(java.lang.String _reason,java.lang.String myMessage)
	{
		super(clusterManagement.ApplicationRegistrationExceptionHelper.id()+ " " + _reason);
		this.myMessage = myMessage;
	}
	public ApplicationRegistrationException(java.lang.String myMessage)
	{
		super(clusterManagement.ApplicationRegistrationExceptionHelper.id());
		this.myMessage = myMessage;
	}
}
