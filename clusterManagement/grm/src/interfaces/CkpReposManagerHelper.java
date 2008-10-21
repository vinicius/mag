package interfaces;


/**
 *	Generated from IDL interface "CkpReposManager"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class CkpReposManagerHelper
{
	public static void insert (final org.omg.CORBA.Any any, final interfaces.CkpReposManager s)
	{
			any.insert_Object(s);
	}
	public static interfaces.CkpReposManager extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static org.omg.CORBA.TypeCode type()
	{
		return org.omg.CORBA.ORB.init().create_interface_tc("IDL:interfaces/CkpReposManager:1.0", "CkpReposManager");
	}
	public static String id()
	{
		return "IDL:interfaces/CkpReposManager:1.0";
	}
	public static CkpReposManager read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object());
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final interfaces.CkpReposManager s)
	{
		_out.write_Object(s);
	}
	public static interfaces.CkpReposManager narrow(final java.lang.Object obj)
	{
		if (obj instanceof interfaces.CkpReposManager)
		{
			return (interfaces.CkpReposManager)obj;
		}
		else if (obj instanceof org.omg.CORBA.Object)
		{
			return narrow((org.omg.CORBA.Object)obj);
		}
		throw new org.omg.CORBA.BAD_PARAM("Failed to narrow in helper");
	}
	public static interfaces.CkpReposManager narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
			return null;
		try
		{
			return (interfaces.CkpReposManager)obj;
		}
		catch (ClassCastException c)
		{
			if (obj._is_a("IDL:interfaces/CkpReposManager:1.0"))
			{
				interfaces._CkpReposManagerStub stub;
				stub = new interfaces._CkpReposManagerStub();
				stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
				return stub;
			}
		}
		throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
	}
	public static interfaces.CkpReposManager unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
			return null;
		try
		{
			return (interfaces.CkpReposManager)obj;
		}
		catch (ClassCastException c)
		{
				interfaces._CkpReposManagerStub stub;
				stub = new interfaces._CkpReposManagerStub();
				stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
				return stub;
		}
	}
}
