package clusterManagement;

/**
 *	Generated from IDL interface "Arsm"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class ArsmHolder	implements org.omg.CORBA.portable.Streamable{
	 public Arsm value;
	public ArsmHolder()
	{
	}
	public ArsmHolder (final Arsm initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return ArsmHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = ArsmHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		ArsmHelper.write (_out,value);
	}
}
