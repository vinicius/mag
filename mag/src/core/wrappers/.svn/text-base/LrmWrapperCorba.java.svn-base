package core.wrappers;

import org.omg.CORBA.ORB;

import dataTypes.ApplicationExecutionInformation;
import dataTypes.ProcessExecutionInformation;
import dataTypes.ExecutionRequestId;

import resourceProviders.Lrm;
import resourceProviders.LrmHelper;



/**
 Class AsctWrapperCorba - Class of Asct wrapper.
 CORBA implementation of the Asct wrapper
 
 @author Rafael Fernandes Lopes 
 */
public class LrmWrapperCorba implements LrmWrapper {
	private String lrmIor = "";
	private Lrm lrm = null;
	
	LrmWrapperCorba (ORB orb, String lrmIor) {
		this.lrmIor = lrmIor;
		
		lrm = LrmHelper.narrow(orb.string_to_object(lrmIor));
	}
	
	public void requestExecution(ApplicationExecutionInformation applicationExecutionInformation, ProcessExecutionInformation processExecutionInformation){
 
		try {
			
			lrm.requestExecution(applicationExecutionInformation,processExecutionInformation);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
