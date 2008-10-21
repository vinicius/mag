package clusterManagement;

/**
 *	Generated from IDL definition of exception "DirectoryCreationException"
 *	@author JacORB IDL compiler 
 */

public final class DirectoryCreationExceptionHolder
	implements org.omg.CORBA.portable.Streamable
{
	public clusterManagement.DirectoryCreationException value;

	public DirectoryCreationExceptionHolder ()
	{
	}
	public DirectoryCreationExceptionHolder(final clusterManagement.DirectoryCreationException initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return clusterManagement.DirectoryCreationExceptionHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = clusterManagement.DirectoryCreationExceptionHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		clusterManagement.DirectoryCreationExceptionHelper.write(_out, value);
	}
}
