package clusterManagement;

/**
 *	Generated from IDL definition of exception "SelfTestException"
 *	@author JacORB IDL compiler 
 */

public final class SelfTestException
	extends org.omg.CORBA.UserException
{
	public SelfTestException()
	{
		super(clusterManagement.SelfTestExceptionHelper.id());
	}

	public java.lang.String myMessage = "";
	public SelfTestException(java.lang.String _reason,java.lang.String myMessage)
	{
		super(clusterManagement.SelfTestExceptionHelper.id()+ " " + _reason);
		this.myMessage = myMessage;
	}
	public SelfTestException(java.lang.String myMessage)
	{
		super(clusterManagement.SelfTestExceptionHelper.id());
		this.myMessage = myMessage;
	}
}
