package grm.ckpReposManager;

import grm.ckpReposManager.CkpRepository.CkpReposAddress;
import grm.executionManager.ExecutionInformation;
import grm.executionManager.ExecutionManagerImpl;
import interfaces.CkpReposManagerPOA;

import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import subtypes.CheckpointRemovalList;
import subtypes.CkpReposAddressList;
import types.CkpInfo;
 
enum CkpReposStoreMode {single, replicated, parityLocal, parityGlobal, ida};

//===================================================================================

public class CkpReposManagerImpl extends CkpReposManagerPOA {

    /**
     * The maximum amount of time allowed between updates to allow the repository to be 
     * returned for checkpoint storage.
     */
    private final long repositorySelectionInterval = 1 * 60 * 1000; // 1 * 60 * 1000; // 1 minute, in ms
    
    /**
     * The maximum amount of time allowed for updates before the repository information
     * is removed from ckpRepositoryMap. This interval is checked when the methods
     * 'registerCkpRepos' or 'getCkpRepos' are called.
     */
    private final long repositoryRemovalInterval = 10 * 60 * 1000; // 1 hour, in ms
	
    /**
     * A reference to the executions map contained in the ExecutionManager
     */
    private ConcurrentHashMap<String, ExecutionInformation> currentExecutions; // appMainRequestId
       
    /**
     * Contains a mapping between ckpReposIds and CkpRepository entries
     */
    private Map<Integer, CkpRepository> ckpRepositoryMap;

    /**
     * Used to provide an unique id for new checkpointRepositories
     */
    AtomicInteger nextCkpReposId = new AtomicInteger(1);
    
    /** 
     * CkpReposManagerImpl
     */
    public CkpReposManagerImpl(ExecutionManagerImpl execManImpl) {
        this.currentExecutions = execManImpl.currentExecutionsMap;
        ckpRepositoryMap = new ConcurrentHashMap<Integer, CkpRepository>();                 
    }

    /**
     * 
     */    
    synchronized public int registerCkpRepos(String ipAddress, short port, int availableSpace) {

        /** Checks if ckpRepository has already registered */
        long currentTime = System.currentTimeMillis();
        for (Entry<Integer, CkpRepository> ckpReposEntry : ckpRepositoryMap.entrySet()) {
            CkpReposAddress address = ckpReposEntry.getValue().ckpReposAddress;            
            if (address.ipAddress.compareTo(ipAddress) == 0 && address.portNumber == port) {
                int ckpReposId = ckpReposEntry.getKey();
                ckpReposEntry.getValue().lastUpdate = System.currentTimeMillis();
                System.out.println("registerCkpRepos --> ckpRepos already registered on ipAddress:" + ipAddress + " port:" + port + " with id " + ckpReposId + ".");
                return -ckpReposId;
            }
            
            /**
             * Removes ckpInfo if the repository does not update for a long time
             */
            else if ( currentTime - ckpReposEntry.getValue().lastUpdate > repositoryRemovalInterval) {
                System.out.println("CkpReposManagerImpl --> removed repository with id " + ckpReposEntry.getValue().ckpReposId + ".");
                ckpRepositoryMap.remove(ckpReposEntry.getValue().ckpReposId);                
            }

        }

        /** Registers the new ckpRepository */
        int ckpReposId = nextCkpReposId.getAndIncrement();
        CkpRepository ckpRepos = new CkpRepository(ckpReposId, ipAddress, port, availableSpace);
        ckpRepositoryMap.put(ckpReposId, ckpRepos);        

        System.out.println("registerCkpRepos --> ckpReposId:" + ckpReposId + " ipAddress:" + ipAddress + " port:" + port + " availableSpace:" + availableSpace);

        return ckpReposId;
    }

