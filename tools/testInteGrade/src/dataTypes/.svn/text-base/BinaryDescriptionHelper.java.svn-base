package dataTypes;


/**
 *	Generated from IDL definition of struct "BinaryDescription"
 *	@author JacORB IDL compiler 
 */

public final class BinaryDescriptionHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(dataTypes.BinaryDescriptionHelper.id(),"BinaryDescription",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("basePath", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("applicationName", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("binaryName", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("description", org.omg.CORBA.ORB.init().create_string_tc(0), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final dataTypes.BinaryDescription s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static dataTypes.BinaryDescription extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:dataTypes/BinaryDescription:1.0";
	}
	public static dataTypes.BinaryDescription read (final org.omg.CORBA.portable.InputStream in)
	{
		dataTypes.BinaryDescription result = new dataTypes.BinaryDescription();
		result.basePath=in.read_string();
		result.applicationName=in.read_string();
		result.binaryName=in.read_string();
		result.description=in.read_string();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final dataTypes.BinaryDescription s)
	{
		out.write_string(s.basePath);
		out.write_string(s.applicationName);
		out.write_string(s.binaryName);
		out.write_string(s.description);
	}
}
