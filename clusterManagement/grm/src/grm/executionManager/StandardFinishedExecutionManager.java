package grm.executionManager;

import grm.executionManager.ExecutionInformation;

import java.util.Iterator;
import java.util.Map;

import org.omg.CORBA.ORB;

import resourceProviders.Lrm;
import resourceProviders.LrmHelper;

import clusterManagement.Grm;
import dataTypes.ApplicationType;
import dataTypes.ExecutionRequestId;
import dataTypes.ProcessExecutionInformation;


public class StandardFinishedExecutionManager {

    Map<String, ExecutionInformation> currentExecutionsMap;
    ORB orb;
    Grm grm;
    
    //--------------------------------------------------------------------------------------
    
    StandardFinishedExecutionManager(Map<String, ExecutionInformation> currentExecutionsMap, ORB orb, Grm grm) {
        this.currentExecutionsMap = currentExecutionsMap;
        this.orb = orb; this.grm = grm;
    }
    
    //--------------------------------------------------------------------------------------
    
    public int treatFinishedExecution(ExecutionInformation appInfo, ExecutionRequestId executionRequestId, int restartId, int status) {

        if (status == 0)     processNormalExit(executionRequestId, appInfo);
        else if (status < 0) processAbnormalExit(executionRequestId, appInfo);
        else if (status > 0) processRestart(executionRequestId, appInfo);            

        return 0;
    }

    //--------------------------------------------------------------------------------------
    
    int processNormalExit(ExecutionRequestId executionRequestId, ExecutionInformation executionInfo) {

        executionInfo.processLocationMap.remove(Integer.parseInt(executionRequestId.processId));                
        
        /** Application is removed from currentExecutions */
        if (executionInfo.processLocationMap.size()==0) {
            System.out.println("ExecutionManagerImpl.reportExecutionFinished--> Execution Finished [" + executionRequestId.requestId + "]");
            executionInfo.checkpoints.setExecutionFinished();
            currentExecutionsMap.remove(executionRequestId.requestId);
        }
        
        return 0;
    }

    //--------------------------------------------------------------------------------------
    
    int processAbnormalExit(ExecutionRequestId executionRequestId, ExecutionInformation executionInfo) {

        /** Remove the reported node from the list, so that it will not be killed again */
        executionInfo.processLocationMap.remove(executionRequestId.processId);                   
        
        /** Kills all the remaining processes */
        Iterator<String[]> it = executionInfo.processLocationMap.values().iterator();                                     
        while (it.hasNext()) {
            String[] lrmIorApplicationId = it.next();
            Lrm lrm = LrmHelper.narrow( orb.string_to_object( lrmIorApplicationId[0] ));
            String applicationId = lrmIorApplicationId[1];               
            if (ExecutionManagerDebugFlag.debug)
                System.err.println(">>>>> ExecutionManagerImpl.processAbnormalExit--> KILLING !!!!! " + " (" + applicationId + ")");
            lrm.killProcess(applicationId);
        }                               
        
        /** Application is removed from the ExecutionManager */
        System.out.println("ExecutionManagerImpl.reportExecutionFinished--> Execution finished [" + executionRequestId.requestId + "]");
        executionInfo.checkpoints.setExecutionFinished();
        currentExecutionsMap.remove(executionRequestId.requestId);                
        
        return 0;
    }

    //--------------------------------------------------------------------------------------
    
    int processRestart(ExecutionRequestId executionRequestId, ExecutionInformation executionInfo) {
        
      if (executionInfo.applicationInformation.applicationType == ApplicationType.regular) {
          
        String[] nodeIorApplicationId = 
        	executionInfo.processLocationMap.remove(Integer.parseInt(executionRequestId.processId));                            
        Lrm lrm = LrmHelper.narrow( orb.string_to_object( nodeIorApplicationId[0] ));
        int ckpNumber = lrm.getLastCheckpointNumber(nodeIorApplicationId[1]); 
        if (ckpNumber >=0) {
          String ckpName = " -r" + ckpNumber + " -d../" + nodeIorApplicationId[1];
          executionInfo.processInformationList[0].processArguments = ckpName;
        }
        grm.requestRemoteExecution(executionInfo.applicationInformation, executionInfo.processInformationList);
        
      }

      /** Removes the node from appInfo and reschedule the execution of the finished process */
      else if (executionInfo.applicationInformation.applicationType == ApplicationType.parametric) {
          
          ProcessExecutionInformation processExecutionInformation = 
        	  executionInfo.processInformationList[Integer.parseInt(executionRequestId.processId)];
          String[] nodeIorApplicationId = 
        	  executionInfo.processLocationMap.remove(Integer.parseInt(executionRequestId.processId));                            
          Lrm lrm = LrmHelper.narrow( orb.string_to_object( nodeIorApplicationId[0] ));
          int ckpNumber = lrm.getLastCheckpointNumber(nodeIorApplicationId[1]); 
          if (ckpNumber >=0) {
              String ckpName = " -r" + ckpNumber + " -d../" + nodeIorApplicationId[1];
              processExecutionInformation.processArguments = ckpName;
          }                          
          grm.requestRemoteExecution(executionInfo.applicationInformation, 
        		  new ProcessExecutionInformation[] {processExecutionInformation});
      }

        return 0;
    }

    //--------------------------------------------------------------------------------------
    
}