    /**
     * Returns a list of checkpoint repository addresses.
     * The number of returned ckpRepos addresses is dependent in adopted storage strategy  
     */       
    synchronized public CkpReposAddressList getCkpRepos(String executionId, String ipAddress, int ckpNumber, String[] checksumList, int ckpSize) {

        int executionIdDelimiter = executionId.lastIndexOf(':');
        int nodeId = Integer.parseInt(executionId.substring(executionIdDelimiter+1));
        ExecutionInformation executionInfo = currentExecutions.get(executionId.substring(0, executionIdDelimiter));
        int nTotal = executionInfo.checkpoints.nRecover + executionInfo.checkpoints.nExtra;
        executionInfo.checkpoints.addExecutionCheckpoint(ckpNumber, nodeId, checksumList, ckpSize);
        
        CkpReposAddressList ckpReposAddressList = new CkpReposAddressList();        
        ckpReposAddressList.ipAddress  = new String[nTotal];
        ckpReposAddressList.portNumber = new short[nTotal];
        for (int i=0; i<nTotal; i++) {
            ckpReposAddressList.ipAddress[i] = "";
            ckpReposAddressList.portNumber[i] = 0;
        }
       
        int nRepositories = ckpRepositoryMap.size();
        if (nRepositories > 0) {

            CkpRepository[] ckpRepositoryArray = new CkpRepository[nRepositories];
            ckpRepositoryMap.values().toArray(ckpRepositoryArray);

            int offset = (new Random()).nextInt(nRepositories);
            int fragmentSizeKbytes = ckpSize / 1024;
            if (executionInfo.checkpoints.ckpReposStoreMode == CkpReposStoreMode.parityLocal ||
                    executionInfo.checkpoints.ckpReposStoreMode == CkpReposStoreMode.ida ) {            
                fragmentSizeKbytes /= executionInfo.checkpoints.nRecover;
            }
            fragmentSizeKbytes++; // compensates for rounding truncation 

            long currentTime = System.currentTimeMillis();
            for (int i=0, nSelected=0; nSelected < nTotal; i++) {

                CkpRepository ckpRepos = ckpRepositoryArray[( i+offset ) % nRepositories];
                if (ckpRepos != null) {
                    
                    /**
                     * Checks if the repository was updated recently and if there is enough storage space
                     */
                    if ( currentTime - ckpRepos.lastUpdate < repositorySelectionInterval) {
                        if (fragmentSizeKbytes < ckpRepos.availableSpace) {
                            ckpReposAddressList.ipAddress[nSelected]  = ckpRepos.ckpReposAddress.ipAddress;
                            ckpReposAddressList.portNumber[nSelected] = ckpRepos.ckpReposAddress.portNumber;
                            nSelected++;
                        }
                    }
                    
                    /**
                     * Removes ckpInfo if the repository does not update for a long time
                     */
                    else if ( currentTime - ckpRepos.lastUpdate > repositoryRemovalInterval) {
                        System.out.println("CkpReposManagerImpl --> removing repository with id " + ckpRepos.ckpReposId + ".");
                        ckpRepositoryMap.remove(ckpRepos.ckpReposId);
                    }
                }
            }
        }

        System.out.println("CkpReposManagerImpl::getCkpRepos --> returning repository addresses.");

        return ckpReposAddressList;
    }
    
    /**
     * Obtains information regarding the last checkpoint stored.
     * @return CkpInfo, which contains the checkpoint number and location. 
     */    
    public CkpInfo getLastCkpInfo(String executionId) {

        int executionIdDelimiter = executionId.lastIndexOf(':');
        int nodeId = Integer.parseInt(executionId.substring(executionIdDelimiter+1));        
        ExecutionInformation executionInfo = currentExecutions.get(executionId.substring(0, executionIdDelimiter));                
        
        /**
         * Obtains the checkpoint info from the application executionInfo
         */
        GlobalCkpInformation ckpData = null;
        if (executionInfo != null)
            ckpData = executionInfo.checkpoints.getLastCkpInfo();
        
        /**
         * Creates the structure CkpInfo and populates its fields
         */
        CkpInfo ckpInfo = new CkpInfo();
        if (ckpData != null) {
        		CkpRepository[] ckpRepositoryList = ckpData.getCkpRepositories(nodeId);
        		ckpInfo.checksum = ckpData.getChecksum(nodeId);
        		ckpInfo.ckpSize  = ckpData.getCkpSize(nodeId);
        		ckpInfo.ipAddress  = new String[ckpRepositoryList.length];
        		ckpInfo.portNumber = new short [ckpRepositoryList.length];
        		for (int i=0; i<ckpRepositoryList.length; i++) {
        			ckpInfo.ipAddress[i]  = ckpRepositoryList[i].ckpReposAddress.ipAddress;        
        			ckpInfo.portNumber[i] = ckpRepositoryList[i].ckpReposAddress.portNumber;
        		}
        		ckpInfo.ckpNumber  = ckpData.getCkpNumber();
        }
        else {
        		ckpInfo.ipAddress  = new String[]{""};        
        		ckpInfo.portNumber = new short[]{0};
        		ckpInfo.checksum   = new String[]{""};
        		ckpInfo.ckpNumber  = -1;        
        }
        
        //System.out.println("getLastCkpInfo --> executionId:" + executionId + " ipAddress:" + ckpInfo.ipAddress[0] + " port:" + ckpInfo.portNumber[0] + " ckpNumber: [" + ckpInfo.ckpNumber + "]");
        return ckpInfo;
    }
    
