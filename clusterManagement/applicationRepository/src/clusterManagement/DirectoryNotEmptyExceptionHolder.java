package clusterManagement;

/**
 *	Generated from IDL definition of exception "DirectoryNotEmptyException"
 *	@author JacORB IDL compiler 
 */

public final class DirectoryNotEmptyExceptionHolder
	implements org.omg.CORBA.portable.Streamable
{
	public clusterManagement.DirectoryNotEmptyException value;

	public DirectoryNotEmptyExceptionHolder ()
	{
	}
	public DirectoryNotEmptyExceptionHolder(final clusterManagement.DirectoryNotEmptyException initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return clusterManagement.DirectoryNotEmptyExceptionHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = clusterManagement.DirectoryNotEmptyExceptionHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		clusterManagement.DirectoryNotEmptyExceptionHelper.write(_out, value);
	}
}
