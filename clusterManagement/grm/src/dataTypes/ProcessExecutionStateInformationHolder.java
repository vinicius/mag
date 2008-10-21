package dataTypes;

/**
 *	Generated from IDL definition of struct "ProcessExecutionStateInformation"
 *	@author JacORB IDL compiler 
 */

public final class ProcessExecutionStateInformationHolder
	implements org.omg.CORBA.portable.Streamable
{
	public dataTypes.ProcessExecutionStateInformation value;

	public ProcessExecutionStateInformationHolder ()
	{
	}
	public ProcessExecutionStateInformationHolder(final dataTypes.ProcessExecutionStateInformation initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return dataTypes.ProcessExecutionStateInformationHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = dataTypes.ProcessExecutionStateInformationHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		dataTypes.ProcessExecutionStateInformationHelper.write(_out, value);
	}
}
