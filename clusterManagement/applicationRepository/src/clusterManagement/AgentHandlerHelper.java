package clusterManagement;


/**
 *	Generated from IDL interface "AgentHandler"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class AgentHandlerHelper
{
	public static void insert (final org.omg.CORBA.Any any, final clusterManagement.AgentHandler s)
	{
			any.insert_Object(s);
	}
	public static clusterManagement.AgentHandler extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static org.omg.CORBA.TypeCode type()
	{
		return org.omg.CORBA.ORB.init().create_interface_tc("IDL:clusterManagement/AgentHandler:1.0", "AgentHandler");
	}
	public static String id()
	{
		return "IDL:clusterManagement/AgentHandler:1.0";
	}
	public static AgentHandler read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object());
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final clusterManagement.AgentHandler s)
	{
		_out.write_Object(s);
	}
	public static clusterManagement.AgentHandler narrow(final java.lang.Object obj)
	{
		if (obj instanceof clusterManagement.AgentHandler)
		{
			return (clusterManagement.AgentHandler)obj;
		}
		else if (obj instanceof org.omg.CORBA.Object)
		{
			return narrow((org.omg.CORBA.Object)obj);
		}
		throw new org.omg.CORBA.BAD_PARAM("Failed to narrow in helper");
	}
	public static clusterManagement.AgentHandler narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
			return null;
		try
		{
			return (clusterManagement.AgentHandler)obj;
		}
		catch (ClassCastException c)
		{
			if (obj._is_a("IDL:clusterManagement/AgentHandler:1.0"))
			{
				clusterManagement._AgentHandlerStub stub;
				stub = new clusterManagement._AgentHandlerStub();
				stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
				return stub;
			}
		}
		throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
	}
	public static clusterManagement.AgentHandler unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
			return null;
		try
		{
			return (clusterManagement.AgentHandler)obj;
		}
		catch (ClassCastException c)
		{
				clusterManagement._AgentHandlerStub stub;
				stub = new clusterManagement._AgentHandlerStub();
				stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
				return stub;
		}
	}
}
