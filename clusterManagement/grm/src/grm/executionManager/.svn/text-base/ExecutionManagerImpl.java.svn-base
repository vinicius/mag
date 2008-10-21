package grm.executionManager;

import grm.ckpReposManager.ExecutionCheckpoints;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.omg.CORBA.ORB;

import clusterManagement.ExecutionManagerPOA;
import clusterManagement.Grm;
import dataTypes.ApplicationExecutionInformation;
import dataTypes.ApplicationType;
import dataTypes.BspProcessZeroInformation;
import dataTypes.ExecutionRequestId;
import dataTypes.ProcessExecutionInformation;

/** Flag to display debugging info. */
interface ExecutionManagerDebugFlag {
    public static final boolean debug = true;
}

public class ExecutionManagerImpl extends ExecutionManagerPOA {  
    
    public ConcurrentHashMap<String, ExecutionInformation> currentExecutionsMap;
    Grm grm; // Reference to the GRM corba object
    ORB orb; // Reference to the ORB
    BspFinishedExecutionManager bspFinishedExecutionManager; 
    StandardFinishedExecutionManager standardFinishedExecutionManager;
    
    public ExecutionManagerImpl(ORB orb, Grm grm) {
        this.orb = orb;
        this.grm = grm;
        this.currentExecutionsMap = new ConcurrentHashMap<String, ExecutionInformation>();
        this.bspFinishedExecutionManager       = new BspFinishedExecutionManager(currentExecutionsMap, orb, grm);
        this.standardFinishedExecutionManager  = new StandardFinishedExecutionManager(currentExecutionsMap, orb, grm);
    }

    /** ---------------------------------------------------------------------------
     *  ExecutionManager Methods
     *  ---------------------------------------------------------------------------
     **/

    
    // TODO: Check if only part of the processes from an application
    // Insert a timer that periodically checks each scheduled application application 
    
    public void setExecutionScheduled(ApplicationExecutionInformation applicationExecutionInformation,
            ProcessExecutionInformation[] processExecutionInformationList) {
        
        if (ExecutionManagerDebugFlag.debug)
            System.err.println(">>>>> ExecutionManagerImpl.reportExecutionScheduled--> applicationId:" + 
            		processExecutionInformationList[0].executionRequestId.requestId);        
                
        String requestId = processExecutionInformationList[0].executionRequestId.requestId;        
        
        ExecutionInformation appInfo = new ExecutionInformation();
        if (currentExecutionsMap.putIfAbsent(requestId, appInfo) == null) {                        
            appInfo.applicationInformation   = applicationExecutionInformation;
            appInfo.processInformationList = processExecutionInformationList;
            appInfo.processLocationMap = new HashMap<Integer, String[]>(processExecutionInformationList.length);
            appInfo.checkpoints = new ExecutionCheckpoints(requestId, processExecutionInformationList.length);
            
            if (applicationExecutionInformation.applicationType == ApplicationType.bsp) {
                appInfo.bspRestartCoordinator = new BspRestartCoordinator(orb, grm, processExecutionInformationList.length);      
                appInfo.isActiveLock = new ReentrantLock();
                appInfo.nextRestartCondition = appInfo.isActiveLock.newCondition();
                appInfo.waitingResponsesLock = new ReentrantLock();
                appInfo.finishedResponsesCondition = appInfo.waitingResponsesLock.newCondition();                
            }
        }

    }

    //--------------------------------------------------------------------------------------
    
    public void setProcessExecutionStarted(String lrmIor, String executionId,
            int restartId, ExecutionRequestId executionRequestId) {
                
        ExecutionInformation appInfo = currentExecutionsMap.get(executionRequestId.requestId);
        if (appInfo == null) {
            /** TODO: We should treat this error better. It would be a good idea to kill the process in the LRM. */
            System.err.println("ExecutionManagerImpl.reportExecutionStarted --> ERROR: Couldn't find execution info.");
            System.err.println("appId:" + executionId + " asctRequestId:" + executionRequestId.requestId+ "|" + executionRequestId.processId);
        }

        int processId = Integer.parseInt(executionRequestId.processId);
        appInfo.processLocationMap.put(processId, new String[] {lrmIor, executionId});
        int nRestarts = appInfo.nRestarts.incrementAndGet();

        if (ExecutionManagerDebugFlag.debug)
            System.err.println(">>>>> ExecutionManagerImpl.reportExecutionStarted-->asctRequestId:" + executionRequestId.requestId + "|" + executionRequestId.processId +  ", nRestarts=" + nRestarts);       
        
        // Waits for all responses from appInfo 
        while (appInfo.isRestarting == true && appInfo.nResponses.get() < appInfo.processInformationList.length) {
            try {Thread.sleep(100);} 
            catch (InterruptedException e) {}
        }
            
        if (nRestarts == appInfo.processInformationList.length) {
            System.out.println("ExecutionManagerImpl.reportExecutionStarted--> Execution Started [" + executionRequestId.requestId + "," + restartId + "]");                
            appInfo.currentRestart++;
            if (appInfo.isActiveLock != null) {
                appInfo.isActiveLock.lock();
                try{
                    if (ExecutionManagerDebugFlag.debug)
                        System.out.println("Finished Starting");
                    appInfo.isRestarting = false;
                    appInfo.nextRestartCondition.signalAll();
                }
                finally{ appInfo.isActiveLock.unlock(); }
            }
        }
 
    }

    //--------------------------------------------------------------------------------------
    
    public int setProcessExecutionFinished(int restartId, ExecutionRequestId executionRequestId, int status) {
                
        if (ExecutionManagerDebugFlag.debug)
            System.err.println(">>>>> ExecutionManagerImpl.reportExecutionFinished-->asctRequestId: " + executionRequestId.requestId + "|" + executionRequestId.processId + " exitCode:" + status + " restart: {" + restartId + "}");

        /** Get the appInfo for the finished process */
        ExecutionInformation appInfo = currentExecutionsMap.get(executionRequestId.requestId);
        if (appInfo == null) {
            System.err.println("ExecutionManagerImpl.reportExecutionFinished --> ERROR: No existing execution." + " asctRequestId:" + executionRequestId.requestId + "|" + executionRequestId.processId);
            return -1;
        }
        
        if (appInfo.applicationInformation.applicationType == ApplicationType.bsp)
            bspFinishedExecutionManager.treatFinishedExecution(appInfo, executionRequestId, restartId, status);
        else
            standardFinishedExecutionManager.treatFinishedExecution(appInfo, executionRequestId, restartId, status);
        
        return 0;
    }

    //--------------------------------------------------------------------------------------
    
    public BspProcessZeroInformation registerBspProcess(ExecutionRequestId executionRequestId,
            String bspProxyIor) {
        
        /** Get the appInfo for the finished process */
        ExecutionInformation appInfo = currentExecutionsMap.get(executionRequestId.requestId);
        if (appInfo == null) {
            System.err.println("ExecutionManagerImpl.registerBspNode --> ERROR: No existing execution." + " asctRequestId:" + executionRequestId.requestId + "|" + executionRequestId.processId);
            return new BspProcessZeroInformation();
        }
        
        return appInfo.bspRestartCoordinator.registerBspNode(executionRequestId, bspProxyIor);
    }
    
    //--------------------------------------------------------------------------------------
}
