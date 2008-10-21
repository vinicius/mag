package clusterManagement;

/**
 *	Generated from IDL definition of exception "SelfTestException"
 *	@author JacORB IDL compiler 
 */

public final class SelfTestExceptionHolder
	implements org.omg.CORBA.portable.Streamable
{
	public clusterManagement.SelfTestException value;

	public SelfTestExceptionHolder ()
	{
	}
	public SelfTestExceptionHolder(final clusterManagement.SelfTestException initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return clusterManagement.SelfTestExceptionHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = clusterManagement.SelfTestExceptionHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		clusterManagement.SelfTestExceptionHelper.write(_out, value);
	}
}
