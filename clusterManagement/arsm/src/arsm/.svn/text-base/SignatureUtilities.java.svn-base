package arsm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.ietf.jgss.*;

/**
 * @author Braga Junior.
 *
 */
public class SignatureUtilities {
	private static ArsmLogger arsmLogger =  ArsmLogger.init();
	private static byte[] nullByte={0};
	
	public static StreamHandler checkSignature(StreamHandler messageStream, GSSContext gssContext, boolean useEncriptedMessages) throws GSSException
	{
		byte[] unwrappedMessage;
		
		MessageProp messageProp = new MessageProp(useEncriptedMessages);
		String messageIdentification = new String(messageStream.getId());

		try
		{ 
			unwrappedMessage = gssContext.unwrap(messageStream.getMsg(), 0, messageStream.getMsgLength(), messageProp);
			if(unwrappedMessage.length==nullByte.length && unwrappedMessage[0]==nullByte[0])
			{
				unwrappedMessage="".getBytes();
			}
			
			return new StreamHandler(messageStream.getId(), unwrappedMessage);
		}
		catch (GSSException gssException)
		{
			System.err.println("Error verifing siginature for message id " + messageIdentification + ": " + gssException.getMessage());
			arsmLogger.severe("Error verifing siginature for message id " + messageIdentification + ": " + gssException.getMessage());
			
			throw gssException;			
		}
	}
	
	public static StreamHandler requestSignature(StreamHandler messageStream, GSSContext gssContext, boolean useEncriptedMessages) throws GSSException
	{
		byte[] wrappedMessage;
		
		MessageProp messageProp = new MessageProp(useEncriptedMessages);
		String messageIdentification = new String(messageStream.getId());

		try
		{ 
			byte[] wrapMessage=nullByte;
			if(messageStream.getMsg().length>0)
			{
				wrapMessage=messageStream.getMsg();
			}
			
			wrappedMessage = gssContext.wrap(wrapMessage, 0, wrapMessage.length, messageProp);
			
			return new StreamHandler(messageStream.getId(), wrappedMessage);
		}
		catch (GSSException gssException)
		{
			System.err.println("Error signing message id " + messageIdentification + ": " + gssException.getMessage());
			arsmLogger.severe("Error signing message id " + messageIdentification + ": " + gssException.getMessage());
			
			throw gssException;			
		}
	}
	
	public static byte[] messageDigest(byte[] messageStream) throws DigestException
	{
		byte[] digest = null;
		
		try
		{	
			MessageDigest messageDigest = MessageDigest.getInstance("SHA");
			messageDigest.update(messageStream);
			digest = messageDigest.digest();
			
			return digest;
		}
		catch (NoSuchAlgorithmException noSuchAlgorithmException) {
			DigestException digestException = new DigestException("Message Digest error: " + noSuchAlgorithmException.getMessage());
			
			throw digestException;
		}
		
	}
	
	public static byte[] verifyDigest(byte[] messageAndDigest) throws DigestException
	{
		try
		{
			MessageDigest messgeDigest = MessageDigest.getInstance("SHA");
			
			int digestLength = messgeDigest.getDigestLength();
			int messageAndDigestLength = messageAndDigest.length;
			
			byte[] message = new byte[messageAndDigestLength -  digestLength];
			byte[] digest = new byte[digestLength];
			
			System.arraycopy(messageAndDigest, digestLength, message, 0, message.length);
			System.arraycopy(messageAndDigest, 0, digest, 0, digest.length);
			
			messgeDigest.update(message);
			
			if(MessageDigest.isEqual(digest, messgeDigest.digest())){
				return message;
			}
			else
			{
				DigestException digestException = new DigestException("Digest verification error.");
				
				throw digestException;
			}
		}
		catch (NoSuchAlgorithmException noSuchAlgorithmException) {
			DigestException digestException = new DigestException("Digest verification error: " + noSuchAlgorithmException.getMessage());
			
			throw digestException;
			
		}
	}	
}