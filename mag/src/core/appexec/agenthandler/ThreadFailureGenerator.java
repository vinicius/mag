package core.appexec.agenthandler;

import java.util.HashMap;
import java.util.Random;

import core.ft.erm.ErmAgent;

/**
 * Class ThreadFailuresGenerator 
 * 
 * This thread acts as failure generator of the container.
 * All the agents of the container are killed at specific times give by the AgentHandler
 * 
 * @author Vinicius Pinheiro
 * @deprecated
 */
public class ThreadFailureGenerator extends Thread{
	private ErmAgent erm;
	private HashMap ahAgents;
	private Random rand = new Random();
	private long mtbf;
    private long mtbfInSec;
    private long interval;
    private int size;
    private static boolean vector[];

	
	public ThreadFailureGenerator(ErmAgent ea) {
		erm = ea;
		mtbf = 2;
		interval = 10000;
		mtbfInSec = mtbf*60000;
		size = (int)(mtbfInSec/interval);
		vector = new boolean[size];
		vector[0] = true;
		for(int i = 1; i < vector.length; i++) {
			vector[i] = true;
		}

	}
	
	public void run() {	
		while(true) {
			if(generateError()) {
//				erm.generateRandomError();
			}
			try {
				sleep(interval);
//				yield();
			} catch (InterruptedException e) {
				System.out.println("Error while sleeping failure generator thread");
				e.printStackTrace();
			}
		}
	}
	
	private boolean generateError() {
		int i = rand.nextInt(size);
		return vector[i];
	}
}
