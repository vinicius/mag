package dataTypes;

/**
 *	Generated from IDL definition of alias "FileSequence"
 *	@author JacORB IDL compiler 
 */

public final class FileSequenceHelper
{
	private static org.omg.CORBA.TypeCode _type = null;

	public static void insert (org.omg.CORBA.Any any, dataTypes.FileStruct[] s)
	{
		any.type (type ());
		write (any.create_output_stream (), s);
	}

	public static dataTypes.FileStruct[] extract (final org.omg.CORBA.Any any)
	{
		return read (any.create_input_stream ());
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_alias_tc(dataTypes.FileSequenceHelper.id(), "FileSequence",org.omg.CORBA.ORB.init().create_sequence_tc(0, dataTypes.FileStructHelper.type()));
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:dataTypes/FileSequence:1.0";
	}
	public static dataTypes.FileStruct[] read (final org.omg.CORBA.portable.InputStream _in)
	{
		dataTypes.FileStruct[] _result;
		int _l_result2 = _in.read_long();
		_result = new dataTypes.FileStruct[_l_result2];
		for (int i=0;i<_result.length;i++)
		{
			_result[i]=dataTypes.FileStructHelper.read(_in);
		}

		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, dataTypes.FileStruct[] _s)
	{
		
		_out.write_long(_s.length);
		for (int i=0; i<_s.length;i++)
		{
			dataTypes.FileStructHelper.write(_out,_s[i]);
		}

	}
}
