package dataTypes;

/**
 *	Generated from IDL definition of alias "ApplicationExecutionStateInformationSequence"
 *	@author JacORB IDL compiler 
 */

public final class ApplicationExecutionStateInformationSequenceHelper
{
	private static org.omg.CORBA.TypeCode _type = null;

	public static void insert (org.omg.CORBA.Any any, dataTypes.ApplicationExecutionStateInformation[] s)
	{
		any.type (type ());
		write (any.create_output_stream (), s);
	}

	public static dataTypes.ApplicationExecutionStateInformation[] extract (final org.omg.CORBA.Any any)
	{
		return read (any.create_input_stream ());
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_alias_tc(dataTypes.ApplicationExecutionStateInformationSequenceHelper.id(), "ApplicationExecutionStateInformationSequence",org.omg.CORBA.ORB.init().create_sequence_tc(0, dataTypes.ApplicationExecutionStateInformationHelper.type()));
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:dataTypes/ApplicationExecutionStateInformationSequence:1.0";
	}
	public static dataTypes.ApplicationExecutionStateInformation[] read (final org.omg.CORBA.portable.InputStream _in)
	{
		dataTypes.ApplicationExecutionStateInformation[] _result;
		int _l_result5 = _in.read_long();
		_result = new dataTypes.ApplicationExecutionStateInformation[_l_result5];
		for (int i=0;i<_result.length;i++)
		{
			_result[i]=dataTypes.ApplicationExecutionStateInformationHelper.read(_in);
		}

		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, dataTypes.ApplicationExecutionStateInformation[] _s)
	{
		
		_out.write_long(_s.length);
		for (int i=0; i<_s.length;i++)
		{
			dataTypes.ApplicationExecutionStateInformationHelper.write(_out,_s[i]);
		}

	}
}
