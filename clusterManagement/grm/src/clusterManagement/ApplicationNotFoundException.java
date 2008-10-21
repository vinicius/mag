package clusterManagement;

/**
 *	Generated from IDL definition of exception "ApplicationNotFoundException"
 *	@author JacORB IDL compiler 
 */

public final class ApplicationNotFoundException
	extends org.omg.CORBA.UserException
{
	public ApplicationNotFoundException()
	{
		super(clusterManagement.ApplicationNotFoundExceptionHelper.id());
	}

	public java.lang.String myMessage = "";
	public ApplicationNotFoundException(java.lang.String _reason,java.lang.String myMessage)
	{
		super(clusterManagement.ApplicationNotFoundExceptionHelper.id()+ " " + _reason);
		this.myMessage = myMessage;
	}
	public ApplicationNotFoundException(java.lang.String myMessage)
	{
		super(clusterManagement.ApplicationNotFoundExceptionHelper.id());
		this.myMessage = myMessage;
	}
}
