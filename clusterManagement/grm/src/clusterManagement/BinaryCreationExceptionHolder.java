package clusterManagement;

/**
 *	Generated from IDL definition of exception "BinaryCreationException"
 *	@author JacORB IDL compiler 
 */

public final class BinaryCreationExceptionHolder
	implements org.omg.CORBA.portable.Streamable
{
	public clusterManagement.BinaryCreationException value;

	public BinaryCreationExceptionHolder ()
	{
	}
	public BinaryCreationExceptionHolder(final clusterManagement.BinaryCreationException initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return clusterManagement.BinaryCreationExceptionHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = clusterManagement.BinaryCreationExceptionHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		clusterManagement.BinaryCreationExceptionHelper.write(_out, value);
	}
}
