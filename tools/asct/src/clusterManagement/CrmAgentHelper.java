package clusterManagement;


/**
 *	Generated from IDL interface "CrmAgent"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class CrmAgentHelper
{
	public static void insert (final org.omg.CORBA.Any any, final clusterManagement.CrmAgent s)
	{
			any.insert_Object(s);
	}
	public static clusterManagement.CrmAgent extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static org.omg.CORBA.TypeCode type()
	{
		return org.omg.CORBA.ORB.init().create_interface_tc("IDL:clusterManagement/CrmAgent:1.0", "CrmAgent");
	}
	public static String id()
	{
		return "IDL:clusterManagement/CrmAgent:1.0";
	}
	public static CrmAgent read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object());
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final clusterManagement.CrmAgent s)
	{
		_out.write_Object(s);
	}
	public static clusterManagement.CrmAgent narrow(final java.lang.Object obj)
	{
		if (obj instanceof clusterManagement.CrmAgent)
		{
			return (clusterManagement.CrmAgent)obj;
		}
		else if (obj instanceof org.omg.CORBA.Object)
		{
			return narrow((org.omg.CORBA.Object)obj);
		}
		throw new org.omg.CORBA.BAD_PARAM("Failed to narrow in helper");
	}
	public static clusterManagement.CrmAgent narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
			return null;
		try
		{
			return (clusterManagement.CrmAgent)obj;
		}
		catch (ClassCastException c)
		{
			if (obj._is_a("IDL:clusterManagement/CrmAgent:1.0"))
			{
				clusterManagement._CrmAgentStub stub;
				stub = new clusterManagement._CrmAgentStub();
				stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
				return stub;
			}
		}
		throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
	}
	public static clusterManagement.CrmAgent unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
			return null;
		try
		{
			return (clusterManagement.CrmAgent)obj;
		}
		catch (ClassCastException c)
		{
				clusterManagement._CrmAgentStub stub;
				stub = new clusterManagement._CrmAgentStub();
				stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
				return stub;
		}
	}
}
