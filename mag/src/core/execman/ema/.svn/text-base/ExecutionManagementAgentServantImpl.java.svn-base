package core.execman.ema;

import clusterManagement.ExecutionManagementAgentPOA;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import dataTypes.ApplicationExecutionInformation;
import dataTypes.ProcessExecutionInformation;


public class ExecutionManagementAgentServantImpl implements Runnable {
	
	private ExecutionManagementAgentImpl ema;
	
	public ExecutionManagementAgentServantImpl( ExecutionManagementAgentImpl ema ){
        
		this.ema = ema;
	}
	
	public void run(){
	/*	try {
			
			ORB orb = ORB.init(new String [] {}, null);
			POA poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			poa.the_POAManager().activate();
			
			Object emaServant = poa.servant_to_reference(this);			
			
			// Registration of CRM on name server			
			System.out.println("ema 1");
			NamingContextExt nameService = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));
			System.out.println("ema 2");
			nameService.rebind(nameService.to_name("EMA"), emaServant);
			System.out.println("ema 3");
			
			orb.run();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	

}
