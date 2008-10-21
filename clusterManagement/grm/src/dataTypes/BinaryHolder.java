package dataTypes;

/**
 *	Generated from IDL definition of alias "Binary"
 *	@author JacORB IDL compiler 
 */

public final class BinaryHolder
	implements org.omg.CORBA.portable.Streamable
{
	public byte[] value;

	public BinaryHolder ()
	{
	}
	public BinaryHolder (final byte[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return BinaryHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = BinaryHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		BinaryHelper.write (out,value);
	}
}
