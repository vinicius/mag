package clusterManagement;

/**
 *	Generated from IDL definition of exception "ApplicationNotFoundException"
 *	@author JacORB IDL compiler 
 */

public final class ApplicationNotFoundExceptionHolder
	implements org.omg.CORBA.portable.Streamable
{
	public clusterManagement.ApplicationNotFoundException value;

	public ApplicationNotFoundExceptionHolder ()
	{
	}
	public ApplicationNotFoundExceptionHolder(final clusterManagement.ApplicationNotFoundException initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return clusterManagement.ApplicationNotFoundExceptionHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = clusterManagement.ApplicationNotFoundExceptionHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		clusterManagement.ApplicationNotFoundExceptionHelper.write(_out, value);
	}
}
