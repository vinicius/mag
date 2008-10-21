package dataTypes;

/**
 *	Generated from IDL definition of struct "RequestAcceptanceInformation"
 *	@author JacORB IDL compiler 
 */

public final class RequestAcceptanceInformation
	implements org.omg.CORBA.portable.IDLEntity
{
	public RequestAcceptanceInformation(){}
	public java.lang.String lrmIor = "";
	public dataTypes.ExecutionRequestId executionRequestId;
	public java.lang.String executionId = "";
	public RequestAcceptanceInformation(java.lang.String lrmIor, dataTypes.ExecutionRequestId executionRequestId, java.lang.String executionId)
	{
		this.lrmIor = lrmIor;
		this.executionRequestId = executionRequestId;
		this.executionId = executionId;
	}
}