    /**
     * notifyCkpStored
     */    
    public void notifyCkpStored(int ckpReposId, String executionId, int ckpNumber, int fragmentNumber, int availableSpace) {
        int executionIdDelimiter = executionId.lastIndexOf(':');
        int processId = Integer.parseInt(executionId.substring(executionIdDelimiter+1));        

        //System.out.println("notifyCkpStored --> executionId:" + executionId + " ckpReposId:" + ckpReposId + " ckpNumber:" + ckpNumber);

        ExecutionInformation executionInfo = currentExecutions.get(executionId.substring(0, executionIdDelimiter));
        if (executionInfo != null) {
            CkpRepository ckpRepos = ckpRepositoryMap.get(ckpReposId);
            executionInfo.checkpoints.addCkpLocation(processId, ckpRepos, ckpNumber, fragmentNumber);
            ckpRepos.availableSpace = availableSpace;
        }
    }
    

    /**
     * Sets the checkpoint storage strategy for checkpoints, including the number of slices. 
     */    
    public void setCkpReposMode(String executionId, int mode, int nRecover, int nExtra) {
		    	
        int executionIdDelimiter = executionId.lastIndexOf(':');        
        ExecutionInformation executionInfo = 
        	currentExecutions.get(executionId.substring(0, executionIdDelimiter));
        executionInfo.checkpoints.nRecover = nRecover; 
        executionInfo.checkpoints.nExtra = nExtra;
		
        // Determine a way to use ckpRepositories from the nodes executing the application
        // It is necessary to mantain a list of which repositories the application is using
        
		if (mode == 0)      executionInfo.checkpoints.ckpReposStoreMode = CkpReposStoreMode.single;
		else if (mode == 1) executionInfo.checkpoints.ckpReposStoreMode = CkpReposStoreMode.replicated;
		else if (mode == 2) executionInfo.checkpoints.ckpReposStoreMode = CkpReposStoreMode.parityLocal;
		else if (mode == 3) executionInfo.checkpoints.ckpReposStoreMode = CkpReposStoreMode.parityGlobal;
		else if (mode == 4) executionInfo.checkpoints.ckpReposStoreMode = CkpReposStoreMode.ida;
	}

    /**
     * -1: error, ckpRepos is not registered
     *  0: updated, no files to remove
     *  1: updated, has files to remove
     */
    synchronized public int updateRepositoryStatus(int ckpReposId, int availableSpace) {
        
        int updateStatus;        
        CkpRepository ckpRepos = ckpRepositoryMap.get(ckpReposId);        
        if (ckpRepos != null) {            
            ckpRepos.availableSpace = availableSpace;
            ckpRepos.lastUpdate = System.currentTimeMillis();            
            //System.out.println("CkpReposManagerImpl.updateRepositoryStatus: CheckpointRepository with id " + ckpReposId + " updated with " + availableSpace + " kbytes of availableSpace.");
            
            if (ckpRepos.ckpRemovalCollection.getNumberOfRemovals() == 0)
                updateStatus = 0;
            else
                updateStatus = 1;
        }
        
        /**
         * CkpRepository is not registered. 
         * It may have been removed because 'repositoryRemovalInterval' has elapsed since the last update. 
         */
        else {
            System.err.println("CkpReposManagerImpl.updateRepositoryStatus: CheckpointRepository with id " + ckpReposId + " is not registered. Ignoring...");
            updateStatus = -1;
        }        
        
        return updateStatus;
    }

    public CheckpointRemovalList getCheckpointRemovalList(int ckpReposId) {        
        
        CkpRepository ckpRepos = ckpRepositoryMap.get(ckpReposId);
        if (ckpRepos != null)
            return ckpRepos.ckpRemovalCollection.generateAndClearCheckpointRemovalList();
        else
            return new CheckpointRemovalList(new String[0], new short[0], new short[0]);
    }
}

//===================================================================================
