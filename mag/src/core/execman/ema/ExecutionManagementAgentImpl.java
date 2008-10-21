package core.execman.ema;

import core.execman.ema.behaviours.ReceiveChangeAgentHostRequest;
import core.execman.ema.behaviours.ReceiveChangeApplicationExecutionStateToFinished;
import core.execman.ema.behaviours.ReceiveChangeApplicationExecutionStateToAccepted;
import core.execman.ema.behaviours.ReceiveRecoverReplicaRequest;
import core.execman.ema.behaviours.ReceiveRegisterAgentRequest;
import core.execman.ema.behaviours.ReceiveUnregisterAgentRequest;
import core.execman.ema.dataBase.ExecutionDatabaseManager;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import jade.core.Agent;
import jade.content.lang.Codec;
import jade.content.lang.leap.LEAPCodec;
import jade.content.onto.Ontology;
import jade.domain.FIPANames;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;

import jade.util.leap.Iterator;
import core.ontology.ExecutionInfo;
import core.ontology.MAGOntology;

import dataTypes.ApplicationType;
import dataTypes.ExecutionRequestId;
import dataTypes.RequestAcceptanceInformation;


import dataTypes.ApplicationExecutionInformation;
import dataTypes.ProcessExecutionInformation;

/** Flag to display debugging info. */
interface ExecutionManagerDebugFlag {
	public static final boolean debug = true;
}

public class ExecutionManagementAgentImpl extends Agent implements ExecutionManagementAgent {
	
	public ConcurrentHashMap<String, ExecutionInformation> currentExecutionsMap;
	private Codec codec = new LEAPCodec();
	private Ontology ontology = MAGOntology.getInstance();
	
	StandardFinishedExecutionManager standardFinishedExecutionManager;
	
	ExecutionDatabaseManager executionDatabaseManager;
	
	protected void setup() {
		
		getContentManager().registerLanguage(codec);
		getContentManager().registerOntology(ontology);
		
		new Thread(new ExecutionManagementAgentServantImpl(this)).start();
		
		this.executionDatabaseManager = ExecutionDatabaseManager.getInstance();

		//this.currentExecutionsMap = new ConcurrentHashMap<String, ExecutionInformation>();
		
		System.out.println("mtRegisterExecution");
		MessageTemplate mtRegisterExecution = AchieveREResponder.createMessageTemplate(FIPANames.InteractionProtocol.FIPA_REQUEST);
		mtRegisterExecution = MessageTemplate.and(mtRegisterExecution, MessageTemplate.MatchConversationId("registerExecution"));
		addBehaviour(new ReceiveRegisterAgentRequest(this, mtRegisterExecution));

		System.out.println("mtUnregisterExecution");		
		MessageTemplate mtUnregisterExecution = AchieveREResponder.createMessageTemplate(FIPANames.InteractionProtocol.FIPA_REQUEST);
		mtUnregisterExecution = MessageTemplate.and(mtUnregisterExecution, MessageTemplate.MatchConversationId("unregisterExecution"));
		addBehaviour(new ReceiveUnregisterAgentRequest(this, mtUnregisterExecution));
		
		System.out.println("mtChangeApplicationExecutionStateToAccepted");		
		MessageTemplate mtChangeApplicationExecutionStateToAccepted = MessageTemplate.and(MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST), MessageTemplate.MatchConversationId("registerExecutionAcceptedState")); 
		addBehaviour(new ReceiveChangeApplicationExecutionStateToAccepted(this, mtChangeApplicationExecutionStateToAccepted));
		
		/*MessageTemplate mtChangeApplicationExecutionStateToAccepted = AchieveREResponder.createMessageTemplate(FIPANames.InteractionProtocol.FIPA_REQUEST);
		mtChangeApplicationExecutionStateToAccepted = MessageTemplate.and(mtChangeApplicationExecutionStateToAccepted, MessageTemplate.MatchConversationId("registerExecutionAcceptedState"));
		addBehaviour(new ReceiveChangeApplicationExecutionStateToAccepted(this, mtChangeApplicationExecutionStateToAccepted));*/		

