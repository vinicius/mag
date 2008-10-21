package grm.executionManager;

import grm.executionManager.ExecutionInformation;

import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

import org.omg.CORBA.ORB;

import resourceProviders.Lrm;
import resourceProviders.LrmHelper;
import clusterManagement.Grm;
import dataTypes.ApplicationExecutionInformation;
import dataTypes.BspProcessZeroInformation;
import dataTypes.ExecutionRequestId;
import dataTypes.ProcessExecutionInformation;

/** A class is created for every restart */
public class BspRestartCoordinator {
    
    private ORB orb;
    private Grm grm;
    private int nProcesses;
    
    private BspFindGlobalCkpNumber bspFindCheckpointNumber;
    private CyclicBarrier barrier;
    private String bspProcessZeroIor;    
        
    //---------------------------------------------------------------------------------------
    
    public BspRestartCoordinator(ORB orb, Grm grm, int nProcesses) {
        bspProcessZeroIor = null;                
        barrier = new CyclicBarrier(nProcesses);
        this.orb = orb; this.grm = grm;
        this.nProcesses = nProcesses;
    }
    
    //---------------------------------------------------------------------------------------
    public void restartBspApplication(ExecutionRequestId executionRequestId, ExecutionInformation appInfo, 
    							      boolean restartAll) {        

        if (restartAll == true) {
            bspFindCheckpointNumber = new BspFindGlobalCkpNumber(nProcesses);
            appInfo.nRestarts.set(0);
            appInfo.nResponses.set(0);
            int nWaiting = barrier.getNumberWaiting();            
            barrier.reset();
            if (nWaiting > 0)
                barrier = new CyclicBarrier(nProcesses);
        }
        else {
            if (appInfo.nResponses.get() < appInfo.processInformationList.length) {  
              /** Wait until all LRM respond to the restart method */
              appInfo.waitingResponsesLock.lock();
              try {appInfo.finishedResponsesCondition.await();}
              catch (Exception e) {e.printStackTrace();}
              finally{appInfo.waitingResponsesLock.unlock();}
            }
            /** Descrements nResponses, so that other reschedules will wait */
            appInfo.nResponses.decrementAndGet();
        }

        if (ExecutionManagerDebugFlag.debug)
            System.err.println("<<<-->>> BspRestartCoordinator.restartBspApplication-->asctRequestId: " + executionRequestId.requestId + "|" + executionRequestId.processId + " nRestart: {" + appInfo.nRestarts + "|" + appInfo.nResponses + "}");

        /** Reschedules the killed process for execution with the GRM */        
        Integer failedNodeId = Integer.valueOf(executionRequestId.processId);                                                                   
        new BspRescheduleExecutionThread(orb, grm, bspFindCheckpointNumber, appInfo, failedNodeId).start();
        appInfo.processLocationMap.remove(failedNodeId);
        
        /** Restart the remaining processes in their currently executing nodes */
        if (restartAll == true) {        
            Iterator<Integer> it = appInfo.processLocationMap.keySet().iterator();                                   
            while (it.hasNext())
                new BspRestartExecutionThread(orb, bspFindCheckpointNumber, appInfo, it.next()).start();        
        }
               
    }
    
    //-------------------------------------------------------------------------------------
    
    public BspProcessZeroInformation registerBspNode(ExecutionRequestId executionRequestId, String bspProxyIor){                
        
        int nodeId = Integer.parseInt(executionRequestId.processId);        
        if(nodeId == 0) bspProcessZeroIor = bspProxyIor;
        
        /** Synchronization point */
        if (ExecutionManagerDebugFlag.debug) 
            System.err.println("RegisterBspNode: waiting for BSP processes, nodeId: " + nodeId);
        try {barrier.await();}
        catch(BrokenBarrierException broken) {
            System.err.println("RegisterBspNode: Barrier was broken.");
        }
        catch(Exception e) {e.printStackTrace();};
        if (nodeId == 0)
            System.err.println("RegisterBspNode: returning Process Zero IOR");               
        
        if (nodeId == 0) return new BspProcessZeroInformation(true, bspProcessZeroIor);
        else return new BspProcessZeroInformation(false, bspProcessZeroIor);
        
    }
}

/** ---------------------------------------------------------------------------
 *  BspFindGlobalCkpNumber Class
 *  ---------------------------------------------------------------------------
 * */

class CkpNumberNotAvailable extends Exception {
    private static final long serialVersionUID = -116761624795937772L;        
}

class BspFindGlobalCkpNumber { 
    
    Vector<Integer> ckpNumbers;
    int nTotal;
    int ckpNumber_;
    boolean ckpNumberAvailable = false;
    
    public BspFindGlobalCkpNumber(int nProcesses) {
        ckpNumbers = new Vector<Integer>(nProcesses);
        nTotal = nProcesses;
    }
    
