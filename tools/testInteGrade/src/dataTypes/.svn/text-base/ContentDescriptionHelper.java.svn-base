package dataTypes;


/**
 *	Generated from IDL definition of struct "ContentDescription"
 *	@author JacORB IDL compiler 
 */

public final class ContentDescriptionHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(dataTypes.ContentDescriptionHelper.id(),"ContentDescription",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("fileName", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("kind", dataTypes.kindOfItensHelper.type(), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final dataTypes.ContentDescription s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static dataTypes.ContentDescription extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:dataTypes/ContentDescription:1.0";
	}
	public static dataTypes.ContentDescription read (final org.omg.CORBA.portable.InputStream in)
	{
		dataTypes.ContentDescription result = new dataTypes.ContentDescription();
		result.fileName=in.read_string();
		result.kind=dataTypes.kindOfItensHelper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final dataTypes.ContentDescription s)
	{
		out.write_string(s.fileName);
		dataTypes.kindOfItensHelper.write(out,s.kind);
	}
}
