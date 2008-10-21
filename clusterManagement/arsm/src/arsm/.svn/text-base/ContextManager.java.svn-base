package arsm;
import clusterManagement.*;

import org.ietf.jgss.*;

import java.util.*;

/**
 * @author Braga Junior.
 *
 */
public class ContextManager {
	GSSManager	gssManager;
	
	private Map initializedContextMap = new HashMap();
	private Map uninitializedContextMap = new HashMap();
	
	private ArsmLogger arsmLogger =  ArsmLogger.init();
	
	public ContextManager(GSSManager gssManager)
	{
		this.gssManager = gssManager;
	}
	
	public synchronized GSSContext getContext(String identification) throws ContextFetchingException
	{
		GSSContext gssContext = (GSSContext) initializedContextMap.get(identification);
		
		if(gssContext == null)
		{
			arsmLogger.severe("Error while getting to initiate context for id " + identification);
			ContextFetchingException contextFetchingException = new ContextFetchingException("Error while getting to initiate context for id " + identification);
			
			throw contextFetchingException;
		}
		
		return gssContext;
	}
	
	public synchronized GSSContext tryContextInitialization(String identification) throws ContextInitiationException
	{
		GSSContext gssContext = (GSSContext) uninitializedContextMap.get(identification);
		
		try
		{
			if(gssContext == null)
			{
				gssContext = gssManager.createContext((GSSCredential) null);
				gssContext.requestConf(true);
				gssContext.requestMutualAuth(true);
				gssContext.requestReplayDet(true);
				gssContext.requestSequenceDet(false);
				uninitializedContextMap.put(identification, gssContext);
			} 					
		}
		catch (GSSException exception)
		{
			arsmLogger.severe("Error while trying to initiate context: " + exception.getMessage());
			ContextInitiationException contextInitiationException = new ContextInitiationException("Error while trying to initiate context: " + exception.getMessage());
			
			throw contextInitiationException;
		}
		
		return gssContext;
	}
	
	public synchronized void confirmContextInitialization(String identification)
	{
		GSSContext unitializedGssContext = (GSSContext) uninitializedContextMap.remove(identification);
		
		if(initializedContextMap.get(identification) != null)
		{	
			arsmLogger.warning("Attempt to double register for " + identification + ".");
			System.out.println("Attempt to double register for " + identification + ".");
		}
		
		initializedContextMap.put(identification, unitializedGssContext);
	}
	
	public synchronized void removeContext(String identification) throws ContextFinalizationException
	{
		GSSContext idContext = (GSSContext) initializedContextMap.get(identification);

		try
		{
			idContext.dispose();
			initializedContextMap.remove(identification);
		}
		catch(GSSException exception)
		{
			arsmLogger.severe("Error while trying to remove context: " + exception.getMessage());
			ContextFinalizationException contextFinalizationException = new ContextFinalizationException("Error while trying to remove context: " + exception.getMessage());
			
			throw contextFinalizationException;
		}	
	}	
}