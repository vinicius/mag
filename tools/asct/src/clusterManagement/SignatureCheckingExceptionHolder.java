package clusterManagement;

/**
 *	Generated from IDL definition of exception "SignatureCheckingException"
 *	@author JacORB IDL compiler 
 */

public final class SignatureCheckingExceptionHolder
	implements org.omg.CORBA.portable.Streamable
{
	public clusterManagement.SignatureCheckingException value;

	public SignatureCheckingExceptionHolder ()
	{
	}
	public SignatureCheckingExceptionHolder(final clusterManagement.SignatureCheckingException initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return clusterManagement.SignatureCheckingExceptionHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = clusterManagement.SignatureCheckingExceptionHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		clusterManagement.SignatureCheckingExceptionHelper.write(_out, value);
	}
}
