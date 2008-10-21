package clusterManagement;

/**
 *	Generated from IDL interface "Grm"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class GrmHolder	implements org.omg.CORBA.portable.Streamable{
	 public Grm value;
	public GrmHolder()
	{
	}
	public GrmHolder (final Grm initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return GrmHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = GrmHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		GrmHelper.write (_out,value);
	}
}
