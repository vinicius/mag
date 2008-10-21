package clusterManagement;

/**
 *	Generated from IDL interface "Erm"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class ErmHolder	implements org.omg.CORBA.portable.Streamable{
	 public Erm value;
	public ErmHolder()
	{
	}
	public ErmHolder (final Erm initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return ErmHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = ErmHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		ErmHelper.write (_out,value);
	}
}
