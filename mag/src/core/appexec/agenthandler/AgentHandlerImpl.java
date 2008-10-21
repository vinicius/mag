package core.appexec.agenthandler;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Properties;

import java.io.*;

import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;
import org.omg.PortableServer.POA;

import clusterManagement.AgentHandlerPOA;

import clusterManagement.AgentHandler;
import clusterManagement.AgentHandlerHelper;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import core.appexec.magagent.MagAgent;
import core.ft.agentrecover.AgentRecoverImpl;
import core.wrappers.GrmWrapper;
import core.wrappers.WrapperFactory;
import dataTypes.AppRequeriments;
import dataTypes.ApplicationExecutionInformation;
import dataTypes.ExecutionRequestId;
import dataTypes.FileStruct;
import dataTypes.ProcessExecutionInformation;
import dataTypes.RequestAcceptanceInformation;

/**
 * Class AgentHandlerImpl - Servant implementation of AgentHandler
 * 
 * The AgentHandler acts as a mobile agent container, becoming transparent to
 * the grid, the existence of mobile agents.
 * 
 * @author Rafael Fernandes Lopes
 * @author
 */
public class AgentHandlerImpl extends AgentHandlerPOA implements Runnable {
	private String ipMainContainer = ""; // < Ip address of Main-Container

	private String myIor = "", iorFile = "";

	private AgentContainer ac; // < Reference to local mobile agents container

	private HashMap map; /*
							 * < Hash map that stores the agents/applications
							 * references running on local AgentHandler
							 */

	private static AgentHandlerImpl instance = null; // < Singleton instance
														// of AgentHandler

	private ThreadAwareOutputStream out = null, err = null; /*
															 * < Applications
															 * output managers.
															 */
	private ORB orb;

