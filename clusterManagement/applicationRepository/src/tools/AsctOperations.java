package tools;

/**
 *	Generated from IDL interface "Asct"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface AsctOperations
{
	/* constants */
	/* operations  */
	void setExecutionAccepted(dataTypes.RequestAcceptanceInformation requestAcceptanceInformation);
	void setExecutionRefused(dataTypes.ExecutionRequestId executionRequestId);
	void setExecutionFinished(dataTypes.ExecutionRequestId executionRequestId);
	dataTypes.FileStruct[] getInputFiles(dataTypes.ExecutionRequestId executionRequestId);
}
