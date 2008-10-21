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

import java.io.Serializable;

/**
 * Responsible for encapsulating a JVM Thread.
 *
 * @version $Id: Computation.java,v 1.1 2006-03-07 17:45:05 rafaelf Exp $
 * @author  <A HREF="http://www.cs.kuleuven.ac.be/~tim">T. Coninx</A>
 */
public class Computation implements Serializable,core.brakes.be.ac.kuleuven.cs.ttm.ttm.Computation {

  Runnable runnable;
  boolean firstTime;
  public boolean running;
  Context myContext;
  Boolean resumeAutomatic;
  
  public boolean isRestoring = false;
  public boolean isSwitching = false;
  public boolean switchRequest = false;

  public static boolean isRestoring() {
    Computation comp = ThreadMapping.getInstance().getComputation();
    boolean result = false;
    if (comp != null) {
      result = comp.isRestoring;
    }
    return result;
  }

  public static boolean isSwitching() {
    Computation comp = ThreadMapping.getInstance().getComputation();
    boolean result = false;
    if (comp != null) {
      result = comp.isSwitching;
    }
    return result;
  }
  
  public Computation(Runnable r) {
    runnable = r;
  }

  void schedule() {
    Debug.println("Computation.schedule: firstTime = " + firstTime);
    resumeAutomatic = null;
    isSwitching = false;
    if (firstTime) {
      myContext = new Context();
      firstTime = false;
    } else {
      isRestoring = true;
      myContext.popThis();
    }
    try {
      running = true;
      ThreadMapping.getInstance().registerThread(this,Thread.currentThread());
      Debug.println("thread " + Thread.currentThread().toString() + " registered -> Context = " + myContext.toString());
      Debug.println("running");
      runnable.run();
      Debug.println("stopped");
      ThreadMapping.getInstance().deregisterThread(Thread.currentThread());
      Debug.println("thread " + Thread.currentThread().toString() + " deregistered");
      running = false;
    } catch (Exception e) {
	e.printStackTrace();
    } finally {
      isSwitching = false;
    }
    Debug.println("Computation.ended");
  }

  /**
   * Start the computation by registering it with the Scheduler
   */
  public void start() {
    firstTime = true;
    ((Scheduler)(Scheduler.getInstance())).schedule(this);
    Debug.println(this.toString() + " scheduled");
  }

  /**
   * Suspend the execution of computation
   * @resume if true, computation will be resumed by "hand"
   *         if false, computation will be resumed automatically by
   *                   scheduler
   */ 
  public void yield(boolean resume) {
    if (isRestoring) {
      Debug.println("Computation.resumed\n" + myContext);
      isRestoring = false;
    } else {
      Debug.println("Computation.block");
      resumeAutomatic = new Boolean(!resume);
      if (resumeAutomatic.equals(Boolean.TRUE))
	((Scheduler)(Scheduler.getInstance())).schedule(this);
      isSwitching = true;
      myContext.pushThis(this);
    }
  }

  /**
   * Resume the execution of computation
   * Attention! is only possible if yield was called with argument "true"
   */
  public void resume() {
    if (resumeAutomatic != null) {
      if (resumeAutomatic.equals(Boolean.FALSE))
	((Scheduler)(Scheduler.getInstance())).schedule(this);
      else throw new IllegalStateException(this + ".resume()1");
    }
    else throw new IllegalStateException(this + ".resume()2");
  }	

  public String toString() {
    return runnable.toString();
  }
}

