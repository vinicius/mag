package dataTypes;

/**
 *	Generated from IDL definition of alias "StringSequence"
 *	@author JacORB IDL compiler 
 */

public final class StringSequenceHolder
	implements org.omg.CORBA.portable.Streamable
{
	public java.lang.String[] value;

	public StringSequenceHolder ()
	{
	}
	public StringSequenceHolder (final java.lang.String[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return StringSequenceHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = StringSequenceHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		StringSequenceHelper.write (out,value);
	}
}
