package dataTypes;

/**
 *	Generated from IDL definition of alias "ContentDescriptionSequence"
 *	@author JacORB IDL compiler 
 */

public final class ContentDescriptionSequenceHolder
	implements org.omg.CORBA.portable.Streamable
{
	public dataTypes.ContentDescription[] value;

	public ContentDescriptionSequenceHolder ()
	{
	}
	public ContentDescriptionSequenceHolder (final dataTypes.ContentDescription[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return ContentDescriptionSequenceHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = ContentDescriptionSequenceHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		ContentDescriptionSequenceHelper.write (out,value);
	}
}
