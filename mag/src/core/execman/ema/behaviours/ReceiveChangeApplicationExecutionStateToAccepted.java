/*
 * Created on 20/01/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package core.execman.ema.behaviours;

import java.util.ArrayList;

import jade.content.AgentAction;
import jade.content.ContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;
import jade.proto.SimpleAchieveREResponder;
import core.execman.ema.ExecutionManagementAgent;
import core.execman.ema.ExecutionManagementAgentImpl;
import core.ontology.ExecutionInfo;
import core.ontology.RegisterExecutionAction;
import core.ontology.SetExecutionAcceptedAction;
import core.ontology.SetProcessExecutionFinishedAction;
/**
 * @author publico
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ReceiveChangeApplicationExecutionStateToAccepted extends SimpleAchieveREResponder {
	/**
	 * 
	 */
	
	private ExecutionManagementAgentImpl executionManagementAgent;  
	
	public ReceiveChangeApplicationExecutionStateToAccepted(ExecutionManagementAgentImpl ema, MessageTemplate mt) {
		super((Agent) ema, mt);
		this.executionManagementAgent = ema;
		System.out.println(" contrutor ReceiveChangeApplicationExecutionStateToAccepted");
	}
	
	protected ACLMessage prepareResponse (ACLMessage msg) throws NotUnderstoodException, RefuseException {
		ACLMessage reply = msg.createReply();
		reply.setPerformative(ACLMessage.AGREE);
		
		System.out.println("prepare Response ReceiveChangeApplicationExecutionStateToAccepted");
		
		ContentElement ce = null;
		
		try {
			ce = myAgent.getContentManager().extractContent(msg);
		} catch (Exception e) {
			throw new NotUnderstoodException("Your request was not understood");
		}
		
		if (ce instanceof AgentAction) {
	    	AgentAction a = (AgentAction) ce;
	    	
	    	if (  !(a instanceof SetExecutionAcceptedAction)  ) {
	    		throw new NotUnderstoodException("Your action request was not understood");
	    	}
	    } else {
	    	throw new NotUnderstoodException("Your request is not an action");
	    }
		
		return reply;
	}
	
	protected ACLMessage prepareResultNotification (ACLMessage request, ACLMessage response) throws FailureException {
		ACLMessage reply = request.createReply();
		reply.setPerformative(ACLMessage.INFORM);
		
		SetExecutionAcceptedAction executionAcceptedAction = null;		

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
		
		String executionId = einfo.getAppExecutionId();
		String executingHost = einfo.getExecutingHost();
		
		System.out.println("ReceiveChangeApplicationAcceptedBehaviour executionId:" + executionId);
		System.out.println("ReceiveChangeApplicationAcceptedBehaviour executingHost:" + executingHost);
		
		executionManagementAgent.appAcceptedNotification(executionId, executingHost);
		
		return reply;
	}
}
