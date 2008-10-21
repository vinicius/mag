package core.execman.ema;

//import grm.ckpReposManager.ExecutionCheckpoints;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import dataTypes.ApplicationExecutionInformation;
import dataTypes.ProcessExecutionInformation;

public class ExecutionInformation {
	
    /** processId x {lrmIor, executionId} */
    Map<Integer, String[]> processLocationMap;
    
    ApplicationExecutionInformation applicationInformation;   
    public ProcessExecutionInformation[] processInformationList;        
    //public ExecutionCheckpoints checkpoints;
    
    //BspRestartCoordinator bspRestartCoordinator;
 
    int currentRestart = -1;
    boolean isRestarting = false;
    boolean isAborting   = false;
    
    AtomicInteger nRestarts  = new AtomicInteger(0);
    AtomicInteger nResponses = new AtomicInteger(0);
    
    Lock isActiveLock;
    Condition nextRestartCondition;        
    Lock waitingResponsesLock;
    Condition finishedResponsesCondition;
    
}
