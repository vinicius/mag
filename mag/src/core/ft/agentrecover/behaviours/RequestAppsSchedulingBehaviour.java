package core.ft.agentrecover.behaviours;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.lang.acl.ACLMessage;
//import subtypes.AppRequeriments;
import core.appexec.magagent.behaviours.ChangeExecutionLocationBehaviour;
import core.execman.ema.ExecutionManagementAgent;
import core.ft.agentrecover.AgentRecover;
import core.ontology.ExecutionInfo;
import core.wrappers.GrmWrapper;

import dataTypes.ApplicationExecutionInformation;
import dataTypes.ProcessExecutionInformation;


/**
 * @author publico
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RequestAppsSchedulingBehaviour extends OneShotBehaviour {
	/**
	 * 
	 */
	private static final long serialVersionUID = -515960935327543186L;
	//private GrmWrapper grm;
	private String [] containers;
	
	private AID emaAID = null;
	
	private GrmWrapper grm;
	
	private ApplicationExecutionInformation applicationExecutionInformation = null;
	private ProcessExecutionInformation processExecutionInformation = null;	
	
	public RequestAppsSchedulingBehaviour(AgentRecover agentRecover, String [] containers, GrmWrapper grm, ApplicationExecutionInformation applicationExecutionInformation, ProcessExecutionInformation processExecutionInformation) {
		this.containers = containers;
		this.grm = grm;
		this.applicationExecutionInformation = applicationExecutionInformation;
		this.processExecutionInformation = processExecutionInformation;
		
	}

	public void action() {
		this.emaAID = new AID(ExecutionManagementAgent.EXECUTION_MANAGEMENT_AGENT_NAME + "@" + myAgent.getContainerController().getPlatformName(), AID.ISGUID);
		
		String location = null;
		
		/*for (int i=0; i < executionInfo.length ; i++){
			containers[i] = executionInfo[i].getExecutingHost();
			System.out.println("AgentRecover Container " + containers[i]);
		}*/
		
		
/*		AppRequeriments[] appRequeriments = new AppRequeriments[this.executionInfo.length];
		
		for(int i = 0; i< this.executionInfo.length;i++){
			appRequeriments[i] = new AppRequeriments(executionInfo[i].getAppConstraints(),executionInfo[i].getAppPreferences());
		}*/
		
		location = grm.migrationLocationRequest(containers);						
		
		SequentialBehaviour seqBehaviour = new SequentialBehaviour();
		
	/*	for (int i = 0; i < this.executionInfo.length; i++) {
			if (!locations[i].equals ("")) {
				ACLMessage changeLocationMsg = new ACLMessage (ACLMessage.REQUEST);
				changeLocationMsg.setSender (myAgent.getAID());
				changeLocationMsg.addReceiver (emaAID);
				
				seqBehaviour.addSubBehaviour (new ChangeExecutionLocationBehaviour (myAgent, changeLocationMsg, executionInfo[i].getAppExecutionId(), locations[i]));
			}
		}*/
		
		seqBehaviour.addSubBehaviour (new RestoreAppsBehaviour(/*executionInfo,*/ location, applicationExecutionInformation, processExecutionInformation));
		
		myAgent.addBehaviour(seqBehaviour);
	}
}
