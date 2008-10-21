package resourceProviders;


/**
 *	Generated from IDL interface "Lrm"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class LrmHelper
{
	public static void insert (final org.omg.CORBA.Any any, final resourceProviders.Lrm s)
	{
			any.insert_Object(s);
	}
	public static resourceProviders.Lrm extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static org.omg.CORBA.TypeCode type()
	{
		return org.omg.CORBA.ORB.init().create_interface_tc("IDL:resourceProviders/Lrm:1.0", "Lrm");
	}
	public static String id()
	{
		return "IDL:resourceProviders/Lrm:1.0";
	}
	public static Lrm read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object());
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final resourceProviders.Lrm s)
	{
		_out.write_Object(s);
	}
	public static resourceProviders.Lrm narrow(final java.lang.Object obj)
	{
		if (obj instanceof resourceProviders.Lrm)
		{
			return (resourceProviders.Lrm)obj;
		}
		else if (obj instanceof org.omg.CORBA.Object)
		{
			return narrow((org.omg.CORBA.Object)obj);
		}
		throw new org.omg.CORBA.BAD_PARAM("Failed to narrow in helper");
	}
	public static resourceProviders.Lrm narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
			return null;
		try
		{
			return (resourceProviders.Lrm)obj;
		}
		catch (ClassCastException c)
		{
			if (obj._is_a("IDL:resourceProviders/Lrm:1.0"))
			{
				resourceProviders._LrmStub stub;
				stub = new resourceProviders._LrmStub();
				stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
				return stub;
			}
		}
		throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
	}
	public static resourceProviders.Lrm unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
			return null;
		try
		{
			return (resourceProviders.Lrm)obj;
		}
		catch (ClassCastException c)
		{
				resourceProviders._LrmStub stub;
				stub = new resourceProviders._LrmStub();
				stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
				return stub;
		}
	}
}
