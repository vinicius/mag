package core.ft.stablestorage;

import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.leap.LEAPCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;
import core.ft.stablestorage.behaviours.ReceiveQueryCheckpoint;
import core.ft.stablestorage.behaviours.ReceiveSaveCheckpointRequest;
import core.ontology.MAGOntology;

import java.io.File;

/**
 Class StableStorageImpl - Implementation of StableStorage
 
 The StableStorageImpl it acts as a stationary agent who is 
 responsible for storing checkpoints of the applications in 
 execution in the grating.  It stores checkpoints supplied by 
 each requested <i>MagAgent</i> and also he returns checkpoints for a 
 <i>MagAgent</i> for one determined application.
 
 @author Bysmarck Barros de Sousa
 
 */

public class StableStorage extends Agent {
	private static final long serialVersionUID = 5877215340119484232L;
	

	public static final String STABLE_STORAGE_NAME = "stable-storage";
	//public static String checkpointDir = "mag/classes/core/ft/stablestorage/checkpoints"; // Vinicius
	public static final String checkpointDir = "/home/pos/vinicius/integrade-mag/mag/classes/core/ft/stablestorage/checkpoints";
	
	private ContentManager manager = getContentManager();
	private Codec codec = new LEAPCodec();
	private Ontology ontology = MAGOntology.getInstance();
	
	protected void setup() {
		manager.registerLanguage(codec);
		manager.registerOntology(ontology);
		
		
		MessageTemplate mtSaveCheckpoint = AchieveREResponder.createMessageTemplate (FIPANames.InteractionProtocol.FIPA_REQUEST);
		mtSaveCheckpoint = MessageTemplate.and(mtSaveCheckpoint, MessageTemplate.MatchConversationId ("saveCheckpoint"));
		addBehaviour(new ReceiveSaveCheckpointRequest (this, mtSaveCheckpoint));
		
		MessageTemplate mtQueryCheckpoint = AchieveREResponder.createMessageTemplate (FIPANames.InteractionProtocol.FIPA_QUERY);
		mtQueryCheckpoint = MessageTemplate.and(mtQueryCheckpoint, MessageTemplate.MatchConversationId ("queryCheckpoint"));
		addBehaviour(new ReceiveQueryCheckpoint (this, mtQueryCheckpoint));
	}
			
//	/**
//	 * This method removes checkpoint information from the application whose Id of 
//	 * execution is <b>appExecutionId</b>
//	 * 
//	 * @param appExecutionId The Id execution 
//	 * 
//	 * @return boolean If true, success, otherwise, false
//	 */
//	public boolean removeCheckpoint(String appExecutionId) {
//		File checkpointFile = new File (checkpointDir + "/" + appExecutionId + ".ckp");
//		
//		if(checkpointFile.exists()){
//			checkpointFile.delete();
//			return true;
//		} else {
//			return false;
//		}
//	}
}
