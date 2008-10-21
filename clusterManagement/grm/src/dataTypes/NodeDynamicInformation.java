package dataTypes;

/**
 *	Generated from IDL definition of struct "NodeDynamicInformation"
 *	@author JacORB IDL compiler 
 */

public final class NodeDynamicInformation
	implements org.omg.CORBA.portable.IDLEntity
{
	public NodeDynamicInformation(){}
	public int freeRam;
	public int freeSwap;
	public int freeDiskSpace;
	public float cpuUsage;
	public NodeDynamicInformation(int freeRam, int freeSwap, int freeDiskSpace, float cpuUsage)
	{
		this.freeRam = freeRam;
		this.freeSwap = freeSwap;
		this.freeDiskSpace = freeDiskSpace;
		this.cpuUsage = cpuUsage;
	}
}
