/**
 * @(#)ExecutionRequestManager.java		Dec 20, 2005
 *
 * Copyleft
 */
package asct.core.submission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import resourceProviders.Lrm;
import asct.core.corba.GrmStubWrapper;
import asct.core.corba.OrbHolder;
import asct.core.util.Util;
import asct.shared.AbstractGridApplication;
import asct.shared.ApplicationState;
import asct.shared.BspGridApplication;
import asct.shared.ExecutionRequestData;
import asct.shared.ExecutionRequestStatus;
import asct.shared.IExecutionListener;
import asct.shared.ParametricCopyHolder;
import asct.shared.ParametricGridApplication;
import asct.shared.SequencialGridApplication;
import dataTypes.ApplicationExecutionInformation;
import dataTypes.ApplicationType;
import dataTypes.ExecutionRequestId;
import dataTypes.FileStruct;
import dataTypes.ProcessExecutionInformation;
import dataTypes.RequestAcceptanceInformation;

/**
 * Class description goes here.
 * 
 * @version 1.0 Dec 20, 2005
 * @author Eduardo Guerra and Eudenia Xavier
 */
public class ExecutionRequestManager {

	/** */
	private GrmStubWrapper grmStubWrapper;
	
	/**  */
	private Vector<ExecutionRequest> requests;

	/**  */
	private List<IExecutionListener> executionListeners;
	
	/** Hold the orb and the stubs. */
	private OrbHolder orbHolder;
	
	/** The local directory to store input, output and other files. */
	private String localDirectory;
	
	/**
	 * Constructor
	 */
	public ExecutionRequestManager(OrbHolder orbHolder, String localDirectory) {
		this.grmStubWrapper = orbHolder.getGrmStubWrapper();
		this.orbHolder = orbHolder;
		
		this.localDirectory = localDirectory;
		
		this.executionListeners = new LinkedList<IExecutionListener>(); 
		this.requests = new Vector<ExecutionRequest>();
	}

	/************************  Grm Client *****************************/

	/** 
	 * 
	 * */
	public ExecutionRequestStatus executeApplication(
				ExecutionRequestData data) {
		AbstractGridApplication application = data.getApplication();
		
	    if (application instanceof SequencialGridApplication) {
			return executeSequencialApplication(data, 
						application.getBinaryIds());
			
		} else if (application instanceof ParametricGridApplication) {
			return executeParametricApplication(data, 
						application.getBinaryIds());
			
		} else if (application instanceof BspGridApplication) {
			return executeBspApplication(data, 
						application.getBinaryIds());
			
		}
	    return null;
	}
	
