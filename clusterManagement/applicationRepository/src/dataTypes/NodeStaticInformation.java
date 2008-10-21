package dataTypes;

/**
 *	Generated from IDL definition of struct "NodeStaticInformation"
 *	@author JacORB IDL compiler 
 */

public final class NodeStaticInformation
	implements org.omg.CORBA.portable.IDLEntity
{
	public NodeStaticInformation(){}
	public java.lang.String hostName = "";
	public java.lang.String osName = "";
	public java.lang.String osVersion = "";
	public java.lang.String processorName = "";
	public int processorMhz;
	public int totalRam;
	public int totalSwap;
	public NodeStaticInformation(java.lang.String hostName, java.lang.String osName, java.lang.String osVersion, java.lang.String processorName, int processorMhz, int totalRam, int totalSwap)
	{
		this.hostName = hostName;
		this.osName = osName;
		this.osVersion = osVersion;
		this.processorName = processorName;
		this.processorMhz = processorMhz;
		this.totalRam = totalRam;
		this.totalSwap = totalSwap;
	}
}
