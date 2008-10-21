package clusterManagement;

/**
 *	Generated from IDL interface "AgentHandler"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface AgentHandlerOperations
	extends resourceProviders.LrmOperations
{
	/* constants */
	/* operations  */
	void unregisterAgent(java.lang.String executionId);
	void restoreExecution(dataTypes.ApplicationExecutionInformation applicationExecutionInformation, dataTypes.ProcessExecutionInformation processExecutionInformation);
	void releaseNode();
	void deleteAllAgents();
}
