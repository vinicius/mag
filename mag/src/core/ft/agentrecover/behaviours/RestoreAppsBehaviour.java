package core.ft.agentrecover.behaviours;

import jade.core.behaviours.OneShotBehaviour;
import jade.util.leap.List;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

//import subtypes.ApplicationType;
import dataTypes.ExecutionRequestId;
import dataTypes.ApplicationExecutionInformation;
import dataTypes.ProcessExecutionInformation;
import dataTypes.ApplicationType;
import core.ontology.ExecutionInfo;
import core.ontology.OutputFile;
import core.wrappers.AgentHandlerWrapper;
import core.wrappers.WrapperFactory;


/**
 * @author publico
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RestoreAppsBehaviour extends OneShotBehaviour {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7551218455583894131L;
	private ExecutionInfo executionInfo[];
	// Vinicius
//	private String locations[];
	private String location = null;
	
	
	private ApplicationExecutionInformation applicationExecutionInformation;
	private ProcessExecutionInformation processExecutionInformation;
	
	public RestoreAppsBehaviour(/*ExecutionInfo executionInfo[],*/ String location, ApplicationExecutionInformation applicationExecutionInformation, ProcessExecutionInformation processExecutionInformation  ) {
		//this.executionInfo = executionInfo;
		// Vinicius {
		this.location = location;
		// } Vinicius
		this.applicationExecutionInformation = applicationExecutionInformation;
		this.processExecutionInformation = processExecutionInformation;
	}

	/*
	 * The localizations of the agents had been gotten in invoking the restoreLocationRequest method in
	 * setup, when the agent is instanciado by the AgentMap.  
	 */
	public void action() {
		
		//for (int i = 0; i < this.executionInfo.length; i++) {
		//	if (!this.locations[i].equals ("")) {
				AgentHandlerWrapper ah = WrapperFactory.getInstance().createAgentHandlerWrapper(location);
				
				/*ApplicationExecutionInformation applicationExecutionInformation = new ApplicationExecutionInformation();
				applicationExecutionInformation.applicationName = executionInfo[i].getAppName();				
				applicationExecutionInformation.applicationType = ApplicationType.regular;
				applicationExecutionInformation.requestingAsctIor = executionInfo[i].getAsctIor();
				applicationExecutionInformation.applicationRepositoryIor = executionInfo[i].getAppReposId();
				applicationExecutionInformation.applicationConstraints = executionInfo[i].getAppConstraints();
				applicationExecutionInformation.applicationPreferences = executionInfo[i].getAppPreferences();
				applicationExecutionInformation.applicationPreferences = executionInfo[i].getAppPreferences();
				
				applicationExecutionInformation.numberOfReplicas = executionInfo[i].getNumberOfReplicas();
				
				//applicationExecutionInformation.deniedExecution = "";
				
				ProcessExecutionInformation processExecutionInformation = new ProcessExecutionInformation();

				ExecutionRequestId executionRequestId = new ExecutionRequestId();
				executionRequestId.requestId = executionInfo[i].getAppMainRequestId();
				executionRequestId.processId = executionInfo[i].getAppNodeRequestId();
				executionRequestId.replicaId = executionInfo[i].getAppReplicaId();
				
				
				processExecutionInformation.executionRequestId = executionRequestId;
				
				processExecutionInformation.processArguments = executionInfo[i].getAppArgs();				
				
				List oFiles = executionInfo[i].getOutputFiles();
				String[] outputFiles = new String[oFiles.size()];
				
				for (int j = 0; j < oFiles.size(); j++) {
					OutputFile file = (OutputFile) oFiles.get(j);
					outputFiles [j] = file.getFileName();
				}
				
				processExecutionInformation.outputFileNames = outputFiles;
				
				// invoca um metodo do AgentHandler para recuperar o Agent*/
				ah.restoreExecution (applicationExecutionInformation, processExecutionInformation);
				//ah.requestExecution(applicationExecutionInformation, processExecutionInformation);
	//		}
	//	}
		
		myAgent.doDelete(); // Vinicius
	}
	
	/**
	 * Return the ior of the AgentHandler
	 * @param file File where is the ior's AgentHandler
	 * @return String The ior's AgentHandler
	 */
	private String getAhIor(String file){
		try{
			BufferedReader br = new BufferedReader (new InputStreamReader (new FileInputStream("ahiors/" + file + ".ior")));
			
			String ahIor = br.readLine();    
			br.close();
			
			return ahIor;
		}catch(FileNotFoundException fne){
			fne.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return null;
	}

}