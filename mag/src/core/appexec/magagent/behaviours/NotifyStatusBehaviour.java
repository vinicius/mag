package core.appexec.magagent.behaviours;

import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import core.appexec.magagent.MagAgent;
import core.application.MagApplication;
import core.ontology.ExecutionInfo;



/**
 Class NotifyStatusBehaviour - A MagAgent behaviour
 
 Represents the MagAgent behaviour responsible for
 monitor the application execution.
 Kills the agent when the application terminates
 
 @author Rafael Fernandes Lopes
 */
public class NotifyStatusBehaviour extends TickerBehaviour {
	private static final long serialVersionUID = 6990766190931255565L;
	
	private static final int SLEEP_TIME = 200; //< time between two checks
	private MagAgent magAgent = null;
	private transient MagApplication application = null;
	private ExecutionInfo executionInfo = null;	
	
	public NotifyStatusBehaviour (MagAgent magAgent, MagApplication m) {
		super (magAgent, SLEEP_TIME);
		
		application = m;
		this.magAgent = magAgent;
	}
	
	// If application is not null and is not 'running', then
	// it is not running any computations... So, kills the agent
	protected void onTick() {
		
		ACLMessage message = null;
		ACLMessage finishedExecutionMessage = null;
		
		if (application != null && !application.isAlive()) {
			
			
			message = new ACLMessage(ACLMessage.REQUEST);
			executionInfo = magAgent.getExecutionInfo();
		    magAgent.addBehaviour(new ChangeProcessExecutionStateToFinishedBehaviour(magAgent, message, executionInfo));
		    
			if (Integer.parseInt(magAgent.getNumberOfReplicas()) > 0) {	
				finishedExecutionMessage = new ACLMessage(ACLMessage.REQUEST);					
				magAgent.addBehaviour(new InformExecutionFinishedBehaviour(magAgent, finishedExecutionMessage, executionInfo));
				
			}
	        
	        this.stop();
		}
	}
}
