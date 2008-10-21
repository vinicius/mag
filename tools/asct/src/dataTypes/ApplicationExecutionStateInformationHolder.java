package dataTypes;

/**
 *	Generated from IDL definition of struct "ApplicationExecutionStateInformation"
 *	@author JacORB IDL compiler 
 */

public final class ApplicationExecutionStateInformationHolder
	implements org.omg.CORBA.portable.Streamable
{
	public dataTypes.ApplicationExecutionStateInformation value;

	public ApplicationExecutionStateInformationHolder ()
	{
	}
	public ApplicationExecutionStateInformationHolder(final dataTypes.ApplicationExecutionStateInformation initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return dataTypes.ApplicationExecutionStateInformationHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = dataTypes.ApplicationExecutionStateInformationHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		dataTypes.ApplicationExecutionStateInformationHelper.write(_out, value);
	}
}
