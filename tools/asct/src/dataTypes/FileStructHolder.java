package dataTypes;

/**
 *	Generated from IDL definition of struct "FileStruct"
 *	@author JacORB IDL compiler 
 */

public final class FileStructHolder
	implements org.omg.CORBA.portable.Streamable
{
	public dataTypes.FileStruct value;

	public FileStructHolder ()
	{
	}
	public FileStructHolder(final dataTypes.FileStruct initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return dataTypes.FileStructHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = dataTypes.FileStructHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		dataTypes.FileStructHelper.write(_out, value);
	}
}
