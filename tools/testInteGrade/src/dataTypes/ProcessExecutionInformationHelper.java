package dataTypes;


/**
 *	Generated from IDL definition of struct "ProcessExecutionInformation"
 *	@author JacORB IDL compiler 
 */

public final class ProcessExecutionInformationHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(dataTypes.ProcessExecutionInformationHelper.id(),"ProcessExecutionInformation",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("executionRequestId", dataTypes.ExecutionRequestIdHelper.type(), null),new org.omg.CORBA.StructMember("processArguments", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("outputFileNames", dataTypes.FileNameSequenceHelper.type(), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final dataTypes.ProcessExecutionInformation s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static dataTypes.ProcessExecutionInformation extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:dataTypes/ProcessExecutionInformation:1.0";
	}
	public static dataTypes.ProcessExecutionInformation read (final org.omg.CORBA.portable.InputStream in)
	{
		dataTypes.ProcessExecutionInformation result = new dataTypes.ProcessExecutionInformation();
		result.executionRequestId=dataTypes.ExecutionRequestIdHelper.read(in);
		result.processArguments=in.read_string();
		result.outputFileNames = dataTypes.FileNameSequenceHelper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final dataTypes.ProcessExecutionInformation s)
	{
		dataTypes.ExecutionRequestIdHelper.write(out,s.executionRequestId);
		out.write_string(s.processArguments);
		dataTypes.FileNameSequenceHelper.write(out,s.outputFileNames);
	}
}
