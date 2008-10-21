package core.ft.erm;


import jade.content.lang.leap.LEAPCodec;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.MessageTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;
import java.util.Vector;

import core.appexec.agenthandler.ThreadFailureGenerator;
import core.ft.erm.behaviours.ExecuteApplicationWithReplicasBehaviour;
import core.ft.erm.behaviours.FailureGeneratorTickerBehaviour;
import core.ft.erm.behaviours.ReceiveExecutionAcceptedBehaviour;
import core.ft.erm.behaviours.ReceiveExecutionFinishedBehaviour;
import core.ft.erm.behaviours.ReceiveInputFilesRequest;
import core.ontology.ExecutionInfo;
import core.ontology.MAGOntology;
import core.wrappers.WrapperFactory;
import dataTypes.ApplicationExecutionInformation;
import dataTypes.FileStruct;
import dataTypes.ProcessExecutionInformation;


/**
 * @author diego
 *
 */
public class ErmAgent extends Agent{
	
	public static final String ERM_AGENT_NAME = "erm-agent";
	
	private ApplicationExecutionInformation applicationExecutionInformation;
	
	private ArrayList <ProcessExecutionInformation[]> processExecutionInformationList;
	
	private ArrayList<String[]> [] agentHandlerIorExecuting = null;
	
	private ArrayList<String> lrmIorsList = new ArrayList<String>();
	
	//private ArrayList inputFiles;
	
	private String[] lrmIors;
	
	// These fields keep information about the application execution
	private String requestId = "", processId = "", asctIor = "";	
	
	// Store temporarily the arguments about the application execution	
	private Object[] args = null;
	
	private Boolean isReplicaExecutionAccepted[] = null;

	private Boolean isReplicaExecutionFinished[] = null;
	
	private String requestIdNormal;
	
	private Random rand = new Random();
	
	///-------------------- protected methods    
	/**
	 First method invoked by the mobile agent plataform on an
	 agent when it is instantiated
	 */
	protected void setup() {
		
		getContentManager().registerLanguage(new LEAPCodec());
		getContentManager().registerOntology(MAGOntology.getInstance());
		
		//args = getArguments();
		// Get arguments passed to the agent and stores them on private attributes
		collectArguments();
		for (int i = 0; i < lrmIors.length; i++) { 
			lrmIorsList.add(lrmIors[i]);
		}			
		
		addBehaviour(new ExecuteApplicationWithReplicasBehaviour(this, applicationExecutionInformation, processExecutionInformationList, lrmIors));
		
		MessageTemplate mtRequestInputFiles = MessageTemplate.and(MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST), MessageTemplate.MatchConversationId("requestInputFiles")); 
		addBehaviour(new ReceiveInputFilesRequest(this, mtRequestInputFiles)); 
		
