package dataTypes;

/**
 *	Generated from IDL definition of struct "SubtreeInformation"
 *	@author JacORB IDL compiler 
 */

public final class SubtreeInformationHolder
	implements org.omg.CORBA.portable.Streamable
{
	public dataTypes.SubtreeInformation value;

	public SubtreeInformationHolder ()
	{
	}
	public SubtreeInformationHolder(final dataTypes.SubtreeInformation initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return dataTypes.SubtreeInformationHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = dataTypes.SubtreeInformationHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		dataTypes.SubtreeInformationHelper.write(_out, value);
	}
}
