package clusterManagement;


/**
 *	Generated from IDL interface "Erm"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class ErmHelper
{
	public static void insert (final org.omg.CORBA.Any any, final clusterManagement.Erm s)
	{
			any.insert_Object(s);
	}
	public static clusterManagement.Erm extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static org.omg.CORBA.TypeCode type()
	{
		return org.omg.CORBA.ORB.init().create_interface_tc("IDL:clusterManagement/Erm:1.0", "Erm");
	}
	public static String id()
	{
		return "IDL:clusterManagement/Erm:1.0";
	}
	public static Erm read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object());
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final clusterManagement.Erm s)
	{
		_out.write_Object(s);
	}
	public static clusterManagement.Erm narrow(final java.lang.Object obj)
	{
		if (obj instanceof clusterManagement.Erm)
		{
			return (clusterManagement.Erm)obj;
		}
		else if (obj instanceof org.omg.CORBA.Object)
		{
			return narrow((org.omg.CORBA.Object)obj);
		}
		throw new org.omg.CORBA.BAD_PARAM("Failed to narrow in helper");
	}
	public static clusterManagement.Erm narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
			return null;
		try
		{
			return (clusterManagement.Erm)obj;
		}
		catch (ClassCastException c)
		{
			if (obj._is_a("IDL:clusterManagement/Erm:1.0"))
			{
				clusterManagement._ErmStub stub;
				stub = new clusterManagement._ErmStub();
				stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
				return stub;
			}
		}
		throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
	}
	public static clusterManagement.Erm unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
			return null;
		try
		{
			return (clusterManagement.Erm)obj;
		}
		catch (ClassCastException c)
		{
				clusterManagement._ErmStub stub;
				stub = new clusterManagement._ErmStub();
				stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
				return stub;
		}
	}
}