	/** 
	 * 
	 * */
	private ExecutionRequestStatus executeSequencialApplication(
				ExecutionRequestData data, String[] binaries) {
		/* Add the request to the resquest list */
		ExecutionRequest execRequest = 
				new ExecutionRequest(data.getApplication());
		
	    synchronized (requests) { 
	    		requests.add(execRequest);
	    }
	    String requestId = String.valueOf(requests.indexOf(execRequest));

		/* Prepare parameters to the request */

	    ApplicationExecutionInformation appExecInfo = 
	    				new ApplicationExecutionInformation();
	    appExecInfo.applicationType = ApplicationType.regular;
	    appExecInfo.applicationConstraints = data.getConstraints();
	    appExecInfo.applicationPreferences = data.getPreferences();
	    appExecInfo.requestingAsctIor = orbHolder.getAsctIor();
	    appExecInfo.originalGrmIor = orbHolder.getGrmIor();
	    appExecInfo.forceDifferentNodes = data.isForcedOnDifferentNodes();

		appExecInfo.basePath = data.getApplication().getBasePath();
		appExecInfo.applicationName = data.getApplication().getName();
		appExecInfo.availableBinaries = binaries;
		appExecInfo.applicationRepositoryIor = orbHolder
				.getApplicationRepositoryIor();
		
		appExecInfo.numberOfReplicas = String.valueOf(data.getNumberOfTasks()); // Vinicius

		ProcessExecutionInformation[] processExecInfo = new ProcessExecutionInformation[1];
		processExecInfo[0] = new ProcessExecutionInformation();
		processExecInfo[0].executionRequestId = new ExecutionRequestId();
		processExecInfo[0].executionRequestId.requestId = requestId;
		processExecInfo[0].executionRequestId.processId = String.valueOf(0);
		processExecInfo[0].outputFileNames = data.getOutputFileNames();
		processExecInfo[0].processArguments = data.getArguments();

		/* Request the execution */
		grmStubWrapper.remoteExecutionRequest(appExecInfo, processExecInfo);

		/* Add the request itens to the request */
		ExecutionRequestItem execRequestItem = new ExecutionRequestItem(data
				.getInputFiles());
		execRequest.addRequestItem(execRequestItem);

		return new ExecutionRequestStatus(data.getApplication().getName(),
				ApplicationState.EXECUTING, requestId);



    		/* Vinicius { */
//    	    appExecInfo.numberOfReplicas = String.valueOf(data.getNumberOfTasks());
//    	    ProcessExecutionInformation[] processExecInfo = 
//    			new  ProcessExecutionInformation[data.getNumberOfTasks()];
//    	    
//    	for (int i = 0; i < processExecInfo.length; i++) {
    		/* } Vinicius {
	    	ProcessExecutionInformation[] processExecInfo =
	    					new  ProcessExecutionInformation[1]; Vinicius
    		processExecInfo[0] = new ProcessExecutionInformation();
    		processExecInfo[0].executionRequestId = new ExecutionRequestId();
    		processExecInfo[0].executionRequestId.requestId = requestId;
    		processExecInfo[0].executionRequestId.processId = String.valueOf(0); 
    		processExecInfo[0].outputFileNames = data.getOutputFileNames(); 
    		processExecInfo[0].processArguments = data.getArguments();
    		/* } Vinicius { */
//    		processExecInfo[i] = new ProcessExecutionInformation();
//    		processExecInfo[i].executionRequestId = new ExecutionRequestId();
//    		processExecInfo[i].executionRequestId.requestId = requestId;
//    		processExecInfo[i].executionRequestId.processId = String.valueOf(0);
//    		processExecInfo[i].executionRequestId.replicaId = String.valueOf(i);
//    		processExecInfo[i].outputFileNames = data.getOutputFileNames(); 
//    		processExecInfo[i].processArguments = data.getArguments();
//    		 
    		/* Add the request itens to the request */
//    		ExecutionRequestItem execRequestItem = 
//    						new ExecutionRequestItem(data.getInputFiles());
//    		execRequest.addRequestItem(execRequestItem);
    		/* } Vinicius */
    		
//    	}
	    
		/* Request the execution */
//	    grmStubWrapper.remoteExecutionRequest(appExecInfo, processExecInfo);

	    
		/* Add the request itens to the request */
	    /* } Vinicius {
		ExecutionRequestItem execRequestItem = 
						new ExecutionRequestItem(data.getInputFiles());
		execRequest.addRequestItem(execRequestItem);
		/* } Vinicius */

//		return new ExecutionRequestStatus(data.getApplication().getName(), 
//					ApplicationState.EXECUTING,requestId);
	}
	
