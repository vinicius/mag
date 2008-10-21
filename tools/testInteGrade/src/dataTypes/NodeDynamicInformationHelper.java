package dataTypes;


/**
 *	Generated from IDL definition of struct "NodeDynamicInformation"
 *	@author JacORB IDL compiler 
 */

public final class NodeDynamicInformationHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(dataTypes.NodeDynamicInformationHelper.id(),"NodeDynamicInformation",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("freeRam", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("freeSwap", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("freeDiskSpace", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("cpuUsage", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(6)), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final dataTypes.NodeDynamicInformation s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static dataTypes.NodeDynamicInformation extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:dataTypes/NodeDynamicInformation:1.0";
	}
	public static dataTypes.NodeDynamicInformation read (final org.omg.CORBA.portable.InputStream in)
	{
		dataTypes.NodeDynamicInformation result = new dataTypes.NodeDynamicInformation();
		result.freeRam=in.read_long();
		result.freeSwap=in.read_long();
		result.freeDiskSpace=in.read_long();
		result.cpuUsage=in.read_float();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final dataTypes.NodeDynamicInformation s)
	{
		out.write_long(s.freeRam);
		out.write_long(s.freeSwap);
		out.write_long(s.freeDiskSpace);
		out.write_float(s.cpuUsage);
	}
}
