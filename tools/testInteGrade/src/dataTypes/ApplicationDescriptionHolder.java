package dataTypes;

/**
 *	Generated from IDL definition of struct "ApplicationDescription"
 *	@author JacORB IDL compiler 
 */

public final class ApplicationDescriptionHolder
	implements org.omg.CORBA.portable.Streamable
{
	public dataTypes.ApplicationDescription value;

	public ApplicationDescriptionHolder ()
	{
	}
	public ApplicationDescriptionHolder(final dataTypes.ApplicationDescription initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return dataTypes.ApplicationDescriptionHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = dataTypes.ApplicationDescriptionHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		dataTypes.ApplicationDescriptionHelper.write(_out, value);
	}
}
