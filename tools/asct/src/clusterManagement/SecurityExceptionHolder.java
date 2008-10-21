package clusterManagement;

/**
 *	Generated from IDL definition of exception "SecurityException"
 *	@author JacORB IDL compiler 
 */

public final class SecurityExceptionHolder
	implements org.omg.CORBA.portable.Streamable
{
	public clusterManagement.SecurityException value;

	public SecurityExceptionHolder ()
	{
	}
	public SecurityExceptionHolder(final clusterManagement.SecurityException initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return clusterManagement.SecurityExceptionHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = clusterManagement.SecurityExceptionHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		clusterManagement.SecurityExceptionHelper.write(_out, value);
	}
}
