package grm;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosTrading.Property;

import resourceProviders.Lrm;
import resourceProviders.LrmHelper;
import tools.Asct;
import tools.AsctHelper;
import clusterManagement.CrmAgent;
import clusterManagement.CrmAgentHelper;
import clusterManagement.ExecutionManagementAgent;
import clusterManagement.ExecutionManager;
import clusterManagement.Grm;
import clusterManagement.GrmHelper;
import clusterManagement.GrmPOA;
import dataTypes.ApplicationExecutionInformation;
import dataTypes.NodeDynamicInformation;
import dataTypes.NodeStaticInformation;
import dataTypes.ProcessExecutionInformation;
import dataTypes.SubtreeInformation;

//Class GrmImpl - Servant implementation of Grm interface described on ResourceManagement.idl
//
//Main functions:
//
//Execution request broker
//LRM availability manager
//
//@author Hammurabi Mendes

public class GrmImpl extends GrmPOA implements Runnable {	
	// This is the ORB used to access CORBA services
	private ORB orb;
	
	// The TraderManager is used for insertion, querying and removal operations
	private TraderManager traderManager;
	
	// This is a reference to the parent GRM
	private Grm parentGrm;

	// This is a reference to the CRM
	private CrmAgent crm;
	
	// This is a reference to the EMA
	private ExecutionManagementAgent ema;	
	
	// This is a reference to the Execution Manager
	private ExecutionManager executionManager;
	
	// This is the last time information from this GRM was updated on the parent GRM
	private int lastUpdateTime;
	
	// This is the last SubtreeInformation updated on the parent GRM
	private SubtreeInformation lastUpdateSubtreeInformation;
	
	// This is the interval between checks for significant resource availability
	// changes in the children GRMs
	private int sampleInterval;
	
	// This is the maximum interval between updates sent to the parent GRM
	private int keepAliveInterval;
	
	// This counter is used to generate a unique ID for each execution request
	private int executionNumber = 0;
	
	private Random rand = new Random();
	
	private Hashtable <String, String> mapLocations = new Hashtable();
	
	// Creates a new GrmImpl object
	//
	// @param orb - A reference to an ORB, used to access CORBA services
	
	public GrmImpl(ORB orb) {    	
		this.orb = orb;
		
		traderManager = new TraderManager(orb);
		
		// Default setting for 5 minutes
		this.sampleInterval = 300;
		
		// Default setting for 25 minutes
		this.keepAliveInterval = 1500;
		
		(new Thread(this)).start();
	}
	
	// -----------------------------------
	// LRM and GRM registration and update
	// -----------------------------------
	
	// See comments for following methods in TraderManager class
	
	public void registerLrm(String lrmIor, NodeStaticInformation nodeStaticInformation) {
		traderManager.registerLrm(lrmIor, nodeStaticInformation);
	}
	
	public void updateLrmInformation(String lrmIor, NodeDynamicInformation nodeDynamicInformation) {
		traderManager.updateLrmInformation(lrmIor, nodeDynamicInformation);
	}
	
	public void registerGrm(String childGrmIor, SubtreeInformation subtreeInformation) {
		traderManager.registerGrm(childGrmIor, subtreeInformation);
	}
	
	public void updateGrmInformation(String childGrmIor, SubtreeInformation subtreeInformation) {
		traderManager.updateGrmInformation(childGrmIor, subtreeInformation);
	}
	
	// --------------------------
	// Execution request handling
	// --------------------------
	
	// Handle an ASCT-submitted application 
	//
	// @param applicationExecutionInformation - Application-wide information
	// @param processExecutionInformation - Process-specific information
	//
	// @returns - True if the GRM accepted the request or it was sucessfully forwared
	