		MessageTemplate mtSetExecutionAccepted = MessageTemplate.and(MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST), MessageTemplate.MatchConversationId("setExecutionAccepted")); 
		addBehaviour(new ReceiveExecutionAcceptedBehaviour(this, mtSetExecutionAccepted));

		MessageTemplate mtSetExecutionFinished = MessageTemplate.and(MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST), MessageTemplate.MatchConversationId("setExecutionFinished")); 
		addBehaviour(new ReceiveExecutionFinishedBehaviour(this, mtSetExecutionFinished));
		
		// addBehaviour(new FailureGeneratorTickerBehaviour(this, 10000)); // Vinicius
	}
	
	/**
	 This method is invoked before the agent's death.
	 */
	protected void takeDown() {
	}
	
	/***********************************************************************
	 * Armazena os arquivos de entrada
	 * 
	 * @param fileStructs -
	 *            Estrutura que contém os conteúdos dos arquivos
	 * @param executionId -
	 *            Identificador de execução da aplicação
	 **********************************************************************/
	public void saveInputFileStructs(FileStruct fileStructs[],
			String executionId) {
		saveFileStructs(fileStructs, executionId, "inputFiles");
	}

	
	private void saveFileStructs(FileStruct fileStructs[],
			String executionId, String type) {
		if (fileStructs != null && executionId != null && type != null) {
			for (int i = 0; i < fileStructs.length; i++) {
				try {
					IOCommonUtils.saveFile(fileStructs[i].fileName,
					fileStructs[i].file, executionId + "/" + type);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}	
	
	/***********************************************************************
	 * Requisita as estruturas com os conteúdos dos arquivos
	 * 
	 * @param filePaths -
	 *            Caminho completo dos arquivos
	 * @return Uma lista de estrutura com os coneúdos dos arquivos
	 *         requisitados
	 * @throws FileNotFoundException
	 * @throws IOException
	 **********************************************************************/
	private FileStruct[] requestFileStructs(String filePaths[])
			throws FileNotFoundException, IOException {
		FileStruct[] fileStructs = new FileStruct[filePaths.length];
		for (int i = 0; i < fileStructs.length; i++) {
			fileStructs[i] = new FileStruct();

			File file = new File(filePaths[i]);

			byte[] serializedFile = IOCommonUtils.readFile(file);
			fileStructs[i].fileName = file.getName();
			fileStructs[i].file = serializedFile;

		}// for

		return fileStructs;

	}

	
	/**
	 Get the arguments passed to the agent and saves them
	 in private attributes
	 */
	private void collectArguments() {
//		fg = new ThreadFailureGenerator(this); // Vinicius
		args = getArguments();
		if (args != null && args.length > 0) {
			applicationExecutionInformation	=  (ApplicationExecutionInformation) args[0];	  
			processExecutionInformationList =  (ArrayList <ProcessExecutionInformation[]> ) args[1];
			lrmIors = (String []) args[2];
			
			asctIor = applicationExecutionInformation.requestingAsctIor;
			//requestId = processExecutionInformationList.executionRequestId.requestId;
			//processId = processExecutionInformationList.executionRequestId.processId;             
			        
			
		}		
		
	}
	
	
	/** Manda matar todos as réplicas de um determinado processo.
	 * @param processId
	 * @param lrmIor
	 */
	public synchronized void killProcess(int processId , String agentHandlerIor){ // Vinicius on sync
		 // Manda matar todas as execuções
		ArrayList <String[]> agentHandlerIorList = agentHandlerIorExecuting[processId];
		ListIterator <String[]> list = agentHandlerIorList.listIterator();
		//System.out.println("CHAMOU AQUI!!!! :) :) Processo que terminou: " + processId);
		
                Vector ahiorsToDelete = new Vector();

	        System.out.println("agentHandlerior " + agentHandlerIor );
		
		while (list.hasNext()) {
			
			String [] agentHandlerIorNextAndExecutionId = list.next();
			
			if (! (agentHandlerIorNextAndExecutionId[0].equals(agentHandlerIor)) ) {
				System.out.println("ErmAgent killprocess agentHandlerIorNextAndExecutionId " + agentHandlerIorNextAndExecutionId[0] );
				core.wrappers.AgentHandlerWrapper agentHandler = WrapperFactory.getInstance().createAgentHandlerWrapper(agentHandlerIorNextAndExecutionId[0]);
//				agentHandler.killProcess(agentHandlerIorNextAndExecutionId[1]+":0"); // Vinicius
				agentHandler.killProcess(agentHandlerIorNextAndExecutionId[1]);

				//lrm.killProcess(agentHandlerIorNextAndExecutionId[1]);
				ahiorsToDelete.add(agentHandlerIorNextAndExecutionId); // Vinicius
			}
		}
		int size = ahiorsToDelete.size();
                for(int i = 0; i < size; i++) {
		    agentHandlerIorExecuting[processId].remove((String [])ahiorsToDelete.get(i)); // Vinicius
		}
		//agentHandlerIorExecuting = null;

	}	
	
	
	public FileStruct[] getInputFiles(ExecutionInfo einfo) {
		File inputFilesDir = new File(einfo.getAppMainRequestId() + ":"
				+ einfo.getAppNodeRequestId() + "/" + "inputFiles");		

		
		FileStruct[] inputFiles = null;

		if (inputFilesDir == null)
			return new FileStruct[0];

		String filesName[] = inputFilesDir.list();
		if (filesName == null)
			return new FileStruct[0];

		String filePaths[] = new String[filesName.length];

		for (int i = 0; i < filesName.length; i++) {
			filePaths[i] = inputFilesDir.getAbsolutePath() + "/"
					+ filesName[i];
		}

		try {
			inputFiles = requestFileStructs(filePaths);
		} catch (FileNotFoundException e) {
			System.err
					.println("ErmImpl::requestInputFiles-->>Someone asked for an inexistent file");

		} catch (IOException e) {
			System.err.println("ErmImpl::requestInputFiles-->>IOException");

		}

		return inputFiles;

	}	
	

	public String getAsctIor() {
		return asctIor;
	}

	public void setAsctIor(String asctIor) {
		this.asctIor = asctIor;
	}

	public String getAppNodeRequestId() {
		return processId;
	}

	public void setAppNodeRequestId(String processId) {
		this.processId = processId;
	}

	public String getAppMainRequestId() {
		return requestId;
	}

	public void setAppMainRequestId(String requestId) {
		this.requestId = requestId;
	}	
	
	public ArrayList getLrmIorsList() {
		return lrmIorsList;
	}
	
	public void setLrmIorsList(ArrayList lrmIorsList) {
		this.lrmIorsList = lrmIorsList;
	}	
	
	public ArrayList<String[]>[] getAgentHandlerIorExecuting() {
		return agentHandlerIorExecuting;
	}

	public void setAgentHandlerIorExecuting(
			ArrayList<String[]>[] agentHandlerIorExecuting) {
		this.agentHandlerIorExecuting = agentHandlerIorExecuting;
	}

	public Boolean[] getIsReplicaExecutionAccepted() {
		return isReplicaExecutionAccepted;
	}
	
	public void setIsReplicaExecutionAccepted(Boolean[] isReplicaExecutionAccepted) {
		this.isReplicaExecutionAccepted = isReplicaExecutionAccepted;
	}
	
	public Boolean[] getIsReplicaExecutionFinished() {
		return isReplicaExecutionFinished;
	}
	
	public void setIsReplicaExecutionFinished(Boolean[] isReplicaExecutionFinished) {
		this.isReplicaExecutionFinished = isReplicaExecutionFinished;
	}	
	
	public String getRequestIdNormal() {
		return requestIdNormal;
	}
	
	public void setRequestIdNormal(String requestIdNormal) {
		this.requestIdNormal = requestIdNormal;		
	}
	
	public void putInIsReplicaExecutionAccepted(int index,Boolean value ) {
		this.isReplicaExecutionAccepted[index] = value;
	}	
	
	public void putInIsReplicaExecutionFinished(int index,Boolean value ) {
		this.isReplicaExecutionFinished[index] = value;
	}

	/**
	 * Vinicius simulating errors
	 *
	 */
	public void generateRandomError() {
		if (agentHandlerIorExecuting[0].size() != 0) {
			ArrayList<String[]> agentHandlerIorList = agentHandlerIorExecuting[0];
			int count = agentHandlerIorList.size();
			String[] agentHandlerIorNextAndExecutionId = agentHandlerIorList
					.get(rand.nextInt(count));
			core.wrappers.AgentHandlerWrapper agentHandler = WrapperFactory
					.getInstance().createAgentHandlerWrapper(
							agentHandlerIorNextAndExecutionId[0]);
			agentHandler.deleteAllAgents();
		}
	}
	
/*
 * public ArrayList getInputFiles() { return inputFiles; }
 * 
 * public void setInputFiles(ArrayList inputFiles) { this.inputFiles =
 * inputFiles; }
 */		
	
}
