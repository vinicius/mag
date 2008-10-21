package clusterManagement;

/**
 *	Generated from IDL interface "ApplicationRepository"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class ApplicationRepositoryHolder	implements org.omg.CORBA.portable.Streamable{
	 public ApplicationRepository value;
	public ApplicationRepositoryHolder()
	{
	}
	public ApplicationRepositoryHolder (final ApplicationRepository initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return ApplicationRepositoryHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = ApplicationRepositoryHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		ApplicationRepositoryHelper.write (_out,value);
	}
}
