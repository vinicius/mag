package grm.ckpReposManager;

//  ===================================================================================

public class CkpRepository {
    
    /**
     *  Contains the address of a single checkpoint repository.
     */
    class CkpReposAddress {
        String ipAddress;
        short portNumber;
    }
    
    /**
     * The network address of the repository.
     */
    CkpReposAddress ckpReposAddress;
    
    /**
     * The amount of available disk space in the repository (in kbytes)
     */
    int availableSpace;
    
    /**
     * The time of the last update from the repository (in ms)
     */
    long lastUpdate;
    
    /**
     * An id that uniquely identifies this repository.
     */
    int ckpReposId;

    /**
     * Contains a list of checkpoints to remove from this repository
     */
    CheckpointRemovalCollection ckpRemovalCollection; 
    
    public CkpRepository(int ckpReposId, String ip, short port, int availableSpace) {
        this.ckpReposAddress = new CkpReposAddress();
        this.ckpReposAddress.ipAddress  = ip;
        this.ckpReposAddress.portNumber = port;
        this.ckpReposId = ckpReposId;
        this.availableSpace = availableSpace;
        this.lastUpdate = System.currentTimeMillis();
        this.ckpRemovalCollection = new CheckpointRemovalCollection();        
    }
    
}

//===================================================================================
