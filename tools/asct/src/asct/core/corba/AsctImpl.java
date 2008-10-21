/**
 * @(#)AsctImpl.java		Dec 20, 2005
 *
 * Copyleft
 */
package asct.core.corba;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import dataTypes.FileStruct;

import tools.AsctPOA;
import asct.core.submission.ExecutionRequestManager;

/**
 * Implements the ASCT IDL interface.
 * 
 * @version 0.5 Fev 01, 2004
 * @author Andrei Goldchleger
 * 
 * @version 1.0 Dec 20, 2005
 * @author Eduardo Guerra and Eudenia Xavier
 * 
 * @version 1.1 May 25, 2006
 * @author Raphael Y. de Camargo
 */
public class AsctImpl extends AsctPOA {

	/**  */
	private ExecutionRequestManager execManager;

	/**  */
	private HashMap bspZerosIors_;

	public AsctImpl(ExecutionRequestManager execManager) {
		this.execManager = execManager;
		bspZerosIors_ = new HashMap();
	}

	/**
	 * Receive confirmation from a remote LRM that accepted our
	 * execution request
	 *
	 * Analog to 2k's "ACK"
	 *
	 * @param offSpecs - Offer information, see(@see types::offSpecs)
	 */
	public void setExecutionAccepted(
			dataTypes.RequestAcceptanceInformation requestAcceptanceInformation) {
		
		System.out.println("Our request represented by main id: " +
				requestAcceptanceInformation.executionRequestId.requestId + " and NODE id: " +
				requestAcceptanceInformation.executionRequestId.processId + " was ACCEPTED by LRM: " +
				requestAcceptanceInformation.lrmIor);
		
		execManager.acceptedExecRequest(requestAcceptanceInformation);
		
	}

	/**
	 * Receive notice that one of our queries was rejected
	 * by all other LRMs (Though we can retry again if we
	 * so wish)
	 *
	 * Analog to 2k's "NACK"
	 *
	 * @param requestId - Id of the refused request
	 */
	public void setExecutionRefused(
			dataTypes.ExecutionRequestId executionRequestId) {
		
		System.out.println("Our request represented by main id: " +
				executionRequestId.requestId + " and NODE id: " +
				executionRequestId.processId + " was REFUSED");		 
	}

	public void setExecutionFinished(
		dataTypes.ExecutionRequestId executionRequestId) {
		if(execManager==null)
			System.out.println("#####O EXECMANAGER E  NULO!! ######");
		else
			System.out.println("#####O EXECMANAGER NAO E  NULO!! ######");

		// Vinicius { -> descomentei a linha
		execManager.nodeFinished(executionRequestId);
		// } Vinicius
	}

	/**
	 * Receive request for files needed to allow the remote execution of
	 * a request made by this ASCT
	 *
	 * @param paths - the file path in the host where this ASCT is running
	 * @return all files needed by the remote execution
	 */
	public dataTypes.FileStruct[] getInputFiles(
			dataTypes.ExecutionRequestId executionRequestId) {
		
		String[] filePaths = execManager.getAppInputFiles(executionRequestId);
		FileStruct[] inputFiles = new FileStruct[filePaths.length]; 

		for (int i = 0; i < inputFiles.length; i++) { 
			inputFiles[i] = new FileStruct(); 
			try { 
				File file = new File(filePaths[i]);
				inputFiles[i].fileName = file.getName(); 
				FileInputStream fis = new FileInputStream(file); 
				byte[] serializedFile = new byte[(int)file.length()];// FIXME: // check // if // cast // is // acceptable
				fis.read(serializedFile); fis.close(); 
				inputFiles[i].file = serializedFile; 
			} 
			catch (FileNotFoundException fnfe) { 
				System.err.println("AsctImpl::requestInputFiles-->>Someone asked for an inexistent file");		 
				System.exit(-1); 
				return null; 
			} 
			catch (IOException ioe) { 
				System.err.println("AsctImpl::requestInputFiles-->>IOException");
				System.exit(-1); 
				return null; 
			} 
		}// for return inputFiles;		  
		
		return inputFiles;
	}

}
