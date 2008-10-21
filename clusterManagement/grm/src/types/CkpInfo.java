package types;

/**
 *	Generated from IDL definition of struct "CkpInfo"
 *	@author JacORB IDL compiler 
 */

public final class CkpInfo
	implements org.omg.CORBA.portable.IDLEntity
{
	public CkpInfo(){}
	public int ckpNumber;
	public int ckpSize;
	public java.lang.String[] ipAddress;
	public short[] portNumber;
	public java.lang.String[] checksum;
	public CkpInfo(int ckpNumber, int ckpSize, java.lang.String[] ipAddress, short[] portNumber, java.lang.String[] checksum)
	{
		this.ckpNumber = ckpNumber;
		this.ckpSize = ckpSize;
		this.ipAddress = ipAddress;
		this.portNumber = portNumber;
		this.checksum = checksum;
	}
}
