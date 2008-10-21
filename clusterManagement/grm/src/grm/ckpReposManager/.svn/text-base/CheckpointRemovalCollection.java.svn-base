package grm.ckpReposManager;

import java.util.LinkedList;
import java.util.List;

import subtypes.CheckpointRemovalList;

public class CheckpointRemovalCollection {

    private class CkpRemovalEntry {
        String executionId; 
        short ckpNumber;
        short fragmentNumber;                    
    }

    /**
     * Contains a list of checkpoints to remove from this repository
     */
    private List<CkpRemovalEntry> removalCollection; 

    public CheckpointRemovalCollection () {
        this.removalCollection = new LinkedList<CkpRemovalEntry>();
    }
    
    synchronized public void addRemovalEntry( String executionId, short ckpNumber, short fragmentNumber ) {
        CkpRemovalEntry removalEntry = new CkpRemovalEntry();
        removalEntry.executionId = executionId;
        removalEntry.ckpNumber = ckpNumber;
        removalEntry.fragmentNumber = fragmentNumber;
        removalCollection.add(removalEntry);
    }
    
    public int getNumberOfRemovals() {
        return removalCollection.size();
    }
    
    synchronized public CheckpointRemovalList generateAndClearCheckpointRemovalList () {
        int nEntries = removalCollection.size();
        CheckpointRemovalList ckpRemovalList =
            new CheckpointRemovalList(new String[nEntries], new short[nEntries], new short[nEntries]);
        
        for (int entryIndex = 0; entryIndex < nEntries; entryIndex++) {
            CkpRemovalEntry entry = removalCollection.get(entryIndex);
            ckpRemovalList.executionId[entryIndex]    = entry.executionId;
            ckpRemovalList.ckpNumber[entryIndex]      = entry.ckpNumber;
            ckpRemovalList.fragmentNumber[entryIndex] = entry.fragmentNumber;
        }
        
        removalCollection.clear();
        
        return ckpRemovalList;
    }

}
