package dataTypes;


/**
 *	Generated from IDL definition of struct "ApplicationDescription"
 *	@author JacORB IDL compiler 
 */

public final class ApplicationDescriptionHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(dataTypes.ApplicationDescriptionHelper.id(),"ApplicationDescription",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("basePath", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("applicationName", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("numberOfBinaries", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("binaryIds", dataTypes.StringSequenceHelper.type(), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final dataTypes.ApplicationDescription s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static dataTypes.ApplicationDescription extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:dataTypes/ApplicationDescription:1.0";
	}
	public static dataTypes.ApplicationDescription read (final org.omg.CORBA.portable.InputStream in)
	{
		dataTypes.ApplicationDescription result = new dataTypes.ApplicationDescription();
		result.basePath=in.read_string();
		result.applicationName=in.read_string();
		result.numberOfBinaries=in.read_string();
		result.binaryIds = dataTypes.StringSequenceHelper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final dataTypes.ApplicationDescription s)
	{
		out.write_string(s.basePath);
		out.write_string(s.applicationName);
		out.write_string(s.numberOfBinaries);
		dataTypes.StringSequenceHelper.write(out,s.binaryIds);
	}
}
