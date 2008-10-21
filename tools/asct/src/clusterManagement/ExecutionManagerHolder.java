package clusterManagement;

/**
 *	Generated from IDL interface "ExecutionManager"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class ExecutionManagerHolder	implements org.omg.CORBA.portable.Streamable{
	 public ExecutionManager value;
	public ExecutionManagerHolder()
	{
	}
	public ExecutionManagerHolder (final ExecutionManager initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return ExecutionManagerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = ExecutionManagerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		ExecutionManagerHelper.write (_out,value);
	}
}
