/**
 * @(#)RequestItem.java		Dec 21, 2005
 *
 * Copyleft
 */
package asct.core.submission;

/**
 * Details about an execution request
 *
 * @version 	1.0 	Dec 21, 2005
 * @author 	Eduardo Guerra and Eudenia Xavier
 */
public class ExecutionRequestItem {

	private String[] inputFiles;
	private String[] outputFiles;
	private String arguments;
    private boolean finishedExecuting_;

    /* Values assigned after request acceptance */
    
    /** */
    private String lrmIor;
    
    /** */ 
    private String remoteRequestId;
    
	/**
	 * Constructor.
	 */
	public ExecutionRequestItem(String[] input) {
		inputFiles= input;
	}

	/**
	 * @return 
	 */
	public String[] getInputFiles() {
		return inputFiles;
	}

    public void setFinishedExecuting(boolean value) {
    	finishedExecuting_ = value; 
    }

	/**
	 * @return Returns the lrmIor.
	 */
	public String getLrmIor() {
		return lrmIor;
	}

	/**
	 * @param lrmIor The lrmIor to set.
	 */
	public void setLrmIor(String lrmIor) {
		this.lrmIor = lrmIor;
	}

	/**
	 * @return Returns the remoteRequestId.
	 */
	public String getRemoteRequestId() {
		return remoteRequestId;
	}

	/**
	 * @param remoteRequestId The remoteRequestId to set.
	 */
	public void setRemoteRequestId(String remoteRequestId) {
		this.remoteRequestId = remoteRequestId;
	}

}
