package core.application;

import jade.core.Agent;

import java.io.File;

import core.appexec.agenthandler.AgentHandlerImpl;
import core.appexec.magagent.MagAgent;
import core.brakes.be.ac.kuleuven.cs.ttm.computation.Context;

/*MAGCAT*/
/*import core.appexec.magagent.behaviours.ReceiveMessageMABehaviour;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.leap.LEAPCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;

import datagrid.ontology.Attribute;
import datagrid.ontology.Expression;
import datagrid.ontology.LogicalFile;
import datagrid.ontology.LogicalObject;
import datagrid.ontology.MetadataCatalogueOntology;
import datagrid.ontology.StoreFileAction;
import datagrid.ontology.Expression;
import datagrid.ontology.FileBytes;*/

import jade.util.Event;
/*MAGCAT*/
/**
 Class MagApplication - Represents a MAG application
 
 To execute an application on the grid, the user must
 extend this abstract class
 
 @author Rafael Fernandes Lopes
 */
public abstract class MagApplication extends Thread implements java.io.Serializable, Cloneable {
	private static final int SLEEP_TIME = 5000; //< time between two checks (in milliseconds)
	
	protected Context tmpContext; //< The application context (internal usage)
	protected Context myContext = null; //< The application context (external usage)
	protected boolean switching = false; //< The application must save its context ?
	protected boolean restoring = false; //< The application is restoring from its context ?
	protected boolean stopping = false; //< The application must stop ?
	protected transient long lastCheckpointTimestamp;
	protected int checkpointCount;
	
	private String appExecutionId;
	protected transient File baseDir = null;
	/*MAGCAT*/
	private transient MagAgent agent = null;
	
	/*private ContentManager manager  = null;
    private Codec          codec    = new LEAPCodec();
 	private Ontology   ontology =null;*/
	/**
	 * @return Returns agent
	 * */
	public final MagAgent getAgent () {
		return agent;
	}
	
	/**
	  */
	public final void setAgent (MagAgent a) {
		agent = a;
		//manager  = (ContentManager)getAgent().getContentManager();
	}
	
	/**
	 * Send message to publish metadata and store file bytes
	 * @param file The byte array to store
	 * @param statement The expression to publish
	 * @return
	 */
	/*public int storeAndPublish(byte[] bytes, String statement){
		manager.registerLanguage(codec);
		ontology = MetadataCatalogueOntology.getInstance();
		manager.registerOntology(ontology);
		int resul = 0;
	
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		AID receiver = new AID("data-manager", false);

   		msg.setLanguage(codec.getName());
		msg.setOntology(ontology.getName()); 				
		msg.setSender(getAgent().getAID());
		msg.addReceiver(receiver);
	
		Expression expression = new Expression();
		expression.setExpression(statement);
	//	datagrid.ontology.File file = new datagrid.ontology.File();
	//	file.setBytes(bytes);
		FileBytes file = new FileBytes();
		file.setBytes(bytes);
		
		StoreFileAction storeFileAction = new StoreFileAction();
		storeFileAction.setExpression(expression);
		storeFileAction.setFileBytes(file);
		try{
		// Fill the content of the message---Manda.
		manager.fillContent(msg, new Action(getAgent().getAID(),storeFileAction));
		}catch(Exception e){
			e.printStackTrace();
		}
			Event ev = new Event (0,this);
			System.out.println("#########1######");
			getAgent().addBehaviour (new ReceiveMessageMABehaviour (getAgent(),msg, ev));
			try {System.out.println("###2#####");
					resul = Integer.parseInt((String)ev.waitUntilProcessed());
					System.out.println("###3####");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		if(resul==1){
			return 1;
		}else{
			return 0;
		}
	}
	*/
	/**
	 * @return Returns the appExecutionId.
	 */
	public String getAppExecutionId() {
		return appExecutionId;
	}
	
