package clusterManagement;

/**
 *	Generated from IDL definition of exception "BinaryNotFoundException"
 *	@author JacORB IDL compiler 
 */

public final class BinaryNotFoundExceptionHolder
	implements org.omg.CORBA.portable.Streamable
{
	public clusterManagement.BinaryNotFoundException value;

	public BinaryNotFoundExceptionHolder ()
	{
	}
	public BinaryNotFoundExceptionHolder(final clusterManagement.BinaryNotFoundException initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return clusterManagement.BinaryNotFoundExceptionHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = clusterManagement.BinaryNotFoundExceptionHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		clusterManagement.BinaryNotFoundExceptionHelper.write(_out, value);
	}
}
