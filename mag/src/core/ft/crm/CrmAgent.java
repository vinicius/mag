package core.ft.crm;

import java.util.ArrayList;

import core.ft.crm.behaviours.StartErmBehaviour;

import jade.core.Agent;
import jade.wrapper.AgentContainer;

import dataTypes.ApplicationExecutionInformation;
import dataTypes.ProcessExecutionInformation;
import dataTypes.ExecutionRequestId;


/**
 * @author diego
 *
 */
public class CrmAgent extends Agent{
	
	private AgentContainer agentContainer; //< Reference to local mobile agents container	
	
	// Store the reference about the AgentContainer where the Erms are going to be created 
	private Object[] args = null;
	
	///-------------------- protected methods    
	/**
	 First method invoked by the mobile agent plataform on an
	 agent when it is instantiated
	 */
	protected void setup(){
		
		new Thread(new CrmAgentServantImpl(this)).start();
		

		args = getArguments();
		if(args != null && args.length > 0)
			agentContainer  =  (AgentContainer) args[0];		
	}
	
	public int requestRemoteExecution(
			ApplicationExecutionInformation applicationExecutionInformation,
			ProcessExecutionInformation[] processExecutionInformation,
			String[] lrmIors) {

		ArrayList<ProcessExecutionInformation[]> processExecutionInformationList = generateReplicas(
				processExecutionInformation,
				Integer.parseInt(applicationExecutionInformation.numberOfReplicas));

		
		addBehaviour(new StartErmBehaviour(this, applicationExecutionInformation, processExecutionInformationList, lrmIors));
		

		return 0;
	}	
	
	private ArrayList<ProcessExecutionInformation[]> generateReplicas(
			ProcessExecutionInformation[] processExecutionInformation,
			int numberOfReplicas) {
		
		ArrayList<ProcessExecutionInformation[]> processExecutionInformationList = new ArrayList<ProcessExecutionInformation[]>();
		


//		for (int i = 0; i < numberOfReplicas + 1; i++) {
		for (int i = 0; i < numberOfReplicas; i++) {

			ProcessExecutionInformation[] processExecutionInformationReplica = new ProcessExecutionInformation[processExecutionInformation.length];
			for (int j = 0; j < processExecutionInformationReplica.length; j++) {
				processExecutionInformationReplica[j] = new ProcessExecutionInformation();

				processExecutionInformationReplica[j].outputFileNames = processExecutionInformation[j].outputFileNames;
				processExecutionInformationReplica[j].processArguments = processExecutionInformation[j].processArguments;
				ExecutionRequestId executionRequestReplicaId = new ExecutionRequestId();
				executionRequestReplicaId.processId = processExecutionInformation[j].executionRequestId.processId;
				executionRequestReplicaId.requestId = processExecutionInformation[j].executionRequestId.requestId;

				processExecutionInformationReplica[j].executionRequestId = executionRequestReplicaId;

				String replicaId = String.valueOf(i + 1);
				String uniqueRequestId = processExecutionInformation[j].executionRequestId.requestId;
				processExecutionInformationReplica[j].executionRequestId.requestId = uniqueRequestId
						+ ":" + replicaId;
//				processExecutionInformation[j].executionRequestId.replicaId = replicaId;
				processExecutionInformationReplica[j].executionRequestId.replicaId = replicaId;
				System.out.println("CRM repId: " + replicaId);
				System.out.println("CRM reqId: " + processExecutionInformationReplica[j].executionRequestId.requestId);
				


			}
			processExecutionInformationList
					.add(processExecutionInformationReplica);

		}
		return processExecutionInformationList;

	}

	/**
	 * @return the agentContainer. 
	 */
	public AgentContainer getAgentContainer() {
		return agentContainer;
	}
	
	
	
}
