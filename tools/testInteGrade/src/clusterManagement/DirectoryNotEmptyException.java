package clusterManagement;

/**
 *	Generated from IDL definition of exception "DirectoryNotEmptyException"
 *	@author JacORB IDL compiler 
 */

public final class DirectoryNotEmptyException
	extends org.omg.CORBA.UserException
{
	public DirectoryNotEmptyException()
	{
		super(clusterManagement.DirectoryNotEmptyExceptionHelper.id());
	}

	public java.lang.String myMessage = "";
	public DirectoryNotEmptyException(java.lang.String _reason,java.lang.String myMessage)
	{
		super(clusterManagement.DirectoryNotEmptyExceptionHelper.id()+ " " + _reason);
		this.myMessage = myMessage;
	}
	public DirectoryNotEmptyException(java.lang.String myMessage)
	{
		super(clusterManagement.DirectoryNotEmptyExceptionHelper.id());
		this.myMessage = myMessage;
	}
}
