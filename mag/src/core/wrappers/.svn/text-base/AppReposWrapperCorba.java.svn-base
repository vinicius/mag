package core.wrappers;



import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;


import clusterManagement.ApplicationRepository;
import clusterManagement.ApplicationRepositoryHelper;

/**
 ClassAppReposWrapperCorba - Class of ApplicationRepository wrapper.
 CORBA implementation of the ApplicationRepository wrapper
 
 @author Rafael Fernandes Lopes
 */
public class AppReposWrapperCorba implements AppReposWrapper {
	private String iorFile = "";
	private ApplicationRepository applicationRepository = null;
	
	AppReposWrapperCorba (ORB orb) {
		try {
			/*String tmpstr = "";
			BufferedReader br = new BufferedReader (new InputStreamReader (new FileInputStream("../mag.conf")));

			while ( (tmpstr = br.readLine()) != null ) {
				if (tmpstr.startsWith("appReposIorFile")) {
					iorFile = tmpstr.substring(tmpstr.indexOf(' ') + 1);
					break;
				}
			}

			br.close();

			br = new BufferedReader (new InputStreamReader (new FileInputStream(iorFile)));

			String ior = br.readLine();
			br.close();
			appRep = ApplicationRepositoryHelper.narrow(orb.string_to_object(ior));
			 */

			try {
				org.omg.CORBA.Object ns = orb.resolve_initial_references("NameService");
				NamingContextExt nameService = NamingContextExtHelper.narrow( ns );
				//look up appRepos
				applicationRepository = ApplicationRepositoryHelper.narrow( nameService.resolve(nameService.to_name("AR")) );
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 Retrieve bytecode of an application from the ApplicationRepository
	 
	 @param applicationId - Id of application that must be retrieved
	 @return serialized bytecode of null if appId was not found
	 */
	public byte [] getApplication(String basePath, String applicationName, String binaryName) {
		byte result[] = null;
		try {
			result = applicationRepository.getApplicationBinary(basePath, applicationName, binaryName);
			//result = applicationRepository.getApplicationBinary(applicationId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
