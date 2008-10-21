package core.ft.erm.behaviours;

import jade.core.behaviours.TickerBehaviour;

import java.util.HashMap;
import java.util.Random;

import core.ft.erm.ErmAgent;

/**
 * Behaviour FailureGeneratorTickerBehaviour 
 * 
 * This behaivour acts as failure generator of the container.
 * All the agents of the container are killed every time a generatorError method returns true
 * 
 * @author Vinicius Pinheiro
 */
public class FailureGeneratorTickerBehaviour extends TickerBehaviour {
	private ErmAgent erm;
	private HashMap ahAgents;
	private Random rand = new Random();
	private double mtbf;
    private double mtbfInSec;
    private long interval;
    private int size;
    private static boolean vector[];

	
	public FailureGeneratorTickerBehaviour(ErmAgent ea, long interval) {
		super(ea, interval);
		this.erm = ea;
		//this.mtbf = 0.5;
		this.mtbf = 1.0;
		this.interval = 10000;
		mtbfInSec = mtbf*60000;
		size = (int)(mtbfInSec/interval);
		vector = new boolean[size];
		vector[0] = true;
		for(int i = 1; i < vector.length; i++) {
			vector[i] = false;
		}

	}
	
	public void onTick() {
		if (generateError()) {
			erm.generateRandomError();
		}
	}
	
	private boolean generateError() {
		int i = rand.nextInt(size);
		return vector[i];
	}
}
