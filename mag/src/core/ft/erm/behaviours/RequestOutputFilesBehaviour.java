/*
 * Created on 26/01/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package core.ft.erm.behaviours;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import jade.util.leap.ArrayList;
import java.util.Iterator;

import jade.content.lang.Codec;
import jade.content.lang.leap.LEAPCodec;
import jade.content.onto.Ontology;

import jade.core.AID;
import jade.domain.FIPANames;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import jade.proto.SimpleAchieveREInitiator;

import core.ft.erm.ErmAgent;
import core.ontology.ExecutionInfo;
import core.ontology.OutputFile;
import core.ontology.MAGOntology;
import core.ontology.RequestOutputFilesAction;
//import core.ontology.RegisterExecutionAction;

import dataTypes.FileStruct;

//import core.ontology.RequestInputFilesAction;

/**
 * @author diego
 *
 */
public class RequestOutputFilesBehaviour extends SimpleAchieveREInitiator {
	
	//private String ERM_AGENT_NAME; 
	
	private AID ermAID = null;	
	
	private ErmAgent ermAgent = null;
	private ExecutionInfo einfo = null;
	private boolean confirmMsgReceive = false;
	
	private Codec codec = new LEAPCodec();
	private Ontology ontology = MAGOntology.getInstance();
	
	/**
	 * @param magAgent
	 * @param msg
	 * @param einfo
	 */
	public RequestOutputFilesBehaviour(ErmAgent ermAgent, ACLMessage msg, ExecutionInfo einfo) {
		super(ermAgent, msg);
		this.einfo = einfo;
		this.ermAgent = ermAgent;
		
		System.out.println("Construtor de RequestOutputFilesBehaviour");
		
	}
	
	/* (non-Javadoc)
	 * @see jade.proto.SimpleAchieveREInitiator#prepareRequest(jade.lang.acl.ACLMessage)
	 */
	protected ACLMessage prepareRequest(ACLMessage msg) {
		
		System.out.println("prepareRequest de RequestOutputFilesBehaviour");
		
		msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);		
		msg.setConversationId("requestOutputFiles");
		msg.setLanguage(codec.getName()); 
		msg.setOntology(ontology.getName());
		
		RequestOutputFilesAction requestOutputFilesAction = new RequestOutputFilesAction(); 
		requestOutputFilesAction.setExecution(einfo); // Vinicius discommented
		
		try {
			myAgent.getContentManager().fillContent(msg, requestOutputFilesAction );
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		return msg;
	}
	
	protected void handleAgree(ACLMessage msg) {
		System.out.println("Mensagem AGREE recebida de: " + msg.getSender());
	}	
	
	protected void handleNotUnderstood(ACLMessage msg) {
		System.out.println("The action request was not understood. Message sent by: " + msg.getSender());
	}		
	
		
	protected void handleInform(ACLMessage msg) {
		
		ArrayList outputFiles = null;		
		
		try {
			System.out.println("here...");
			outputFiles = (ArrayList) msg.getContentObject();
		} catch (UnreadableException e) {
			e.printStackTrace();
		}		

		Iterator < OutputFile > i = outputFiles.iterator();
		
		while (i.hasNext()) {
			
			OutputFile iFile = i.next();
			
			try {
//				FileOutputStream fos = new FileOutputStream("./mag/classes/" + einfo.getAppExecutionId() + "/" + iFile.getFileName());
				FileOutputStream fos = new FileOutputStream("/home/pos/vinicius/integrade-mag/mag/classes/" + einfo.getAppExecutionId() + "/" + iFile.getFileName());
				fos.write(iFile.getContent());
				fos.close();				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			//magAgent.getInputFiles().add(iFile);
		}	
	
		

		
		
		
		System.out.println ("Communication ok . Message sent by: " + msg.getSender());
		confirmMsgReceive = true;
	}
	
    protected void handleFailure(ACLMessage msg) {
    	System.err.println ("Communication failed . Message sent by: " + msg.getSender());
    	
    	confirmMsgReceive = true;
    }
    
	public boolean done() {
		return confirmMsgReceive;
	}
}
