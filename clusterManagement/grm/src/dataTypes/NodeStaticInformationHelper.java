package dataTypes;


/**
 *	Generated from IDL definition of struct "NodeStaticInformation"
 *	@author JacORB IDL compiler 
 */

public final class NodeStaticInformationHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(dataTypes.NodeStaticInformationHelper.id(),"NodeStaticInformation",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("hostName", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("osName", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("osVersion", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("processorName", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("processorMhz", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("totalRam", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("totalSwap", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final dataTypes.NodeStaticInformation s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static dataTypes.NodeStaticInformation extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:dataTypes/NodeStaticInformation:1.0";
	}
	public static dataTypes.NodeStaticInformation read (final org.omg.CORBA.portable.InputStream in)
	{
		dataTypes.NodeStaticInformation result = new dataTypes.NodeStaticInformation();
		result.hostName=in.read_string();
		result.osName=in.read_string();
		result.osVersion=in.read_string();
		result.processorName=in.read_string();
		result.processorMhz=in.read_long();
		result.totalRam=in.read_long();
		result.totalSwap=in.read_long();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final dataTypes.NodeStaticInformation s)
	{
		out.write_string(s.hostName);
		out.write_string(s.osName);
		out.write_string(s.osVersion);
		out.write_string(s.processorName);
		out.write_long(s.processorMhz);
		out.write_long(s.totalRam);
		out.write_long(s.totalSwap);
	}
}
