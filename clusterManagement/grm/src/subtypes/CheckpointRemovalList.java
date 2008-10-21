package subtypes;

/**
 *	Generated from IDL definition of struct "CheckpointRemovalList"
 *	@author JacORB IDL compiler 
 */

public final class CheckpointRemovalList
	implements org.omg.CORBA.portable.IDLEntity
{
	public CheckpointRemovalList(){}
	public java.lang.String[] executionId;
	public short[] ckpNumber;
	public short[] fragmentNumber;
	public CheckpointRemovalList(java.lang.String[] executionId, short[] ckpNumber, short[] fragmentNumber)
	{
		this.executionId = executionId;
		this.ckpNumber = ckpNumber;
		this.fragmentNumber = fragmentNumber;
	}
}