	public boolean requestRemoteExecution(ApplicationExecutionInformation applicationExecutionInformation, ProcessExecutionInformation[] processExecutionInformation) {
		
		String constraints = traderManager.generateConstraints(applicationExecutionInformation);
		String preferences = traderManager.generatePreferences(applicationExecutionInformation.applicationPreferences);
		
		boolean withReplicasExecution =false; // informa se a execuÃ§Ã£o Ã© com replicas ou nÃ£o
//		applicationExecutionInformation.numberOfReplicas = "0";
		
		int numberOfReplicas = Integer.parseInt(applicationExecutionInformation.numberOfReplicas);
		//int numberOfReplicas = 1;
		if( numberOfReplicas > 0){
			withReplicasExecution = true;
			System.out.println("Execution Request with Replicas:");
			for(int j=0; j < processExecutionInformation.length; j++)
				System.out.println("ReplicaId: " + processExecutionInformation[j].executionRequestId.replicaId);
		}else{
			for(int j=0; j < processExecutionInformation.length; j++)
				processExecutionInformation[j].executionRequestId.replicaId = "0";
			numberOfReplicas = 1;
			System.out.println("Execution Request:");
			
		}
		
		System.out.print("Request for " + processExecutionInformation.length * (numberOfReplicas)+ " processes");
		if(applicationExecutionInformation.forceDifferentNodes) {
			System.out.println(" that should run on different machines.");
		}
		else {
			System.out.println(".");
		}
		
		System.out.println("Constraints: " + constraints);
		System.out.println("Preferences: " + preferences);
		
		//constraints, preferences );
		ArrayList<Property[]> fetchedLrmInformation = new ArrayList<Property[]>();
		
		fetchedLrmInformation = traderManager.getLrmInformation(constraints, preferences, new String[] {"lrmIor"});
		
		// When there is not enough hosts suitable to execute the application...
		
		boolean existSufficentLrms = true;
		
		if(withReplicasExecution){
			if(fetchedLrmInformation.size() < numberOfReplicas)			
			existSufficentLrms = false;
		}else{
			if(fetchedLrmInformation.size() == 0)
			existSufficentLrms = false;	
		}
		
/*		// Recupera referÃªncia para o ExecutionManagementAgent no ServiÃ§o de Nomes
		try {

			org.omg.CORBA.Object ns = orb
					.resolve_initial_references("NameService");
			 NamingContextExt nameService = NamingContextExtHelper.narrow( ns );
			 ema = ExecutionManagementAgentHelper.narrow(nameService.resolve(nameService
					.to_name("EMA")));
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		System.out.println("Tipo da aplicação: " + applicationExecutionInformation.availableBinaries[0]);
		
		if(((fetchedLrmInformation.size() < processExecutionInformation.length*(numberOfReplicas)) && applicationExecutionInformation.forceDifferentNodes) || (!existSufficentLrms)) {    		
			System.out.println("Needed: " + processExecutionInformation.length + " hosts. Got: " + fetchedLrmInformation.size() + " hosts.");
			
		/*	if ( ema != null )	
				ema.setExecutionRefused(applicationExecutionInformation, processExecutionInformation);*/
			
			if(parentGrm == null) {
				System.out.println("Execution request was denied.");
				
				
				try {
					Asct asct = AsctHelper.narrow(orb.string_to_object(applicationExecutionInformation.requestingAsctIor));
					
					
					for(int i = 0; i < processExecutionInformation.length; i++) {
						asct.setExecutionRefused(processExecutionInformation[i].executionRequestId);
					}
				}
				catch(org.omg.CORBA.TRANSIENT transientException) {
					System.err.println("Unable to reach ASCT. Failure notification is incomplete.");
				}
				
				return false;
			}
			
			System.out.println("Trying other GRMs for execution request.");
			
			return forwardExecutionRequest(applicationExecutionInformation, processExecutionInformation); 
		}
		
		// Send a message to the ExecutionManager containing the request properties.
		
		String uniqueRequestId;
		if((uniqueRequestId = generateUniqueId(processExecutionInformation)) == null) {
			System.err.println("Aborting request due to unique application ID generation failure.");
			
			return false;
		}
		for(int i = 0; i < processExecutionInformation.length; i++) {
			
			
			processExecutionInformation[i].executionRequestId.requestId = uniqueRequestId;
		}
		
		if (executionManager != null && !withReplicasExecution) {
			executionManager.setExecutionScheduled(applicationExecutionInformation, processExecutionInformation);
		}
		
