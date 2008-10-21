package dataTypes;

/**
 *	Generated from IDL definition of alias "ContentDescriptionSequence"
 *	@author JacORB IDL compiler 
 */

public final class ContentDescriptionSequenceHelper
{
	private static org.omg.CORBA.TypeCode _type = null;

	public static void insert (org.omg.CORBA.Any any, dataTypes.ContentDescription[] s)
	{
		any.type (type ());
		write (any.create_output_stream (), s);
	}

	public static dataTypes.ContentDescription[] extract (final org.omg.CORBA.Any any)
	{
		return read (any.create_input_stream ());
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_alias_tc(dataTypes.ContentDescriptionSequenceHelper.id(), "ContentDescriptionSequence",org.omg.CORBA.ORB.init().create_sequence_tc(0, dataTypes.ContentDescriptionHelper.type()));
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:dataTypes/ContentDescriptionSequence:1.0";
	}
	public static dataTypes.ContentDescription[] read (final org.omg.CORBA.portable.InputStream _in)
	{
		dataTypes.ContentDescription[] _result;
		int _l_result2 = _in.read_long();
		_result = new dataTypes.ContentDescription[_l_result2];
		for (int i=0;i<_result.length;i++)
		{
			_result[i]=dataTypes.ContentDescriptionHelper.read(_in);
		}

		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, dataTypes.ContentDescription[] _s)
	{
		
		_out.write_long(_s.length);
		for (int i=0; i<_s.length;i++)
		{
			dataTypes.ContentDescriptionHelper.write(_out,_s[i]);
		}

	}
}
