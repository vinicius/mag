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
import jade.proto.SimpleAchieveREResponder;
import core.execman.ema.ExecutionManagementAgent;
import core.execman.ema.ExecutionManagementAgentImpl;
import core.ontology.ExecutionInfo;
import core.ontology.RegisterExecutionAction;
/**
 * @author publico
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ReceiveRecoverReplicaRequest extends AchieveREResponder {
	/**
	 * 
	 */
	
	private ExecutionManagementAgentImpl executionManagementAgent;  
	
	public ReceiveRecoverReplicaRequest (ExecutionManagementAgentImpl ema, MessageTemplate mt) {
		super((Agent) ema, mt);
		this.executionManagementAgent = ema;
	}
	
	protected ACLMessage prepareResponse (ACLMessage msg) throws NotUnderstoodException, RefuseException {
		ACLMessage reply = msg.createReply();
		reply.setPerformative(ACLMessage.AGREE);
		
		ContentElement ce = null;
		
		try {
			ce = myAgent.getContentManager().extractContent(msg);
		} catch (Exception e) {
			throw new NotUnderstoodException("Your request was not understood");
		}
		
		if (ce instanceof AgentAction) {
	    	AgentAction a = (AgentAction) ce;
	    	
	    	if ( !(a instanceof RegisterExecutionAction) ) {
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
		
		try {
		    RegisterExecutionAction registerExecutionAction = (RegisterExecutionAction) myAgent.getContentManager().extractContent (request);
		    ExecutionInfo einfo = registerExecutionAction.getExecution();
		    
		    String [] containers = executionManagementAgent.getReplicasLocations(einfo.getAppMainRequestId(), einfo.getAppNodeRequestId(), einfo.getAppReplicaId() );
		    
		    reply.setContentObject(containers); 
		    	
		    
		} catch (Exception e) {
			throw new FailureException ("An error occurred when your request was being processed");
		}
		
		return reply;
	}
}
