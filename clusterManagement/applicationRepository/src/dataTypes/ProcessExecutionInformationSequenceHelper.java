package dataTypes;

/**
 *	Generated from IDL definition of alias "ProcessExecutionInformationSequence"
 *	@author JacORB IDL compiler 
 */

public final class ProcessExecutionInformationSequenceHelper
{
	private static org.omg.CORBA.TypeCode _type = null;

	public static void insert (org.omg.CORBA.Any any, dataTypes.ProcessExecutionInformation[] s)
	{
		any.type (type ());
		write (any.create_output_stream (), s);
	}

	public static dataTypes.ProcessExecutionInformation[] extract (final org.omg.CORBA.Any any)
	{
		return read (any.create_input_stream ());
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_alias_tc(dataTypes.ProcessExecutionInformationSequenceHelper.id(), "ProcessExecutionInformationSequence",org.omg.CORBA.ORB.init().create_sequence_tc(0, dataTypes.ProcessExecutionInformationHelper.type()));
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:dataTypes/ProcessExecutionInformationSequence:1.0";
	}
	public static dataTypes.ProcessExecutionInformation[] read (final org.omg.CORBA.portable.InputStream _in)
	{
		dataTypes.ProcessExecutionInformation[] _result;
		int _l_result4 = _in.read_long();
		_result = new dataTypes.ProcessExecutionInformation[_l_result4];
		for (int i=0;i<_result.length;i++)
		{
			_result[i]=dataTypes.ProcessExecutionInformationHelper.read(_in);
		}

		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, dataTypes.ProcessExecutionInformation[] _s)
	{
		
		_out.write_long(_s.length);
		for (int i=0; i<_s.length;i++)
		{
			dataTypes.ProcessExecutionInformationHelper.write(_out,_s[i]);
		}

	}
}