		int currentExecutionTrial = 0;
	    int currentExecutionReplicas = 0;// contador das  replicas
		int counterReplicas=0;//
		
		
		if (ema != null && !withReplicasExecution) {
			ema.setExecutionScheduled(applicationExecutionInformation, processExecutionInformation);
		}		
		
		
		if(!withReplicasExecution){
			while(currentExecutionTrial < processExecutionInformation.length) {
				if(fetchedLrmInformation.size() == 0) {
					break;
				}

				int currentPositionTrial = currentExecutionTrial % fetchedLrmInformation.size();

				Property[] lrmProperties = fetchedLrmInformation.get(currentPositionTrial);

				String lrmIor = lrmProperties[0].value.extract_string();

				try {  
					Lrm lrm = LrmHelper.narrow(orb.string_to_object(lrmIor));

					lrm.requestExecution(applicationExecutionInformation, processExecutionInformation[currentExecutionTrial]);

					traderManager.setRecentlyPicked(lrmIor);

					currentExecutionTrial++;

					// If the application must run on different nodes, discard the current LRM

					if(applicationExecutionInformation.forceDifferentNodes) {
						fetchedLrmInformation.remove(currentPositionTrial);
					}
				}
				catch(org.omg.CORBA.TRANSIENT transientException) {
					System.err.println("Unable to reach LRM. Removing it from database");

					traderManager.removeLrmInformation(lrmIor);
					fetchedLrmInformation.remove(currentPositionTrial);
				}
			}
		}else{ // fim if(!withReplicasExecution){

			ArrayList<String>  offersIorsReplicas = new ArrayList<String>();
			while(currentExecutionTrial < processExecutionInformation.length) {
				currentExecutionReplicas = 0;
				while((currentExecutionReplicas < numberOfReplicas) && (currentExecutionReplicas < fetchedLrmInformation.size())){
					Property[] lrmProperties = fetchedLrmInformation.get( counterReplicas);
					String lrmIor = lrmProperties[0].value.extract_string();
					if(!offersIorsReplicas.contains(lrmIor)){
						offersIorsReplicas.add(lrmIor);
						traderManager.setRecentlyPicked(lrmIor);
					}

					currentExecutionReplicas++;
					counterReplicas++;
					counterReplicas = counterReplicas % fetchedLrmInformation.size();
					currentExecutionTrial++;
				}// while


			}


			String lrmIors[] = offersIorsReplicas.toArray(new String[offersIorsReplicas.size()]);	


			try {

				org.omg.CORBA.Object ns = orb
				.resolve_initial_references("NameService");
				NamingContextExt nameService = NamingContextExtHelper.narrow( ns );
				crm = CrmAgentHelper.narrow(nameService.resolve(nameService
						.to_name("CRMAGENT")));
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (crm != null) {
				System.out.println("Crm->requestRemoteExecution");
				crm.requestRemoteExecution(applicationExecutionInformation,processExecutionInformation,lrmIors);
			}				




		}
		if(currentExecutionTrial < processExecutionInformation.length) {			
			if(parentGrm == null) {
				System.out.println("Application execution request was denied.");

				try {
					Asct asct = AsctHelper.narrow(orb.string_to_object(applicationExecutionInformation.requestingAsctIor));

					for(int counter = currentExecutionTrial; counter < processExecutionInformation.length; counter++) {
						asct.setExecutionRefused(processExecutionInformation[counter].executionRequestId);
					}
				}
				catch(org.omg.CORBA.TRANSIENT transientException) {
					System.err.println("Unable to reach ASCT. Failure notification is incomplete.");
				}

				return false;
			}

			System.out.println("Trying other GRMs for execution request.");

			return forwardExecutionRequest(applicationExecutionInformation, processExecutionInformation);
		}

		return true;    	
	}
	
	// Forward request to child GRMs and parent GRM
	//
	// @param applicationExecutionInformation - Application-wide information
	// @param processExecutionInformation - Process-specific information
	//
	// @returns - True if some GRM accepted the request
	
	private boolean forwardExecutionRequest(ApplicationExecutionInformation applicationExecutionInformation, ProcessExecutionInformation[] processExecutionInformation) {
		GrmTracker grmTracker = new GrmTracker(orb, applicationExecutionInformation.previousGrmIor, getParentGrmIor());
		
		ArrayList<Property[]> fetchedGrmInformation = new ArrayList<Property[]>();
		
		// FIXME: Apply some ASCT originated constraints/preferences for GRMs
		fetchedGrmInformation = traderManager.getGrmInformation(null, null, new String[] { "childGrmIOR" });
		
		String childGrmId;
		
		for(int counter = 0; counter < fetchedGrmInformation.size(); counter++) {
			childGrmId = fetchedGrmInformation.get(counter)[0].value.extract_string();
			
			grmTracker.insertUntraversedGrmId(childGrmId);
		}
		
		applicationExecutionInformation.previousGrmIor = getGrmIor();
		
		Grm nextGrm;
		
		while((nextGrm = grmTracker.getNextGrm()) != null) {
			try {
				if(nextGrm.requestRemoteExecution(applicationExecutionInformation, processExecutionInformation)) {	
					return true;
				}
			}
			catch(org.omg.CORBA.TRANSIENT transientException) {
				String nextGrmId = orb.object_to_string(nextGrm);
				
				if(nextGrmId.equals(getParentGrmIor())) {
					System.err.println("Unable to reach parent GRM.");
					System.err.println("Contact your local system administrator.");
				}
				else {
					System.err.println("Unable to reach GRM " + nextGrmId + ".");
					System.err.println("Removing it from database.");
					
					traderManager.removeGrmInformation(nextGrmId);					
				}
			}
		}
		
		try {
			Asct asct = AsctHelper.narrow(orb.string_to_object(applicationExecutionInformation.requestingAsctIor));
			
			for(int counter = 0; counter < processExecutionInformation.length; counter++) {
				asct.setExecutionRefused(processExecutionInformation[counter].executionRequestId);
			}
		}
		catch(org.omg.CORBA.TRANSIENT transientException) {
			System.err.println("Unable to reach ASCT. Failure notification is incomplete.");
		}
		
		return false;
	}
	
