package core.appexec.magagent;

import jade.content.lang.Codec;
import jade.content.lang.leap.LEAPCodec;
import jade.content.onto.Ontology;
import jade.core.AID;
import jade.core.Agent;
import jade.core.ContainerID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.ArrayList;
import jade.wrapper.AgentController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;

import core.appexec.agenthandler.AgentHandlerImpl;
import core.appexec.agenthandler.ThreadAwareOutputStream;
import core.appexec.magagent.behaviours.ChangeExecutionLocationBehaviour;
import core.appexec.magagent.behaviours.ChangeProcessExecutionStateToAcceptedBehaviour;
import core.appexec.magagent.behaviours.ExecuteApplicationBehaviour;
import core.appexec.magagent.behaviours.FinishApplicationBehaviour;
import core.appexec.magagent.behaviours.InformExecutionAcceptedBehaviour;
import core.appexec.magagent.behaviours.MigrationFinishedBehaviour;
import core.appexec.magagent.behaviours.QueryCheckpointBehaviour;
import core.appexec.magagent.behaviours.ReceiveOutputFilesRequest;
import core.appexec.magagent.behaviours.RecoverReplicaBehaviour;
import core.appexec.magagent.behaviours.RegisterExecutionBehaviour;
import core.appexec.magagent.behaviours.RequestInputFilesBehaviour;
import core.application.MagApplication;
import core.ft.stablestorage.StableStorage;
import core.ontology.ExecutionInfo;
import core.ontology.MAGOntology;
import core.ontology.OutputFile;
import dataTypes.ApplicationExecutionInformation;
import dataTypes.ProcessExecutionInformation;

/**
 Class MagAgent - Mobile agent responsible for the execution
 and control of applications in the grid
 
 This mobile agent execute applications in the grid. For each
 application we have an agent associated with it. MagAgent is
 also responsible for migrate its application if the user requests
 the release of the node, and notifies the AgentHandler about the
 finish of application execution
 
 @author Rafael Fernandes Lopes
 */
public class MagAgent extends Agent implements Thread.UncaughtExceptionHandler {
	///-------------------- fields
	private static final long serialVersionUID = -8531733227757316774L;
	
	private AID emaAID = null;
	private AID stableStorageAID = null;
	private AID ermAID = null;
	
	private Codec codec = new LEAPCodec();
	private Ontology ontology = MAGOntology.getInstance();
	
	// These fields keep information about the application execution and
	// some user information (e.g. constraints, preferences...)
	private String applicationName= "", applicationJavaName = "", executionId = "", applicationArguments = "", basePath ="";
	private String requestId = "", processId = "", replicaId = "", asctIor = "", ahIor=""; 
	
	private ApplicationExecutionInformation applicationExecutionInformation;
	private ProcessExecutionInformation processExecutionInformation;
	
	private String numberOfReplicas;
	
	private ArrayList outputFiles;
	private ArrayList inputFiles;
	
	private String applicationConstraints = "", applicationPreferences = "", userName = "";
	
	private HashSet behaviourSet = null;	
	
	private String [] containers = null;
	
    private boolean emaNotified = false;

    private boolean ermNotified = false;

	
	// This field stores the (serialized) bytecode of the application when
	// the agent migrates to other location
	private byte serializedApplication [];
	
	private transient byte compressedCheckpoint [] = null;
	
	private transient Boolean recovering = new Boolean (false);
	
	// Local reference of the AgentHandler
	private transient AgentHandlerImpl agentHandler = null;
	
	// User application
	private transient MagApplication application = null;
	
	// Store temporarily the arguments about the application execution
	// (information that are stored in the first collection of fields (appReposId, appName...)
	private transient Object[] args = null;
	
	// The output controllers (responsible for receive and handle the application output)
	private transient ThreadAwareOutputStream out = null;
	private transient ThreadAwareOutputStream err = null;
	
	private ExecutionInfo executionInfo = null;

