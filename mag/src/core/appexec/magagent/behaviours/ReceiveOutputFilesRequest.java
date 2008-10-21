/*
 * Created on 20/01/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package core.appexec.magagent.behaviours;

import jade.content.AgentAction;
import jade.content.ContentElement;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.SimpleAchieveREResponder;
import jade.util.leap.ArrayList;

import java.io.File;
import java.io.FileInputStream;

import core.appexec.magagent.MagAgent;
import core.ontology.OutputFile;
import core.ontology.RequestOutputFilesAction;


/**
 * @author diego
 *
 */
public class ReceiveOutputFilesRequest extends SimpleAchieveREResponder {	

	private MagAgent magAgent;
	
	public ReceiveOutputFilesRequest(MagAgent magAgent, MessageTemplate mt) {
		super(magAgent, mt);

		this.magAgent = magAgent;
		
		System.out.println("Construtor de ReceiveOutputFilesRequest");

	}
	
	/* (non-Javadoc)
	 * @see jade.proto.SimpleAchieveREResponder#prepareResponse(jade.lang.acl.ACLMessage)
	 */
	protected ACLMessage prepareResponse(ACLMessage msg) throws NotUnderstoodException, RefuseException {
		
		System.out.println("prepareResponse de ReceiveOutputFilesRequest");
		ACLMessage reply = msg.createReply();
		
		ContentElement ce = null;
		
		try {
			ce = myAgent.getContentManager().extractContent (msg);

		} catch (Exception e) {


			throw new NotUnderstoodException("Your request was not understood");
		}
		
		if (ce instanceof AgentAction) {
	    	AgentAction a = (AgentAction) ce;
	    	
	    	if (!(a instanceof RequestOutputFilesAction)) {	
	    		throw new NotUnderstoodException("Your action request was not understood");
	    	} else {	    		
	    		reply.setPerformative(ACLMessage.AGREE);
	    	}	    	
	    } else {

	    	throw new NotUnderstoodException("Your request is not an action");
	    }
		
		return reply;
	}		
	
	/* (non-Javadoc)
	 * @see jade.proto.SimpleAchieveREResponder#prepareResultNotification(jade.lang.acl.ACLMessage, jade.lang.acl.ACLMessage)
	 */
	protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException  {
		ArrayList  outputFiles;
		//OutputFile file = null;
		
		ACLMessage reply = request.createReply();
		reply.setPerformative(ACLMessage.INFORM);
		
	    try {
	    	System.out.println("Entrou no prepareResult");
			for (int i = 0; i < magAgent.getOutputFiles().size(); i++) {
				System.out.println("    getOutputFiles...");
				// Vinicius
				File file = new File(magAgent.getAppExecutionId() + "/" + ((OutputFile) magAgent.getOutputFiles().get(i)).getFileName() );
//				File file = new File("/home/pos/vinicius/integrade-mag/resourceProviders/lrm/" + magAgent.getAppOutputDirectory() + "/" + ((OutputFile) magAgent.getOutputFiles().get(i)).getFileName() );
				
				if (file.exists()) {
					FileInputStream fis = new FileInputStream(file);
					byte code [] = new byte[(int)file.length()];
					fis.read(code);
					fis.close();
					System.out.println("    arquivo existe e é: " + ((OutputFile) magAgent.getOutputFiles().get(i)).getFileName());
					((OutputFile) magAgent.getOutputFiles().get(i)).setContent(code);
				}	
			}
			reply.setContentObject(magAgent.getOutputFiles());
		} catch (Exception e) {
			
		}
		
		
		
		
		magAgent.terminateAgent();
		
		return reply;
	}
}
