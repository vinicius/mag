package dataTypes;

/**
 *	Generated from IDL definition of struct "BinaryDescription"
 *	@author JacORB IDL compiler 
 */

public final class BinaryDescriptionHolder
	implements org.omg.CORBA.portable.Streamable
{
	public dataTypes.BinaryDescription value;

	public BinaryDescriptionHolder ()
	{
	}
	public BinaryDescriptionHolder(final dataTypes.BinaryDescription initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return dataTypes.BinaryDescriptionHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = dataTypes.BinaryDescriptionHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		dataTypes.BinaryDescriptionHelper.write(_out, value);
	}
}
