package dataTypes;

/**
 *	Generated from IDL definition of alias "ApplicationExecutionStateInformationSequence"
 *	@author JacORB IDL compiler 
 */

public final class ApplicationExecutionStateInformationSequenceHolder
	implements org.omg.CORBA.portable.Streamable
{
	public dataTypes.ApplicationExecutionStateInformation[] value;

	public ApplicationExecutionStateInformationSequenceHolder ()
	{
	}
	public ApplicationExecutionStateInformationSequenceHolder (final dataTypes.ApplicationExecutionStateInformation[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return ApplicationExecutionStateInformationSequenceHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = ApplicationExecutionStateInformationSequenceHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		ApplicationExecutionStateInformationSequenceHelper.write (out,value);
	}
}
