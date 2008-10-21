package core.appexec.magagent.behaviours;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.net.URLClassLoader;

import core.appexec.agenthandler.ClassLoaderObjectInputStream;
import core.appexec.magagent.MagAgent;
import core.application.MagApplication;

public class MigrationFinishedBehaviour extends OneShotBehaviour {
	private static final long serialVersionUID = -8141409486811596637L;
	
	private MagAgent magAgent = null;
	
	public MigrationFinishedBehaviour(MagAgent magAgent) {
		super ((Agent) magAgent);
		
		this.magAgent = magAgent;
	}

	public void action() {
		try {
			URL url[] = null;
			
			if (magAgent.getAppName().endsWith(".class")) {
				url = new URL[] { new URL("file:./" + magAgent.getAppExecutionId() + "/"), new URL("file:.") };
			} else if (magAgent.getAppName().endsWith(".jar")) {
				url = new URL[] { new URL("file:./" + magAgent.getAppExecutionId() + "/" + magAgent.getAppName()), new URL("file:.") };
			}
			
			URLClassLoader loader = new URLClassLoader(url);
			
			// Deserializes the migrated bytecode and resume the application execution
			// setting the classloader of bytecode while loading
			ByteArrayInputStream bais = new ByteArrayInputStream (magAgent.getSerializedApplication());
			
			// Deserializes the bytecode associating with it a classloader
			ClassLoaderObjectInputStream clois = new ClassLoaderObjectInputStream (bais, loader);
			
			magAgent.setApplication((MagApplication) clois.readObject());
			
			magAgent.redirectOutput (magAgent.getApplication(), magAgent.getAppExecutionId());
			magAgent.getApplication().setBaseDir ("./" + magAgent.getAppExecutionId() + "/");
			
			// Resumes the application execution
			magAgent.getApplication().doResume();
			
			// Add behaviours: NotifyStatusBehaviour - monitors the application execution,
			// waiting for its end; kill the agent when the application finishes
			// CheckpointCollectBehaviour - saves periodically the application's checkpoint
			magAgent.addBehaviour (new NotifyStatusBehaviour(magAgent, magAgent.getApplication()));
			magAgent.addBehaviour (new CheckpointCollectBehaviour(magAgent));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