	private ExecutionRequestStatus executeParametricApplication(
			ExecutionRequestData data, String[] binaries) {
		/* Add the request to the resquest list */

		ExecutionRequest execRequest = 
				new ExecutionRequest(data.getApplication());
		synchronized (requests) { 
	    		requests.add(execRequest);
	    }
	    String requestId = String.valueOf(requests.indexOf(execRequest));

		/* Prepare parameters to the request */

	    ApplicationExecutionInformation appExecInfo = 
	    				new ApplicationExecutionInformation();
	    appExecInfo.applicationType = ApplicationType.parametric;
	    appExecInfo.applicationConstraints = data.getConstraints();
	    appExecInfo.applicationPreferences = data.getPreferences();
	    appExecInfo.requestingAsctIor = orbHolder.getAsctIor();
	    appExecInfo.originalGrmIor = orbHolder.getGrmIor();
	    appExecInfo.forceDifferentNodes = data.isForcedOnDifferentNodes();
	    
	    appExecInfo.numberOfReplicas = String.valueOf(data.getNumberOfTasks()); // Vinicius
	    
	    appExecInfo.basePath = data.getApplication().getBasePath();
	    appExecInfo.applicationName = data.getApplication().getName();
	    appExecInfo.availableBinaries = binaries;
	    
	    

	    ProcessExecutionInformation[] processExecInfo = 
			new  ProcessExecutionInformation[data.getNumberOfCopies()];
	    
	    ParametricCopyHolder parametricCopyHolder;
	    
	    for (int i = 0; i < processExecInfo.length; i++) {
			processExecInfo[i] = new ProcessExecutionInformation();
			processExecInfo[i].executionRequestId = new ExecutionRequestId();
			processExecInfo[i].executionRequestId.requestId = requestId;
			processExecInfo[i].executionRequestId.processId = 
					String.valueOf(i); 
			
			// Get the args, input and output files for one process
			parametricCopyHolder = data.getParametricCopies()[i];
			processExecInfo[i].outputFileNames = 
						parametricCopyHolder.getOutputFiles(); 
			processExecInfo[i].processArguments = 
						parametricCopyHolder.getArguments();
			
			/* Add the request itens to the request */
			
			ExecutionRequestItem execRequestItem = 
						new ExecutionRequestItem(
								parametricCopyHolder.getInputFiles());
			execRequest.addRequestItem(execRequestItem);

		}
		
		/* Request the execution */
		grmStubWrapper.remoteExecutionRequest(appExecInfo, processExecInfo);
		
		return new ExecutionRequestStatus(data.getApplication().getName(), 
				ApplicationState.EXECUTING,requestId);

	}

	/** 
	 * 
	 * */
	private ExecutionRequestStatus executeBspApplication(
				ExecutionRequestData data, String[] binaries) {
		/* Add the request to the resquest list */

		ExecutionRequest execRequest = 
					new ExecutionRequest(data.getApplication());
	    synchronized (requests) { 
	    		requests.add(execRequest);
	    }
	    String requestId = String.valueOf(requests.indexOf(execRequest));

	    /* Create temporary bsp descriptor files */
        String[] bspDescriptorPath = new String[data.getNumberOfTasks()];
        createTmpBspDesciptorFiles(bspDescriptorPath, requestId);

	    
		/* Prepare parameters to the request */

	    ApplicationExecutionInformation appExecInfo = 
	    				new ApplicationExecutionInformation();
	    appExecInfo.applicationType = ApplicationType.bsp;
	    appExecInfo.applicationConstraints = data.getConstraints();
	    appExecInfo.applicationPreferences = data.getPreferences();
	    appExecInfo.requestingAsctIor = orbHolder.getAsctIor();
	    appExecInfo.originalGrmIor = orbHolder.getGrmIor();
	    appExecInfo.forceDifferentNodes = data.isForcedOnDifferentNodes();
	    
	    
	    appExecInfo.basePath = data.getApplication().getBasePath();
	    appExecInfo.applicationName = data.getApplication().getName();
	    appExecInfo.availableBinaries = binaries;


	    ProcessExecutionInformation[] processExecInfo = 
			new  ProcessExecutionInformation[data.getNumberOfTasks()];
	    
	    for (int i = 0; i < processExecInfo.length; i++) {
			processExecInfo[i] = new ProcessExecutionInformation();
			processExecInfo[i].executionRequestId = new ExecutionRequestId();
			processExecInfo[i].executionRequestId.requestId = requestId;
			processExecInfo[i].executionRequestId.processId = 
						String.valueOf(i); 
			processExecInfo[i].outputFileNames = data.getOutputFileNames(); 
			processExecInfo[i].processArguments = data.getArguments();

			/* Add the request itens to the request */

			// misterious code:
			// creates an array incluing the inputfiles array, 
			// plus the bspdescriptor
			String[] dstInputFiles = 
					new String[data.getInputFiles().length + 1];
			System.arraycopy(data.getInputFiles(), 0, dstInputFiles, 0, 
					data.getInputFiles().length);
			dstInputFiles[dstInputFiles.length - 1] = bspDescriptorPath[i];
			// end
			
			ExecutionRequestItem execRequestItem = 
						new ExecutionRequestItem(dstInputFiles);
			execRequest.addRequestItem(execRequestItem);

		}

		/* Request the execution */
		grmStubWrapper.remoteExecutionRequest(appExecInfo, processExecInfo);
		
		return new ExecutionRequestStatus(data.getApplication().getName(), 
				ApplicationState.EXECUTING,requestId);
	}
	
