/*
 * Created on 20/01/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package core.ft.erm.behaviours;

import jade.content.AgentAction;
import jade.content.ContentElement;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.SimpleAchieveREResponder;

import java.util.ArrayList;

import core.ft.erm.ErmAgent;
import core.ontology.ExecutionInfo;
import core.ontology.InputFile;
import core.ontology.RequestInputFilesAction;

import dataTypes.FileStruct;


/**
 * @author diego
 *
 */
public class ReceiveInputFilesRequest extends SimpleAchieveREResponder {	

	private ErmAgent ermAgent;
	
	public ReceiveInputFilesRequest(ErmAgent ermAgent, MessageTemplate mt) {
		super(ermAgent, mt);

		this.ermAgent = ermAgent;		

	}
	
	/* (non-Javadoc)
	 * @see jade.proto.SimpleAchieveREResponder#prepareResponse(jade.lang.acl.ACLMessage)
	 */
	protected ACLMessage prepareResponse(ACLMessage msg) throws NotUnderstoodException, RefuseException {
		ACLMessage reply = msg.createReply();
		
		ContentElement ce = null;
		
		try {
			ce = myAgent.getContentManager().extractContent (msg);

		} catch (Exception e) {


			throw new NotUnderstoodException("Your request was not understood");
		}
		
		if (ce instanceof AgentAction) {
	    	AgentAction a = (AgentAction) ce;
	    	
	    	if (!(a instanceof RequestInputFilesAction)) {	
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
		ArrayList < InputFile > inputFiles;
		InputFile file = null;
		
		ACLMessage reply = request.createReply();
		reply.setPerformative(ACLMessage.INFORM);
		

		
		
		try {

		    RequestInputFilesAction requestInputFilesAction = (RequestInputFilesAction) myAgent.getContentManager().extractContent(request);
		    
		    ExecutionInfo einfo = requestInputFilesAction.getExecution();
		    
		    FileStruct [] iFiles = ermAgent.getInputFiles(einfo);
		    
		    inputFiles = new ArrayList < InputFile > ();
		    
		    for (int i = 0; i < iFiles.length; i++) {	
		    	
		    	file = new InputFile();
			
		    	file.setFileName(iFiles[i].fileName);		    	

		    	file.setContent(iFiles[i].file);
		    	
		    	inputFiles.add(file);
			
			
		    }		    
		    
		    reply.setContentObject(inputFiles);  
		    		

		} catch (Exception e) {
			e.printStackTrace();
			throw new FailureException("An error occurred when your request was being processed");
		}
		
		return reply;
	}
}
