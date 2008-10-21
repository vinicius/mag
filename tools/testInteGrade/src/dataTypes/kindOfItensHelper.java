package dataTypes;
/**
 *	Generated from IDL definition of enum "kindOfItens"
 *	@author JacORB IDL compiler 
 */

public final class kindOfItensHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_enum_tc(dataTypes.kindOfItensHelper.id(),"kindOfItens",new String[]{"applicationDirectory","commonDirectory","binaryFile","applicationDescriptionFile","rootDirectory"});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final dataTypes.kindOfItens s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static dataTypes.kindOfItens extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:dataTypes/kindOfItens:1.0";
	}
	public static kindOfItens read (final org.omg.CORBA.portable.InputStream in)
	{
		return kindOfItens.from_int(in.read_long());
	}

	public static void write (final org.omg.CORBA.portable.OutputStream out, final kindOfItens s)
	{
		out.write_long(s.value());
	}
}
