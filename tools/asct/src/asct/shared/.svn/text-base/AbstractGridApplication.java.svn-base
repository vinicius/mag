/*
 * @(#) AbstractGridApplication.java 1.0 May 11, 2005
 *
 * CopyLeft IME/USP
 */
package asct.shared;

import java.util.Vector;

import asct.core.submission.ExecutionRequest;
import dataTypes.ApplicationDescription;


/**
 * An abstract Grid Application.
 * @version 1.0 May 11, 2005
 * @author Eduardo Guerra
 *
 */
public abstract class AbstractGridApplication {
	
	private Vector<ExecutionRequest> executionRequests;

	/** Contains information about the application */
	private ApplicationDescription description;
	
	/**
	 * Constructor
	 */
	public AbstractGridApplication(String name, String remoteApplicationPath) {
		description = new ApplicationDescription();
		description.applicationName = name;
		description.basePath = remoteApplicationPath;
		executionRequests = new Vector<ExecutionRequest>();
	}

	/**
	 * 
	 * */
	public String getName() {
		return description.applicationName;
	}
	
	/**
	 * 
	 * */
	public String getBasePath() {
		return description.basePath;
	}
	
	public String[] getBinaryIds() {
		return this.description.binaryIds;
	}

	/**
	 * @param description The description to set.
	 */
	public final void setDescription(ApplicationDescription description) {
		this.description = description;
	}

}
