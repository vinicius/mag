package clusterManagement;

/**
 *	Generated from IDL interface "AgentHandler"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class AgentHandlerHolder	implements org.omg.CORBA.portable.Streamable{
	 public AgentHandler value;
	public AgentHandlerHolder()
	{
	}
	public AgentHandlerHolder (final AgentHandler initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return AgentHandlerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AgentHandlerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		AgentHandlerHelper.write (_out,value);
	}
}
