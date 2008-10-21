package dataTypes;

/**
 *	Generated from IDL definition of alias "Stream"
 *	@author JacORB IDL compiler 
 */

public final class StreamHolder
	implements org.omg.CORBA.portable.Streamable
{
	public byte[] value;

	public StreamHolder ()
	{
	}
	public StreamHolder (final byte[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return StreamHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = StreamHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		StreamHelper.write (out,value);
	}
}
