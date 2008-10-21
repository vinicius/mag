package core.wrappers;

import dataTypes.ExecutionRequestId;
import dataTypes.FileStruct;
import dataTypes.RequestAcceptanceInformation;


/**
 Interface AsctWrapper - Interface of Asct wrapper
 
 @author Rafael Fernandes Lopes 
 */
public interface AsctWrapper {
	/**
	 Receive confirmation from a remote AgentHandler that accepted our execution request.
	 
	 Analog to 2k's "ACK"
	 */
	public void setExecutionAccepted (RequestAcceptanceInformation requestAcceptanceInformation);
	
	/**
	 Signals that an application execution was refused
	 */
	public void setExecutionRefused(String requestId, String processId); // Vinicius on replicaId
	
	/**
	 Signals that an application execution was finished
	 */
	// Vinicius on replicaId {
	public void setExecutionFinished(String requestId, String processId);
//	public void setExecutionFinished(String requestId, String processId, String replicaId);
	// } Vinicius
	
	/**
	 Receive request for files needed to allow the remote execution of
	 a request made by this ASCT
	 */
	public FileStruct [] getInputFiles (String requestId, String processId, String destinationPath); // Vinicius on replicaId
	
	
	public FileStruct [] returnInputFiles(ExecutionRequestId executionRequestId);
}
