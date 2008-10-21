/*
 * Created on 20/01/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package core.ft.stablestorage.behaviours;

import jade.content.AgentAction;
import jade.content.ContentElement;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import core.ft.stablestorage.StableStorage;
import core.ontology.Checkpoint;
import core.ontology.RetrieveCheckpointAction;
/**
 * @author publico
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ReceiveQueryCheckpoint extends AchieveREResponder {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2554764664213309408L;

	public ReceiveQueryCheckpoint (StableStorage stableStorage, MessageTemplate mt) {
		super((Agent) stableStorage, mt);
	}
	
	protected ACLMessage prepareResponse (ACLMessage msg) throws NotUnderstoodException, RefuseException {
		ACLMessage reply = msg.createReply();
		reply.setPerformative(ACLMessage.AGREE);
		
		ContentElement ce = null;
		
		try {
		    ce = myAgent.getContentManager().extractContent (msg);
		} catch (Exception e) {
			throw new NotUnderstoodException ("Your request was not understood");
		}
		
		if (ce instanceof AgentAction) {
	    	AgentAction a = (AgentAction) ce;
	    	
	    	if ( !(a instanceof RetrieveCheckpointAction) ) {
	    		throw new NotUnderstoodException ("Your action request was not understood");
	    	}
	    } else {
	    	throw new NotUnderstoodException ("Your request is not an action");
	    }
		
		return reply;
	}
	
	protected ACLMessage prepareResultNotification (ACLMessage request, ACLMessage response) throws FailureException {
		ACLMessage reply = request.createReply();
		reply.setPerformative(ACLMessage.INFORM);
		
		try {
			RetrieveCheckpointAction retrieveCheckpointAction = (RetrieveCheckpointAction) myAgent.getContentManager().extractContent (request);
		    
			byte ckp[] = this.retrieveCheckpoint (retrieveCheckpointAction.getExecId());
		    
    		if (ckp == null) {
    			throw new FailureException ("An error occurred when your request was being processed");
			}
    		
    		Checkpoint c = new Checkpoint ();
    		c.setContent (ckp);
    		
    		retrieveCheckpointAction.setCheckpoint (c);
    		
    		myAgent.getContentManager().fillContent (reply, retrieveCheckpointAction);
		} catch (Exception e) {
			throw new FailureException ("An error occurred when your request was being processed");
		}
		
		return reply;
	}
	
	/**
	 * This method returns checkpoint information from the application whose Id of 
	 * execution is <b>appExecutionId</b>
	 * 
	 * @param appExecutionId the execution id
	 *  
	 * @return the checkpoint object as a byte array
	 */
	private byte[] retrieveCheckpoint (String appExecutionId) {
		// Vinicius
		System.out.println("Recuperando checkpoint...");
		File checkpointFile = new File (StableStorage.checkpointDir + "/" + appExecutionId + ".ckp");
		
		if (checkpointFile.exists()) {
			try{
				ObjectInputStream  input = new ObjectInputStream (new FileInputStream (checkpointFile));
				byte ckp[] = (byte []) input.readObject();
				input.close();
				
				return ckp;
			} catch (ClassNotFoundException cnfe) {
				cnfe.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
}
