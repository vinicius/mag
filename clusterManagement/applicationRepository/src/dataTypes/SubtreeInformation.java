package dataTypes;

/**
 *	Generated from IDL definition of struct "SubtreeInformation"
 *	@author JacORB IDL compiler 
 */

public final class SubtreeInformation
	implements org.omg.CORBA.portable.IDLEntity
{
	public SubtreeInformation(){}
	public dataTypes.NodeStaticInformation staticAverages;
	public dataTypes.NodeDynamicInformation dynamicAverages;
	public SubtreeInformation(dataTypes.NodeStaticInformation staticAverages, dataTypes.NodeDynamicInformation dynamicAverages)
	{
		this.staticAverages = staticAverages;
		this.dynamicAverages = dynamicAverages;
	}
}
