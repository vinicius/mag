package dataTypes;

/**
 *	Generated from IDL definition of struct "ApplicationExecutionInformation"
 *	@author JacORB IDL compiler 
 */

public final class ApplicationExecutionInformationHolder
	implements org.omg.CORBA.portable.Streamable
{
	public dataTypes.ApplicationExecutionInformation value;

	public ApplicationExecutionInformationHolder ()
	{
	}
	public ApplicationExecutionInformationHolder(final dataTypes.ApplicationExecutionInformation initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return dataTypes.ApplicationExecutionInformationHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = dataTypes.ApplicationExecutionInformationHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		dataTypes.ApplicationExecutionInformationHelper.write(_out, value);
	}
}
