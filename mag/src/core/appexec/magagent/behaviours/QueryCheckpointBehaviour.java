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
import core.ontology.Checkpoint;
import core.ontology.MAGOntology;
import core.ontology.RetrieveCheckpointAction;

/**
 * @author rafaelf
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class QueryCheckpointBehaviour extends SimpleAchieveREInitiator {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4436634547385833410L;
	private String appExecutionId = "";
	private boolean confirmMsgReceive = false;
	private MagAgent magAgent = null;
	
	private Codec codec = new LEAPCodec();
	private Ontology ontology = MAGOntology.getInstance();
	
	public QueryCheckpointBehaviour (MagAgent magAgent, ACLMessage msg, String appExecutionId) {
		super(magAgent, msg);

		this.magAgent = magAgent;
		this.appExecutionId = appExecutionId;
	}
	
	protected ACLMessage prepareRequest (ACLMessage msg) {
		msg.setProtocol (FIPANames.InteractionProtocol.FIPA_QUERY);
		msg.setConversationId ("queryCheckpoint");
		msg.setLanguage (codec.getName()); 
		msg.setOntology (ontology.getName());
	    
		RetrieveCheckpointAction retrieveCheckpointAction = new RetrieveCheckpointAction();
		retrieveCheckpointAction.setExecId (this.appExecutionId);
		retrieveCheckpointAction.setCheckpoint (new Checkpoint ());
		
		try {
			myAgent.getContentManager().fillContent(msg, retrieveCheckpointAction);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return msg;
	}
	
	protected void handleInform (ACLMessage msg) {
		try {
			RetrieveCheckpointAction retrieveCheckpointAction = (RetrieveCheckpointAction) myAgent.getContentManager().extractContent (msg);
			Checkpoint checkpoint = retrieveCheckpointAction.getCheckpoint();
			
			byte compressedCheckpoint[] = checkpoint.getContent();
			
			magAgent.setCompressedCheckpoint (compressedCheckpoint);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		confirmMsgReceive = true;
	}
	
    protected void handleFailure (ACLMessage msg) {
    	confirmMsgReceive = true;
    }
    
	public boolean done() {
		return confirmMsgReceive;
	}
}
