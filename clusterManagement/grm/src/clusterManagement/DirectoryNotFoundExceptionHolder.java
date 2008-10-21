package clusterManagement;

/**
 *	Generated from IDL definition of exception "DirectoryNotFoundException"
 *	@author JacORB IDL compiler 
 */

public final class DirectoryNotFoundExceptionHolder
	implements org.omg.CORBA.portable.Streamable
{
	public clusterManagement.DirectoryNotFoundException value;

	public DirectoryNotFoundExceptionHolder ()
	{
	}
	public DirectoryNotFoundExceptionHolder(final clusterManagement.DirectoryNotFoundException initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return clusterManagement.DirectoryNotFoundExceptionHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = clusterManagement.DirectoryNotFoundExceptionHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		clusterManagement.DirectoryNotFoundExceptionHelper.write(_out, value);
	}
}
