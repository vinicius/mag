package dataTypes;

/**
 *	Generated from IDL definition of struct "ExecutionRequestId"
 *	@author JacORB IDL compiler 
 */

public final class ExecutionRequestId
	implements org.omg.CORBA.portable.IDLEntity
{
	public ExecutionRequestId(){}
	public java.lang.String requestId = "";
	public java.lang.String processId = "";
	public ExecutionRequestId(java.lang.String requestId, java.lang.String processId)
	{
		this.requestId = requestId;
		this.processId = processId;
	}
}
