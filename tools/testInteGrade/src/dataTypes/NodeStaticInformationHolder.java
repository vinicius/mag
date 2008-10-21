package dataTypes;

/**
 *	Generated from IDL definition of struct "NodeStaticInformation"
 *	@author JacORB IDL compiler 
 */

public final class NodeStaticInformationHolder
	implements org.omg.CORBA.portable.Streamable
{
	public dataTypes.NodeStaticInformation value;

	public NodeStaticInformationHolder ()
	{
	}
	public NodeStaticInformationHolder(final dataTypes.NodeStaticInformation initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return dataTypes.NodeStaticInformationHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = dataTypes.NodeStaticInformationHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		dataTypes.NodeStaticInformationHelper.write(_out, value);
	}
}
