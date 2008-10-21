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

package core.brakes.be.ac.kuleuven.cs.ttm.computation;

import java.util.Hashtable;

/**
 * The Scheduler 
 * 	1) accepts computations
 * 	2) embeds them in a Running (runnable) computation
 * 	3) starts them up one by one
 *	4) restarts them if they get rescheduled
 *
 * @version $Id: Scheduler.java,v 1.1 2006-03-07 17:45:05 rafaelf Exp $
 * @author  <A HREF="http://www.cs.kuleuven.ac.be/~tim">Tim Coninx</A>
 */
public class Scheduler extends core.brakes.be.ac.kuleuven.cs.ttm.ttm.Scheduler {

  static private final int MAX_TASKS = 100;

  private Thread[] readyQueue;
  private int head;
  private int tail;
  public Thread currentComputation;

  Hashtable map;

  public Scheduler() {
      readyQueue = new Thread[MAX_TASKS];
      head = 0;
      tail = 0;
      map = new Hashtable();
  }  

  /**
   * Return the currently executing task.
   */
  public core.brakes.be.ac.kuleuven.cs.ttm.ttm.Computation currentComputation() {
	return (core.brakes.be.ac.kuleuven.cs.ttm.ttm.Computation)ThreadMapping.getInstance().getComputation();
  }  
  
  void schedule(Computation c) {
    if ((head + 1) % MAX_TASKS == tail) {
    	System.err.println("CoreScheduler.TaskList overflow !");
    	System.exit(-1);
    }
    RunningComp rc = new RunningComp(c);
    Thread t = new Thread(rc);
    readyQueue[head++] = t;
    	if (head == MAX_TASKS)
	head = 0;
        Debug.println("Scheduler.schedule() " + c);
  }

  public void start() {
        while (true) {
	  if (head != tail) {
	    currentComputation = readyQueue[tail];
	    if (++tail == MAX_TASKS) tail = 0;
    	    Debug.println("Scheduler: scheduling " + currentComputation);
	    currentComputation.start();
	  } else {
	    try {
	      Thread.sleep(200);
	    } catch (Exception e) {}
	  }
        }
  }
}