	/**
	 * 
	 * */
	private void createTmpBspDesciptorFiles(String[] bspDescriptorPath, 
				final String requestId) {
		String strBspConfDir = localDirectory + "bspConfs" ;
        String strBspDescriptorDir = strBspConfDir
		+ System.getProperty("file.separator") 
		+ requestId;
        
        File tmpBspDir = new File(strBspDescriptorDir);
        
        if (!tmpBspDir.exists()) {
        		if (!tmpBspDir.mkdirs()) {
        			System.err.println("Cannot create dir");
        		}
        }
        
        for (int i = 0; i < bspDescriptorPath.length; i++) {
        	
        	String bspDescriptorDir = 
        		strBspDescriptorDir + System.getProperty("file.separator") + i;
        	
        	bspDescriptorPath[i] = bspDescriptorDir 
        				+ System.getProperty("file.separator") 
        				+ "bspExecution.conf";
        	
        	try {
        		File bspDir = new File(bspDescriptorDir);
        		if (!bspDir.exists()) {
        			if (!bspDir.mkdirs()) {
        				System.err.println("Cannot create dir");
        			}
        		}
        		
        		PrintWriter ps = new PrintWriter(new FileOutputStream(
        				new File(bspDescriptorPath[i])));
        		
        		ps.println("applicationId " + requestId);
        		ps.println("processId " + i);
        		ps.println("numExecs " + bspDescriptorPath.length);
        		ps.close();
        	} catch (FileNotFoundException fnfe) {
        		fnfe.printStackTrace();
        		System.exit(-1);
        	}
        }
	}
	
	/** 
	 * 
	 * */
	public ExecutionRequestStatus executeBinary(ExecutionRequestData data,
			String binaryFileName) {
		AbstractGridApplication application = data.getApplication();
		
	    if (application instanceof SequencialGridApplication) {
			return executeSequencialApplication(data, 
						new String[] {binaryFileName});
			
		} else if (application instanceof ParametricGridApplication) {
			return executeParametricApplication(data, 
						new String[] {binaryFileName});
			
		} else if (application instanceof BspGridApplication) {
			return executeBspApplication(data,
						new String[] {binaryFileName});
		}
	    return null;
	}
	
	/************************* LRM Client ************************/
	
	/** 
	 * @param 
	 * */
	public void killApplication(String requestId) {
		ExecutionRequest executionRequest;
		
	    synchronized (requests) {
	    		executionRequest = (ExecutionRequest) requests.elementAt((new Integer(requestId)).intValue());
	    }
		
        synchronized (requests) {
        		requests.remove(requestId);
        }
        
        ExecutionRequestItem[] subRequests = executionRequest.getRequestItems();

        for (int i = 0; i < subRequests.length; i++) {
            Lrm lrm = orbHolder.getLrmObject(subRequests[i].getLrmIor());
            lrm.killProcess(subRequests[i].getRemoteRequestId());
        }

	}
	
	/** 
	 * 
	 * */
	public void getApplicationResults(final String executionRequestId) {
		String resultsFolderName = localDirectory + executionRequestId; 
		
		File resultsFolder = new File(resultsFolderName);
		if (!resultsFolder.exists() && !resultsFolder.mkdirs()) {
			System.err.println("Directory creation failed: " 
					+ executionRequestId);
			return;
		}
		
		ExecutionRequest executionRequest;
		ExecutionRequestItem[] subRequests;
		
		synchronized (requests) {
			executionRequest = (ExecutionRequest) requests.elementAt(
					Integer.parseInt(executionRequestId));
			subRequests = executionRequest.getRequestItems();
		}
		
		if (Util.DEBUG) {
			System.out.println("Requesting results from " + subRequests.length 
					+ " nodes");
		}
		for (int i = 0; i < subRequests.length; i++) {
			// Vinicius {
			String subfolder = (resultsFolderName) + "/" + i;
//			String subfolder = (resultsFolderName) + ":" + i;
			// } Vinicius
			File subFolderFile = new File(subfolder);
			if (!subFolderFile.exists() && !subFolderFile.mkdirs()) {
				System.err.println("Directory creation failed: " + 
						subfolder);
			}
			
			Lrm lrm = orbHolder.getLrmObject(subRequests[i].getLrmIor());
			
			FileStruct[] returnFiles = lrm.requestOutputFiles(
					subRequests[i].getRemoteRequestId());
			for (int j = 0; j < returnFiles.length; j++) {
				try {
					FileOutputStream fos = new FileOutputStream(subfolder + "/" 
							+ returnFiles[j].fileName);
					fos.write(returnFiles[j].file);
					fos.close();
				} catch (IOException ioe) {
					System.err.println("collectResults-->> Write failed");
				}
			}
		}
		
	} 
	
