package resourceProviders;

/**
 *	Generated from IDL interface "Lrm"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface LrmOperations
{
	/* constants */
	/* operations  */
	void setSampleInterval(int seconds);
	void setKeepAliveInterval(int seconds);
	boolean isAvailable();
	int requestExecution(dataTypes.ApplicationExecutionInformation applicationExecutionInformation, dataTypes.ProcessExecutionInformation processExecutionInformation);
	dataTypes.FileStruct[] requestOutputFiles(java.lang.String executionId);
	java.lang.String getStatus(java.lang.String executionId);
	int getLastCheckpointNumber(java.lang.String executionId);
	int restartExecution(java.lang.String executionId, java.lang.String processArguments);
	void killProcess(java.lang.String executionId);
}
