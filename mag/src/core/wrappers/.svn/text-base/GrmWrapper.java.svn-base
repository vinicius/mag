package core.wrappers;





/**
 Interface GrmWrapper - Interface of Grm wrapper
 
 @author Rafael Fernandes Lopes
 */
public interface GrmWrapper {
	public static final String MAG_CONF_DEFAULT_PATH = "../mag.conf";
	
	/**
	 Supply to the Grm an array of applications requirements to consult
	 about available locations to migrate applications
	 
	 @param locations - Array of locations where the application can't execute
	 @return available locations where the applications can migrate
	 */
	public String migrationLocationRequest(String [] locations); 
	
	public void setLocations(String location, String agentHandlerIor );
}