	/**
	 * @param appExecutionId The appExecutionId to set.
	 */
	public void setAppExecutionId(String appExecutionId) {
		this.appExecutionId = appExecutionId;
	}
	
	/**
	 * @param baseDir The baseDir to set.
	 */
	public void setBaseDir (String baseDir) {
		this.baseDir = new File (baseDir);
	}
	
	/**
	 Retrieve the application's checkpoint. If there isn't a new checkpoint returns null.
	 This method is invoked by the {@link CheckpointCollectorBehaviour}
	 
	 @return the application's checkpoint or null if there isn't a new checkpoint.
	 */
	public final void captureCheckpoint () {
		stopping = false;
		switching = true;
	}
	
	/**
	 Returns 'true' if it isn't the last stackframe in this context object.
	 */
	protected final boolean notLastStackInContext() {
		if (tmpContext != null) {
			return (!tmpContext.lastThisInContext());
		} else {
			return false;
		}
	}
	
	/**
	 An internal method used for copy the actual checkpoint from the temporary
	 context (tmpContext) to the 'stable' context attribute (myContext)
	 */
	protected final synchronized void copyStableCheckpoint() {
		Agent agent = AgentHandlerImpl.getInstance().getAgent (appExecutionId);
		
		if (agent != null) {
			try {
				Object ckp = this.clone();
				agent.putO2AObject (ckp, false);
			} catch (Exception e) { }
		}
	}
	
	public MagApplication() {
		this(new String[] {});
	}
	
	public MagApplication (String args[]) {
		if (!restoring) {
			switching = false;
			restoring = false;
			stopping = false;
			tmpContext = new Context();
			myContext = null;
			appExecutionId = "";
		}
		
		lastCheckpointTimestamp = System.currentTimeMillis();
	}
	
	/**
	 Test done by the instrumented application code, asking if the execution state
	 must be captured
	 
	 @return true if the application have to capture its context
	 */
	protected final boolean isSwitching () {
		if (!switching && ((System.currentTimeMillis() - lastCheckpointTimestamp) >= SLEEP_TIME)) {
			return true;
		}
		
		return switching;
	}
	
	/**
	 Test done by the instrumented application code, asking if the execution must be
	 stopped
	 
	 @return true if the application have to stop
	 */
	protected final boolean isStopping () {
		return stopping;
	}
	
	/**
	 Test done by the instrumented application code, asking if the execution state
	 must be restored from the application context
	 
	 @return true if the application have to restore its own context
	 */
	protected final boolean isRestoring () {
		return restoring;
	}
	
	/**
	 Method invoked by the {@link MagAgent} to start the execution of an user application
	 */
	public final void doStart() {
		if (!restoring) {
			switching = false;
			restoring = false;
			stopping = false;
			tmpContext = new Context();
			myContext = null;
			
			lastCheckpointTimestamp = System.currentTimeMillis();
		}
		
		this.start();
	}
	
	/**
	 Stops the execution of an instrumented application, and force it save its own context.
	 Method invoked by the {@link MagAgent} in the migration mechanism
	 */
	public final void doYield() {
		restoring = true;
		switching = true;
		stopping = true;
	}
	
	/**
	 Resumes the execution of an instrumented application, from its own context.
	 */
	public final void doResume() {
		switching = false;
		restoring = true;
		
		lastCheckpointTimestamp = System.currentTimeMillis();
		
		this.start();
	}
	
	public final void doResume (Context context) {
		if (context != null) {
			try {
				tmpContext = context;
				
				doResume();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 Force the capture of a checkpoint 
	 */
	protected final void mayCheckpoint() {
		checkpointCount++;
	}
	
	/**
	 Force the capture of a checkpoint 
	 */
	public final int getCheckpointCount() {
		return checkpointCount;
	}

	/**
	 Force the capture of a checkpoint 
	 */
	public final void setCheckpointCount(int count) {
		this.checkpointCount = count;
	}
	
	/**
	 Abstract method that forces the user to implement the run() method
	 */
	public abstract void run();
}
