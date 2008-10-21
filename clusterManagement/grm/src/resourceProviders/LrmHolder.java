package resourceProviders;

/**
 *	Generated from IDL interface "Lrm"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class LrmHolder	implements org.omg.CORBA.portable.Streamable{
	 public Lrm value;
	public LrmHolder()
	{
	}
	public LrmHolder (final Lrm initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return LrmHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = LrmHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		LrmHelper.write (_out,value);
	}
}
