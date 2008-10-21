package clusterManagement;

/**
 *	Generated from IDL definition of exception "ContextFinalizationException"
 *	@author JacORB IDL compiler 
 */

public final class ContextFinalizationExceptionHolder
	implements org.omg.CORBA.portable.Streamable
{
	public clusterManagement.ContextFinalizationException value;

	public ContextFinalizationExceptionHolder ()
	{
	}
	public ContextFinalizationExceptionHolder(final clusterManagement.ContextFinalizationException initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return clusterManagement.ContextFinalizationExceptionHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = clusterManagement.ContextFinalizationExceptionHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		clusterManagement.ContextFinalizationExceptionHelper.write(_out, value);
	}
}