	///-------------------- public methods
	public void terminateAgent(){
		ACLMessage unregisterMsg = new ACLMessage(ACLMessage.REQUEST);
	    unregisterMsg.setSender(this.getAID());
	    unregisterMsg.addReceiver(emaAID);
	    
	    ExecutionInfo einfo = new ExecutionInfo();
	    einfo.setAppExecutionId(executionId);
	    einfo.setAppNodeRequestId(this.getAppNodeRequestId());
	    
	    
	    try {
			for (int i = 0; i < outputFiles.size(); i++) {
				// Vinicius 
				File file = new File(executionId + "/" + ((OutputFile) outputFiles.get(i)).getFileName() );
//				File file = new File("/home/pos/vinicius/integrade-mag/resourceProviders/lrm/" + getAppOutputDirectory() + "/" + ((OutputFile) outputFiles.get(i)).getFileName() );
				
				if (file.exists()) {
					FileInputStream fis = new FileInputStream(file);
					byte code [] = new byte[(int)file.length()];
					fis.read(code);
					fis.close();
					
					((OutputFile) outputFiles.get(i)).setContent(code);
				}	
			}
		} catch (Exception e) {
			
		}
		
		einfo.setOutputFiles(outputFiles);
		
		SequentialBehaviour seqBehaviour = new SequentialBehaviour();	
		
		//Desregistra no EMA
		//seqBehaviour.addSubBehaviour (new UnregisterExecutionBehaviour (this, unregisterMsg, einfo));
		seqBehaviour.addSubBehaviour(new FinishApplicationBehaviour(this));
		
		this.addBehaviour(seqBehaviour);
	}
	
	/**
	 Returns an error message to an user
	 
	 @param appReposId - the appReposId represents the correct directory where
	 the error file are located
	 @param message - message to be returned to an user
	 */
	public void errorMessage(String appReposId, String message) {
		try {
			FileOutputStream fileErr = new FileOutputStream ("./" + appReposId + "/stderr");
			PrintWriter pw = new PrintWriter (fileErr, true);
			
			pw.println ("Error -->> " + message);
			pw.close();
			fileErr.close();
		} catch (Exception e) { }
	}
	
