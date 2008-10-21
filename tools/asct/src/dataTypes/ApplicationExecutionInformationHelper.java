package dataTypes;


/**
 *	Generated from IDL definition of struct "ApplicationExecutionInformation"
 *	@author JacORB IDL compiler 
 */

public final class ApplicationExecutionInformationHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(dataTypes.ApplicationExecutionInformationHelper.id(),"ApplicationExecutionInformation",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("requestingAsctIor", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("originalGrmIor", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("previousGrmIor", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("applicationType", dataTypes.ApplicationTypeHelper.type(), null),new org.omg.CORBA.StructMember("applicationRepositoryIor", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("applicationName", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("basePath", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("applicationConstraints", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("applicationPreferences", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("availableBinaries", dataTypes.BinaryNameSequenceHelper.type(), null),new org.omg.CORBA.StructMember("forceDifferentNodes", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(8)), null),new org.omg.CORBA.StructMember("numberOfReplicas", org.omg.CORBA.ORB.init().create_string_tc(0), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final dataTypes.ApplicationExecutionInformation s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static dataTypes.ApplicationExecutionInformation extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:dataTypes/ApplicationExecutionInformation:1.0";
	}
	public static dataTypes.ApplicationExecutionInformation read (final org.omg.CORBA.portable.InputStream in)
	{
		dataTypes.ApplicationExecutionInformation result = new dataTypes.ApplicationExecutionInformation();
		result.requestingAsctIor=in.read_string();
		result.originalGrmIor=in.read_string();
		result.previousGrmIor=in.read_string();
		result.applicationType=dataTypes.ApplicationTypeHelper.read(in);
		result.applicationRepositoryIor=in.read_string();
		result.applicationName=in.read_string();
		result.basePath=in.read_string();
		result.applicationConstraints=in.read_string();
		result.applicationPreferences=in.read_string();
		result.availableBinaries = dataTypes.BinaryNameSequenceHelper.read(in);
		result.forceDifferentNodes=in.read_boolean();
		result.numberOfReplicas=in.read_string();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final dataTypes.ApplicationExecutionInformation s)
	{
		out.write_string(s.requestingAsctIor);
		out.write_string(s.originalGrmIor);
		out.write_string(s.previousGrmIor);
		dataTypes.ApplicationTypeHelper.write(out,s.applicationType);
		out.write_string(s.applicationRepositoryIor);
		out.write_string(s.applicationName);
		out.write_string(s.basePath);
		out.write_string(s.applicationConstraints);
		out.write_string(s.applicationPreferences);
		dataTypes.BinaryNameSequenceHelper.write(out,s.availableBinaries);
		out.write_boolean(s.forceDifferentNodes);
		out.write_string(s.numberOfReplicas);
	}
}
