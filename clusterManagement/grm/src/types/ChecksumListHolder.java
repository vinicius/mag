package types;

/**
 *	Generated from IDL definition of alias "ChecksumList"
 *	@author JacORB IDL compiler 
 */

public final class ChecksumListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public java.lang.String[] value;

	public ChecksumListHolder ()
	{
	}
	public ChecksumListHolder (final java.lang.String[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return ChecksumListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = ChecksumListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		ChecksumListHelper.write (out,value);
	}
}
