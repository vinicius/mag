package dataTypes;


/**
 *	Generated from IDL definition of struct "BspProcessZeroInformation"
 *	@author JacORB IDL compiler 
 */

public final class BspProcessZeroInformationHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(dataTypes.BspProcessZeroInformationHelper.id(),"BspProcessZeroInformation",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("isProcessZero", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(8)), null),new org.omg.CORBA.StructMember("processZeroIor", org.omg.CORBA.ORB.init().create_string_tc(0), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final dataTypes.BspProcessZeroInformation s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static dataTypes.BspProcessZeroInformation extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:dataTypes/BspProcessZeroInformation:1.0";
	}
	public static dataTypes.BspProcessZeroInformation read (final org.omg.CORBA.portable.InputStream in)
	{
		dataTypes.BspProcessZeroInformation result = new dataTypes.BspProcessZeroInformation();
		result.isProcessZero=in.read_boolean();
		result.processZeroIor=in.read_string();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final dataTypes.BspProcessZeroInformation s)
	{
		out.write_boolean(s.isProcessZero);
		out.write_string(s.processZeroIor);
	}
}
