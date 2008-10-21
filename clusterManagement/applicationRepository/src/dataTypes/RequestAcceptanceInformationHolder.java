package dataTypes;

/**
 *	Generated from IDL definition of struct "RequestAcceptanceInformation"
 *	@author JacORB IDL compiler 
 */

public final class RequestAcceptanceInformationHolder
	implements org.omg.CORBA.portable.Streamable
{
	public dataTypes.RequestAcceptanceInformation value;

	public RequestAcceptanceInformationHolder ()
	{
	}
	public RequestAcceptanceInformationHolder(final dataTypes.RequestAcceptanceInformation initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return dataTypes.RequestAcceptanceInformationHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = dataTypes.RequestAcceptanceInformationHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		dataTypes.RequestAcceptanceInformationHelper.write(_out, value);
	}
}
