package clusterManagement;

/**
 *	Generated from IDL definition of exception "InvalidPathNameException"
 *	@author JacORB IDL compiler 
 */

public final class InvalidPathNameExceptionHolder
	implements org.omg.CORBA.portable.Streamable
{
	public clusterManagement.InvalidPathNameException value;

	public InvalidPathNameExceptionHolder ()
	{
	}
	public InvalidPathNameExceptionHolder(final clusterManagement.InvalidPathNameException initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return clusterManagement.InvalidPathNameExceptionHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = clusterManagement.InvalidPathNameExceptionHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		clusterManagement.InvalidPathNameExceptionHelper.write(_out, value);
	}
}