	public AgentHandlerImpl(ORB orb) {

		this.orb = orb;

		System.out.println("AgentHandlerImpl");
		try {
			POA rootpoa = POAHelper.narrow(orb
					.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();
			clusterManagement.AgentHandler agentHandler = AgentHandlerHelper
					.narrow(rootpoa.servant_to_reference(this));
			// Write the datamanager's ior;
			File f = new File("agentHandler.ior");
			f.createNewFile();
			FileWriter archivesWriter = new FileWriter(f);
			archivesWriter.write("agentHandler="
					+ orb.object_to_string(agentHandler));
			archivesWriter.close();

			System.out.println("file-final" + f.getAbsoluteFile()
					+ orb.object_to_string(agentHandler));
			// initiate the servant client.

			(new Thread(this)).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// initiate servant client.
	public void run() {
		orb.run();
	}

	/**
	 * Creates a new AgentHandlerImpl object
	 * 
	 * @param file -
	 *            path of local AgentHandler IOR file
	 */
	public AgentHandlerImpl(String file) {
		try {
			// cleanup();

			if (instance == null) {
				instance = this;
			} else {
				System.exit(0);
			}

			iorFile = file;

			map = new HashMap();
			Runtime rt = Runtime.instance();
			rt.setCloseVM(true);

			ipMainContainer = "";

			String tmpstr = "";
			String hostname = hostname = InetAddress.getLocalHost()
					.getHostName();

			if (hostname.indexOf('.') >= 0) {
				hostname = hostname.substring(0, hostname.indexOf('.'));
			}

			// Reads the ip address of main container (ipMainContainer
			// variable),
			// stored in mag.conf file.
			// BufferedReader br = new BufferedReader (new InputStreamReader
			// (new FileInputStream("../mag.conf")));

			Properties properties = new Properties();
			properties.load(new FileInputStream("setup.conf"));
			ipMainContainer = properties.getProperty("GRMMachineName");

			/*
			 * while ( (tmpstr = br.readLine()) != null ) { if
			 * (tmpstr.startsWith("GRMMachineName")) { ipMainContainer =
			 * tmpstr.substring(tmpstr.indexOf('=') + 1); ipMainContainer =
			 * InetAddress.getByName(ipMainContainer); break; } }
			 */

			// br.close();
			// Creation of a new AgentContainer
			Profile p = new ProfileImpl();
			p.setParameter(Profile.MAIN_HOST, ipMainContainer);

			System.out.println("IPMAIN$ " + ipMainContainer + "$");

			p.setParameter(Profile.MAIN_PORT, "1099");
			p.setParameter(Profile.CONTAINER_NAME, hostname);

			System.out.println("hostname$ " + hostname + "$");

			ac = rt.createAgentContainer(p);
			
			// Creation of output managers
			this.out = new ThreadAwareOutputStream(System.out);
			this.err = new ThreadAwareOutputStream(System.err);

			// Set the default output and error output
			System.setOut(new PrintStream(this.out));
			System.setErr(new PrintStream(this.err));

			// The user cannot request an input from keyboard
			try {
				System.in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the singleton instance of the local AgentHandler
	 * 
	 * @return instance of the unique local AgentHandler
	 */
	public static AgentHandlerImpl getInstance() {
		return instance;
	}

	/**
	 * Register an agent/application information in local AgentHandler
	 * 
	 * @param magAgent -
	 *            The agent reference
	 * @param appExecutionId -
	 *            Id of current execution request
	 * @param appMainRequestId -
	 *            Reserved for future use
	 * @param appNodeRequestId -
	 *            Reserved for future use
	 * @param appConstraints -
	 *            Constraints of an application
	 * @param appPreferences -
	 *            Prefences of an application
	 * @param asctIor -
	 *            Asct IOR that generated the request to grid
	 * @param outputFiles -
	 *            List of output files requested by the user
	 * 
	 * @return true if the register occurs successfully; otherwise returns false
	 */
	public synchronized void registerAgent(String appExecutionId,
			MagAgent magAgent) {
		System.out.println("Adding agent with executionId: " + appExecutionId);
		map.put(appExecutionId, magAgent);
	}

	/**
	 * Unregister an agent/application information from local AgentHandler
	 * 
	 * @param appExecutionId -
	 *            Id of current execution request to be unregistered
	 * @return returns true, if no errors occurs; otherwise returns false
	 */
	public synchronized void unregisterAgent(String appExecutionId) {
		map.remove(appExecutionId);

	}

	/**
	 * Method that starts the execution of an application on a grid node
	 * 
	 * @param applicationId -
	 *            Id of application in the ApplicationRepository
	 * @param appArgs -
	 *            Application arguments
	 * @param asctIor -
	 *            Asct IOR that generated the request to grid
	 * @param appMainRequestId -
	 *            Reserved for future use
	 * @param appNodeRequestId -
	 *            Reserved for future use
	 * @param appConstraints -
	 *            Constraints of an application
	 * @param appPreferences -
	 *            Prefences of an application
	 * @param outputFiles -
	 *            List of output files requested by the user
	 */
	public int requestExecution(
			ApplicationExecutionInformation applicationExecutionInformation,
			ProcessExecutionInformation processExecutionInformation) {

		int res = -1;

		core.wrappers.AsctWrapper asct = WrapperFactory.getInstance()
				.createAsctWrapper(
						applicationExecutionInformation.requestingAsctIor);

		if (processExecutionInformation.executionRequestId.requestId.trim()
				.length() > 0
				&& processExecutionInformation.executionRequestId.processId
						.trim().length() > 0) {
			String appExecutionId = processExecutionInformation.executionRequestId.requestId
					+ ":"
					+ processExecutionInformation.executionRequestId.processId;

			// Inform the Asct that accepted the execution request

			RequestAcceptanceInformation requestAcceptanceInformation = new RequestAcceptanceInformation();

			// Vinicius discommented {
			requestAcceptanceInformation.executionRequestId = processExecutionInformation.executionRequestId;
//			requestAcceptanceInformation.executionRequestId = new ExecutionRequestId(
//					processExecutionInformation.executionRequestId.requestId,
//					processExecutionInformation.executionRequestId.processId,
//					processExecutionInformation.executionRequestId.replicaId); // Vinicius
			// } Vinicius
			String[] tmpRequestId = (processExecutionInformation.executionRequestId.requestId
					.split(":"));
			requestAcceptanceInformation.executionRequestId.requestId = tmpRequestId[2];

			requestAcceptanceInformation.executionId = appExecutionId;
			requestAcceptanceInformation.lrmIor = myIor;
			System.out.println("AgentHandlerIor" + myIor);

			asct.setExecutionAccepted(requestAcceptanceInformation);

			res = this.requestExecution(appExecutionId,
					applicationExecutionInformation,
					processExecutionInformation, false);
		} else {
			asct.setExecutionRefused(
//					processExecutionInformation.executionRequestId.requestId.split(":")[2], // Vinicius on split
					processExecutionInformation.executionRequestId.requestId, 
					processExecutionInformation.executionRequestId.processId);
//					processExecutionInformation.executionRequestId.replicaId); // Vinicius on replicaId

		}

		return res;

	}

	/**
	 * 
	 */
	public void restoreExecution(
			ApplicationExecutionInformation applicationExecutionInformation,
			ProcessExecutionInformation processExecutionInformation) {
		String appExecutionId;

		if (Integer.parseInt(applicationExecutionInformation.numberOfReplicas) == 0) {
			appExecutionId = processExecutionInformation.executionRequestId.requestId
					+ ":"
					+ processExecutionInformation.executionRequestId.processId;
		} else {
			appExecutionId = processExecutionInformation.executionRequestId.requestId
					+ ":"
					+ processExecutionInformation.executionRequestId.processId
					+ ":" // Vinicius
					+ processExecutionInformation.executionRequestId.replicaId;
		}

		// this.restoreExecution(applicationExecutionInformation,
		// processExecutionInformation);
		this.requestExecution(appExecutionId, applicationExecutionInformation,
				processExecutionInformation, true);

		// this.requestExecution(applicationExecutionInformation,
		// processExecutionInformation , true);
	}

	/**
	 * Release the local node. If a user requests the local machine, it have to
	 * be released for he/she use.
	 */
	public void releaseNode() {
		GrmWrapper grm = WrapperFactory.getInstance().createGrmWrapper();
		Object agents[] = map.values().toArray();
		AppRequeriments appReq[] = new AppRequeriments[agents.length];

		// Generates a list of "AppRequeriments" of the local applications
		for (int i = 0; i < agents.length; i++) {
			MagAgent magAgent = (MagAgent) agents[i];
			appReq[i] = new AppRequeriments(magAgent.getAppConstraints(),
					magAgent.getAppPreferences());
		}

		// Asks to the GRM for new locations to execute the local applications
		// TODO: Descomentar a linha abaixo quando incluir o mecanismo de
		// tolerância a falhas
		// Vinicius {

		String[] undesirableLocations = {myIor};
		String result = grm.migrationLocationRequest(undesirableLocations);

		// Migrate the agents
		for (int i = 0; i < agents.length; i++) {
			if (!result.equals("")) {
				try {
					((MagAgent) agents[i]).migrateAgent(result);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		// } Vinicius

	}

	public MagAgent getAgent(String execId) {
		return ((MagAgent) map.get(execId));
	}

	/**
	 * Method invoked by an Asct to request the output files generated by an
	 * application execution
	 * 
	 * @param appExecutionId -
	 *            Id of current execution request
	 * @return an array of FileStruct (each of them representing an output file)
	 */
	public FileStruct[] requestOutputFiles(String executionId) {
		FileStruct[] results = null;

		try {
			// AppInfoHolder appInfo = (AppInfoHolder) map.get(appExeuctionId);
			// Vinicius discommented{
			 String fileList[] = (new File ("./" + executionId)).list();
//			 "/outputFiles")).list(); // Vinicius
//			String fileList[] = (new File(
//					"/home/pos/vinicius/integrade-mag/resourceProviders/lrm/"
//							+ executionId)).list();
			// } Vinicius

			if (fileList != null) {
				results = new FileStruct[fileList.length];

				for (int i = 0; i < fileList.length; i++) {
					results[i] = new FileStruct();
					results[i].fileName = fileList[i];
					// Vinicius {
//					 File file = new File("./" + executionId + "/outputFiles/"
					 File file = new File("./" + executionId + "/"
					 + results[i].fileName);
//					File file = new File(
//							"/home/pos/vinicius/integrade-mag/resourceProviders/lrm/"
//									+ executionId + "/" + results[i].fileName);
					// } Vinicius
					FileInputStream fis = new FileInputStream(file);
					byte[] serializedFile = new byte[(int) file.length()];

					fis.read(serializedFile);
					fis.close();
					results[i].file = serializedFile;
				}
			}
		} catch (FileNotFoundException fnfe) {
			System.err
					.println("AgentHandlerImpl::requestOutputFiles-->>Someone asked for an inexistent file");
		} catch (IOException ioe) {
			System.err
					.println("AgentHandlerImpl::requestOutputFiles-->>IOException");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return results;
	}

	/**
	 * Method invoked by an agent to inform that an application execution has
	 * finished
	 * 
	 * @param appExecutionId -
	 *            Id of current execution request
	 */
	synchronized public void appFinished(String appExecutionId) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(iorFile)));

			myIor = br.readLine();
			br.close();

			MagAgent magAgent = (MagAgent) map.get(appExecutionId);

			// Inform the Asct that the application execution has finished
			core.wrappers.AsctWrapper asct = WrapperFactory.getInstance()
					.createAsctWrapper(magAgent.getAsctIor());
			// Vinicius {
			// asct.setExecutionFinished(magAgent.getAppMainRequestId(),
			// magAgent.getAppNodeRequestId());
			System.out.println("AgentHandlerImpl->appFinished");
			asct.setExecutionFinished(magAgent.getAppMainRequestId(), magAgent
					.getAppNodeRequestId()); // Vinicius on replicaId
			
			// } Vinicius

			// Removes the local agent/application information references
			this.unregisterAgent(appExecutionId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method invoked by an Asct to kill a specific application.
	 * 
	 * @param appExecutionId -
	 *            Id of current execution reque
	 */
	public void killProcess(String executionId) {
		try {
			// Vinicius {
			System.out.println("Killing Process de executionId: " + executionId);
			MagAgent magAgent = (MagAgent) map.get(executionId);
//			String [] execId = executionId.split(":");
//			MagAgent magAgent;
//			if(execId.length == 5) {
//				magAgent = (MagAgent) map.get(execId[2]+":"+execId[4]+":"+execId[3]);
//			} else {
//				magAgent = (MagAgent) map.get(execId[2]+":"+execId[3]);
//			}
			// } Vinicius

			this.unregisterAgent(executionId);

			magAgent.doDelete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method invoked by an agent to request the output stream
	 * 
	 * @return the output controller
	 */
	public ThreadAwareOutputStream requestOutputStream() {
		return this.out;
	}

	/**
	 * Method invoked by an agent to request the error output stream
	 * 
	 * @return the output error controller
	 */
	public ThreadAwareOutputStream requestErrorStream() {
		return this.err;
	}

	/**
	 * @param applicationExecutionInformation
	 * @param processExecutionInformation
	 * @param isRestoring
	 *            TODO
	 * @return TODO
	 */
	private int requestExecution(String appExecutionId,
			ApplicationExecutionInformation applicationExecutionInformation,
			ProcessExecutionInformation processExecutionInformation,
			boolean isRestoring) {
		String applicationConstraints = "", applicationPreferences = "";

		try {
			// Generates the agent name
			String agentName = "mag-"
					+ java.net.InetAddress.getLocalHost().getHostAddress()
					+ "-" + System.currentTimeMillis();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(iorFile)));

			myIor = br.readLine();
			br.close();

			if (applicationExecutionInformation.applicationConstraints.length() == 0) {
				applicationConstraints = " ";
			}

			if (applicationExecutionInformation.applicationPreferences.length() == 0) {
				applicationPreferences = " ";
			}

			// Application information passed to agent
			Object o[] = new Object[15];

			// o[0] = applicationExecutionInformation.applicationId;
			o[0] = applicationExecutionInformation.applicationName;
			o[1] = applicationExecutionInformation.basePath;
			o[2] = processExecutionInformation.processArguments;
			o[3] = processExecutionInformation.executionRequestId.requestId;
			o[4] = processExecutionInformation.executionRequestId.processId;
			o[5] = applicationConstraints;
			o[6] = applicationPreferences;
			o[7] = applicationExecutionInformation.requestingAsctIor;
			o[8] = processExecutionInformation.outputFileNames;
			o[9] = new Boolean(isRestoring);
			o[10] = new Integer(
					applicationExecutionInformation.numberOfReplicas);
			o[11] = myIor;
			// Vinicius -> necessário já que o replicaId vem vazio na submissão de execução {
			if(processExecutionInformation.executionRequestId.replicaId.equals("") || 
					processExecutionInformation.executionRequestId.replicaId.equals("0")) {
				String[] reqId = appExecutionId.split(":");
				if(reqId.length == 5) {
					processExecutionInformation.executionRequestId.replicaId = reqId[3];
				}
			}
			System.out.println("Setting replicaId to: " + processExecutionInformation.executionRequestId.replicaId);
			// } Vinicius
			o[12] = processExecutionInformation.executionRequestId.replicaId;
			o[13] = applicationExecutionInformation;
			o[14] = processExecutionInformation;

			// Agent creation on local AgentHandler
			AgentController agent = ac.createNewAgent(agentName,
					"core.appexec.magagent.MagAgent", o);
			agent.start();
		} catch (Exception e) {
			try {
				MagAgent agent = ((MagAgent) map.get(appExecutionId));

				this.unregisterAgent(appExecutionId);
				agent.doDelete();

				this.appFinished(appExecutionId);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return 0;
	}

	// /**
	// Removes the application directories. These directories stores
	// the execution results of applications, including the outputs
	// */
	// private void cleanup () {
	// int i = 10000;
	// File file = new File("./" + i);
	//		
	// while ( file.exists() ) {
	// System.out.println ("Removing directory " + file.getName() + "/");
	// removeDirectory (file);
	//			
	// i++;
	// file = new File("./" + i);
	// }
	// }

	/**
	 * Used by the cleanup method to remove a directory recursively
	 * 
	 * @param directory -
	 *            Directory to be removed
	 */
	private void removeDirectory(File directory) {
		File fileList[] = directory.listFiles();

		for (int i = 0; i < fileList.length; i++) {
			File f = fileList[i];

			if (f.getName().equals(".") || f.getName().equals("..")) {
				continue;
			}

			if (f.isDirectory()) {
				removeDirectory(f);
			}

			f.delete();
		}

		directory.delete();
	}

	public int getLastCheckpointNumber(String arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getStatus(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isAvailable() {
		// TODO Auto-generated method stub
		return false;
	}

	public int restartExecution(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setKeepAliveInterval(int arg0) {
		// TODO Auto-generated method stub

	}

	public void setSampleInterval(int arg0) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Mata todos os agentes dessa plataforma
	 * @author Vinicius
	 * @return
	 */
	public synchronized void deleteAllAgents() {
		Object agents[] = map.values().toArray();
		int quant = agents.length;

		for (int i = 0; i < quant; i++) {
			if (agents[i] instanceof MagAgent) {
				((MagAgent) agents[i]).doDelete();
			}
		}
	}
}
