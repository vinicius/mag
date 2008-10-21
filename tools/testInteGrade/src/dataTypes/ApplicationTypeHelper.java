package dataTypes;
/**
 *	Generated from IDL definition of enum "ApplicationType"
 *	@author JacORB IDL compiler 
 */

public final class ApplicationTypeHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_enum_tc(dataTypes.ApplicationTypeHelper.id(),"ApplicationType",new String[]{"regular","parametric","bsp"});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final dataTypes.ApplicationType s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static dataTypes.ApplicationType extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:dataTypes/ApplicationType:1.0";
	}
	public static ApplicationType read (final org.omg.CORBA.portable.InputStream in)
	{
		return ApplicationType.from_int(in.read_long());
	}

	public static void write (final org.omg.CORBA.portable.OutputStream out, final ApplicationType s)
	{
		out.write_long(s.value());
	}
}
