package dataTypes;

/**
 *	Generated from IDL definition of alias "ProcessExecutionStateInformationSequence"
 *	@author JacORB IDL compiler 
 */

public final class ProcessExecutionStateInformationSequenceHelper
{
	private static org.omg.CORBA.TypeCode _type = null;

	public static void insert (org.omg.CORBA.Any any, dataTypes.ProcessExecutionStateInformation[] s)
	{
		any.type (type ());
		write (any.create_output_stream (), s);
	}

	public static dataTypes.ProcessExecutionStateInformation[] extract (final org.omg.CORBA.Any any)
	{
		return read (any.create_input_stream ());
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_alias_tc(dataTypes.ProcessExecutionStateInformationSequenceHelper.id(), "ProcessExecutionStateInformationSequence",org.omg.CORBA.ORB.init().create_sequence_tc(0, dataTypes.ProcessExecutionStateInformationHelper.type()));
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:dataTypes/ProcessExecutionStateInformationSequence:1.0";
	}
	public static dataTypes.ProcessExecutionStateInformation[] read (final org.omg.CORBA.portable.InputStream _in)
	{
		dataTypes.ProcessExecutionStateInformation[] _result;
		int _l_result6 = _in.read_long();
		_result = new dataTypes.ProcessExecutionStateInformation[_l_result6];
		for (int i=0;i<_result.length;i++)
		{
			_result[i]=dataTypes.ProcessExecutionStateInformationHelper.read(_in);
		}

		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, dataTypes.ProcessExecutionStateInformation[] _s)
	{
		
		_out.write_long(_s.length);
		for (int i=0; i<_s.length;i++)
		{
			dataTypes.ProcessExecutionStateInformationHelper.write(_out,_s[i]);
		}

	}
}
