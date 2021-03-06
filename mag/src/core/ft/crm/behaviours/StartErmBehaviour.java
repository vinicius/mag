package core.ft.crm.behaviours;

import core.ft.crm.CrmAgent;
import core.ft.erm.ErmAgent;

import java.util.ArrayList;

import java.net.UnknownHostException;

import jade.core.behaviours.OneShotBehaviour;

import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import dataTypes.ApplicationExecutionInformation;
import dataTypes.ProcessExecutionInformation;

/**
 * @author diego
 *
 */
public class StartErmBehaviour extends OneShotBehaviour {
	private CrmAgent crmAgent = null;
	private ApplicationExecutionInformation applicationExecutionInformation;
	private ArrayList<ProcessExecutionInformation[]> processExecutionInformationList;
	private String[] lrmIors;
	
	
	public StartErmBehaviour(CrmAgent crmAgent,
							 ApplicationExecutionInformation applicationExecutionInformation,
							 ArrayList processExecutionInformationList,
							 String[] lrmIors){
		super(crmAgent);
		
		this.crmAgent = crmAgent;
		this.applicationExecutionInformation = applicationExecutionInformation;
		this.processExecutionInformationList = processExecutionInformationList;
		this.lrmIors = lrmIors;		
		
		
	}	
	
	public void action() {
		
		String agentName = "";
		
		ProcessExecutionInformation[] p = processExecutionInformationList.get(0);
		

		String ids[] = p[0].executionRequestId.requestId.split(":");
//		agentName = "erm-agent:" + ids[2]; // Vinicius
		agentName = ErmAgent.ERM_AGENT_NAME + ":" + ids[2];
		System.out.println("ERMAgent in behaviour: " + agentName);
		
		Object args[] = new Object[3];
		args[0] = applicationExecutionInformation;
		args[1] = processExecutionInformationList;
		args[2] = lrmIors;
		
		try{
			

			
			// Agent creation
			AgentController agent = crmAgent.getContainerController().createNewAgent(agentName, "core.ft.erm.ErmAgent", args);
			
			if (agent ==null)
				System.out.println("O agent esta nulo");
			agent.start();
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
	}
	
}
