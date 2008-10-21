package clusterManagement;

/**
 *	Generated from IDL definition of exception "FileChecksumException"
 *	@author JacORB IDL compiler 
 */

public final class FileChecksumExceptionHolder
	implements org.omg.CORBA.portable.Streamable
{
	public clusterManagement.FileChecksumException value;

	public FileChecksumExceptionHolder ()
	{
	}
	public FileChecksumExceptionHolder(final clusterManagement.FileChecksumException initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return clusterManagement.FileChecksumExceptionHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = clusterManagement.FileChecksumExceptionHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		clusterManagement.FileChecksumExceptionHelper.write(_out, value);
	}
}
