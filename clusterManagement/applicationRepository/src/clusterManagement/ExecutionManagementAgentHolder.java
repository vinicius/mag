package clusterManagement;

/**
 *	Generated from IDL interface "ExecutionManagementAgent"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class ExecutionManagementAgentHolder	implements org.omg.CORBA.portable.Streamable{
	 public ExecutionManagementAgent value;
	public ExecutionManagementAgentHolder()
	{
	}
	public ExecutionManagementAgentHolder (final ExecutionManagementAgent initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return ExecutionManagementAgentHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = ExecutionManagementAgentHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		ExecutionManagementAgentHelper.write (_out,value);
	}
}
