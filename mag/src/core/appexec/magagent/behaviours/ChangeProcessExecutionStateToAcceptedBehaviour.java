/*
 * Created on 26/01/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package core.appexec.magagent.behaviours;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import jade.content.lang.Codec;
import jade.content.lang.leap.LEAPCodec;
import jade.content.onto.Ontology;

import jade.core.AID;
import jade.domain.FIPANames;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import jade.proto.SimpleAchieveREInitiator;


import core.appexec.magagent.MagAgent;
import core.ontology.ExecutionInfo;
import core.ontology.InputFile;
import core.ontology.MAGOntology;
import core.ontology.RequestInputFilesAction;
import core.ontology.SetExecutionAcceptedAction;
//import core.ontology.RegisterExecutionAction;

import dataTypes.FileStruct;

//import core.ontology.RequestInputFilesAction;

/**
 * @author diego
 *
 */
public class ChangeProcessExecutionStateToAcceptedBehaviour extends SimpleAchieveREInitiator {
	
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
	public ChangeProcessExecutionStateToAcceptedBehaviour(MagAgent magAgent, ACLMessage msg, ExecutionInfo einfo){
		super(magAgent, msg);
		this.einfo = einfo;
		this.magAgent = magAgent;

		System.out.println("Construtor ChangeProcessExecutionStateToAcceptedBehaviour");
		
	}
	
	/* (non-Javadoc)
	 * @see jade.proto.SimpleAchieveREInitiator#prepareRequest(jade.lang.acl.ACLMessage)
	 */
	protected ACLMessage prepareRequest(ACLMessage msg) {
		
		msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);		
		msg.setConversationId("registerExecutionAcceptedState");
		msg.setLanguage(codec.getName()); 
		msg.setOntology(ontology.getName());
		msg.setSender(magAgent.getAID());
		msg.addReceiver(magAgent.getEmaAID());

		System.out.println("prepareRequest ChangeProcessExecutionStateToAcceptedBehaviour");
		
		
		SetExecutionAcceptedAction setExecutionAcceptedAction = new SetExecutionAcceptedAction();		 
		
		setExecutionAcceptedAction.setExecution(einfo);
		
		setExecutionAcceptedAction.setAhIor(magAgent.getAhIor());		
		
		try {		
			myAgent.getContentManager().fillContent(msg, setExecutionAcceptedAction);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return msg;
	}
	
	protected void handleAgree(ACLMessage msg) {
		System.out.println("ChangeProcessExecutionStateToAccepted: Mesage AGREE received. Message sent by: " + msg.getSender());
	}	
	
	protected void handleNotUnderstood(ACLMessage msg) {
		System.err.println("ChangeProcessExecutionStateToAccepted: The action request was not understood InformExecutionAccepted.  Message sent by: " + msg.getSender());
	}		
	
		
	protected void handleInform(ACLMessage msg) {
		confirmMsgReceive = true;
		
		System.out.println ("ChangeProcessExecutionStateToAccepted: Communication ok. Message sent by: " + msg.getSender());
		
	}
	
    protected void handleFailure(ACLMessage msg) {
    	confirmMsgReceive = true;
    	System.err.println ("ChangeProcessExecutionStateToAccepted: Communication failed RegisterExecutionState. Message sent by: " + msg.getSender());
    }
    
	public boolean done() {
		return confirmMsgReceive;
	}
}
