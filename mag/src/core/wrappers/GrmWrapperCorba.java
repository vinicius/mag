package core.wrappers;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import clusterManagement.Grm;
import clusterManagement.GrmHelper;
import dataTypes.AppRequeriments;



/**
 * Class GrmWrapperCorba - Interface of Grm wrapper. CORBA implementation of the
 * Grm wrapper
 * 
 * @author Rafael Fernandes Lopes
 */
class GrmWrapperCorba implements GrmWrapper {

	private Grm grm = null;
	
	GrmWrapperCorba(ORB orb) {
		try {

			org.omg.CORBA.Object ns = orb
					.resolve_initial_references("NameService");
			 NamingContextExt nameService = NamingContextExtHelper.narrow( ns );
			grm = GrmHelper.narrow(nameService.resolve(nameService
					.to_name("GRM")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 Supply to the Grm an array of applications requirements to consult
	 about available locations to migrate applications
	 
	 @param locations - Array of locations where the application can't execute
	 @return available locations where the applications can migrate
	 */
	public String migrationLocationRequest(String [] locations) {
		return grm.migrationLocationRequest(locations);
	}

	public void setLocations(String location, String agentHandlerIor ) {
		grm.setLocation(location, agentHandlerIor);
	}	
	

	/**
	 * Supply to the Grm an array of applications requirements to consult about
	 * available locations to migrate applications
	 * 
	 * @param appReq -
	 *            Array of applications requirements
	 * @return available locations where the applications can migrate
	 */
/*	public String[] migrationLocationRequest(AppRequeriments[] appReq) {
		
		//TODO: descomentar quando colocar o mecanismo de tolerância a falhas.
		//return grm.migrationLocationRequest(appReq);
		return new String[]{};
	}*/
}