	// Generates a unique ID for the application using host address, current time and original ASCT ID
	//
	// @param processExecutionInformation - Information about each process that should be executed
	//
	// @returns - A string that is unique
	
	private String generateUniqueId(ProcessExecutionInformation[] processExecutionInformation) {
		StringBuffer newId = new StringBuffer();
		
		if(processExecutionInformation[0].executionRequestId.requestId.indexOf(":") < 0) {
			try {              
				newId.append(java.net.InetAddress.getLocalHost().getHostAddress().replaceAll("[.]", ""));
				newId.append(Calendar.getInstance().getTimeInMillis());
				newId.append(":");
				newId.append(++executionNumber);
				newId.append(":");
			}
			catch (UnknownHostException unknownHostException) {
				System.err.println("Could not get host information.");
				
				return null;
			}
		}
		
		newId.append(processExecutionInformation[0].executionRequestId.requestId);
		
		return newId.toString();
	}
	
	// ---------------------
	// GRM main funcionality
	// ---------------------
	
	// This thread has 2 responsabilities:
	//
	// 1 - Periodically checks if the registered LRMs are online. If their last information
	//     update is older than a threshold value, check if they are available
	//
	// 2 - Periodically updates the parent GRM with information about the subtree
	//     represented by this GRM (the local LRMs and the child GRM subtrees)
	
	public void run() {
		while(true) {    		    		
			// This is responsability 1   		
			
			int currentTime =  (int) (new Date()).getTime() / 1000;
			
			if(parentGrm != null) {
				try {    				
					SubtreeInformation subtreeInformation = calculateGrmInformation();
					
					if(currentTime - lastUpdateTime >= keepAliveInterval || hasSignificantChange(subtreeInformation, lastUpdateSubtreeInformation)) {
						parentGrm.updateGrmInformation(getGrmIor(), subtreeInformation);
						
						lastUpdateSubtreeInformation = subtreeInformation;
						lastUpdateTime = currentTime;
					}
				}
				catch(org.omg.CORBA.TRANSIENT transientException) {
					System.err.println("Unable to reach parent GRM.");
					System.err.println("Contact your local system administrator.");
				}
			}
			
			// This is responsability 2.
			
			checkLrmAvailability();
			
			// Now, determine the time to pause the thread, and do it.
			
			try {
				int sleepTime = lastUpdateTime + keepAliveInterval - currentTime;
				
				if(sampleInterval < sleepTime || sleepTime < 0) {
					sleepTime = sampleInterval;
				}
				
				//System.out.println("GRM sleeping for " + sleepTime + " seconds.");
				
				// Convert seconds into miliseconds.
				sleepTime = sleepTime * 1000;
				
				Thread.sleep(sleepTime);    			
			}
			catch(InterruptedException interruptedException) {
				interruptedException.printStackTrace();
			}
		}
	}
	
	// If some LRM's last information update is older than keepAliveInterval,
	// check if it is available. If it is not the case, remove it from the trader
	//
	// This implements responsability 2 of the thread's run() method responsabilities,
	// as described there
	
