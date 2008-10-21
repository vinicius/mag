package asct.shared;

/**
 * This class represents the data/status about a
 * previous made execution request.
 * @author Ricardo Andrade
 *
 */
		
public class ExecutionRequestStatus {

	private String applicationPath;
    private ApplicationState applicationState;
    private String applicationName;
    	private String requestId;
	
	public ExecutionRequestStatus() {
		super();
	}

	public ExecutionRequestStatus(String applicationName, 
			ApplicationState applicationState, String requestId) {
		this.applicationName = applicationName;
		this.applicationState = applicationState;
		this.requestId = requestId;
		
	}
	
	/**
	 * @return Returns the applicationName.
	 */
	public String getApplicationName() {
		return applicationName;
	}

	/**
	 * Sets the application name
	 * @param applicationName The applicationName to set.
	 */
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	/**
	 * @return Returns the applicationPath.
	 */
	public String getApplicationPath() {
		return applicationPath;
	}

	/**
	 * Sets the application path
	 * @param applicationPath The applicationPath to set.
	 */
	public void setApplicationPath(String applicationPath) {
		this.applicationPath = applicationPath;
	}

	/**
	 * @return Returns the applicationState.
	 */
	public ApplicationState getApplicationState() {
		return applicationState;
	}

	/**
	 * Sets the pplication state
	 * @param applicationState The applicationState to set.
	 */
	public void setApplicationState(ApplicationState applicationState) {
		this.applicationState = applicationState;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
	public String toString(){
		return applicationName + " [" + requestId + "]";
	}

}
