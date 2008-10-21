/*
 * Created on 26/01/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package core.appexec.magagent.behaviours;



import jade.content.lang.Codec;
import jade.content.lang.leap.LEAPCodec;
import jade.content.onto.Ontology;

import jade.domain.FIPANames;

import jade.lang.acl.ACLMessage;

import jade.proto.SimpleAchieveREInitiator;

import core.appexec.magagent.MagAgent;
import core.ontology.ExecutionInfo;
import core.ontology.MAGOntology;

import core.ontology.SetProcessExecutionFinishedAction;
//import core.ontology.RegisterExecutionAction;

import dataTypes.FileStruct;

//import core.ontology.RequestInputFilesAction;

/**
 * @author diego
 *
 */
public class ChangeProcessExecutionStateToFinishedBehaviour extends SimpleAchieveREInitiator {
	
	private MagAgent magAgent = null;
	private ExecutionInfo einfo = null;
	private boolean confirmMsgReceive = false;
	
	private Codec codec = new LEAPCodec();
	private Ontology ontology = MAGOntology.getInstance();
	
	/**
	 * @param magAgent
	 * @param msg
	 * @param einfo
	 */
	public ChangeProcessExecutionStateToFinishedBehaviour(MagAgent magAgent, ACLMessage msg, ExecutionInfo einfo){
		super(magAgent, msg);
		this.einfo = einfo;
		this.magAgent = magAgent;
		System.out.println("Construtor ChangeProcessExecutionStateToFinishedBehaviour");
		
		
	}
	
	/* (non-Javadoc)
	 * @see jade.proto.SimpleAchieveREInitiator#prepareRequest(jade.lang.acl.ACLMessage)
	 */
	protected ACLMessage prepareRequest(ACLMessage msg) {		
		
		msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);		
		msg.setConversationId("registerExecutionFinishedState");
		msg.setLanguage(codec.getName()); 
		msg.setOntology(ontology.getName());
		msg.setSender(magAgent.getAID());
		msg.addReceiver(magAgent.getEmaAID());		
		
		System.out.println("prepareRequest ChangeProcessExecutionStateToFinishedBehaviour");
		SetProcessExecutionFinishedAction setProcessExecutionFinishedAction = new SetProcessExecutionFinishedAction();		 
		
		setProcessExecutionFinishedAction.setExecution(einfo);
		
		setProcessExecutionFinishedAction.setAhIor(magAgent.getAhIor());		
		
		try {		
			myAgent.getContentManager().fillContent(msg, setProcessExecutionFinishedAction);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return msg;
	}
	
	protected void handleAgree(ACLMessage msg) {
		System.out.println("ChangeProcessExecutionStateToFinished: Mesage AGREE received. Message sent by: " + msg.getSender());		
	}	
	
	protected void handleNotUnderstood(ACLMessage msg) {
		System.err.println("ChangeProcessExecutionStateToFinished: The action request was not understood.  Message sent by: " + msg.getSender());		
	}		
	
		
	protected void handleInform(ACLMessage msg) {
		confirmMsgReceive = true;
		System.out.println ("ChangeProcessExecutionStateToFinished: Communication ok. Message sent by: " + msg.getSender());
		magAgent.setEmaNotified(true);		
		//magAgent.terminateAgent();		
		
	}
	
    protected void handleFailure(ACLMessage msg) {    	
    	
    	confirmMsgReceive = true;
    	System.err.println ("ChangeProcessExecutionStateToFinished: Communication failed. Message sent by: " + msg.getSender());
    }
    
	public boolean done() {
		return confirmMsgReceive;
	}
}
