package dataTypes;
/**
 *	Generated from IDL definition of enum "kindOfItens"
 *	@author JacORB IDL compiler 
 */

public final class kindOfItensHolder
	implements org.omg.CORBA.portable.Streamable
{
	public kindOfItens value;

	public kindOfItensHolder ()
	{
	}
	public kindOfItensHolder (final kindOfItens initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return kindOfItensHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = kindOfItensHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		kindOfItensHelper.write (out,value);
	}
}
