package core.wrappers;


import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import clusterManagement.AgentHandler;
import clusterManagement.AgentHandlerHelper;
import dataTypes.ApplicationExecutionInformation;
import dataTypes.ProcessExecutionInformation;


/**
 Class AgentHandlerWrapperCorba - Class of AgentHandler wrapper.
 CORBA implementation of the ApplicationRepository wrapper
 
 @author Rafael Fernandes Lopes
 */
public class AgentHandlerWrapperCorba implements AgentHandlerWrapper {
	private String ior = "";
	private AgentHandler agentHandler = null;
	
	AgentHandlerWrapperCorba (ORB orb, String ahIor) {        
		agentHandler = AgentHandlerHelper.narrow(orb.string_to_object(ahIor));
	}
	/*AgentHandlerWrapperCorba (ORB orb) {        
			try {

				org.omg.CORBA.Object ns = orb
						.resolve_initial_references("NameService");
				 NamingContextExt nameService = NamingContextExtHelper.narrow( ns );
				 agentHandler = AgentHandlerHelper.narrow(
						 nameService.resolve(nameService.to_name(
								 					"AGENTHANDLER")));
			} catch (Exception e) {
				e.printStackTrace();
			}
	}*/
	/**
	 Unregister an application from a remote AgentHandler
	 
	 @param appExecutionId - Id of the application to be unregistered
	 @return true if the operation was successfully completed; false otherwise
	 */
	public void unregisterAgent (String appExecutionId) {
		agentHandler.unregisterAgent (appExecutionId);
	}
	
	
	/**
	 Retrieve the identifier of the remote AgentHandler
	 
	 @return the identifier of remote AgentHandler
	 */
	public String getIor() {
		return ior;
	}

	public void killProcess(String executionId){
		agentHandler.killProcess(executionId);
	}

	/**
	 * INSERIDA PARA RESTAURAR AGENTES
	 * @param applicationId
	 * @param appArgs
	 * @param asctIor
	 * @param appMainRequestId
	 * @param appNodeRequestId
	 * @param appConstraints
	 * @param appPreferences
	 * @param outputFiles
	 */
	public void restoreExecution (
			ApplicationExecutionInformation applicationExecutionInformation, 
			ProcessExecutionInformation processExecutionInformation) {
		
		agentHandler.restoreExecution (applicationExecutionInformation,
											processExecutionInformation);
	}
	
	/**
	 * Vinicius
	 */
	public void deleteAllAgents() {
		agentHandler.deleteAllAgents();
	}
}