	/************************* Asct Server ************************/

	/**
	 * 
	 * */
	public void acceptedExecRequest(RequestAcceptanceInformation acceptanceInfo) {

		ExecutionRequest request;
        synchronized (requests) {
            request = (ExecutionRequest) requests.elementAt(Integer.parseInt(
            		acceptanceInfo.executionRequestId.requestId));
        }
        
        request.fillSubRequest(acceptanceInfo.executionRequestId.processId,
        					acceptanceInfo.lrmIor,
        					acceptanceInfo.executionId);
        
        /* Notify listeners */ 
        
        if (request.isNoPendingRequestsLeft()) {
        	notifyExecutionListeners(acceptanceInfo.executionRequestId, 
        				ApplicationState.EXECUTING);
        }
    }
    
	/**
	 * 
	 * */
    public void refusedExecRequest(ExecutionRequestId requestId) {
		ExecutionRequest request;
        synchronized (requests) {
            request = (ExecutionRequest) requests.get(
            		Integer.valueOf(requestId.requestId));
        }
        
        if (request == null) {
        	return;
        }

        request.setExecRefused();
        notifyExecutionListeners(requestId, ApplicationState.REFUSED);
    }    

	/**
	 * 
	 * */
    public String[] getAppInputFiles(ExecutionRequestId requestId){
		ExecutionRequest request;
        synchronized (requests) {
            request = (ExecutionRequest) requests.get(
            		Integer.valueOf(requestId.requestId));
        }

        ExecutionRequestItem requestItem = 
        		request.getRequestItem(Integer.valueOf(requestId.processId));
        return requestItem.getInputFiles();
    }
    
	/**
	 * 
	 * */
    synchronized public void nodeFinished(ExecutionRequestId requestId) {
		ExecutionRequest request;
		
        synchronized (requests) {
            request = (ExecutionRequest) requests.get(
            		// Vinicius {
            		Integer.valueOf(requestId.requestId));
//            		Integer.valueOf(requestId.requestId.split(":")[2]));
            		// } Vinicius
        }                   

        if (request == null) {
		System.out.println("##### O REQUEST E  NULO!! ######");
        	return;
        } else {
        	System.out.println("##### O REQUEST NAO E  NULO!! ######");
        }

        request.setNodeFinished(Integer.valueOf(requestId.processId));

        if (request.isAllNodesFinished() && (!request.isCollectedResults())) {
        	// Vinicius {
        	getApplicationResults(requestId.requestId);
//        	getApplicationResults(requestId.requestId.split(":")[2]);
        	// } Vinicius
        	request.setCollectedResults(true);
        	
        }
        // Vinicius {
//        requestId.requestId = requestId.requestId.split(":")[2];
        // } Vinicius
    	notifyExecutionListeners(requestId, ApplicationState.FINISHED);

	}
	
	/************************* UI Interaction *********************/
	
	/** 
	 * 
	 * */
	public void registerExecutionStateListener(IExecutionListener listener) {
		executionListeners.add(listener);
	} 

	/** 
	 * 
	 * */
	public void notifyExecutionListeners(ExecutionRequestId execRequestId, 
			ApplicationState status) {
		Iterator iter = executionListeners.iterator();
		
		while (iter.hasNext()) {
			IExecutionListener listener = (IExecutionListener) iter.next();
			listener.updateStatus(execRequestId, status);
		}
	} 

}
