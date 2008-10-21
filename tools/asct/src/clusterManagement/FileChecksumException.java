package clusterManagement;

/**
 *	Generated from IDL definition of exception "FileChecksumException"
 *	@author JacORB IDL compiler 
 */

public final class FileChecksumException
	extends org.omg.CORBA.UserException
{
	public FileChecksumException()
	{
		super(clusterManagement.FileChecksumExceptionHelper.id());
	}

	public java.lang.String myMessage = "";
	public FileChecksumException(java.lang.String _reason,java.lang.String myMessage)
	{
		super(clusterManagement.FileChecksumExceptionHelper.id()+ " " + _reason);
		this.myMessage = myMessage;
	}
	public FileChecksumException(java.lang.String myMessage)
	{
		super(clusterManagement.FileChecksumExceptionHelper.id());
		this.myMessage = myMessage;
	}
}
