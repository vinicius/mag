package subtypes;

/**
 *	Generated from IDL definition of struct "CkpReposAddressList"
 *	@author JacORB IDL compiler 
 */

public final class CkpReposAddressListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public subtypes.CkpReposAddressList value;

	public CkpReposAddressListHolder ()
	{
	}
	public CkpReposAddressListHolder(final subtypes.CkpReposAddressList initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return subtypes.CkpReposAddressListHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = subtypes.CkpReposAddressListHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		subtypes.CkpReposAddressListHelper.write(_out, value);
	}
}
