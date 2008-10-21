package clusterManagement;


/**
 *	Generated from IDL interface "ApplicationRepository"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class ApplicationRepositoryHelper
{
	public static void insert (final org.omg.CORBA.Any any, final clusterManagement.ApplicationRepository s)
	{
			any.insert_Object(s);
	}
	public static clusterManagement.ApplicationRepository extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static org.omg.CORBA.TypeCode type()
	{
		return org.omg.CORBA.ORB.init().create_interface_tc("IDL:clusterManagement/ApplicationRepository:1.0", "ApplicationRepository");
	}
	public static String id()
	{
		return "IDL:clusterManagement/ApplicationRepository:1.0";
	}
	public static ApplicationRepository read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object());
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final clusterManagement.ApplicationRepository s)
	{
		_out.write_Object(s);
	}
	public static clusterManagement.ApplicationRepository narrow(final java.lang.Object obj)
	{
		if (obj instanceof clusterManagement.ApplicationRepository)
		{
			return (clusterManagement.ApplicationRepository)obj;
		}
		else if (obj instanceof org.omg.CORBA.Object)
		{
			return narrow((org.omg.CORBA.Object)obj);
		}
		throw new org.omg.CORBA.BAD_PARAM("Failed to narrow in helper");
	}
	public static clusterManagement.ApplicationRepository narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
			return null;
		try
		{
			return (clusterManagement.ApplicationRepository)obj;
		}
		catch (ClassCastException c)
		{
			if (obj._is_a("IDL:clusterManagement/ApplicationRepository:1.0"))
			{
				clusterManagement._ApplicationRepositoryStub stub;
				stub = new clusterManagement._ApplicationRepositoryStub();
				stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
				return stub;
			}
		}
		throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
	}
	public static clusterManagement.ApplicationRepository unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
			return null;
		try
		{
			return (clusterManagement.ApplicationRepository)obj;
		}
		catch (ClassCastException c)
		{
				clusterManagement._ApplicationRepositoryStub stub;
				stub = new clusterManagement._ApplicationRepositoryStub();
				stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
				return stub;
		}
	}
}
