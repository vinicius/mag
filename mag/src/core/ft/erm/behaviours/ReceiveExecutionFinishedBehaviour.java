/*
 * Created on 20/01/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package core.ft.erm.behaviours;

import java.util.ArrayList;

import jade.content.AgentAction;
import jade.content.ContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.SimpleAchieveREResponder;



import core.ft.erm.ErmAgent;
import core.ontology.ExecutionInfo;
import core.ontology.SetProcessExecutionFinishedAction;

import dataTypes.FileStruct;


/**
 * @author diego
 *
 */
public class ReceiveExecutionFinishedBehaviour extends SimpleAchieveREResponder {	

	private ErmAgent ermAgent;
	
	public ReceiveExecutionFinishedBehaviour(ErmAgent ermAgent, MessageTemplate mt) {
		super(ermAgent, mt);
		this.ermAgent = ermAgent;
		
		System.out.println("Entrou no ReceiveExecutionFinishedBehaviour");
	}
	
	/* (non-Javadoc)
	 * @see jade.proto.SimpleAchieveREResponder#prepareResponse(jade.lang.acl.ACLMessage)
	 */
	protected ACLMessage prepareResponse(ACLMessage msg) throws NotUnderstoodException, RefuseException {
		ACLMessage reply = msg.createReply();
		//reply.setPerformative(ACLMessage.AGREE);
		
		ContentElement ce = null;
		
		System.out.println("prepareResponse ReceiveExecutionFinishedBehaviour");
		
		try {
			ce = myAgent.getContentManager().extractContent(msg);
		} catch (Exception e) {
			
			//e.printStackTrace();
			throw new NotUnderstoodException("Your request was not understood");
		}
		
		if (ce instanceof AgentAction) {
	    	AgentAction a = (AgentAction) ce;
	    	
	    	if (!(a instanceof SetProcessExecutionFinishedAction)) {
	    	
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
		
		SetProcessExecutionFinishedAction executionFinishedAction = null;
		
		ACLMessage reply = request.createReply();
		reply.setPerformative(ACLMessage.INFORM);
		
		try {
			executionFinishedAction = (SetProcessExecutionFinishedAction) myAgent.getContentManager().extractContent(request);
		} catch (CodecException e) {
			e.printStackTrace();
		} catch (UngroundedException e1) {
			e1.printStackTrace();
		} catch (OntologyException e2) {
			e2.printStackTrace();
		}
		
		ExecutionInfo einfo = executionFinishedAction.getExecution();
		
		String  agentHandlerIor = executionFinishedAction.getAhIor();
		
		int processId = Integer.parseInt(einfo.getAppNodeRequestId());
		
		ermAgent.killProcess(processId, agentHandlerIor);
		
		// Vinicius based on diego-mag {
   	    ACLMessage requestOutputFileMsg = request.createReply();
        requestOutputFileMsg.setPerformative(ACLMessage.REQUEST);

        ermAgent.addBehaviour(new RequestOutputFilesBehaviour(ermAgent, requestOutputFileMsg, einfo));
//      } Vinicius
		
		return reply;
	}
}
