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
	public java.lang.String replicaId = "";
	public ExecutionRequestId(java.lang.String requestId, java.lang.String processId, java.lang.String replicaId)
	{
		this.requestId = requestId;
		this.processId = processId;
		this.replicaId = replicaId;
	}
}
