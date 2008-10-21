package clusterManagement;

/**
 *	Generated from IDL interface "CrmAgent"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface CrmAgentOperations
{
	/* constants */
	/* operations  */
	void requestRemoteExecution(dataTypes.ApplicationExecutionInformation applicationExecutionInformation, dataTypes.ProcessExecutionInformation[] processExecutionInformationSequence, java.lang.String[] lrmIors);
}
