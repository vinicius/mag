/*
 * Created on 28/01/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package core.appexec.magagent.behaviours;

import jade.core.behaviours.OneShotBehaviour;
import core.appexec.agenthandler.AgentHandlerImpl;
import core.appexec.magagent.MagAgent;

/**
 * @author rafaelf
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FinishApplicationBehaviour extends OneShotBehaviour {
	private MagAgent magAgent = null;
	
	public FinishApplicationBehaviour(MagAgent magAgent) {
		super(magAgent);
		
		this.magAgent = magAgent;
	}

	public void action() {
		// removes the association among the application and its output files
		magAgent.disableRedirectOutput(magAgent.getApplication());
		
		// if exists an application...
		if (magAgent.getApplication() != null) {
			// if the application was stopped, inform the AgentHandler
			if (!magAgent.getApplication().isAlive()) {
				AgentHandlerImpl.getInstance().appFinished(magAgent.getAppExecutionId());
			} else {
				// if the agent died but the application is running
				// then, a kill was invoked and the application must be stopped
				magAgent.getApplication().stop();
				magAgent.setApplication(null);
			}
		} else {
			// if there isn't application to execute then,
			// inform the AgentHandler
			AgentHandlerImpl.getInstance().appFinished(magAgent.getAppExecutionId());			
		}
		System.out.println("FinishApllication");
		magAgent.doDelete();
	}
}
