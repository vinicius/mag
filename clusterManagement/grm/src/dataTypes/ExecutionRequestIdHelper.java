package dataTypes;


/**
 *	Generated from IDL definition of struct "ExecutionRequestId"
 *	@author JacORB IDL compiler 
 */

public final class ExecutionRequestIdHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(dataTypes.ExecutionRequestIdHelper.id(),"ExecutionRequestId",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("requestId", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("processId", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("replicaId", org.omg.CORBA.ORB.init().create_string_tc(0), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final dataTypes.ExecutionRequestId s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static dataTypes.ExecutionRequestId extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:dataTypes/ExecutionRequestId:1.0";
	}
	public static dataTypes.ExecutionRequestId read (final org.omg.CORBA.portable.InputStream in)
	{
		dataTypes.ExecutionRequestId result = new dataTypes.ExecutionRequestId();
		result.requestId=in.read_string();
		result.processId=in.read_string();
		result.replicaId=in.read_string();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final dataTypes.ExecutionRequestId s)
	{
		out.write_string(s.requestId);
		out.write_string(s.processId);
		out.write_string(s.replicaId);
	}
}
