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
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.SimpleAchieveREInitiator;
import core.ontology.ChangeExecutionLocationAction;
import core.ontology.ExecutionInfo;
import core.ontology.MAGOntology;

/**
 * @author rafaelf
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ChangeExecutionLocationBehaviour extends SimpleAchieveREInitiator {
	private String appExecutionId = "";
	private boolean confirmMsgReceive = false;
	private String newLocation = null;
	
	private Codec codec = new LEAPCodec();
	private Ontology ontology = MAGOntology.getInstance();
	
	public ChangeExecutionLocationBehaviour (Agent agent, ACLMessage msg, String appExecutionId, String newLocation) {
		super(agent, msg);
		
		this.appExecutionId = appExecutionId;
		this.newLocation = newLocation;
	}
	
	protected ACLMessage prepareRequest (ACLMessage msg) {
		msg.setProtocol (FIPANames.InteractionProtocol.FIPA_REQUEST);
		msg.setConversationId ("changeExecutionLocation");
		msg.setLanguage (codec.getName()); 
		msg.setOntology (ontology.getName());
		
		ExecutionInfo einfo = new ExecutionInfo ();
		einfo.setAppExecutionId (appExecutionId);
		
		ChangeExecutionLocationAction changeExecutionLocationAction = new ChangeExecutionLocationAction ();
		changeExecutionLocationAction.setExecution (einfo);
		changeExecutionLocationAction.setNewHost (this.newLocation);
		
		try {
			myAgent.getContentManager().fillContent(msg, changeExecutionLocationAction);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return msg;
	}
	
	protected void handleInform (ACLMessage msg) {
		confirmMsgReceive = true;
	}
	
    protected void handleFailure (ACLMessage msg) {
    	System.out.println ("Communication failed. Message sent by: " + msg.getSender());
    	confirmMsgReceive = true;
    }
    
	public boolean done() {
		return confirmMsgReceive;
	}
}
