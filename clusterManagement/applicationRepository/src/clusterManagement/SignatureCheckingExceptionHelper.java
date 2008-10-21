package clusterManagement;


/**
 *	Generated from IDL definition of exception "SignatureCheckingException"
 *	@author JacORB IDL compiler 
 */

public final class SignatureCheckingExceptionHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_exception_tc(clusterManagement.SignatureCheckingExceptionHelper.id(),"SignatureCheckingException",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("myMessage", org.omg.CORBA.ORB.init().create_string_tc(0), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final clusterManagement.SignatureCheckingException s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static clusterManagement.SignatureCheckingException extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:clusterManagement/SignatureCheckingException:1.0";
	}
	public static clusterManagement.SignatureCheckingException read (final org.omg.CORBA.portable.InputStream in)
	{
		clusterManagement.SignatureCheckingException result = new clusterManagement.SignatureCheckingException();
		if (!in.read_string().equals(id())) throw new org.omg.CORBA.MARSHAL("wrong id");
		result.myMessage=in.read_string();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final clusterManagement.SignatureCheckingException s)
	{
		out.write_string(id());
		out.write_string(s.myMessage);
	}
}
