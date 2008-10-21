package core.execman.ema;

/*import subtypes.ApplicationParam;
import subtypes.ApplicationStateInfo;
import subtypes.AsctRequestId;
import subtypes.FileStruct;*/
import jade.util.leap.Iterator;

import core.ontology.ExecutionInfo;
 
/*
 * Created on 28/09/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author gilberto
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface ExecutionManagementAgent {
	
	/**
	 * ---------------------------------------------------------------------------
	 * ExecutionManager Methods
	 * ---------------------------------------------------------------------------
	 */

	
	public static final String EXECUTION_MANAGEMENT_AGENT_NAME = "executionmanagementagent";
	
	/**
	 * Method responsible for register an agent in the
	 * executionmanagementagent.
	 * @param execInfo - Application's Data .
	 */
	public void registerAgent (ExecutionInfo execInfo);
	

	/**
	 * This method changes the location (host) where the
	 * agent is running on.
	 * @param appExecId -identify the application.
	 * @param newHost - New host's name where the application will be running.
	 */		
	//public boolean changeAgentHost (String appExecId, String newHost);
	
	/**
	 * This method retrieves the list of agents
	 * that is running on specified host.
	 * @param host - Host where the list of agents, are running.
	 * @return informations about all agents running in a host
	 */
	public ExecutionInfo[] getAgentList (String host);
	
	/**
	 * This method recover all process
	 * that were running on specified host.
	 * @param host - Host's name.
	 * */
	public void recoverHost(String host);
	
	/**
	 * This method returns the containers names where the process replicas are runnig
	 * 
	 * @param requestId - identify the application.
	 * @param processId - identify the process.  	 
	 * */	
	
	public String[] getReplicasLocations(String requestId, String processId, String ReplicaId);	
	
	/**
	 * This method kill the application.
	 * @param appExecId - Applications's Identify.
	 *  */
	//public void killApplication (String appExecId);
	
	/**
	 * This method store that the application's request was accepted.
	 * @param app - Application's Data that sended. 
	 * @param user - User that sended the application. 
	 * */
	/*public void acceptedExecutionRequestNotification(String source, String appName, 
			types.CommonExecutionSpecs commonExecutionSpecs, types.DistinctExecutionSpecs distinctExecutionSpecs,
			String[] inputFiles, String userName);*/
	
	/**
	 * This method store the info that the application, was refused.
	 * @param id - identify the application.
	 * */
	//public void refusedExecutionRequestNotification(AsctRequestId	id);
	
	/**
	 * This method store the info that the application, was migrated.
	 * @param id - identify the application.
	 * @param newHost - host's name where the application will be running.
	 *  */
	//public boolean migrateExecutionNotification(String appExecutionId, String newHost);
	
	/**
	 *  This method notify the ExecutionManagementAgent before, which the MagAgent will
	 *  running the applicaiton.
	 * @param id - identify the application.
	 * @param inputFiles - InputFiles about application.
	 * */
	//public boolean appInitiatedNotification(String execId, Iterator inputFiles);
	
	/**
	 *  This method notify the ExecutionManagementAgent after, which the MagAgent will
	 *  finished the applicaiton.
	 * @param id - identify the application.
	 * @param outputFiles - OutputFiles about application.
	 * */
	//public boolean appFinishedNotification(String appExecutionId, Iterator outputFiles);
	
	/**
	 * This method return all Application's Param.
	 * @param id - identify the application.
	 * @return ApplicationParam.
	 * */
	//public ApplicationParam getApplicationParam(AsctRequestId id);
		
	/**
	 * This method return one InputFile.
	 * @param id- identify the application.
	 * @param name - InputFile's name.
	 * @return FileStruct.
	 * */
	//public FileStruct getInputFile(AsctRequestId id, String name);

	/**
	 * This method return one OutputFile.
	 * @param id- identify the application.
	 * @param name - OutputFile's name.
	 * @return FileStruct.
	 * */
	//public FileStruct getOutputFile(AsctRequestId id,String name);

	/**
	 * This method return the Application's State.
	 * @param user - User that sended the application. 
	 * @return ApplicationStateInfo[].
	 * */
	//public ApplicationStateInfo[] getApplicationsStateInfo(String user);
	
	 /**
	  *This method removes the pertaining results to the application  
	  *@param id - identify the application.
	  *@return true, if success; or else, false. 
	  **/
	//public boolean removeExecResults(AsctRequestId id);

	/**
	 * This method update Agent's status and Application's info.
	 * @param ainfo - Application's Data .
	 * */
    //public boolean updateAgent(ExecutionInfo einfo);
	
	
	

}
