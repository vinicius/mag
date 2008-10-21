package dataTypes;

/**
 *	Generated from IDL definition of struct "NodeDynamicInformation"
 *	@author JacORB IDL compiler 
 */

public final class NodeDynamicInformationHolder
	implements org.omg.CORBA.portable.Streamable
{
	public dataTypes.NodeDynamicInformation value;

	public NodeDynamicInformationHolder ()
	{
	}
	public NodeDynamicInformationHolder(final dataTypes.NodeDynamicInformation initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return dataTypes.NodeDynamicInformationHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = dataTypes.NodeDynamicInformationHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		dataTypes.NodeDynamicInformationHelper.write(_out, value);
	}
}
