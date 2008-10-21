package grm.ckpReposManager;

import java.util.LinkedList;

/**
 * Contains information about all checkpoints from an execution.
 * Is kept as a filed in the class ExecutionInformation from the ExecutionManager.
 */
public class ExecutionCheckpoints {
       
    private final int numberOfStoredCheckpoints = 3; 
    
    LinkedList<GlobalCkpInformation> ckpDataList;    
    int nProcesses;
    String requestId;
    
	CkpReposStoreMode ckpReposStoreMode = CkpReposStoreMode.single;
	int nRecover = 1; 
	int nExtra = 0;
    
    public ExecutionCheckpoints(String requestId, int nProcesses) {
        ckpDataList = new LinkedList<GlobalCkpInformation>();
        this.nProcesses = nProcesses;
        this.requestId = requestId;
    }

    //------------------------------------------------------------------------
    /**
     * Should be called when an execution finishes.
     * It notifies all ckpRepositories holding checkpoints from this execution to remove them.
     */
    synchronized public void setExecutionFinished() {
        
        System.err.println("Request " + requestId + " finished. Marking checkpoints for removal.");
        
        for (GlobalCkpInformation ckpInformation : ckpDataList)
            ckpInformation.setCheckpointsForRemoval(requestId);
    }
    
    //------------------------------------------------------------------------
    
    synchronized public int addExecutionCheckpoint(int ckpNumber, int nodeId, String[] checksum, int ckpSize) {

        int lastCkpNumber = -1;
        if (ckpDataList.size() > 0)
            lastCkpNumber = ckpDataList.getLast().getCkpNumber();
        
        GlobalCkpInformation ckpData = null;
        if (ckpNumber > lastCkpNumber)
            while (ckpNumber > lastCkpNumber) {
              ckpData = new GlobalCkpInformation(nProcesses, ++lastCkpNumber, nRecover+nExtra);
              ckpDataList.addLast(ckpData);
            }
        else 
            ckpData = ckpDataList.getLast(); 

        ckpData.setChecksum(nodeId, checksum, ckpSize);

        System.err.println("Created new global checkpoint for ckpNumber " + ckpNumber + ".");
        while (ckpNumber - ckpDataList.peek().getCkpNumber() >= numberOfStoredCheckpoints ) {
            GlobalCkpInformation globalCkpData = ckpDataList.poll();
            System.err.println("Removing checkpoints from ckpNumber " + globalCkpData.getCkpNumber() + ".");
            globalCkpData.setCheckpointsForRemoval(requestId);
        }

    	return 0;
    }
    
    //------------------------------------------------------------------------
    
    synchronized public int addCkpLocation(int processId, CkpRepository ckpRepos, int ckpNumber, int fragmentNumber) {

        GlobalCkpInformation ckpData = ckpDataList.getLast();
        if ( ckpData != null && ckpData.getCkpNumber() == ckpNumber ) {            
            ckpData.addCkpRepos(ckpRepos, processId, fragmentNumber);
            if ( ckpData.isComplete() )
                System.out.println("notifyCkpStored --> Finished storing checkpoint " + ckpNumber + " from request " + requestId + ".");
            return 0;
        }
        else {            
            System.err.println("CkpReposManager -> Error adding checkpoint location. Checkpoint " + ckpNumber + " is not registered.");            
            return -1;
        }                
    }

    //------------------------------------------------------------------------------------
    synchronized public GlobalCkpInformation getLastCkpInfo () {

        if (ckpDataList.size() == 0) return null;

        GlobalCkpInformation ckpData = ckpDataList.getLast();        
        if (ckpData.isComplete() == true)
            return ckpData;

        ckpDataList.removeLast();
        while (ckpDataList.size() > 0) {
            ckpData = ckpDataList.getLast();
            if (ckpData.isComplete() == true) 
                return ckpData;  
            ckpDataList.removeLast();
        }

        return null;        
    }
    
} // class
