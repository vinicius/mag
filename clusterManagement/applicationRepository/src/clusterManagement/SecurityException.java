package clusterManagement;

/**
 *	Generated from IDL definition of exception "SecurityException"
 *	@author JacORB IDL compiler 
 */

public final class SecurityException
	extends org.omg.CORBA.UserException
{
	public SecurityException()
	{
		super(clusterManagement.SecurityExceptionHelper.id());
	}

	public java.lang.String myMessage = "";
	public SecurityException(java.lang.String _reason,java.lang.String myMessage)
	{
		super(clusterManagement.SecurityExceptionHelper.id()+ " " + _reason);
		this.myMessage = myMessage;
	}
	public SecurityException(java.lang.String myMessage)
	{
		super(clusterManagement.SecurityExceptionHelper.id());
		this.myMessage = myMessage;
	}
}
