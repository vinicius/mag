package dataTypes;

/**
 *	Generated from IDL definition of struct "ProcessExecutionInformation"
 *	@author JacORB IDL compiler 
 */

public final class ProcessExecutionInformation
	implements org.omg.CORBA.portable.IDLEntity
{
	public ProcessExecutionInformation(){}
	public dataTypes.ExecutionRequestId executionRequestId;
	public java.lang.String processArguments = "";
	public java.lang.String[] outputFileNames;
	public ProcessExecutionInformation(dataTypes.ExecutionRequestId executionRequestId, java.lang.String processArguments, java.lang.String[] outputFileNames)
	{
		this.executionRequestId = executionRequestId;
		this.processArguments = processArguments;
		this.outputFileNames = outputFileNames;
	}
}
