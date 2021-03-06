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
import core.ontology.SetExecutionAcceptedAction;

import dataTypes.FileStruct;


/**
 * @author diego
 *
 */
public class ReceiveExecutionAcceptedBehaviour extends SimpleAchieveREResponder {	

	private ErmAgent ermAgent;
	
	public ReceiveExecutionAcceptedBehaviour(ErmAgent ermAgent, MessageTemplate mt) {
		super(ermAgent, mt);

		this.ermAgent = ermAgent;		

	}
	
	/* (non-Javadoc)
	 * @see jade.proto.SimpleAchieveREResponder#prepareResponse(jade.lang.acl.ACLMessage)
	 */
	protected ACLMessage prepareResponse(ACLMessage msg) throws NotUnderstoodException, RefuseException {
		ACLMessage reply = msg.createReply();
		//reply.setPerformative(ACLMessage.AGREE);
		
		ContentElement ce = null;
		
		try {
			ce = myAgent.getContentManager().extractContent(msg);
		} catch (Exception e) {
			
			//e.printStackTrace();
			throw new NotUnderstoodException("Your request was not understood");
		}
		
		if (ce instanceof AgentAction) {
	    	AgentAction a = (AgentAction) ce;
	    	
	    	if (!(a instanceof SetExecutionAcceptedAction))  {
	    	
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

		SetExecutionAcceptedAction executionAcceptedAction = null;
		
		ACLMessage reply = request.createReply();
		reply.setPerformative(ACLMessage.INFORM);
		try {
			executionAcceptedAction = (SetExecutionAcceptedAction) myAgent.getContentManager().extractContent(request);
		} catch ( CodecException e) {
			e.printStackTrace();
		} catch ( UngroundedException e1 ){
			e1.printStackTrace();
		} catch ( OntologyException e2 ){
			e2.printStackTrace();
		}
		
		
		ExecutionInfo einfo = executionAcceptedAction.getExecution();
		
		// Vinicius
//		String executionId = einfo.getAppMainRequestId() + ":" + einfo.getAppNodeRequestId();
		String executionId = einfo.getAppMainRequestId() + ":" + einfo.getAppNodeRequestId() + ":" + einfo.getAppReplicaId();
		
		String  agentHandlerIor = executionAcceptedAction.getAhIor();
		
		ArrayList <String[]> [] agentHandlerIorExecuting = ermAgent.getAgentHandlerIorExecuting();
		
		int processId = Integer.parseInt(einfo.getAppNodeRequestId());		

		
		
		synchronized (agentHandlerIorExecuting) {
				
			    ArrayList <String[]> lrmIorList = agentHandlerIorExecuting[processId];
			    lrmIorList.add(new String[]{agentHandlerIor,executionId}); // adiciona a ior do agentHandler
			    
		} 
		
		
		return reply;
	}
}
