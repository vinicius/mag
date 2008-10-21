package clusterManagement;

/**
 *	Generated from IDL definition of exception "InvalidPathNameException"
 *	@author JacORB IDL compiler 
 */

public final class InvalidPathNameException
	extends org.omg.CORBA.UserException
{
	public InvalidPathNameException()
	{
		super(clusterManagement.InvalidPathNameExceptionHelper.id());
	}

	public java.lang.String myMessage = "";
	public InvalidPathNameException(java.lang.String _reason,java.lang.String myMessage)
	{
		super(clusterManagement.InvalidPathNameExceptionHelper.id()+ " " + _reason);
		this.myMessage = myMessage;
	}
	public InvalidPathNameException(java.lang.String myMessage)
	{
		super(clusterManagement.InvalidPathNameExceptionHelper.id());
		this.myMessage = myMessage;
	}
}