		MessageTemplate mtChangeApplicationExecutionStateToFinished = AchieveREResponder.createMessageTemplate(FIPANames.InteractionProtocol.FIPA_REQUEST);
		mtChangeApplicationExecutionStateToFinished = MessageTemplate.and(mtChangeApplicationExecutionStateToFinished, MessageTemplate.MatchConversationId("registerExecutionFinishedState"));
		addBehaviour(new ReceiveChangeApplicationExecutionStateToFinished(this, mtChangeApplicationExecutionStateToFinished));				
		
		MessageTemplate mtChangeExecutionLocation = AchieveREResponder.createMessageTemplate(FIPANames.InteractionProtocol.FIPA_REQUEST);
		mtChangeExecutionLocation = MessageTemplate.and(mtChangeExecutionLocation, MessageTemplate.MatchConversationId("changeExecutionLocation"));
		addBehaviour(new ReceiveChangeAgentHostRequest(this, mtChangeExecutionLocation));		

		// Vinicius {
//		MessageTemplate mtRecoverReplica = AchieveREResponder.createMessageTemplate(FIPANames.InteractionProtocol.FIPA_REQUEST);
//		mtRecoverReplica = MessageTemplate.and(mtRecoverReplica, MessageTemplate.MatchConversationId("recoverReplica"));
//		addBehaviour(new ReceiveRecoverReplicaRequest(this, mtRecoverReplica));
		// } Vinicius
	}	
	
	/**
	 * ---------------------------------------------------------------------------
	 * ExecutionManager Methods
	 * ---------------------------------------------------------------------------
	 */
	
	/**
	 * Method responsible for register an agent in the agent map.
	 * 
	 * @param ainfo - Application's Data .
	 */
	public synchronized void registerAgent(ExecutionInfo execInfo) {
		
		execInfo.setInitialTimestamp(Long.toString(System.currentTimeMillis()));
		
		executionDatabaseManager.registerApplicationExecution(execInfo);
		
	}
	
	/**
	 * This method returns the containers names where the process replicas are runnig
	 * 
	 * @param requestId - identify the application.
	 * @param processId - identify the process.  	 
	 * */	
	
	public String[] getReplicasLocations(String requestId, String processId, String replicaId){
		return executionDatabaseManager.getReplicasLocations(requestId, processId, replicaId);
	}

	/**
	 * This method notify the ExecutionManagementAgent after, which the MagAgent
	 * will run the applicaiton.
	 * 
	 * @param appExecutionid - identify the application.
	 */
	public boolean appAcceptedNotification(String appExecutionId, String hostname) {
		
		executionDatabaseManager.setAcceptedStateApplication(appExecutionId, hostname);

		return true;

	}	
	
	
	/**
	 * This method notify the ExecutionManagementAgent after, which the MagAgent
	 * will finished the applicaiton.
	 * 
	 * @param appExecutionid - identify the application.
	 */
	public boolean appFinishedNotification(String appExecutionId, String hostname) {
		
		executionDatabaseManager.setFinishedStateApplication(appExecutionId, hostname);

		return true;

	}	
	
	


	// --------------------------------------------------------------------------------------
	
	
	

	public boolean appFinishedNotification(String appExecutionId, Iterator outputFiles) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean appInitiatedNotification(String execId, Iterator inputFiles) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean changeAgentHost(String appExecId, String newHost) {
		// TODO Auto-generated method stub
		return false;
	}

	public ExecutionInfo[] getAgentList(String host) {
		return executionDatabaseManager.getHostApplications(host);		 
	}

	public void killApplication(String appExecId) {
		// TODO Auto-generated method stub
		
	}

	public boolean migrateExecutionNotification(String appExecutionId, String newHost) {
		// TODO Auto-generated method stub
		return false;
	}

	public void recoverHost(String host) {
	ExecutionInfo einfo[] = this.getAgentList(host);
		
	/*	if (einfo.length > 0) {
			String agentName = AgentRecover.AGENT_RECOVER_NAME + "-" + System.currentTimeMillis();
			
			try {
				AgentController agent = getContainerController().createNewAgent(agentName, "core.ft.agentrecover.AgentRecoverImpl",	einfo);
				agent.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/ 
		
	}	

	public boolean updateAgent(ExecutionInfo einfo) {
		// TODO Auto-generated method stub
		return false;
	}

}
