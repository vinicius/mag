/*
 * Created on 02/06/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author Braga Junior.
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package arsc;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.security.auth.Subject;
import javax.security.auth.kerberos.KerberosPrincipal;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.security.MessageDigest;
import java.security.DigestException;
import java.security.NoSuchAlgorithmException;


import org.ietf.jgss.*;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import com.sun.security.auth.callback.DialogCallbackHandler;
import com.sun.security.auth.callback.TextCallbackHandler;
import com.sun.security.auth.module.Krb5LoginModule;

import arsm.ContextManager;
import arsm.SignatureUtilities;
import dataTypes.*;
import clusterManagement.Arsm;
import clusterManagement.ArsmHelper;
import clusterManagement.SignatureCheckingException;
import clusterManagement.ContextFinalizationException;
import clusterManagement.ContextInitiationException;
import clusterManagement.SignatureRequestException;
import arsm.StreamHandler;

public class ArscImpl {
	private Arsm arsm;
	private String arsmIor;
	private ORB orb;
	private GSSManager manager;
	private GSSName targetName;
	private GSSContext context;
	private LoginContext lc;
	public String id;
	public static boolean isWrap=false;
	
	public ArscImpl(){
		initStubs();
	}
	public void initStubs()
	{
		Properties p = new Properties();
		p.put("org.omg.CORBA.ORBClass","org.jacorb.orb.ORB");
		p.put("Dorg.omg.CORBA.ORBSingletonClass","org.jacorb.orb.ORBSingleton");
		orb = ORB.init(new String [] {}, p);
		try{ 
//	    get a reference to the naming service
	      org.omg.CORBA.Object ns = orb.resolve_initial_references("NameService");
	      NamingContextExt nameService = NamingContextExtHelper.narrow( ns );
//	    look up GRM
	      arsm = ArsmHelper.narrow( nameService.resolve(nameService.to_name("ARSM")) );
	      arsmIor=arsm.toString();
	      BufferedReader configFile = new BufferedReader(
										(new InputStreamReader
										 (new FileInputStream(new File("arsc.conf")))));
		  String line = "";
		  while((line = configFile.readLine()) != null){
			StringTokenizer st = new StringTokenizer(line);
			String configOption = st.nextToken();
			if(configOption.equals("isWrap")){
				isWrap=(st.nextToken().compareTo("true")==0);
			}

		  }
		}
		catch(FileNotFoundException fnfe){
			//Difficult to happen, once that the file was choosed through a FileChooser
			System.err.println("Inexistent file");
			fnfe.printStackTrace();
			System.exit(-1);
		}
		catch(IOException ioe){
		  ioe.printStackTrace();
		  System.exit(-1);
		}catch(Exception e)
		{
			System.err.println(e.toString());
		}
	
	}
	class initContextDoAs implements  java.security.PrivilegedAction
	{

		public Object run() {
			// TODO Auto-generated method stub
			try {
				manager = GSSManager.getInstance();
				GSSName serviceName = manager.createName("srv/ARSM", null);
				 Oid krb5Oid = new Oid("1.2.840.113554.1.2.2");
				 KerberosPrincipal userPrinc = (KerberosPrincipal) lc.getSubject().getPrincipals(KerberosPrincipal.class).iterator().next();
	             GSSName userGSSName = manager.createName(userPrinc.getName(), GSSName.NT_USER_NAME);  		 			
	             GSSCredential userCreds = manager.createCredential(userGSSName,
	                             GSSCredential.DEFAULT_LIFETIME, krb5Oid,
	                             GSSCredential.INITIATE_ONLY);
				context = manager.createContext(serviceName,
								krb5Oid,
								userCreds,
								GSSContext.DEFAULT_LIFETIME);				
									

			} catch (GSSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return context;
		}
		
	}
	
	public boolean initContext() throws ContextInitiationException
	{
		
		if(auth("com.sun.security.jgss.initiate"))
		{
			
		if(Subject.doAs(lc.getSubject(),new initContextDoAs()) == null)
		{
			System.out.println("erro");
			System.exit(0);
		}
		
		
		StreamHandler msgOut;
		StreamHandler msgIn;
		byte[] inToken = new byte[1];
		inToken[0]=(byte) 0;
		byte[] outToken;
		
			try {
				while (!context.isEstablished()) {
				
				outToken = context.initSecContext(inToken, 0, 
						      inToken.length);
			
				// send the output token if generated
					if (outToken != null)
					{
						id = context.getSrcName().toString();
						msgOut=new StreamHandler(id.getBytes(),outToken);
						msgIn=new StreamHandler(arsm.initiateContext(msgOut.getStream()));
						inToken=msgIn.getMsg();
					}
				}
				
			} catch (GSSException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.exit(0);
				throw new ContextInitiationException();
			}
		
		    System.out.println("Context initiate sucessfull");
			return true;
		 } else{
			System.err.println("Not Logged");
			System.exit(1);
				}
		
		return false;
	}
	
	public void finishContext() throws  ContextFinalizationException
	{
		StreamHandler idSigned = new StreamHandler(id.getBytes(),id.getBytes());
		arsm.finalizeContext(idSigned.getStream());
	}
	

	public byte[] checkForeignSignature(byte[] signedStream) throws SignatureCheckingException
	{
		StreamHandler messageSigned=new StreamHandler(signedStream);
		StreamHandler psDoubleSigned= new StreamHandler(getId(),messageSigned.getStream());
		try {
			psDoubleSigned=SignatureUtilities.requestSignature(psDoubleSigned,context,isWrap);
		} catch (GSSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		StreamHandler MPRet=null;
			
		MPRet =  new StreamHandler(arsm.checkSignature(psDoubleSigned.getStream()));	
		try {
			return SignatureUtilities.checkSignature(MPRet,context,isWrap).getMsg();
		} catch (GSSException e) {
			throw new SignatureCheckingException(e.toString());
		}		
	}
	
	public String checkForeignSignature(String signedString) throws SignatureCheckingException
	{
		return new String( checkForeignSignature(signedString.getBytes()));
	}
	
	public byte[] requestForeignSignature(byte[] targetId, byte[] message) throws SignatureRequestException 
	{
		StreamHandler messagePacket= new StreamHandler(targetId,message);
		StreamHandler messageSigned= new StreamHandler(messagePacket.getId(),messagePacket.getMsg());
		messageSigned = new StreamHandler(getId(),messageSigned.getStream());
		try {
			messageSigned = SignatureUtilities.requestSignature(messageSigned,context,isWrap);
		} catch (GSSException e1) {
			e1.printStackTrace();
			
		}
		StreamHandler MPRet=null;
		try{
			MPRet =  new StreamHandler(arsm.requestSignature(messageSigned.getStream()));
		}catch(Exception e){
			System.err.println(e.toString());
			throw new SignatureRequestException(e.toString());
		}
		try {
			messageSigned = SignatureUtilities.checkSignature(MPRet,context,isWrap);
		} catch (GSSException e) {
			// TODO Auto-generated catch block
			throw new SignatureRequestException(e.toString());
		} 
		return messageSigned.getMsg();
    }
	
	public String requestForeignSignature(String targetId, String message) throws SignatureRequestException
	{
		return new String(requestForeignSignature(targetId.getBytes(),message.getBytes()));
	}
	
	public  byte[] checkSignature(byte[] signedStream) throws SignatureCheckingException
	{
		StreamHandler messagePacketSigned=new StreamHandler(signedStream);
		try {
			return SignatureUtilities.checkSignature(messagePacketSigned, context,isWrap).getMsg();
		} catch (GSSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SignatureCheckingException(e.toString());
		}
	}
	
	public String checkSignature(String signedString) throws SignatureCheckingException
	{
		return new String (checkSignature(signedString.getBytes()));
	}
	
	public byte[] requestSignature(byte[] messageStream) throws SignatureRequestException 
	{
		// sign digest
		StreamHandler message=new StreamHandler(id.getBytes(),messageStream); 
		try {
			return  SignatureUtilities.requestSignature(message,context,isWrap).getStream();
		} catch (GSSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SignatureRequestException(e.toString());
		}
		
	}
	
	public String requestSignature(String signedString) throws SignatureRequestException
	{
		return new String (requestSignature(signedString.getBytes()));
	}
	
	
	public boolean auth(String name)
	{
		lc = null;
			
				 ArscCallbackHandler arscCallbackHandler = new ArscCallbackHandler();
				 while(lc == null || lc.getSubject()==null)
				 {
					 try {
					 lc = new LoginContext(name, arscCallbackHandler);
					 lc.login();
					 } catch (LoginException le) {
						 arscCallbackHandler.resetPassword();
						 System.err.println("Authentication failed:");
						 System.err.println("  " + le.getMessage());
					 } catch (SecurityException se) {
						 System.err.println("Cannot create LoginContext. "
							 + se.getMessage());
						 System.exit(-1);
					 } 
				 }

			

			System.out.println("Authentication succeeded!");
			Iterator principalIterator = lc.getSubject().getPrincipals().iterator();
		   	System.out.println("Authenticated user has the following Principals:");
		   while (principalIterator.hasNext())
		   {
			   KerberosPrincipal p = (KerberosPrincipal) principalIterator.next();
			   System.out.println("\t" + p.toString());
		   }
		   
			return true;

   }
	public byte[] verifyDigest(byte[] messageDigest) throws arsm.DigestException{
		return SignatureUtilities.verifyDigest(messageDigest);
	}
	public  static byte[] messageDigest(byte[] inputStream) throws arsm.DigestException
	{
	
		return SignatureUtilities.messageDigest(inputStream);
	}
    		
	public byte[] getId()
	{
		return this.id.getBytes();
	}
	public String getStringId()
	{
		return this.id;
	}
	public static byte[] getId(byte[] message)
	{
		return StreamHandler.getId(message);
	}
	public static String getId(String message)
	{
		return new String(StreamHandler.getId(message.getBytes()));
	}
}
