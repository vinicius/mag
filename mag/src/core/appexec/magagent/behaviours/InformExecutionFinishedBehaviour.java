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
import jade.core.AID;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.SimpleAchieveREInitiator;
import core.appexec.magagent.MagAgent;
import core.ontology.ExecutionInfo;
import core.ontology.MAGOntology;
import core.ontology.SetProcessExecutionFinishedAction;



/**
 * @author diego
 *
 */
public class InformExecutionFinishedBehaviour extends SimpleAchieveREInitiator {
	
	//private String ERM_AGENT_NAME; 
	
	private AID ermAID = null;	
	
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
	public InformExecutionFinishedBehaviour(MagAgent magAgent, ACLMessage msg, ExecutionInfo einfo){
		super(magAgent, msg);
		this.einfo = einfo;
		this.magAgent = magAgent;
		//System.out.print("einfo requestid: " + einfo.getAppMainRequestId());
		//ERM_AGENT_NAME = "erm-agent:"+ magAgent.getAppMainRequestId(); 
		
		System.out.println("Comportamento InformExecutionFinished " + magAgent.getErmAID().getName());
		
	}
	
	/* (non-Javadoc)
	 * @see jade.proto.SimpleAchieveREInitiator#prepareRequest(jade.lang.acl.ACLMessage)
	 */
	/* (non-Javadoc)
	 * @see jade.proto.SimpleAchieveREInitiator#prepareRequest(jade.lang.acl.ACLMessage)
	 */
	protected ACLMessage prepareRequest(ACLMessage msg) {
		
		msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);		
		msg.setConversationId("setExecutionFinished");
		msg.setLanguage(codec.getName()); 
		msg.setOntology(ontology.getName());
		msg.setSender(magAgent.getAID());
		msg.addReceiver(magAgent.getErmAID());		
		
		SetProcessExecutionFinishedAction setProcessExecutionFinishedAction = new SetProcessExecutionFinishedAction();
		
		System.out.println("SetExecutionFinishedAction einfo" + einfo);
		
		setProcessExecutionFinishedAction.setExecution(einfo);
		
		System.out.println("InformExecutionFinishedBehaviour IOR:" + magAgent.getAhIor() );
		
		setProcessExecutionFinishedAction.setAhIor(magAgent.getAhIor());
	
		try {		
			myAgent.getContentManager().fillContent(msg, setProcessExecutionFinishedAction);
			//myAgent.getContentManager().fillContent(msg, setExecutionAcceptedAction);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return msg;
	}
	
	protected void handleAgree(ACLMessage msg) {
		System.out.println("Mensagem AGREE InformExecutionFinishedBehaviour recebida de: " + msg.getSender());
	}	
	
	protected void handleNotUnderstood(ACLMessage msg) {
		System.out.println("The action request was not understood InformExecutionFinishedBehaviour.  Message sent by: " + msg.getSender());
	}		
	
		
	protected void handleInform(ACLMessage msg) {
		
		System.out.println("Entrou no handleInform do InformExecutionFinished");
		System.out.println("Communication ok InformExecutionFinished . Message sent by: " + msg.getSender());
		
		confirmMsgReceive = true;
		
		// Vinicius based on mag-diego
		magAgent.setErmNotified(true);
//		magAgent.terminateAgent();
		
	}
	
    protected void handleFailure(ACLMessage msg) {
    	System.err.println("Communication failed InformExecutionFinished . Message sent by: " + msg.getSender());
    	
    	confirmMsgReceive = true;
    }
    
	public boolean done() {
		return confirmMsgReceive;
	}
}
