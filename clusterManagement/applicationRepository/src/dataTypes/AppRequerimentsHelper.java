package dataTypes;


/**
 *	Generated from IDL definition of struct "AppRequeriments"
 *	@author JacORB IDL compiler 
 */

public final class AppRequerimentsHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(dataTypes.AppRequerimentsHelper.id(),"AppRequeriments",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("applicationConstraints", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("applicationPreferences", org.omg.CORBA.ORB.init().create_string_tc(0), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final dataTypes.AppRequeriments s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static dataTypes.AppRequeriments extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:dataTypes/AppRequeriments:1.0";
	}
	public static dataTypes.AppRequeriments read (final org.omg.CORBA.portable.InputStream in)
	{
		dataTypes.AppRequeriments result = new dataTypes.AppRequeriments();
		result.applicationConstraints=in.read_string();
		result.applicationPreferences=in.read_string();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final dataTypes.AppRequeriments s)
	{
		out.write_string(s.applicationConstraints);
		out.write_string(s.applicationPreferences);
	}
}
