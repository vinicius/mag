package core.wrappers;

import org.omg.CORBA.ORB;

/**
 Class WrapperFactory - Factory of communication wrappers.
 
 Becomes the MAG code independent of the used communication technology
 
 @author Rafael Fernandes Lopes
 */
public class WrapperFactory {
	private static ORB orb = null;
	private static WrapperFactory instance = null;
	private AppReposWrapper ar = null;
	private GrmWrapper grm = null;
	private AgentHandlerWrapper agentHandler = null;

	public static void init (ORB orb) {
		WrapperFactory.orb = orb;
		instance = new WrapperFactory();
	}
	 
	public static WrapperFactory getInstance () {
		if (instance == null) {
			instance = new WrapperFactory();
			
			if (orb == null) {
				orb = ORB.init (new String [0], null);
			}
		}
		
		return instance;
	}
	
	/**
	 Creates an ApplicationRepository wrapper
	 
	 @return the ApplicationRepository wrapper
	 */
	public synchronized AppReposWrapper createAppReposWrapper () {
		this.ar = new AppReposWrapperCorba (orb);
		
		return ar;
	}
	
	/**
	 Creates an Grm wrapper
	 
	 @return the Grm wrapper
	 */
	public synchronized GrmWrapper createGrmWrapper () {
		this.grm = new GrmWrapperCorba (orb);
		
		return grm;
	}  
	
	public synchronized GrmWrapper createGrmWrapper (String magConfFile, String additionalPath) {
		GrmWrapper grm = new GrmWrapperCorba (orb);
		
		return grm;
	}
	
	/**
	 Creates an Lrm wrapper
	 
	 @param lrmIor - Identifier of a determined Lrm
	 @return the Lrm wrapper
	 */
	public synchronized LrmWrapper createLrmWrapper (String lrmIor) {
		LrmWrapper lrm = new LrmWrapperCorba(orb, lrmIor);
		
		return lrm;
	}	
	
	/**
	 Creates an Asct wrapper
	 
	 @param asctIor - Identifier of a determined Asct
	 @return the Asct wrapper
	 */
	public synchronized AsctWrapper createAsctWrapper (String asctIor) {
		AsctWrapper asct = new AsctWrapperCorba(orb, asctIor);
		
		return asct;
	}
	
	/**
	 Creates an AgentHandler wrapper
	 
	 @param ahIor - Identifier of a determined AgentHandler
	 @return the AgentHandler wrapper
	 */    
	public synchronized AgentHandlerWrapper createAgentHandlerWrapper (String ahIor) {
		this.agentHandler = new AgentHandlerWrapperCorba (orb, ahIor);
		
		return agentHandler;
	}
}