    public int getCkpNumber () throws CkpNumberNotAvailable {
        if (ckpNumberAvailable == false) throw new CkpNumberNotAvailable();
        return ckpNumber_;
    }
    
    /** Adds a checkpoint number from a execution. 
     *  It automatically synchronizes the threads so that they are realeased only
     *  after a checkpoint mumber for the recovery has been determined */
    public synchronized int addCkpNumber(int ckpNumber) {
        ckpNumbers.add(new Integer(ckpNumber));
        //System.out.println("ckpNumber before:" + ckpNumber);
        int nCkp = ckpNumbers.size();
        try {
            if (nCkp < nTotal)
                wait();
            else if (nCkp == nTotal)
                findCkpNumber();
            notifyAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("ckpNumber after:" + ckpNumber_);
        return ckpNumber_;
    }
    
    private void findCkpNumber () {
        ckpNumber_ = Integer.MAX_VALUE;        
        for (int i=0; i < ckpNumbers.size(); i++) {
            if ((ckpNumbers.get(i)).intValue() < ckpNumber_ )
                ckpNumber_ = ckpNumbers.get(i).intValue();                
        }        
        ckpNumberAvailable = true;
    }
}


/** ---------------------------------------------------------------------------
 *  BspRescheduleExecutionThread Class
 *  ---------------------------------------------------------------------------
 * */

class BspRescheduleExecutionThread extends Thread {
    
    private Grm grm;
    private ProcessExecutionInformation distinctSpecs;
    private ApplicationExecutionInformation commonSpecs;
    
    private ExecutionInformation appInfo;
    private String appId;
    private AtomicInteger nResponses;
    
    BspRescheduleExecutionThread (ORB orb, Grm grm, BspFindGlobalCkpNumber findCkpNumber,
                                  ExecutionInformation appInfo, int nodeIndex) {

        this.appInfo = appInfo;
        this.commonSpecs = appInfo.applicationInformation;
        this.distinctSpecs = appInfo.processInformationList[nodeIndex];
        this.nResponses = appInfo.nResponses;
        String[] nodeInfo = appInfo.processLocationMap.get(nodeIndex);
        this.appId  = nodeInfo[1];
        
        this.grm = grm;        
    }
    
    public void run() {

    	//commonSpecs.nRestarts = (short)(appInfo.currentRestart+1);
  	
        /** Performs the remote execution request */
        grm.requestRemoteExecution(commonSpecs, new ProcessExecutionInformation[] {distinctSpecs});        

        int nResp = nResponses.incrementAndGet();
        if (nResp == appInfo.processInformationList.length) {
            appInfo.waitingResponsesLock.lock();
            try {appInfo.finishedResponsesCondition.signal();}
            finally{appInfo.waitingResponsesLock.unlock();}
        }
        
        if (ExecutionManagerDebugFlag.debug)
        	System.err.println(">>>>> RescheduleExecutionThread --> " + appId + " nResponses=" + nResponses);
    }
    
}

/** ---------------------------------------------------------------------------
 *  BspRestartExecutionThread Class
 *  ---------------------------------------------------------------------------
 * */

class BspRestartExecutionThread extends Thread {
    
    private ExecutionInformation appInfo;
    private Lrm lrm;
    private String appId;
    private ProcessExecutionInformation distinctSpecs;
    private AtomicInteger nRestarts;
    private AtomicInteger nResponses;    
    
    BspRestartExecutionThread(ORB orb, BspFindGlobalCkpNumber findCkpNumber,
                              ExecutionInformation appInfo, int nodeIndex) {

        this.appInfo = appInfo;
        this.distinctSpecs = appInfo.processInformationList[nodeIndex];
        this.nRestarts  = appInfo.nRestarts;
        this.nResponses = appInfo.nResponses;
        String[] nodeInfo = appInfo.processLocationMap.get(nodeIndex);
        this.appId  = nodeInfo[1];
               
        this.lrm = LrmHelper.narrow( orb.string_to_object( nodeInfo[0] ));
    }
    
    public void run() {

        int restartFlag=-1;
        try { 
        	restartFlag = lrm.restartExecution(appId, distinctSpecs.processArguments); 
        }
        catch (org.omg.CORBA.TRANSIENT e) {} 
        catch (org.omg.CORBA.OBJECT_NOT_EXIST e) {}        

    	/** Restarting was succesful */
        if (restartFlag==0) nRestarts.incrementAndGet();

        int nResp = nResponses.incrementAndGet();
        if (nResp == appInfo.processInformationList.length) {
            appInfo.waitingResponsesLock.lock();
            try {appInfo.finishedResponsesCondition.signal();}
            finally{appInfo.waitingResponsesLock.unlock();}
        }
        
        if (ExecutionManagerDebugFlag.debug)
            System.err.println(">>>>> RestartExecutionThread --> " + appId + "|" + restartFlag + "  nResponses=" + nResponses + " nRestarts=" + nRestarts);
    }
}

//-----------------------------------------------------------------------------------------
