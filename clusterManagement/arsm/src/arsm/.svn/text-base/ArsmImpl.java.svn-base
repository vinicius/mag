package arsm;

import clusterManagement.*;

import org.ietf.jgss.*;

/**
 * @author Braga Junior.
 *
 */

public class ArsmImpl extends ArsmPOA
{
	private GSSManager gssManager;
	private ContextManager contextManager;
	
	private static  String securityManagerIdentification = "ARSM"; 
	private boolean useEncriptedMessages = false;

	private ArsmLogger arsmLogger = ArsmLogger.init();
	
	public ArsmImpl(boolean useEncriptedMessages)
	{
		gssManager = GSSManager.getInstance();
		contextManager = new ContextManager(gssManager);
		
		this.useEncriptedMessages = useEncriptedMessages;
		
		System.out.println("Waiting for clients...");
	}
	
	byte[] getIdentification()
	{
		return securityManagerIdentification.getBytes();
	}
	
	public synchronized byte[] selfTest(byte[] messageStream) throws SelfTestException
	{
		arsmLogger.info("Testing the ARSM module");
		
		StreamHandler inputStreamHandler = new StreamHandler(messageStream);
		StreamHandler outputStreamHandler;
		
		try
		{
			inputStreamHandler = checkSignature(inputStreamHandler);
			outputStreamHandler = requestSignature(inputStreamHandler);
		}
		catch (Exception exception)
		{
			arsmLogger.severe("An error occurred during the test: " + exception.getMessage());
			SelfTestException selfTestException = new SelfTestException("An error occurred during the test: " + exception.getMessage());
			
			throw selfTestException;
		}
		
		if(inputStreamHandler.getStream() == null || outputStreamHandler.getStream() == null)
		{
			arsmLogger.severe("An error occurred during the test: Either input or output stream is not valid.");
			SelfTestException selfTestException = new SelfTestException("An error occurred during the test: Either input or output stream is not valid.");
			
			throw selfTestException;
		}
		
		return outputStreamHandler.getStream();
	}
	
	public synchronized byte[] initiateContext(byte[] contextStream) throws ContextInitiationException
	{
		GSSContext gssContext;
		
		StreamHandler contextRequestHandler = new  StreamHandler(contextStream);
		StreamHandler contextConfirmationHandler;
		
		String contextRequestIdentification = new String(contextRequestHandler.getId());
		
		try
		{
			gssContext = contextManager.tryContextInitialization(contextRequestIdentification);
			
			contextConfirmationHandler = new StreamHandler(getIdentification(),
					gssContext.acceptSecContext(contextRequestHandler.getMsg(), 0, contextRequestHandler.getMsg().length));
			
			if(gssContext.isEstablished())
			{
				System.out.println("Context accepted: " + gssContext.getSrcName().toString());
				contextManager.confirmContextInitialization(contextRequestIdentification);
			}
			
		}
		catch (Exception exception)
		{
			arsmLogger.severe("Initiation error for " + contextRequestIdentification + ": " + exception.getMessage());
			ContextInitiationException contextInitiationException = new ContextInitiationException("Initiation error for " + contextRequestIdentification + ": " + exception.getMessage());
			
			throw contextInitiationException;
		}
		
		if(contextConfirmationHandler != null)
		{
			return contextConfirmationHandler.getStream();
		}
		else
		{
			arsmLogger.severe("Initiation error for " + contextRequestIdentification + ": Initial token is invalid");
			ContextInitiationException contextInitiationException = new ContextInitiationException("Initiation error for " + contextRequestIdentification + ": Initial token is invalid");
			
			throw contextInitiationException;
		}
	}

	public synchronized void finalizeContext(byte[] contextStream) throws ContextFinalizationException
	{ 
		StreamHandler contextDroppingHandler = new StreamHandler(contextStream);
		String contextDroppingIdentification = new String(StreamHandler.getId(contextStream));
		
		try
		{
			GSSContext gssContext = contextManager.getContext(contextDroppingIdentification);
			
			SignatureUtilities.requestSignature(contextDroppingHandler, gssContext, true);
			contextManager.removeContext(contextDroppingIdentification);
		}
		catch(Exception exception)
		{
			arsmLogger.severe("Context finalization error for " + contextDroppingIdentification + ": " + exception.getMessage());
			ContextFinalizationException contextFinalizationException = new ContextFinalizationException("Context finalization error for " + contextDroppingIdentification + ": " + exception.getMessage());
			
			throw contextFinalizationException;
		}		
	}
	
