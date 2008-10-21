package dataTypes;

/**
 *	Generated from IDL definition of struct "ApplicationExecutionStateInformation"
 *	@author JacORB IDL compiler 
 */

public final class ApplicationExecutionStateInformation
	implements org.omg.CORBA.portable.IDLEntity
{
	public ApplicationExecutionStateInformation(){}
	public java.lang.String requestId = "";
	public java.lang.String applicationName = "";
	public int executionState;
	public java.lang.String submissionTimeStamp = "";
	public java.lang.String finishTimeStamp = "";
	public ApplicationExecutionStateInformation(java.lang.String requestId, java.lang.String applicationName, int executionState, java.lang.String submissionTimeStamp, java.lang.String finishTimeStamp)
	{
		this.requestId = requestId;
		this.applicationName = applicationName;
		this.executionState = executionState;
		this.submissionTimeStamp = submissionTimeStamp;
		this.finishTimeStamp = finishTimeStamp;
	}
}
