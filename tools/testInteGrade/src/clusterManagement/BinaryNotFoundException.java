package clusterManagement;

/**
 *	Generated from IDL definition of exception "BinaryNotFoundException"
 *	@author JacORB IDL compiler 
 */

public final class BinaryNotFoundException
	extends org.omg.CORBA.UserException
{
	public BinaryNotFoundException()
	{
		super(clusterManagement.BinaryNotFoundExceptionHelper.id());
	}

	public java.lang.String myMessage = "";
	public BinaryNotFoundException(java.lang.String _reason,java.lang.String myMessage)
	{
		super(clusterManagement.BinaryNotFoundExceptionHelper.id()+ " " + _reason);
		this.myMessage = myMessage;
	}
	public BinaryNotFoundException(java.lang.String myMessage)
	{
		super(clusterManagement.BinaryNotFoundExceptionHelper.id());
		this.myMessage = myMessage;
	}
}
