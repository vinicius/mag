package clusterManagement;

/**
 *	Generated from IDL interface "Crm"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class CrmHolder	implements org.omg.CORBA.portable.Streamable{
	 public Crm value;
	public CrmHolder()
	{
	}
	public CrmHolder (final Crm initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return CrmHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CrmHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		CrmHelper.write (_out,value);
	}
}
