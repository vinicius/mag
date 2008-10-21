package dataTypes;

/**
 *	Generated from IDL definition of alias "FileNameSequence"
 *	@author JacORB IDL compiler 
 */

public final class FileNameSequenceHolder
	implements org.omg.CORBA.portable.Streamable
{
	public java.lang.String[] value;

	public FileNameSequenceHolder ()
	{
	}
	public FileNameSequenceHolder (final java.lang.String[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return FileNameSequenceHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = FileNameSequenceHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		FileNameSequenceHelper.write (out,value);
	}
}