	/**
	 Redirect the output of an application to its own output files
	 
	 @param th - the application to have the output redirected
	 @param appReposId - the appReposId represents the correct directory where
	 the files are located
	 */
	public void redirectOutput (Thread th, String appReposId) {
		try {
			// Associates the applications with its own output files
			// Vinicius discommented {
//			this.out.attachFile(th, new File("/home/pos/vinicius/integrade-mag/resourceProviders/lrm/" + appReposId + "/stdout"));
//			this.err.attachFile(th, new File("/home/pos/vinicius/integrade-mag/resourceProviders/lrm/" + appReposId + "/stderr"));
			this.out.attachFile(th, new File("./" + appReposId + "/stdout"));
			this.err.attachFile(th, new File("./" + appReposId + "/stderr"));
			// } Vinicius
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 Redirect the output of an application to its own output files
	 
	 @param th - the application to have the output redirected
	 */
	public synchronized void disableRedirectOutput (Thread th) {
		try {
			this.out.removeFile(th);
			
			this.err.removeFile(th);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 Migrate this agent and its application
	 
	 @param location - Location where the agent have to migrate.
	 Location represents a container name
	 */
	public void migrateAgent(String location) {
		serializedApplication = this.suspendAppExecution();
		application = null;
				
		this.setEnabledO2ACommunication(false, 0);
		
		clearBehaviourList();
		
		// Causes the agent migration for 'location'
		doMove (new ContainerID(location, null));
	}
	
	/**
	 Serializes an object
	 
	 @param obj - the object to be serialized
	 @return the object in serialized form
	 @throws Exception case any error occurs
	 */
	public byte[] marshall(Object obj) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(obj);
		
		byte[] result = baos.toByteArray();
		
		oos.close();
		baos.close();
		
		return result;
	}
	
	/**
	 Deserializes an object
	 
	 @param b - the serialized object, to be deserialized
	 @return the deserialized object
	 @throws Exception case any error occurs
	 */
	public Object unmarshall(byte b[]) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(b);
		ObjectInputStream ois = new ObjectInputStream(bais);
		
		Object result = ois.readObject();
		ois.close();
		bais.close();
		
		return result;
	}
	
	public void addBehaviour(Behaviour b) {
		super.addBehaviour(b);
		behaviourSet.add(b);
	}
	
	///-------------------- protected methods    
	/**
	 First method invoked by the mobile agent plataform on an
	 agent when it is instantiated
	 */
	protected void setup() {
		//Setting the comunication ontology	
		getContentManager().registerLanguage(new LEAPCodec());
		getContentManager().registerOntology(MAGOntology.getInstance());		
		
		//this.emaAID = new AID(ExecutionManagementAgent.EXECUTION_MANAGEMENT_AGENT_NAME + "@" + getContainerController().getPlatformName(), AID.ISGUID);
		this.emaAID = new AID("executionmanagementagent" + "@" + getContainerController().getPlatformName(), AID.ISGUID);
		this.stableStorageAID = new AID(StableStorage.STABLE_STORAGE_NAME + "@" + this.getContainerController().getPlatformName(), AID.ISGUID);
		
		
		setEnabledO2ACommunication(true, 0);
		
		outputFiles = new ArrayList();
		inputFiles = new ArrayList();
		
		behaviourSet = new HashSet();
		
		// Get arguments passed to the agent and stores them on private attributes
		collectArguments();

		if (Integer.parseInt(numberOfReplicas) == 0 )
			executionId = requestId + ":" + processId;
		else
			executionId = requestId + ":" + processId + ":" + replicaId;		
		
		executionInfo = new ExecutionInfo();		
		executionInfo = createExecutionInfo();
		
		// Vinicius based on mag-diego {
	    MessageTemplate mtRequestOutputFiles = MessageTemplate.and(MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST), MessageTemplate.MatchConversationId("requestOutputFiles"));
        addBehaviour(new ReceiveOutputFilesRequest(this, mtRequestOutputFiles));
        // } Vinicius
		
		// Vinicius discommented
		 this.ermAID = new AID("erm-agent:" + this.getAppMainRequestId() + "@" + this.getContainerController().getPlatformName(), AID.ISGUID);
//		this.ermAID = new AID("erm-agent:" + this.requestId + "@" + this.getContainerController().getPlatformName(), AID.ISGUID);
		System.out.println("ERMAgent: " + ermAID.getName());		
		
/*		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		
		addBehaviour(new RegisterApplicationExecutionAcceptedStateBehaviour(this, message ,executionInfo));
		
		if (numberOfReplicas > 0) {
			addBehaviour(new InformExecutionAcceptedBehaviour(this, message ,executionInfo));
			
			
		}*/		
		
		File currentDir = new File("./" + this.getAppExecutionId());
		if ( !(currentDir.exists())) {
			currentDir.mkdir();
		}
		
		userName = "";
		
		try {
			System.in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			// Get singleton  instance of the AgentHandler
			agentHandler = AgentHandlerImpl.getInstance();
			
			// Request to AgentHandler the references of output controllers
			this.out = agentHandler.requestOutputStream();
			this.err = agentHandler.requestErrorStream();

			// Register this agent in the local AgentHandler
			agentHandler.registerAgent(executionId, this);
			
			SequentialBehaviour seqBehaviour = new SequentialBehaviour();
			
			if (!recovering.booleanValue()) {
				seqBehaviour.addSubBehaviour(registerAgent(createExecutionInfo()));
			} else {
				ACLMessage queryCkpMsg = new ACLMessage(ACLMessage.QUERY_REF);
			    queryCkpMsg.setSender(this.getAID());
			    queryCkpMsg.addReceiver(stableStorageAID);
			    
				seqBehaviour.addSubBehaviour (new QueryCheckpointBehaviour(this, queryCkpMsg, executionId + "." + application.getCheckpointCount()));
			}
			
			ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
			
			
			if (Integer.parseInt(getNumberOfReplicas()) > 0) {
				
				seqBehaviour.addSubBehaviour(requestInputFilesFromErmAgent());
				// Vinicius -> a adição desse behaviour trava a execução do agente sem motivo aparente
				seqBehaviour.addSubBehaviour(new InformExecutionAcceptedBehaviour(this, message ,executionInfo));				
			}
			
			// Based on mag-diego
			ACLMessage changeStateMsg = new ACLMessage(ACLMessage.REQUEST);
            System.out.println("ChangeProcessExecutionStateToAcceptedBehaviour");

			seqBehaviour.addSubBehaviour(new ChangeProcessExecutionStateToAcceptedBehaviour(this, changeStateMsg,executionInfo));
			seqBehaviour.addSubBehaviour(new ExecuteApplicationBehaviour(this));
			//System.out.println("ADICIONOU $$$$$$");
			
			/*Para registro do Agente.*/
			addBehaviour(seqBehaviour);
		} catch (Exception e) {
			e.printStackTrace();
			        
	        terminateAgent();
		}
	}

	protected void beforeMove() {
		agentHandler.unregisterAgent(executionId);
	}
	
	/**
	 Method invoked on the agent after the end of migration process
	 */
	protected void afterMove() {
		this.getContentManager().registerLanguage(codec);
		this.getContentManager().registerOntology(ontology);
		
		this.setEnabledO2ACommunication (true, 0);
		
		clearBehaviourList();
		
		// Retrieve the refence of the local AgentHandler
		agentHandler = AgentHandlerImpl.getInstance();
		
		// Get new output controllers
		this.out = agentHandler.requestOutputStream();
		this.err = agentHandler.requestErrorStream();
		
		try {	
			// If the agent sucessfully registered itself on local AgentHandler...
			agentHandler.registerAgent (executionId, this);
			// appMainRequestId, appNodeRequestId, appConstraints, appPreferences, asctIor, outputFiles)) {
			SequentialBehaviour seqBehaviour = new SequentialBehaviour();
			
			ACLMessage changeLocationMsg = new ACLMessage(ACLMessage.REQUEST);
			changeLocationMsg.setSender(this.getAID());
			changeLocationMsg.addReceiver(emaAID);
			
			seqBehaviour.addSubBehaviour(new ChangeExecutionLocationBehaviour (this, changeLocationMsg, this.getAppExecutionId(), this.here().getName()));
			seqBehaviour.addSubBehaviour(new MigrationFinishedBehaviour(this));
			
			this.addBehaviour(seqBehaviour);
		} catch (Exception e) {
			e.printStackTrace();
			
	        terminateAgent();
		}
	}
	
	/**
	 This method is invoked before the agent's death
	 */
	protected void takeDown() {
		// Disables the object-to-agent communication channel, thus
		// waking up all waiting threads
		setEnabledO2ACommunication(false, 0);
		
		System.out.println("takeDown");
		
		agentHandler.unregisterAgent(executionId);
		
		if (application != null) {
			application.stop();
		}
	}
	
/*	private String getAgentHandlerIor(){
		
		String hostname = "", iorFile = "";
		
		// Retrieves local hostname
		try {
			hostname = InetAddress.getLocalHost().getHostName();
			if (hostname.indexOf('.') >= 0) {
				hostname = hostname.substring(0, hostname.indexOf('.'));
			}
		} catch (UnknownHostException e) {			
			e.printStackTrace();
		}
		
		iorFile = "ahiors/" + hostname + ".ior";
		
		BufferedReader br = null;
		try {
			br = new BufferedReader (new InputStreamReader (new FileInputStream(iorFile)));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		try {
			String myIor = br.readLine();
			br.close();
		} catch (IOException e) {		
			e.printStackTrace();
		}		
		
		System.out.println("");

		
	}*/
	
	private Behaviour requestInputFilesFromErmAgent() {
	    getContentManager().registerLanguage(codec);  
	    getContentManager().registerOntology(ontology); 
	    
	    ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		message.setSender(getAID());
		message.addReceiver(getErmAID());		
	    
	    return (new RequestInputFilesBehaviour(this, message, getExecutionInfo()));
	}
	
	///-------------------- private methods
	/**
	 * Register the agent in the AgentMap
	 */
	private Behaviour registerAgent(ExecutionInfo einfo) {
	    getContentManager().registerLanguage(codec);  
	    getContentManager().registerOntology(ontology); 
	    
	    ACLMessage registerMsg = new ACLMessage(ACLMessage.REQUEST);
	    registerMsg.setSender(getAID());
	    registerMsg.addReceiver(emaAID);
	    
	    return (new RegisterExecutionBehaviour(this, registerMsg, einfo));
	}	

	
	private ExecutionInfo createExecutionInfo(){
		
		ExecutionInfo einfo = new ExecutionInfo(); 
		// <application>
		
		einfo.setAppExecutionId(this.getAppExecutionId());
		
		// <applicationInfo>
		einfo.setExecutingHost(this.here().getName());
		
		einfo.setAppReposId(this.getAppReposId());
		
		einfo.setAppName(this.getAppName());
		
		// <executionInfo>
		einfo.setUserName(this.getUserName());
		
		einfo.setAppArgs(this.getAppArgs());
		
		einfo.setAppMainRequestId(this.getAppMainRequestId());
		
		einfo.setAppReplicaId(this.getReplicaId());
		
		einfo.setNumberOfReplicas(this.getNumberOfReplicas());
		
		einfo.setAppNodeRequestId(this.getAppNodeRequestId());
		
		einfo.setAppConstraints(this.getAppConstraints());
		
		einfo.setAppPreferences(this.getAppPreferences());
		
		einfo.setAsctIor(this.getAsctIor());
				
		einfo.setOutputFiles(this.getOutputFiles());
		
		einfo.setInputFiles(this.getInputFiles());
		
		return einfo;
	}
	
	/**
	 Get the arguments passed to the agent and saves them
	 in private attributes
	 */
	private void collectArguments() {
		args = getArguments();
		if (args != null && args.length > 0) {
			applicationName        =  (String) args[0];	  
			basePath               =  (String) args[1];
			applicationArguments   =  (String) args[2];
			requestId              =  (String) args[3];
			processId              =  (String) args[4];
			applicationConstraints =  (String) args[5];
			applicationPreferences =  (String) args[6];
			asctIor                =  (String) args[7];
			
			numberOfReplicas	   =  ((Integer) args[10]).toString();
			
			ahIor = (String) args[11];
			replicaId 			  =  (String) args[12];
			System.out.println("ReplicaId do MAGAgent: " + replicaId); // Vinicius
			applicationExecutionInformation = (ApplicationExecutionInformation) args[13];
			processExecutionInformation = (ProcessExecutionInformation) args[14];
			
			String [] outputFileNames = (String []) args[8];
			for (int i = 0; i < (outputFileNames).length; i++) {
				OutputFile oFile = new OutputFile();
				oFile.setFileName(outputFileNames [i]);
				
				outputFiles.add(oFile);
			}
			
			recovering = (Boolean) args[9];
		} else {
			this.errorMessage (executionId, "There is not specificated application");
	        terminateAgent();
		}
	}
	
	/**
	 * 
	 */
	private void clearBehaviourList() {
		Iterator behaviours = behaviourSet.iterator();
		
		while (behaviours.hasNext()) {
			Behaviour b = (Behaviour) behaviours.next();
			
			this.removeBehaviour(b);
			b.restart();
			
			behaviours.remove();
		}
	}
	
	

	private byte [] suspendAppExecution () {
		// Stops the application execution - ONLY IF IT WAS INSTRUMENTED!!!
		application.doYield();
		
		// Waits for the application stops
		try {
			application.join();
			
			// Removes the association among the application and its output files
			this.disableRedirectOutput(application);
			
			// Serialize the application
			return this.marshall(application);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return (new byte[0]);
	}
	
/*	public void uncaughtException(Thread t, Throwable e) {
		System.out.println("Tratador de excessÃ£o padrÃ£o redirecionado");
	}*/

	public void uncaughtException(Thread t, Throwable e) {
		System.out.println("entrou no uncaughtException");

		String agentName = "agentrecover" + "-" + System.currentTimeMillis();

		Object o[] = new Object[3];
		containers = new String[1];

		//ExecutionInfo[] einfo = new ExecutionInfo[1];
		//einfo[0] = executionInfo;

		if (this.getNumberOfReplicas().equals("0")){		
			System.out.println("uncaughtException" + this.here().getName() );

			containers = new String[1];
			containers[0] =  this.here().getName();

		}else{
//			ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
//			this.addBehaviour(new RecoverReplicaBehaviour(this,  message ,executionInfo ));
//			while(this.containers.length == 0) {}
//			this.doWait();
		}
		containers[0] = this.here().getName();
		o[0] = containers;
		o[1] = applicationExecutionInformation;
		o[2] = processExecutionInformation;

		try {
			AgentController agent = getContainerController().createNewAgent(agentName, "core.ft.agentrecover.AgentRecoverImpl",	o);
			agent.start();
		} catch (Exception e1) {
			e1.printStackTrace();
		}								


	}
	
	///-------------------- getters and setters
	/**
	 * @return Returns the appArgs.
	 */
	public String getAppArgs() {
		return applicationArguments;
	}
	/**
	 * @param appArgs The appArgs to set.
	 */
	public void setAppArgs(String appArgs) {
		this.applicationArguments = appArgs;
	}
	/**
	 * @return Returns the appConstraints.
	 */
	public String getAppConstraints() {
		return applicationConstraints;
	}
	/**
	 * @param appConstraints The appConstraints to set.
	 */
	public void setAppConstraints(String appConstraints) {
		this.applicationConstraints = appConstraints;
	}
	/**
	 * @return Returns the application.
	 */
	public MagApplication getApplication() {
		return application;
	}
	/**
	 * @param application The application to set.
	 */
	public void setApplication(MagApplication application) {
		this.application = application;
	}
	/**
	 * @return Returns the appMainRequestId.
	 */
	public String getAppMainRequestId() {
		// Vinicius {
		return requestId;
//		return processExecutionInformation.executionRequestId.requestId;
	}
	/**
	 * @param appMainRequestId The appMainRequestId to set.
	 */
	public void setAppMainRequestId(String appMainRequestId) {
		this.requestId = appMainRequestId;
	}
	/**
	 * @return Returns the appName.
	 */
	public String getAppName() {
		return applicationName;
	}
	/**
	 * @param appName The appName to set.
	 */
	public void setAppName(String appName) {
		this.applicationName = appName;
	}
	/**
	 * @return Returns the appNodeRequestId.
	 */
	public String getAppNodeRequestId() {
		return processId;
	}
	/**
	 * @param appNodeRequestId The appNodeRequestId to set.
	 */
	public void setAppNodeRequestId(String appNodeRequestId) {
		this.processId = appNodeRequestId;
	}
	/**
	 * @return Returns the appPreferences.
	 */
	public String getAppPreferences() {
		return applicationPreferences;
	}
	/**
	 * @param appPreferences The appPreferences to set.
	 */
	public void setAppPreferences(String appPreferences) {
		this.applicationPreferences = appPreferences;
	}
	/**
	 * @return Returns the appReposId.
	 */
	public String getAppReposId() {
		return applicationName;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getBasePath() {
		return basePath;
	}
	/**
	 * 
	 * @param basePath
	 */
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	/**
	 * @param appReposId The appReposId to set.
	 */
	public void setAppReposId(String appReposId) {
		this.applicationJavaName = appReposId;
	}
	/**
	 * @return Returns the args.
	 */
	public Object[] getArgs() {
		return args;
	}
	/**
	 * @param args The args to set.
	 */
	public void setArgs(Object[] args) {
		this.args = args;
	}
	/**
	 * @return Returns the asctIor.
	 */
	public String getAsctIor() {
		return asctIor;
	}
	/**
	 * @param asctIor The asctIor to set.
	 */
	public void setAsctIor(String asctIor) {
		this.asctIor = asctIor;
	}
	/**
	 * @return Returns the appExecutionId.
	 */
	public String getAppExecutionId() {
		return executionId;
		
	}
//	 Vinicius {
	/**
	 * @return Returns the directory where the lrm puts the output data.
	 */
	public String getAppOutputDirectory() {
		return getAppExecutionId();
//		return processExecutionInformation.executionRequestId.requestId+":"+processId; // Vinicius on replicaId
	}
//	 } Vinicius
	/**
	 * @param appExecutionId The appExecutionId to set.
	 */
	public void setAppExecutionId(String appExecutionId) {
		this.executionId = appExecutionId;
	}
	/**
	 * @return Returns the inputFiles.
	 */
	public ArrayList getInputFiles() {
		return inputFiles;
	}
	/**
	 * @param inputFiles The inputFiles to set.
	 */
	public void setInputFiles(ArrayList inputFiles) {
		this.inputFiles = inputFiles;
	}
	/**
	 * @return Returns the outputFiles.
	 */
	public ArrayList getOutputFiles() {
		return outputFiles;
	}
	/**
	 * @param outputFiles The outputFiles to set.
	 */
	public void setOutputFiles(ArrayList outputFiles) {
		this.outputFiles = outputFiles;
	}
	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return Returns the recovering.
	 */
	public boolean getIsRecovering() {
		return recovering.booleanValue();
	}
	/**
	 * @param recovering The recovering to set.
	 */
	public void setIsRecovering(boolean recovering) {
		this.recovering = new Boolean (recovering);
	}
	/**
	 * @return Returns the serializedApplication.
	 */
	public byte[] getSerializedApplication() {
		return serializedApplication;
	}
	/**
	 * @param serializedApplication The serializedApplication to set.
	 */
	public void setSerializedApplication(byte[] serializedApplication) {
		this.serializedApplication = serializedApplication;
	}
	/**
	 * @return Returns the compressedCheckpoint.
	 */
	public byte[] getCompressedCheckpoint() {
		return compressedCheckpoint;
	}
	/**
	 * @param compressedCheckpoint The compressedCheckpoint to set.
	 */
	public void setCompressedCheckpoint(byte[] compressedCheckpoint) {
		this.compressedCheckpoint = compressedCheckpoint;
	}
	
	public String getNumberOfReplicas() {
		return numberOfReplicas;
	}

	public void setNumberOfReplicas(String numberOfReplicas) {
		this.numberOfReplicas = numberOfReplicas;
	}	
	
	public ExecutionInfo getExecutionInfo() {
		return executionInfo;
	}
	
	public void setExecutionInfo(ExecutionInfo executioInfo) {
		this.executionInfo = executionInfo;
	}
	
	public AID getErmAID() {
		return ermAID;
	}

	public String getAhIor() {
		return ahIor;
	}

	public void setAhIor(String ahIor) {
		this.ahIor = ahIor;
	}

	public String getReplicaId() {
		return replicaId;
	}

	public void setReplicaId(String replicaId) {
		this.replicaId = replicaId;
	}

	public AID getEmaAID() {
		return emaAID;
	}

	public void setEmaAID(AID emaAID) {
		this.emaAID = emaAID;
	}

	public String[] getContainers() {
		return containers;
	}

	public void setContainers(String[] containers) {
		this.containers = containers;
	}

	// Vinicius based on diego-mag
	
    public boolean isEmaNotified() {
		return emaNotified;
	}

	public void setEmaNotified(boolean emaNotified) {
		this.emaNotified = emaNotified;
	}

	public boolean isErmNotified() {
		return ermNotified;
	}

	public void setErmNotified(boolean ermNotified) {
		this.ermNotified = emaNotified;
	}

}
