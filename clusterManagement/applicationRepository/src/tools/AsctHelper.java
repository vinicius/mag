package tools;


/**
 *	Generated from IDL interface "Asct"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class AsctHelper
{
	public static void insert (final org.omg.CORBA.Any any, final tools.Asct s)
	{
			any.insert_Object(s);
	}
	public static tools.Asct extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static org.omg.CORBA.TypeCode type()
	{
		return org.omg.CORBA.ORB.init().create_interface_tc("IDL:tools/Asct:1.0", "Asct");
	}
	public static String id()
	{
		return "IDL:tools/Asct:1.0";
	}
	public static Asct read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object());
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final tools.Asct s)
	{
		_out.write_Object(s);
	}
	public static tools.Asct narrow(final java.lang.Object obj)
	{
		if (obj instanceof tools.Asct)
		{
			return (tools.Asct)obj;
		}
		else if (obj instanceof org.omg.CORBA.Object)
		{
			return narrow((org.omg.CORBA.Object)obj);
		}
		throw new org.omg.CORBA.BAD_PARAM("Failed to narrow in helper");
	}
	public static tools.Asct narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
			return null;
		try
		{
			return (tools.Asct)obj;
		}
		catch (ClassCastException c)
		{
			if (obj._is_a("IDL:tools/Asct:1.0"))
			{
				tools._AsctStub stub;
				stub = new tools._AsctStub();
				stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
				return stub;
			}
		}
		throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
	}
	public static tools.Asct unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
			return null;
		try
		{
			return (tools.Asct)obj;
		}
		catch (ClassCastException c)
		{
				tools._AsctStub stub;
				stub = new tools._AsctStub();
				stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
				return stub;
		}
	}
}
