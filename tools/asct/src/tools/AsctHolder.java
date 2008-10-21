package tools;

/**
 *	Generated from IDL interface "Asct"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class AsctHolder	implements org.omg.CORBA.portable.Streamable{
	 public Asct value;
	public AsctHolder()
	{
	}
	public AsctHolder (final Asct initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return AsctHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AsctHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		AsctHelper.write (_out,value);
	}
}
