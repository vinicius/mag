/*
 * Created on 05/10/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package core.ft.crm;

import clusterManagement.CrmAgentPOA;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import dataTypes.ApplicationExecutionInformation;
import dataTypes.ProcessExecutionInformation;

/*import subtypes.ApplicationParam;
import subtypes.ApplicationStateInfo;
import subtypes.AsctRequestId;
import subtypes.FileStruct;*/

/**
 * @author gilberto
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CrmAgentServantImpl extends CrmAgentPOA implements Runnable {
	private CrmAgent crmAgent;	
	
	public CrmAgentServantImpl(CrmAgent crmAgent){
         
		this.crmAgent = crmAgent;
/*	try{	
		ORB orb = ORB.init(new String [0], null);
		POA poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		poa.the_POAManager().activate();
		
		Object crmServant = poa.servant_to_reference(this);
		
		// Registration of CRM on name server			
		NamingContextExt nameService = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));
		nameService.rebind(nameService.to_name("CRMAGENT"), crmServant);
		
		orb.run();

	}
	catch(Exception e){
		e.printStackTrace();
	}*/		
	}
	
	public void run(){
		try{
			
			ORB orb = ORB.init(new String [] {}, null);
			POA poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			poa.the_POAManager().activate();
			
			Object crmServant = poa.servant_to_reference(this);
			
			// Registration of CRM on name server			

			NamingContextExt nameService = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));

			nameService.rebind(nameService.to_name("CRMAGENT"), crmServant);

			
			orb.run();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void requestRemoteExecution(
			ApplicationExecutionInformation applicationExecutionInformation,
			ProcessExecutionInformation[] processExecutionInformation,
			String[] lrmIors) {
		
		crmAgent.requestRemoteExecution(applicationExecutionInformation, processExecutionInformation,
				lrmIors); 
	}
	
}


