package clusterManagement;

/**
 *	Generated from IDL definition of exception "SignatureRequestException"
 *	@author JacORB IDL compiler 
 */

public final class SignatureRequestExceptionHolder
	implements org.omg.CORBA.portable.Streamable
{
	public clusterManagement.SignatureRequestException value;

	public SignatureRequestExceptionHolder ()
	{
	}
	public SignatureRequestExceptionHolder(final clusterManagement.SignatureRequestException initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return clusterManagement.SignatureRequestExceptionHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = clusterManagement.SignatureRequestExceptionHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		clusterManagement.SignatureRequestExceptionHelper.write(_out, value);
	}
}
