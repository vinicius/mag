package grm.ckpReposManager;

/**
 * Contains information about a global checkpoint
 */
public class GlobalCkpInformation {
    /**
     * The repositories containing the fragments from each local checkpoint
     */
    private CkpRepository[][] ckpRepositories;
    
    /**
     * The checksum of each fragment and checkpoint for each local checkpoint
     */
    private String[][] checksumList; 
    
    /**
     * The sizes of each local checkpoint, in bytes
     */
    private int[] ckpSizeList;

    /**
     * The total number of saved checkpoint fragments
     */
    private int nSavedFragments;
    
    /**
     * The identifier of this checkpoint
     */
    private int ckpNumber;

    /**
     * The number of processes the application contains
     */
    private int nProcesses;
    
    /**
     * The number of fragments for each local checkpoint
     */
    private int nFragments;
    
    public GlobalCkpInformation(int nProcesses, int ckpNumber, int nFragments) {
        this.ckpRepositories = new CkpRepository[nProcesses][nFragments];
        this.checksumList = new String[nProcesses][];
        this.ckpSizeList = new int[nProcesses];
        this.nProcesses = nProcesses; this.nFragments = nFragments;
        this.ckpNumber  = ckpNumber;
    }
    
    public void setChecksum(int nodeId, String[] checksum, int ckpSize) {           
        this.checksumList[nodeId] = checksum;        
        this.ckpSizeList[nodeId]  = ckpSize;
    }
    
    public void setCheckpointsForRemoval(String requestId) {
        
        for (int process=0; process < nProcesses; process++) {
            String executionId = requestId + ":" + process;
            for (short fragment=0; fragment < nFragments; fragment++) {
                CkpRepository repository = ckpRepositories[process][fragment];
                if (repository != null)
                    repository.ckpRemovalCollection.addRemovalEntry(executionId, (short)ckpNumber, fragment);
            }
        }
    }
           
    public int addCkpRepos(CkpRepository ckpRepos, int processId, int fragmentNumber) {
        
        if (fragmentNumber < 0 || fragmentNumber >= nFragments) {
            System.err.println("WARNING: The fragment number is not vaild. Value='" + fragmentNumber + "'.");
            return nSavedFragments;
        }
        
        ckpRepositories[processId][fragmentNumber] = ckpRepos;                        
        nSavedFragments++;

        //System.out.println("Added ckpRepos at port " + ckpRepos.ckpReposAddress.portNumber + " nSavedFrag " + nSavedFragments + " nSavedCkp " + nSavedCheckpoints);
        
        return nSavedFragments;
    }
    
    public int getNumberCkps() {return nSavedFragments;}
    
    public boolean isComplete() {
        if(nProcesses*nFragments == nSavedFragments) return true;
        else return false;
    }
    
    public int getCkpNumber() {return ckpNumber;}

    public int getCkpSize(int nodeId) {return ckpSizeList[nodeId];}
    public String[] getChecksum(int nodeId) {return checksumList[nodeId];}
    public CkpRepository[] getCkpRepositories(int nodeId) {return ckpRepositories[nodeId];}
}
