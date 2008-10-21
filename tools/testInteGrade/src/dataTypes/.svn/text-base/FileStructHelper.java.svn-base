package dataTypes;


/**
 *	Generated from IDL definition of struct "FileStruct"
 *	@author JacORB IDL compiler 
 */

public final class FileStructHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(dataTypes.FileStructHelper.id(),"FileStruct",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("fileName", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("file", dataTypes.FileHelper.type(), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final dataTypes.FileStruct s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static dataTypes.FileStruct extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:dataTypes/FileStruct:1.0";
	}
	public static dataTypes.FileStruct read (final org.omg.CORBA.portable.InputStream in)
	{
		dataTypes.FileStruct result = new dataTypes.FileStruct();
		result.fileName=in.read_string();
		result.file = dataTypes.FileHelper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final dataTypes.FileStruct s)
	{
		out.write_string(s.fileName);
		dataTypes.FileHelper.write(out,s.file);
	}
}
