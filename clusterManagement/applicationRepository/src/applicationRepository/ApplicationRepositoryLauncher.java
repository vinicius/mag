package applicationRepository;

import java.util.Properties;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

/**
 Class ApplicationRepositoryServer - Launches an ApplicationRepository
 @author Andrei Goldchleger

*/
public class ApplicationRepositoryLauncher{
	
	/**
	 Launches an ApplicationRepository
	 
	 @param args - Should contain:<br>
	 args[0] - Filename to output the server IOR
	 
	 */
	public static void main(String [] args){
		try{
			ORB orb = ORB.init(new String [] {} , null);
			POA poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			poa.the_POAManager().activate();
			Object appReposReference = poa.servant_to_reference(new ApplicationRepositoryServant(orb));
			// get a reference to the naming service
			org.omg.CORBA.Object ns = orb.resolve_initial_references("NameService");
			NamingContextExt nameService = NamingContextExtHelper.narrow( ns );
			// register AR object
			nameService.rebind(nameService.to_name("AR"), appReposReference); 
			orb.run();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
}


