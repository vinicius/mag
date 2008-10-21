package grm;

import grm.ckpReposManager.CkpReposManagerImpl;
import grm.executionManager.ExecutionManagerImpl;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import clusterManagement.ExecutionManagerHelper;
import clusterManagement.GrmHelper;

/**
 Class GrmLauncher - Launches a GRM
 @author Hammurabi Mendes
 */

public class GrmLauncher { 	
	private static final int MINIMUM_SAMPLE_INTERVAL = 300;
	private static final int MINIMUM_KEEPALIVE_INTERVAL = 1500;
	
	// Launches a GRM
	 
	// @param args - Optionally contains:
	//               args[0] - Filename to output the server IOR
	//               args[1] - Filename to output the execution manager IOR
	
	public static void main(String[] args) {
		try {
			// CORBA runtime initialization
			
			ORB orb = ORB.init(new String[] {}, null);
			POA poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			poa.the_POAManager().activate();

			// GRM creation
			GrmImpl grmImplementation = new GrmImpl(orb);
			Object grmObject = poa.servant_to_reference(grmImplementation);

            ExecutionManagerImpl executionManagerImpl = new ExecutionManagerImpl(orb, GrmHelper.narrow(grmObject));
			Object executionManagerObject = poa.servant_to_reference(executionManagerImpl);
				
			// ExecutionManager registration on GRM			
			grmImplementation.setExecutionManager(ExecutionManagerHelper.narrow(executionManagerObject));

			// CkpReposManager creation
			CkpReposManagerImpl ckpReposManagerImpl = new CkpReposManagerImpl(executionManagerImpl);
            Object ckpReposManagerObject = poa.servant_to_reference(ckpReposManagerImpl);            

			// Registration of GRM and ExecutionManager on name server			
			NamingContextExt nameService = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));
			nameService.rebind(nameService.to_name("GRM"), grmObject);
			nameService.rebind(nameService.to_name("EM"),  executionManagerObject);
			nameService.rebind(nameService.to_name("CkpReposManager"), ckpReposManagerObject);
            
			// Set the GRM's parent GRM.
			try {
				NamingContextExt parentNameService = NamingContextExtHelper.narrow(orb.resolve_initial_references("ParentNameService"));		
				Object parentGrm = parentNameService.resolve_str("GRM");
				grmImplementation.setParentGrm(orb.object_to_string(parentGrm));
				System.out.println("GRM registered in the InteGrade Grid.");
			}
			catch (Exception e) {
				System.out.println("Parent GRM not found. Running as standalone GRM.");
			}
						
			// Set the GRM's sample interval, with a minimun value of 5 minutes.			
			String sampleIntervalVariable = System.getenv("GRM_SAMPLE_INTERVAL");
			
			if(sampleIntervalVariable != null) {
				int sampleInterval = Integer.valueOf(sampleIntervalVariable);
			
				if(sampleInterval < MINIMUM_SAMPLE_INTERVAL) {
					sampleInterval = MINIMUM_SAMPLE_INTERVAL;
				}
			
				grmImplementation.setSampleInterval(sampleInterval);			
			}
			
			// Set the GRM's keep-alive interval, with a minimun value of 25 minutes.
			
			String keepAliveIntervalVariable = System.getenv("GRM_KEEPALIVE_INTERVAL");
			
			if(keepAliveIntervalVariable != null) {
				int keepAliveInterval = Integer.valueOf(keepAliveIntervalVariable);
				
				if(keepAliveInterval < MINIMUM_KEEPALIVE_INTERVAL) {
					keepAliveInterval = MINIMUM_KEEPALIVE_INTERVAL;
				}			
				
				grmImplementation.setKeepAliveInterval(keepAliveInterval);
			}
						
			orb.run();
		}
		catch(Exception exception) {
			exception.printStackTrace();
			
			System.err.println("Error launching the GRM.");
		}
	}
}