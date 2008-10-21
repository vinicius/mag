/*
 * Created on 26/01/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package core.appexec.magagent.behaviours;

import jade.lang.acl.ACLMessage;
import jade.proto.SimpleAchieveREInitiator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPOutputStream;

import core.appexec.magagent.MagAgent;
import core.ontology.Checkpoint;
import core.ontology.SaveCheckpointAction;


/**
 * @author rafaelf
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SaveCheckpointBehaviour extends SimpleAchieveREInitiator {
	private static final long serialVersionUID = -1439040289568846281L;
	
	private MagAgent magAgent = null;
	private String appExecutionId = "";
	private transient Object checkpoint = null;
	
	private boolean confirmMsgReceive = false;
	
	public SaveCheckpointBehaviour (MagAgent magAgent, ACLMessage msg, String appExecutionId, Object checkpoint) {
		super(magAgent, msg);
		
		this.magAgent = magAgent;
		this.appExecutionId = appExecutionId;
		this.checkpoint = checkpoint;
	}
	
	protected ACLMessage prepareRequest (ACLMessage msg) {
		SaveCheckpointAction saveCheckpointAction = new SaveCheckpointAction();
		saveCheckpointAction.setExecId (this.appExecutionId);
		
		try {
			if (checkpoint != null) {
				Checkpoint c = new Checkpoint();
				c.setContent (compressCheckpoint (checkpoint));
				
				saveCheckpointAction.setCheckpoint (c);
				
				magAgent.getContentManager().fillContent(msg, saveCheckpointAction);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return msg;
	}
	
	protected void handleInform (ACLMessage msg) {
		confirmMsgReceive = true;
	}
	
    protected void handleFailure (ACLMessage msg) {
    	System.err.println ("Communication failed. Message sent by: " + msg.getSender());
    	
    	confirmMsgReceive = true;
    }
    
	public boolean done() {
		if (confirmMsgReceive) {
			magAgent.addBehaviour (new CheckpointCollectBehaviour (magAgent));
		}
		
		return confirmMsgReceive;
	}
	
	private byte[] compressCheckpoint (Object ckp) throws IOException {
		if (ckp != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream ();
			ObjectOutputStream oos = new ObjectOutputStream (new GZIPOutputStream (baos));
			
			oos.writeObject (ckp);
			oos.close();
			
			byte [] compressedCkp = baos.toByteArray();
			baos.close();
			
			return compressedCkp;
		}
		
		return (new byte[0]);
	}
}
