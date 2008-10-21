package dataTypes;

/**
 *	Generated from IDL definition of struct "ContentDescription"
 *	@author JacORB IDL compiler 
 */

public final class ContentDescriptionHolder
	implements org.omg.CORBA.portable.Streamable
{
	public dataTypes.ContentDescription value;

	public ContentDescriptionHolder ()
	{
	}
	public ContentDescriptionHolder(final dataTypes.ContentDescription initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return dataTypes.ContentDescriptionHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = dataTypes.ContentDescriptionHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		dataTypes.ContentDescriptionHelper.write(_out, value);
	}
}