	private void checkLrmAvailability() {
		ArrayList<Property[]> fetchedLrmInformation = new ArrayList<Property[]>();
		
		fetchedLrmInformation = traderManager.getLrmInformation(null, null, new String[] { "lrmIor", "lastUpdated" });
		
		for(int counter = 0; counter < fetchedLrmInformation.size(); counter++) {
			String lrmIor = fetchedLrmInformation.get(counter)[0].value.extract_string();
			
			int lastUpdateTime = fetchedLrmInformation.get(counter)[1].value.extract_long();
			int currentTime =  (int) (new Date()).getTime() / 1000;
			
			// This threshold value used to be 200 (hardcoded).
			// Now it is set to be the same as keepAliveInterval.
			
			if((currentTime - lastUpdateTime) > keepAliveInterval) {
				Lrm lrm = LrmHelper.narrow(orb.string_to_object(lrmIor));
				
				try {
					lrm.isAvailable();
				}
				catch(Exception exception) {
					System.err.println("Unable to reach LRM " + lrmIor + ".");
					System.err.println("Removing it from database.");
					
					traderManager.removeLrmInformation(lrmIor);
				}
			}
		}
	}
	
	// --------------------------
	// Setters for timeout values
	// --------------------------
	
	// Sets the interval between checks for significant resource availability
	// changes in the children GRMs. In case they exist, the parent GRM will
	// be notified
	//
	// @param seconds - New interval in seconds
	
	public void setSampleInterval(int seconds) {
		this.sampleInterval = seconds;
	}
	
	// Sets the maximum interval between updates sent to the parent GRM.
	//
	// @param seconds - New interval in seconds
	
	public void setKeepAliveInterval(int seconds) {
		this.keepAliveInterval = seconds;
	}
	
	// --------------------------------
	// Setter for the Execution Manager
	// --------------------------------
	
	// Sets the execution manager that is nofified when an execution starts
	//
	// @param executionManager - The reference to the new execution manager
	
	public void setExecutionManager(ExecutionManager executionManager) {
		this.executionManager = executionManager;
	}
	
	// ------------------------------------------
	// Accounting of subtree resources by the GRM
	// ------------------------------------------
	
	// Calculates information averages for the subtree determined by this GRM.
	// It accounts for direct connected LRMs and its child subtrees
	//
	// @returns - A SubtreeInformation structure
	
	private SubtreeInformation calculateGrmInformation() {
		ArrayList<SubtreeInformation> fetchedLrmInformationList = traderManager.getLrmInformationTotals();
		ArrayList<SubtreeInformation> fetchedGrmInformationList = traderManager.getGrmInformationTotals();
		
		ArrayList<SubtreeInformation> fetchedInformationList = new ArrayList<SubtreeInformation>();
		
		fetchedInformationList.addAll(fetchedLrmInformationList);
		fetchedInformationList.addAll(fetchedGrmInformationList);
		
		int numberEntities = fetchedInformationList.size();
		
		SubtreeInformation subtreeInformation = new SubtreeInformation(new NodeStaticInformation(), new NodeDynamicInformation());		
		SubtreeInformation temporarySubtreeInformation;
		
		for(int subtreeInfoCounter = 0; subtreeInfoCounter < numberEntities; subtreeInfoCounter++) {
			temporarySubtreeInformation = fetchedInformationList.get(subtreeInfoCounter);
			
			subtreeInformation.staticAverages.processorMhz += temporarySubtreeInformation.staticAverages.processorMhz;
			subtreeInformation.staticAverages.totalRam += temporarySubtreeInformation.staticAverages.totalRam;
			subtreeInformation.staticAverages.totalSwap += temporarySubtreeInformation.staticAverages.totalSwap;
			subtreeInformation.dynamicAverages.freeRam += temporarySubtreeInformation.dynamicAverages.freeRam;
			subtreeInformation.dynamicAverages.freeSwap += temporarySubtreeInformation.dynamicAverages.freeSwap;
			subtreeInformation.dynamicAverages.freeDiskSpace += temporarySubtreeInformation.dynamicAverages.freeDiskSpace;
			subtreeInformation.dynamicAverages.cpuUsage += temporarySubtreeInformation.dynamicAverages.cpuUsage;
		}
		
		if(numberEntities > 0) {
			subtreeInformation.staticAverages.processorMhz /= numberEntities;
			subtreeInformation.staticAverages.totalRam /= numberEntities;
			subtreeInformation.staticAverages.totalSwap /= numberEntities;
			subtreeInformation.dynamicAverages.freeRam /= numberEntities;
			subtreeInformation.dynamicAverages.freeSwap /= numberEntities;
			subtreeInformation.dynamicAverages.freeDiskSpace /= numberEntities;
			subtreeInformation.dynamicAverages.cpuUsage /= numberEntities;
		}
		
		return subtreeInformation;
	}
	
