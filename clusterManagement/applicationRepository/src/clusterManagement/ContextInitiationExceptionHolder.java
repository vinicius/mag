package clusterManagement;

/**
 *	Generated from IDL definition of exception "ContextInitiationException"
 *	@author JacORB IDL compiler 
 */

public final class ContextInitiationExceptionHolder
	implements org.omg.CORBA.portable.Streamable
{
	public clusterManagement.ContextInitiationException value;

	public ContextInitiationExceptionHolder ()
	{
	}
	public ContextInitiationExceptionHolder(final clusterManagement.ContextInitiationException initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return clusterManagement.ContextInitiationExceptionHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = clusterManagement.ContextInitiationExceptionHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		clusterManagement.ContextInitiationExceptionHelper.write(_out, value);
	}
}
