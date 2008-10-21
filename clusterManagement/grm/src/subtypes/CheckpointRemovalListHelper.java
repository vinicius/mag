package subtypes;


/**
 *	Generated from IDL definition of struct "CheckpointRemovalList"
 *	@author JacORB IDL compiler 
 */

public final class CheckpointRemovalListHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(subtypes.CheckpointRemovalListHelper.id(),"CheckpointRemovalList",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("executionId", org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_string_tc(0)), null),new org.omg.CORBA.StructMember("ckpNumber", org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(2))), null),new org.omg.CORBA.StructMember("fragmentNumber", org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(2))), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final subtypes.CheckpointRemovalList s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static subtypes.CheckpointRemovalList extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:subtypes/CheckpointRemovalList:1.0";
	}
	public static subtypes.CheckpointRemovalList read (final org.omg.CORBA.portable.InputStream in)
	{
		subtypes.CheckpointRemovalList result = new subtypes.CheckpointRemovalList();
		int _lresult_executionId2 = in.read_long();
		result.executionId = new java.lang.String[_lresult_executionId2];
		for (int i=0;i<result.executionId.length;i++)
		{
			result.executionId[i]=in.read_string();
		}

		int _lresult_ckpNumber3 = in.read_long();
		result.ckpNumber = new short[_lresult_ckpNumber3];
	in.read_short_array(result.ckpNumber,0,_lresult_ckpNumber3);
		int _lresult_fragmentNumber4 = in.read_long();
		result.fragmentNumber = new short[_lresult_fragmentNumber4];
	in.read_short_array(result.fragmentNumber,0,_lresult_fragmentNumber4);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final subtypes.CheckpointRemovalList s)
	{
		
		out.write_long(s.executionId.length);
		for (int i=0; i<s.executionId.length;i++)
		{
			out.write_string(s.executionId[i]);
		}

		
		out.write_long(s.ckpNumber.length);
		out.write_short_array(s.ckpNumber,0,s.ckpNumber.length);
		
		out.write_long(s.fragmentNumber.length);
		out.write_short_array(s.fragmentNumber,0,s.fragmentNumber.length);
	}
}
