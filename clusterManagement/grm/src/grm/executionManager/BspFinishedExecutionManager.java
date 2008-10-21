package grm.executionManager;

import grm.executionManager.ExecutionInformation;

import java.util.Map;
import org.omg.CORBA.ORB;

import clusterManagement.Grm;

import dataTypes.ExecutionRequestId;

public class BspFinishedExecutionManager extends StandardFinishedExecutionManager {

    //--------------------------------------------------------------------------------------
    
    BspFinishedExecutionManager(Map<String, ExecutionInformation> currentExecutionsMap, ORB orb, Grm grm) {
        super(currentExecutionsMap, orb, grm);
    }

    //--------------------------------------------------------------------------------------
    
    int processRestart(ExecutionRequestId executionRequestId, ExecutionInformation appInfo) {
        appInfo.bspRestartCoordinator.restartBspApplication(executionRequestId, appInfo, true);
        return 0;
    }

    //--------------------------------------------------------------------------------------
    
    public int treatFinishedExecution(ExecutionInformation appInfo, 
    		ExecutionRequestId executionRequestId, int restartId, int status) {

        /** Only one process from an execution shall pass each time */        
        appInfo.isActiveLock.lock();
        try {
            
            /** This is a failure of the next iteration.
             *  Must wait until all processes from the previous iteration start. */        
            if (restartId == appInfo.currentRestart + 1) {
                try {appInfo.nextRestartCondition.await();}
                catch (Exception e) {e.printStackTrace();}
            }
           
            /** Process that finished during a restart process */
            if (appInfo.isRestarting == true) {
                appInfo.bspRestartCoordinator.restartBspApplication(executionRequestId, appInfo, false);
            }
            /** Process that finished due to a error during the abort process */
            else if (appInfo.isAborting == true) {
                appInfo.processLocationMap.remove(executionRequestId.processId);
            }
            /** Process finished */
            else {                
                if (status == 0)     
                    processNormalExit(executionRequestId, appInfo);
                else if (status < 0) { /** TODO: Insert syncronization here */
                    appInfo.isAborting = true;
                    processAbnormalExit(executionRequestId, appInfo);
                }
                else if (status > 0) {
                    appInfo.isRestarting = true;
                    appInfo.bspRestartCoordinator.restartBspApplication(executionRequestId, appInfo, true);
                }
            }            
                        
            return 0;
        }
        finally{ appInfo.isActiveLock.unlock(); }                
    }

}
