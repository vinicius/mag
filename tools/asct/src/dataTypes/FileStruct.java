package dataTypes;

/**
 *	Generated from IDL definition of struct "FileStruct"
 *	@author JacORB IDL compiler 
 */

public final class FileStruct
	implements org.omg.CORBA.portable.IDLEntity
{
	public FileStruct(){}
	public java.lang.String fileName = "";
	public byte[] file;
	public FileStruct(java.lang.String fileName, byte[] file)
	{
		this.fileName = fileName;
		this.file = file;
	}
}
