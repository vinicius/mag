package clusterManagement;

/**
 *	Generated from IDL definition of exception "ApplicationRegistrationException"
 *	@author JacORB IDL compiler 
 */

public final class ApplicationRegistrationExceptionHolder
	implements org.omg.CORBA.portable.Streamable
{
	public clusterManagement.ApplicationRegistrationException value;

	public ApplicationRegistrationExceptionHolder ()
	{
	}
	public ApplicationRegistrationExceptionHolder(final clusterManagement.ApplicationRegistrationException initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return clusterManagement.ApplicationRegistrationExceptionHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = clusterManagement.ApplicationRegistrationExceptionHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		clusterManagement.ApplicationRegistrationExceptionHelper.write(_out, value);
	}
}
