package core.wrappers;



import java.io.FileOutputStream;

import org.omg.CORBA.ORB;

import tools.Asct;
import tools.AsctHelper;
import dataTypes.ExecutionRequestId;
import dataTypes.FileStruct;
import dataTypes.RequestAcceptanceInformation;


/**
 Class AsctWrapperCorba - Class of Asct wrapper.
 CORBA implementation of the Asct wrapper
 
 @author Rafael Fernandes Lopes 
 */
public class AsctWrapperCorba implements AsctWrapper {
	private String asctIor = "";
	private Asct asct = null;
	
	AsctWrapperCorba (ORB orb, String aAsctIor) {
		asctIor = aAsctIor;
		
		asct = AsctHelper.narrow(orb.string_to_object(asctIor));
	}
	
	/**
	 Receive confirmation from a remote AgentHandler that accepted our execution request.
	 
	 Analog to 2k's "ACK"
	 */
	public void setExecutionAccepted (RequestAcceptanceInformation requestAcceptanceInformation) { 
		try {
			
			asct.setExecutionAccepted(requestAcceptanceInformation);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 Signals that an application execution was refused
	 */
	public void setExecutionRefused(String requestId, String processId) { // Vinicius on replicaId
		try {
			ExecutionRequestId executionRequestId = new ExecutionRequestId();
			
			executionRequestId.requestId = requestId;
			executionRequestId.processId = processId;
//			executionRequestId.replicaId = replicaId; // Vinicius
			
			asct.setExecutionRefused(executionRequestId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 Signals that an application execution was finished
	 */
	public void setExecutionFinished(String requestId, String processId) { // Vinicius on replicaId
		try {
            
			ExecutionRequestId executionRequestId = new ExecutionRequestId();
			// Vinicius {
//			executionRequestId.requestId = requestId.split(":")[2];
			executionRequestId.requestId = requestId;
			executionRequestId.processId = processId;
//			executionRequestId.replicaId = replicaId;
			// } Vinicius
			
			asct.setExecutionFinished(executionRequestId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public FileStruct [] returnInputFiles(ExecutionRequestId executionRequestId ){
		FileStruct returnFiles[] = asct.getInputFiles(executionRequestId);
		
		return returnFiles;
	
	}
	/**
	 Receive request for files needed to allow the remote execution of
	 a request made by this ASCT
	 */
	public FileStruct [] getInputFiles (String requestId, String processId, String destinationPath) { // Vinicius on replicaId
		try {
			ExecutionRequestId executionRequestId = new ExecutionRequestId(); 
			
			executionRequestId.requestId = requestId;
			executionRequestId.processId = processId;
//			executionRequestId.replicaId = replicaId; // Vinicius
						
			FileStruct returnFiles[] = asct.getInputFiles(executionRequestId);
						
			for (int i = 0; i < returnFiles.length; i++) {
				System.out.println (destinationPath + "/" + returnFiles[i].fileName);
				
				FileOutputStream fos = new FileOutputStream(destinationPath + "/" + returnFiles[i].fileName);
				fos.write(returnFiles[i].file);
				fos.close();
			}
		
			return returnFiles;
		} catch (Exception e) {
			System.err.println("requestInputFiles-->> Write failed");
		}
		
		return null;
	}
}
