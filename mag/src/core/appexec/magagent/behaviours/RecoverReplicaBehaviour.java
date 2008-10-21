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
import jade.lang.acl.UnreadableException;
import jade.proto.SimpleAchieveREInitiator;
import core.appexec.magagent.MagAgent;
import core.ontology.ExecutionInfo;
import core.ontology.MAGOntology;
import core.ontology.RegisterExecutionAction;

/**
 * @author rafaelf
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RecoverReplicaBehaviour extends SimpleAchieveREInitiator {
	private static final long serialVersionUID = -7708648039535707309L;
	
	private MagAgent magAgent = null;
	private ExecutionInfo einfo = null;
	private boolean confirmMsgReceive = false;
	
	private Codec codec = new LEAPCodec();
	private Ontology ontology = MAGOntology.getInstance();
	
	public RecoverReplicaBehaviour (MagAgent magAgent, ACLMessage msg, ExecutionInfo einfo) {
		super(magAgent, msg);

		this.einfo = einfo;
		this.magAgent = magAgent;
	}
	
	protected ACLMessage prepareRequest (ACLMessage msg) {
		msg.setProtocol (FIPANames.InteractionProtocol.FIPA_REQUEST);
		msg.setConversationId ("recoverReplica");
		msg.setLanguage (codec.getName()); 
		msg.setOntology (ontology.getName());
		msg.setSender(magAgent.getAID());
		msg.addReceiver (magAgent.getEmaAID());
	    
		RegisterExecutionAction registerExecutionAction = new RegisterExecutionAction ();
		registerExecutionAction.setExecution (this.einfo);
		
		try {
			myAgent.getContentManager().fillContent (msg, registerExecutionAction);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

		return msg;
	}
	
	protected void handleInform (ACLMessage msg) {
		
		try{
			magAgent.setContainers( (String []) msg.getContentObject());
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
    	
    	magAgent.doWake();		
		
		confirmMsgReceive = true;
	}
	
    protected void handleFailure (ACLMessage msg) {    	
    	

    	
    	System.err.println ("Communication failed . Message sent by: " + msg.getSender());
    	
    	confirmMsgReceive = true;
    }
    
	public boolean done() {
		
		return confirmMsgReceive;
	}
}
