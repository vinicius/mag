package dataTypes;


/**
 *	Generated from IDL definition of struct "RequestAcceptanceInformation"
 *	@author JacORB IDL compiler 
 */

public final class RequestAcceptanceInformationHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(dataTypes.RequestAcceptanceInformationHelper.id(),"RequestAcceptanceInformation",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("lrmIor", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("executionRequestId", dataTypes.ExecutionRequestIdHelper.type(), null),new org.omg.CORBA.StructMember("executionId", org.omg.CORBA.ORB.init().create_string_tc(0), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final dataTypes.RequestAcceptanceInformation s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static dataTypes.RequestAcceptanceInformation extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:dataTypes/RequestAcceptanceInformation:1.0";
	}
	public static dataTypes.RequestAcceptanceInformation read (final org.omg.CORBA.portable.InputStream in)
	{
		dataTypes.RequestAcceptanceInformation result = new dataTypes.RequestAcceptanceInformation();
		result.lrmIor=in.read_string();
		result.executionRequestId=dataTypes.ExecutionRequestIdHelper.read(in);
		result.executionId=in.read_string();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final dataTypes.RequestAcceptanceInformation s)
	{
		out.write_string(s.lrmIor);
		dataTypes.ExecutionRequestIdHelper.write(out,s.executionRequestId);
		out.write_string(s.executionId);
	}
}
