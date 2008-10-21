package dataTypes;

/**
 *	Generated from IDL definition of struct "BspProcessZeroInformation"
 *	@author JacORB IDL compiler 
 */

public final class BspProcessZeroInformationHolder
	implements org.omg.CORBA.portable.Streamable
{
	public dataTypes.BspProcessZeroInformation value;

	public BspProcessZeroInformationHolder ()
	{
	}
	public BspProcessZeroInformationHolder(final dataTypes.BspProcessZeroInformation initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return dataTypes.BspProcessZeroInformationHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = dataTypes.BspProcessZeroInformationHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		dataTypes.BspProcessZeroInformationHelper.write(_out, value);
	}
}
