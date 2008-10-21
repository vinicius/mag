package core.appexec.magagent.behaviours;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.util.Event;


/**
 * @author Gilberto Cunha Filho
 * Created on 13/02/2006
 */
//public class ReceiveMessageMABehaviour extends SimpleAchieveREInitiator {
public class ReceiveMessageMABehaviour extends OneShotBehaviour{
	private Event ev;
	private boolean eventThrown = false;
	private ClassLoader classLoader = null;
	
	private Agent agent;
	private ACLMessage msgReceiver;
	
	private ACLMessage tmpmsg;

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ReceiveMessageMABehaviour (Agent agent,ACLMessage msg,Event ev) {
		//super(agent, msg);
		super(agent);
		this.agent = agent;
		this.ev = ev;
		this.tmpmsg = msg;

	}
	
	public void action(){
		agent.send(tmpmsg);
		msgReceiver = agent.blockingReceive();
		//System.out.println("ENTROU NO HAND-INFORM");
		try {
			String resul = msgReceiver.getContent();
			//System.out.println("RESUL->"+resul);
			ev.notifyProcessed (resul);
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println("ENTROU NO HAND-INFORM-TRY");
			ev.notifyProcessed (null);
		}
	}
	

}
