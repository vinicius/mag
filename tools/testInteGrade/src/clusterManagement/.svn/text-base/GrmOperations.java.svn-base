package clusterManagement;

/**
 *	Generated from IDL interface "Grm"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface GrmOperations
{
	/* constants */
	/* operations  */
	void registerLrm(java.lang.String lrmIor, dataTypes.NodeStaticInformation nodeStaticInformation);
	void updateLrmInformation(java.lang.String lrmIor, dataTypes.NodeDynamicInformation nodeDynamicInformation);
	boolean requestRemoteExecution(dataTypes.ApplicationExecutionInformation applicationExecutionInformation, dataTypes.ProcessExecutionInformation[] processExecutionInformationSequence);
	void registerGrm(java.lang.String childGrmIor, dataTypes.SubtreeInformation subtreeInformation);
	void updateGrmInformation(java.lang.String childGrmIor, dataTypes.SubtreeInformation subtreeInformation);
	void setSampleInterval(int seconds);
	void setKeepAliveInterval(int seconds);
	boolean isAvailable();
}