	// Checks if two SubtreeInformation objects differ in one field in more than 10 percent
	//
	// @param subtreeInformation1 - First subtree information
	// @param subtreeInformation2 - Second subtree information
	//
	// @returns - True if at least one corresponding field of the parameters differ more than 10 percent
	
	private boolean hasSignificantChange(SubtreeInformation subtreeInformation1, SubtreeInformation subtreeInformation2) {    	
		if(subtreeInformation1 == null || subtreeInformation2 == null) {
			return true;
		}
		
		if(areTenPercentDifferent(subtreeInformation1.staticAverages.processorMhz, subtreeInformation2.staticAverages.processorMhz)) {
			return true;
		}
		
		if(areTenPercentDifferent(subtreeInformation1.staticAverages.totalRam, subtreeInformation2.staticAverages.totalRam)) {
			return true;
		}
		
		if(areTenPercentDifferent(subtreeInformation1.staticAverages.totalSwap, subtreeInformation2.staticAverages.totalSwap)) {
			return true;
		}
		
		if(areTenPercentDifferent(subtreeInformation1.dynamicAverages.freeRam, subtreeInformation2.dynamicAverages.freeRam)) {
			return true;
		}
		
		if(areTenPercentDifferent(subtreeInformation1.dynamicAverages.freeSwap, subtreeInformation2.dynamicAverages.freeSwap)) {
			return true;
		}
		
		if(areTenPercentDifferent(subtreeInformation1.dynamicAverages.freeDiskSpace, subtreeInformation2.dynamicAverages.freeDiskSpace)) {
			return true;
		}
		
		if(areTenPercentDifferent(subtreeInformation1.dynamicAverages.cpuUsage, subtreeInformation2.dynamicAverages.cpuUsage)) {
			return true;
		}    	
		
		return false;
	}
	
	// Checks if a two float division ratio is more than 10 percent
	//
	// @param float1 - First floating point data
	// @param float2 - Second floating point data
	//
	// @returns - True if the float division ratio (any order of operands) is more than 10 percent.
	//            False, otherwise
	
	private boolean areTenPercentDifferent(float float1, float float2) {
		float ratio = float1 / float2;
		
		if(ratio >= 1.1 || ratio <= 0.9) {
			return true;
		}
		
		return false;
	}
	
	// --------------------------------------------------
	// Parent setting, IOR fetching and availability test
	// --------------------------------------------------
	
	// Sets the parent GRM for this GRM
	//
	// @param parentGrmIor - IOR of the GRM that should be the parent
	
	public void setParentGrm(String parentGrmIor) { 
		try {
			parentGrm = GrmHelper.narrow(orb.string_to_object(parentGrmIor)); 
			
			SubtreeInformation subtreeInformation = calculateGrmInformation();
			parentGrm.registerGrm(getGrmIor(), subtreeInformation);
		}
		catch(org.omg.CORBA.TRANSIENT transientException) {
			System.err.println("Unable to reach parent GRM.");
			System.err.println("Contact your local system administrator.");
			
			parentGrm = null;
		}
	}
	
	// Fetches the parent GRM IOR
	//
	// @returns - IOR of the parent GRM
	
	public String getParentGrmIor() {
		return orb.object_to_string(parentGrm);
	}    
	
	// Fetches the GRM IOR
	//
	// @returns - IOR of the GRM
	
	public String getGrmIor() {
		return orb.object_to_string(this._this_object());
	}
	
	// Tests if this GRM is available.
	//
	// @returns - True if it is available
	
	public boolean isAvailable() {
		return true;
	}	

	
	public String migrationLocationRequest(String[] locations) {
		
		Enumeration e = mapLocations.keys();
		
		String [] iors = new String[mapLocations.size()];
		
		int j = 0;
		
		while (e.hasMoreElements()){
			String container = (String) e.nextElement();			
			
			
			for (int i =0; i < locations.length; i++ ){
				System.out.println("Container "+ container + "Location " + locations[i] );				
				if( ! (container.equals(locations[i])) ){
					
					System.out.println("Ior" + mapLocations.get(container) );
					iors[j] = mapLocations.get(container);
					j++;
				}
			}			
		}		
		// Vinicius { tenta migrar pra outra máquina. Se não tiver pra onder migrar, fica no mesmo container
		if(j == 0) return locations[0];
		int index = rand.nextInt(j);
//		return iors[0];
		return iors[index];
		// } Vinicius
	}	
	

	public void setLocation(String location, String ior) {
		mapLocations.put(location, ior);
		System.out.println("Adicionando  Container " + location + ior); 
		
	}
}
