package core.ft.agentrecover;

import jade.content.lang.Codec;
import jade.content.lang.leap.LEAPCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;
import core.ft.agentrecover.behaviours.RequestAppsSchedulingBehaviour;
import core.ontology.ExecutionInfo;
import core.ontology.MAGOntology;
import core.wrappers.GrmWrapper;
import core.wrappers.WrapperFactory;

import dataTypes.ApplicationExecutionInformation;
import dataTypes.ProcessExecutionInformation;


/**
 Class AgentRecoverImpl - Implementation of AgentRecoverImpl
 
 The AgentRecoverImpl it acts as a stationary agent who is 
 responsible for restore failed applications  
 
 @author Bysmarck Barros de Sousa
 
 */
public class AgentRecoverImpl extends Agent implements AgentRecover {
	private static final long serialVersionUID = 696297918100815462L;
	
	private GrmWrapper grm;
	private ExecutionInfo[] executionInfo;
	
	private Codec codec = new LEAPCodec();
	private Ontology ontology = MAGOntology.getInstance();
	
	private String [] containers = null;
	
	private ApplicationExecutionInformation applicationExecutionInformation = null;
	private ProcessExecutionInformation processExecutionInformation = null;
	
	
	protected void setup(){
		try {
			getContentManager().registerLanguage (codec);
			getContentManager().registerOntology (ontology);
			
			grm = WrapperFactory.getInstance().createGrmWrapper();
			
			Object [] args = getArguments();
			if (args != null && args.length > 0) {
				this.containers= (String []) args[0];
				this.applicationExecutionInformation = (ApplicationExecutionInformation) args[1];
				this.processExecutionInformation = (ProcessExecutionInformation) args[2];
			}
			
			//this.setExecutionInfoArray ((ExecutionInfo[])getArguments());
			
	        //addBehaviour(new RequestAppsSchedulingBehaviour (this, executionInfo, grm));
	        addBehaviour(new RequestAppsSchedulingBehaviour (this, containers, grm, applicationExecutionInformation, processExecutionInformation));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}    
	
	/**
	 * Set agent list
	 * @param ApplicationInfo Array whit agents for restore
	 */
	public void setExecutionInfoArray(ExecutionInfo[] executionInfo) {
		this.executionInfo = executionInfo;
	}
	
	/**
	 * Return agent list
	 * @return ApplicationInfo The array of agents
	 */
	public ExecutionInfo[] getExecutionInfoArray() {
		return this.executionInfo;
	}
}

