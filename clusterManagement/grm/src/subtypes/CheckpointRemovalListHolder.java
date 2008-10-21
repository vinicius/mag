package subtypes;

/**
 *	Generated from IDL definition of struct "CheckpointRemovalList"
 *	@author JacORB IDL compiler 
 */

public final class CheckpointRemovalListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public subtypes.CheckpointRemovalList value;

	public CheckpointRemovalListHolder ()
	{
	}
	public CheckpointRemovalListHolder(final subtypes.CheckpointRemovalList initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return subtypes.CheckpointRemovalListHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = subtypes.CheckpointRemovalListHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		subtypes.CheckpointRemovalListHelper.write(_out, value);
	}
}
