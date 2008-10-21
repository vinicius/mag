package subtypes;

/**
 *	Generated from IDL definition of struct "CkpReposAddressList"
 *	@author JacORB IDL compiler 
 */

public final class CkpReposAddressList
	implements org.omg.CORBA.portable.IDLEntity
{
	public CkpReposAddressList(){}
	public java.lang.String[] ipAddress;
	public short[] portNumber;
	public CkpReposAddressList(java.lang.String[] ipAddress, short[] portNumber)
	{
		this.ipAddress = ipAddress;
		this.portNumber = portNumber;
	}
}
