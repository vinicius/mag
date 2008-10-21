package dataTypes;

/**
 *	Generated from IDL definition of struct "ContentDescription"
 *	@author JacORB IDL compiler 
 */

public final class ContentDescription
	implements org.omg.CORBA.portable.IDLEntity
{
	public ContentDescription(){}
	public java.lang.String fileName = "";
	public dataTypes.kindOfItens kind;
	public ContentDescription(java.lang.String fileName, dataTypes.kindOfItens kind)
	{
		this.fileName = fileName;
		this.kind = kind;
	}
}
