package clusterManagement;

/**
 *	Generated from IDL definition of exception "DirectoryNotFoundException"
 *	@author JacORB IDL compiler 
 */

public final class DirectoryNotFoundException
	extends org.omg.CORBA.UserException
{
	public DirectoryNotFoundException()
	{
		super(clusterManagement.DirectoryNotFoundExceptionHelper.id());
	}

	public java.lang.String myMessage = "";
	public DirectoryNotFoundException(java.lang.String _reason,java.lang.String myMessage)
	{
		super(clusterManagement.DirectoryNotFoundExceptionHelper.id()+ " " + _reason);
		this.myMessage = myMessage;
	}
	public DirectoryNotFoundException(java.lang.String myMessage)
	{
		super(clusterManagement.DirectoryNotFoundExceptionHelper.id());
		this.myMessage = myMessage;
	}
}
