package dataTypes;


/**
 *	Generated from IDL definition of struct "ApplicationExecutionStateInformation"
 *	@author JacORB IDL compiler 
 */

public final class ApplicationExecutionStateInformationHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(dataTypes.ApplicationExecutionStateInformationHelper.id(),"ApplicationExecutionStateInformation",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("requestId", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("applicationName", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("executionState", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("submissionTimeStamp", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("finishTimeStamp", org.omg.CORBA.ORB.init().create_string_tc(0), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final dataTypes.ApplicationExecutionStateInformation s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static dataTypes.ApplicationExecutionStateInformation extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:dataTypes/ApplicationExecutionStateInformation:1.0";
	}
	public static dataTypes.ApplicationExecutionStateInformation read (final org.omg.CORBA.portable.InputStream in)
	{
		dataTypes.ApplicationExecutionStateInformation result = new dataTypes.ApplicationExecutionStateInformation();
		result.requestId=in.read_string();
		result.applicationName=in.read_string();
		result.executionState=in.read_long();
		result.submissionTimeStamp=in.read_string();
		result.finishTimeStamp=in.read_string();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final dataTypes.ApplicationExecutionStateInformation s)
	{
		out.write_string(s.requestId);
		out.write_string(s.applicationName);
		out.write_long(s.executionState);
		out.write_string(s.submissionTimeStamp);
		out.write_string(s.finishTimeStamp);
	}
}
