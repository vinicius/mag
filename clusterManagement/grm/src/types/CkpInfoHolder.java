package types;

/**
 *	Generated from IDL definition of struct "CkpInfo"
 *	@author JacORB IDL compiler 
 */

public final class CkpInfoHolder
	implements org.omg.CORBA.portable.Streamable
{
	public types.CkpInfo value;

	public CkpInfoHolder ()
	{
	}
	public CkpInfoHolder(final types.CkpInfo initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return types.CkpInfoHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = types.CkpInfoHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		types.CkpInfoHelper.write(_out, value);
	}
}
