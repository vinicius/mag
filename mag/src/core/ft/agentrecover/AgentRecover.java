package core.ft.agentrecover;

import core.ontology.ExecutionInfo;

/**
 *	Interface AgentRecover - A AgentRecover interface  
 * 
 *  Represents the AgentRecover interface and its public operations
 * 
 * 
 *  @author Bysmarck Barros de Sousa
 *  
 *  
*/
public interface AgentRecover {
	public static final String AGENT_RECOVER_NAME = "agentrecover";
	
	public void setExecutionInfoArray (ExecutionInfo[] executionInfo);
	
	public ExecutionInfo[] getExecutionInfoArray ();
}
