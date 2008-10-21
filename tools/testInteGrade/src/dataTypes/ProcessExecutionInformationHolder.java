package dataTypes;

/**
 *	Generated from IDL definition of struct "ProcessExecutionInformation"
 *	@author JacORB IDL compiler 
 */

public final class ProcessExecutionInformationHolder
	implements org.omg.CORBA.portable.Streamable
{
	public dataTypes.ProcessExecutionInformation value;

	public ProcessExecutionInformationHolder ()
	{
	}
	public ProcessExecutionInformationHolder(final dataTypes.ProcessExecutionInformation initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return dataTypes.ProcessExecutionInformationHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = dataTypes.ProcessExecutionInformationHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		dataTypes.ProcessExecutionInformationHelper.write(_out, value);
	}
}
