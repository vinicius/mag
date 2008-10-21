package dataTypes;

/**
 *	Generated from IDL definition of struct "ExecutionRequestId"
 *	@author JacORB IDL compiler 
 */

public final class ExecutionRequestIdHolder
	implements org.omg.CORBA.portable.Streamable
{
	public dataTypes.ExecutionRequestId value;

	public ExecutionRequestIdHolder ()
	{
	}
	public ExecutionRequestIdHolder(final dataTypes.ExecutionRequestId initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return dataTypes.ExecutionRequestIdHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = dataTypes.ExecutionRequestIdHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		dataTypes.ExecutionRequestIdHelper.write(_out, value);
	}
}
