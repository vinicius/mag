/**
 * @(#)ExecutionRequest.java		Dec 20, 2005
 *
 * Copyleft
 */
package asct.core.submission;

import java.util.Vector;

import asct.shared.AbstractGridApplication;

/**
 * Class description goes here.
 * 
 * @version 1.0 Dec 20, 2005
 * @author Eduardo Guerra and Eudenia Xavier
 */
public class ExecutionRequest {
	/** */
    private AbstractGridApplication submittedApplication;

	/** */
    //private String requestId_;   /**< The id of the accepted execution */
    
	/** */
    private int finishedNodes_;
    
	/** */
	private Vector < ExecutionRequestItem >  requestItens;
	
	/** */
    private boolean collectedResults_;
    
	/** */
    private int pendingSubRequests_;
    
    /** */
    private boolean refusedExecution;
    
	/**
	 * Constructor.
	 * 
	 * @param application
	 *            that will be executed
	 */
	public ExecutionRequest(AbstractGridApplication application) {
	      //requestId_ = requestId;
	      finishedNodes_ = 0;
	      requestItens = new Vector();
	      collectedResults_ = false;
	      pendingSubRequests_ = 0;
	      submittedApplication = application;
	      refusedExecution = false;

	}

	public void addRequestItem(ExecutionRequestItem reqItem) {
	      requestItens.add(reqItem);
	}
	
	public ExecutionRequestItem getRequestItem(int id) {
		return (ExecutionRequestItem)requestItens.get(id);
	}
	
	public ExecutionRequestItem[] getRequestItems() {
		return requestItens.toArray(new ExecutionRequestItem[0]);
	}
	
	
    public void setNodeFinished(int asctSubRequestId) {
        ((ExecutionRequestItem) requestItens.get(asctSubRequestId)).setFinishedExecuting(true);
        finishedNodes_++;
    }

    public boolean isAllNodesFinished() {
    	return finishedNodes_ == requestItens.size(); 
    }
    
    public boolean isCollectedResults() {
    	return collectedResults_; 
    }
    
    public void setCollectedResults(boolean value) { 
    	collectedResults_ = value; 
    }
    
    /**
     * 
     * */
    public void fillSubRequest(final String localSubRequestId, 
    				final String lrmIor, final String remoteRequestId) {
        ExecutionRequestItem requestItem = (ExecutionRequestItem) 
        				requestItens.get(Integer.parseInt(localSubRequestId));
        requestItem.setLrmIor(lrmIor);
        requestItem.setRemoteRequestId(remoteRequestId);
        pendingSubRequests_--;
    }
    
    public boolean isNoPendingRequestsLeft() { 
    	return (pendingSubRequests_ == 0); 
    }
    
	public void setExecRefused() {
		refusedExecution = true;
	}
}
