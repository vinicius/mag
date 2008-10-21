package core.wrappers;

import dataTypes.ApplicationExecutionInformation;
import dataTypes.ProcessExecutionInformation;


/**
 Interface AgentHandlerWrapper - Interface of AgentHandler wrapper
 
 @author Rafael Fernandes Lopes
 */
public interface AgentHandlerWrapper {
	/**
	 Unregister an application from a remote AgentHandler
	 
	 @param appExecutionId - Id of the application to be unregistered
	 @return true if the operation was successfully completed; false otherwise
	 */
	public void unregisterAgent (String appExecutionId);
	
	//public void restoreExecution (CommonExecutionSpecs commonSpecs, DistinctExecutionSpecs distinctSpecs);
	public void restoreExecution (ApplicationExecutionInformation applicationExecutionInformation, 
		       ProcessExecutionInformation processExecutionInformation);
	
    ///
	/// Method invoked by LRM to release a grid node
	///
	//void releaseNode ();	
	
	/**
	 Retrieve the identifier of the remote AgentHandler
	 
	 @return the identifier of remote AgentHandler
	 */
	public String getIor();
		
	/** Method invoked by an Asct to kill a specific application.
	 * @param executionId Id of current execution request
	 */
	public void killProcess(String executionId);
	
	public void deleteAllAgents(); // Vinicius
}
