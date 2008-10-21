package dataTypes;

/**
 *	Generated from IDL definition of struct "ProcessExecutionStateInformation"
 *	@author JacORB IDL compiler 
 */

public final class ProcessExecutionStateInformation
	implements org.omg.CORBA.portable.IDLEntity
{
	public ProcessExecutionStateInformation(){}
	public dataTypes.ExecutionRequestId executionRequestId;
	public int executionState;
	public java.lang.String executionCode = "";
	public java.lang.String lrmIOR = "";
	public java.lang.String startExecutionTimeStamp = "";
	public java.lang.String endExecutionTimeStamp = "";
	public ProcessExecutionStateInformation(dataTypes.ExecutionRequestId executionRequestId, int executionState, java.lang.String executionCode, java.lang.String lrmIOR, java.lang.String startExecutionTimeStamp, java.lang.String endExecutionTimeStamp)
	{
		this.executionRequestId = executionRequestId;
		this.executionState = executionState;
		this.executionCode = executionCode;
		this.lrmIOR = lrmIOR;
		this.startExecutionTimeStamp = startExecutionTimeStamp;
		this.endExecutionTimeStamp = endExecutionTimeStamp;
	}
}
