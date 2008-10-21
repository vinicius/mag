package dataTypes;

/**
 *	Generated from IDL definition of alias "BinaryNameSequence"
 *	@author JacORB IDL compiler 
 */

public final class BinaryNameSequenceHolder
	implements org.omg.CORBA.portable.Streamable
{
	public java.lang.String[] value;

	public BinaryNameSequenceHolder ()
	{
	}
	public BinaryNameSequenceHolder (final java.lang.String[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return BinaryNameSequenceHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = BinaryNameSequenceHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		BinaryNameSequenceHelper.write (out,value);
	}
}
