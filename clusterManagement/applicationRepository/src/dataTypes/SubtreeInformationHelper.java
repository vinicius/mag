package dataTypes;


/**
 *	Generated from IDL definition of struct "SubtreeInformation"
 *	@author JacORB IDL compiler 
 */

public final class SubtreeInformationHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(dataTypes.SubtreeInformationHelper.id(),"SubtreeInformation",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("staticAverages", dataTypes.NodeStaticInformationHelper.type(), null),new org.omg.CORBA.StructMember("dynamicAverages", dataTypes.NodeDynamicInformationHelper.type(), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final dataTypes.SubtreeInformation s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static dataTypes.SubtreeInformation extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:dataTypes/SubtreeInformation:1.0";
	}
	public static dataTypes.SubtreeInformation read (final org.omg.CORBA.portable.InputStream in)
	{
		dataTypes.SubtreeInformation result = new dataTypes.SubtreeInformation();
		result.staticAverages=dataTypes.NodeStaticInformationHelper.read(in);
		result.dynamicAverages=dataTypes.NodeDynamicInformationHelper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final dataTypes.SubtreeInformation s)
	{
		dataTypes.NodeStaticInformationHelper.write(out,s.staticAverages);
		dataTypes.NodeDynamicInformationHelper.write(out,s.dynamicAverages);
	}
}
