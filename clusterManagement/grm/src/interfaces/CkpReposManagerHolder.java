package interfaces;

/**
 *	Generated from IDL interface "CkpReposManager"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class CkpReposManagerHolder	implements org.omg.CORBA.portable.Streamable{
	 public CkpReposManager value;
	public CkpReposManagerHolder()
	{
	}
	public CkpReposManagerHolder (final CkpReposManager initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return CkpReposManagerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CkpReposManagerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		CkpReposManagerHelper.write (_out,value);
	}
}
