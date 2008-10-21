package dataTypes;

/**
 *	Generated from IDL definition of struct "BinaryDescription"
 *	@author JacORB IDL compiler 
 */

public final class BinaryDescription
	implements org.omg.CORBA.portable.IDLEntity
{
	public BinaryDescription(){}
	public java.lang.String basePath = "";
	public java.lang.String applicationName = "";
	public java.lang.String binaryName = "";
	public java.lang.String description = "";
	public BinaryDescription(java.lang.String basePath, java.lang.String applicationName, java.lang.String binaryName, java.lang.String description)
	{
		this.basePath = basePath;
		this.applicationName = applicationName;
		this.binaryName = binaryName;
		this.description = description;
	}
}
