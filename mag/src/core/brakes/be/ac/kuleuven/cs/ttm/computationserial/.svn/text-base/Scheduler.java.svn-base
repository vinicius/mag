/** This file is part of the BRAKES framework v0.3
  * Developed by: 
  *   Distributed Systems and Computer Networks Group (DistriNet)
  *   Katholieke Universiteit Leuven  
  *   Department of Computer Science
  *   Celestijnenlaan 200A
  *   3001 Leuven (Heverlee)
  *   Belgium
  * Project Manager and Principal Investigator: 
  *                        Pierre Verbaeten(pv@cs.kuleuven.ac.be)
  * Licensed under the Academic Free License version 1.1 (see COPYRIGHT)
  */

package core.brakes.be.ac.kuleuven.cs.ttm.computationserial;

public class Scheduler extends core.brakes.be.ac.kuleuven.cs.ttm.ttm.Scheduler {

  static private final int MAX_TASKS = 100;

  private Computation[] readyQueue;
  private int head;
  private int tail;
  private Computation currentComputation;

  public Scheduler() {
	readyQueue = new Computation[MAX_TASKS];
	head = 0;
	tail = 0;
  }  
  /**
   * Return the currently executing task. As the CoreScheduler only allows
   * one task to execute concurrently, this is well-defined.
   */
  public core.brakes.be.ac.kuleuven.cs.ttm.ttm.Computation currentComputation() {
	return (core.brakes.be.ac.kuleuven.cs.ttm.ttm.Computation)currentComputation;
  }  
void schedule(Computation c) {
	if ((head + 1) % MAX_TASKS == tail) {
		System.err.println("CoreScheduler.TaskList overflow !");
		System.exit(-1);
	}
	readyQueue[head++] = c;
	if (head == MAX_TASKS)
		head = 0;
	// System.out.println("Scheduler.schedule() " + c);
}
/**
   * Start scheduling messages. This operation never finishes.
   */
public void start() {
	while (true) {
		if (head != tail) {
			currentComputation = readyQueue[tail];
			if (++tail == MAX_TASKS)
				tail = 0;
		} else
			break;
		System.out.println("Scheduler: scheduling " + currentComputation);
		currentComputation.schedule();
	}
}
}
