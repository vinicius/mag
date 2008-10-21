package dataTypes;
/**
 *	Generated from IDL definition of enum "ApplicationType"
 *	@author JacORB IDL compiler 
 */

public final class ApplicationTypeHolder
	implements org.omg.CORBA.portable.Streamable
{
	public ApplicationType value;

	public ApplicationTypeHolder ()
	{
	}
	public ApplicationTypeHolder (final ApplicationType initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return ApplicationTypeHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = ApplicationTypeHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		ApplicationTypeHelper.write (out,value);
	}
}
