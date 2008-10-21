package core.ft.crm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;



public class CrmLauncher {
	
	private static String ipMainContainer = ""; //< Ip address of Main-Container
	private static AgentContainer ac; //< Reference to local mobile agents container
	private static String agentName;
	private static String hostname;
	
	public static void main(String[] args) {		
		
		try{ 	
			agentName = "crm-" + java.net.InetAddress.getLocalHost().getHostAddress() + "-" + System.currentTimeMillis();
		
			hostname = InetAddress.getLocalHost().getHostName();
	
		} catch ( UnknownHostException e) {
			e.printStackTrace();
		}
		
		
		Runtime rt = Runtime.instance();
		//rt.setCloseVM(true);
		
		Properties properties = new Properties();
		
		try{
			properties.load(new FileInputStream("setup.conf"));
		
		
		}catch(FileNotFoundException e){
			e.printStackTrace();
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		ipMainContainer = properties.getProperty("GRMMachineName");
		
		// Creation of a new AgentContainer
		Profile p = new ProfileImpl();
		p.setParameter(Profile.MAIN_HOST, ipMainContainer);
		
		System.out.println("IPMAIN$ " + ipMainContainer + "$");
		
		p.setParameter(Profile.MAIN_PORT, "1099");
		p.setParameter(Profile.CONTAINER_NAME, hostname);
		
		System.out.println("hostname$ " + hostname + "$");
		
		ac = rt.createAgentContainer(p);
		
		Object arguments[] = new Object[1];
		arguments[0]= ac;
		
		try{
			// Agent creation
			AgentController agent = ac.createNewAgent (agentName, "core.ft.replication.crm.CrmAgent", arguments);
			//agent.start();
		}catch(StaleProxyException e){
			
			
		}
		
	}

}
