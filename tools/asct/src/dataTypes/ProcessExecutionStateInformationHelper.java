package dataTypes;


/**
 *	Generated from IDL definition of struct "ProcessExecutionStateInformation"
 *	@author JacORB IDL compiler 
 */

public final class ProcessExecutionStateInformationHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(dataTypes.ProcessExecutionStateInformationHelper.id(),"ProcessExecutionStateInformation",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("executionRequestId", dataTypes.ExecutionRequestIdHelper.type(), null),new org.omg.CORBA.StructMember("executionState", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("executionCode", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("lrmIOR", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("startExecutionTimeStamp", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("endExecutionTimeStamp", org.omg.CORBA.ORB.init().create_string_tc(0), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final dataTypes.ProcessExecutionStateInformation s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static dataTypes.ProcessExecutionStateInformation extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:dataTypes/ProcessExecutionStateInformation:1.0";
	}
	public static dataTypes.ProcessExecutionStateInformation read (final org.omg.CORBA.portable.InputStream in)
	{
		dataTypes.ProcessExecutionStateInformation result = new dataTypes.ProcessExecutionStateInformation();
		result.executionRequestId=dataTypes.ExecutionRequestIdHelper.read(in);
		result.executionState=in.read_long();
		result.executionCode=in.read_string();
		result.lrmIOR=in.read_string();
		result.startExecutionTimeStamp=in.read_string();
		result.endExecutionTimeStamp=in.read_string();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final dataTypes.ProcessExecutionStateInformation s)
	{
		dataTypes.ExecutionRequestIdHelper.write(out,s.executionRequestId);
		out.write_long(s.executionState);
		out.write_string(s.executionCode);
		out.write_string(s.lrmIOR);
		out.write_string(s.startExecutionTimeStamp);
		out.write_string(s.endExecutionTimeStamp);
	}
}
