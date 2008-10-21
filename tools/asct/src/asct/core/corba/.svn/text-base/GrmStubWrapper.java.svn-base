/**
 * @(#)GrmStubWrapper.java		Dec 21, 2005
 *
 * Copyleft
 */
package asct.core.corba;

import clusterManagement.Grm;
import dataTypes.ApplicationExecutionInformation;
import dataTypes.ProcessExecutionInformation;

/**
 * Class description goes here.
 * 
 * @version 1.0 Dec 21, 2005
 * @author Eduardo Guerra and Eudenia Xavier and Raphael Y. de Camargo
 */
public class GrmStubWrapper {

	/** */
	private Grm grm;

	/**
	 * Constructor.
	 */
	GrmStubWrapper(Grm grm) {

		this.grm = grm;

	}

	public void remoteExecutionRequest(
			ApplicationExecutionInformation execInformation,
			ProcessExecutionInformation[] processesInformations) {

		new RequestRemoteExecutionThread (grm, execInformation, processesInformations).start();
	}


}

class RequestRemoteExecutionThread extends Thread {

	/** */
	private Grm grm;

	/** */
	private ApplicationExecutionInformation execInformation;

	/** */
	private ProcessExecutionInformation[] processesInformations;

	/**
	 * Constructor.
	 */
	RequestRemoteExecutionThread (Grm grm,
			ApplicationExecutionInformation execInformation,
			ProcessExecutionInformation[] processesInformations) {
		
		this.grm = grm;
		this.execInformation = execInformation;
		this.processesInformations = processesInformations;
	}

	public void run() {
		grm.requestRemoteExecution(execInformation, processesInformations);
	}

}