	private synchronized StreamHandler checkSignature(StreamHandler messageStreamHandler) throws SignatureCheckingException
	{
		GSSContext gssContext;
		
		try
		{
			gssContext = contextManager.getContext(new String(messageStreamHandler.getId()));
			
			// FIXME: Lembrar de mudar o nome dessa classe.
			return SignatureUtilities.checkSignature(messageStreamHandler, gssContext, useEncriptedMessages);
		}
		catch (Exception exception) {
			arsmLogger.severe("Unable to check signature: " + exception.getMessage());
			SignatureCheckingException checkSignatureException = new SignatureCheckingException("Unable to check signature: " + exception.getMessage());
			
			throw checkSignatureException;			
		}
}
	
	public synchronized byte[] checkSignature(byte[] managerStream) throws SignatureCheckingException
	{	
		StreamHandler managerStreamHandler = new StreamHandler(managerStream);
		String managerIdentification = "UNKNOWN";
		
		try
		{			
			managerStreamHandler = checkSignature(managerStreamHandler);
			
			byte[] managerIdentificationInBytes = managerStreamHandler.getId();
			managerIdentification= new String(managerIdentificationInBytes);
			
			if(managerIdentification.indexOf("/ARSM@") == -1)
			{
				arsmLogger.severe("Id " + managerIdentification + " is not valid for signature checking.");
				SignatureCheckingException signatureCheckingException = new SignatureCheckingException("Id " + managerIdentification + " is not valid for signature checking.");
				
				throw signatureCheckingException;
			}
			
			StreamHandler clientStreamHandler = new StreamHandler(managerStreamHandler.getMsg());
			
			clientStreamHandler = checkSignature(clientStreamHandler);
			
			return (requestSignature(new StreamHandler(managerIdentificationInBytes, clientStreamHandler.getMsg()))).getStream();
		}
		catch(Exception exception)
		{
			arsmLogger.severe("Foreign signature checking error for id " + managerIdentification + ": " + exception.getMessage());
			SignatureCheckingException checkSignatureException = new SignatureCheckingException("Foreign signature checking error for id " + managerIdentification + ": " + exception.getMessage());
			
			throw checkSignatureException;
		}
	}
	
	private synchronized StreamHandler requestSignature(StreamHandler messageStreamHandler) throws SignatureCheckingException
	{		
		try
		{
			GSSContext gssContext = contextManager.getContext(new String(messageStreamHandler.getId()));
			
			// FIXME: Lembrar de mudar o nome dessa classe.
			return SignatureUtilities.requestSignature(messageStreamHandler,gssContext,useEncriptedMessages);
		}
		catch (Exception exception) {
			arsmLogger.severe("Unable to check signature: " + exception.getMessage());
			SignatureCheckingException checkSignatureException = new SignatureCheckingException("Unable to check signature: " + exception.getMessage());
			
			throw checkSignatureException;			
		}
	}
	
	public synchronized byte[] requestSignature(byte[] managerStream) throws SignatureRequestException
	{
		StreamHandler managerStreamHandler = new StreamHandler(managerStream);
		String managerIdentification = "UNKNOWN";
		
		try
		{
			managerStreamHandler = checkSignature(managerStreamHandler);
			
			byte[] managerIdentificationInBytes = managerStreamHandler.getId();			
			managerIdentification = new String(managerIdentificationInBytes);
			
			if(managerIdentification.indexOf("/ARSM@") == -1)
			{
				arsmLogger.severe("Id " + managerIdentification + " is not valid for signing.");
				SignatureRequestException signatureRequestException = new SignatureRequestException("Id " + managerIdentification + " is not valid for signing.");
				
				throw signatureRequestException;
			}
			
			StreamHandler clientStreamHandler;
			StreamHandler returnStreamHandler;
			
			clientStreamHandler = new StreamHandler(managerStreamHandler.getMsg());
			clientStreamHandler = requestSignature(clientStreamHandler);
			
			returnStreamHandler = new StreamHandler(managerIdentificationInBytes, clientStreamHandler.getStream());
			returnStreamHandler = requestSignature(returnStreamHandler);
			
			return returnStreamHandler.getStream(); 
		}
		catch(Exception exception)
		{
			arsmLogger.severe("Foreign signature request error for id " + managerIdentification + ": " + exception.getMessage());
			SignatureRequestException signatureRequestException = new SignatureRequestException("Foreign signature request error for id " + managerIdentification + ": " + exception.getMessage());
			
			throw signatureRequestException;
		}
	}
}