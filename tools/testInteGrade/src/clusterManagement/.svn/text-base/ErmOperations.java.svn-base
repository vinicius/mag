package clusterManagement;

/**
 *	Generated from IDL interface "Erm"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface ErmOperations
	extends resourceProviders.LrmOperations
{
	/* constants */
	/* operations  */
	void setExecutionAccepted(dataTypes.RequestAcceptanceInformation requestAcceptanceInformation);
	void setExecutionRefused(dataTypes.ExecutionRequestId executionRequestId);
	dataTypes.FileStruct[] getInputFiles(dataTypes.ExecutionRequestId executionRequestId);
	void setProcessExecutionStarted(java.lang.String lrmIor, java.lang.String executionId, int restartId, dataTypes.ExecutionRequestId executionRequestId);
	int setProcessExecutionFinished(java.lang.String lrmIor, int restartId, dataTypes.ExecutionRequestId executionRequestId, int status);
	void setExecutionFinished(dataTypes.ExecutionRequestId executionRequestId);
}
