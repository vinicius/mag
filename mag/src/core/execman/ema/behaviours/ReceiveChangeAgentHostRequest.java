/*
 * Created on 20/01/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package core.execman.ema.behaviours;

import jade.content.AgentAction;
import jade.content.ContentElement;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;
import core.execman.ema.ExecutionManagementAgent;
import core.execman.ema.ExecutionManagementAgentImpl;
import core.ontology.ChangeExecutionLocationAction;

/**
 * @author publico
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ReceiveChangeAgentHostRequest extends AchieveREResponder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2019613422085070431L;
	private ExecutionManagementAgentImpl executionManagementAgent;
	
	public ReceiveChangeAgentHostRequest (ExecutionManagementAgentImpl ema, MessageTemplate mt) {
		super((Agent) ema, mt);
		this.executionManagementAgent = ema;
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
	    	
	    	if ( !(a instanceof ChangeExecutionLocationAction) ) {
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
		
		ChangeExecutionLocationAction changeExecutionLocationAction = null;		

		
		return reply;
	}			

}
