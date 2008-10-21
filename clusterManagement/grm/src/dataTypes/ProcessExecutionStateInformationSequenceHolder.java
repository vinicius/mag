package dataTypes;

/**
 *	Generated from IDL definition of alias "ProcessExecutionStateInformationSequence"
 *	@author JacORB IDL compiler 
 */

public final class ProcessExecutionStateInformationSequenceHolder
	implements org.omg.CORBA.portable.Streamable
{
	public dataTypes.ProcessExecutionStateInformation[] value;

	public ProcessExecutionStateInformationSequenceHolder ()
	{
	}
	public ProcessExecutionStateInformationSequenceHolder (final dataTypes.ProcessExecutionStateInformation[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return ProcessExecutionStateInformationSequenceHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = ProcessExecutionStateInformationSequenceHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		ProcessExecutionStateInformationSequenceHelper.write (out,value);
	}
}
