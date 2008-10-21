package clusterManagement;


/**
 *	Generated from IDL definition of exception "SelfTestException"
 *	@author JacORB IDL compiler 
 */

public final class SelfTestExceptionHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_exception_tc(clusterManagement.SelfTestExceptionHelper.id(),"SelfTestException",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("myMessage", org.omg.CORBA.ORB.init().create_string_tc(0), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final clusterManagement.SelfTestException s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static clusterManagement.SelfTestException extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:clusterManagement/SelfTestException:1.0";
	}
	public static clusterManagement.SelfTestException read (final org.omg.CORBA.portable.InputStream in)
	{
		clusterManagement.SelfTestException result = new clusterManagement.SelfTestException();
		if (!in.read_string().equals(id())) throw new org.omg.CORBA.MARSHAL("wrong id");
		result.myMessage=in.read_string();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final clusterManagement.SelfTestException s)
	{
		out.write_string(id());
		out.write_string(s.myMessage);
	}
}
