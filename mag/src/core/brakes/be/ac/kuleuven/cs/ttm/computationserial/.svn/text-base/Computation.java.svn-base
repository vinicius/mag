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

import java.io.Serializable;

public class Computation implements Serializable,core.brakes.be.ac.kuleuven.cs.ttm.ttm.Computation {

  Runnable runnable;
  boolean firstTime;
  Context myContext;
  Boolean resumeAutomatic;
  
  public static boolean isRestoring = false;
  public static boolean isSwitching = false;
  
  public static boolean isRestoring() {
    return isRestoring;
  }

  public static boolean isSwitching() {
    return isSwitching;
  }

  public Computation(Runnable r) {
    runnable = r;
  }
  void schedule() {
    // System.out.println("Computation.schedule: firstTime = " + firstTime);
    resumeAutomatic = null;
    isSwitching = false;
    if (firstTime) {
      myContext = new Context();
      firstTime = false;
    } else {
      isRestoring = true;
      myContext.popThis();
    }
    Context.setCurrentContext(myContext);
    try {
      runnable.run();
    } finally {
      isSwitching = false;
    }
    // 	System.out.println("Computation.ended");
  }
  public void start() {
    firstTime = true;
    ((Scheduler)Scheduler.getInstance()).schedule(this);
  }
  /**
   * Suspend the execution of computation
   * @resume if true, computation will be resumed by "hand"
   *         if false, computation will be resumed automatically by
   *                   scheduler
   */ 
  public void yield(boolean resume) {
    if (isRestoring) {
      // 	    System.out.println("Computation.resumed\n" + myContext);
      isRestoring = false;
    } else {
      // 	    System.out.println("Computation.block");
      resumeAutomatic = new Boolean(!resume);
      if (resumeAutomatic.equals(Boolean.TRUE))
	((Scheduler)Scheduler.getInstance()).schedule(this);
      isSwitching = true;
      myContext.pushThis(this);
    }
  }

  public void resume() {
    if (resumeAutomatic != null) {
      if (resumeAutomatic.equals(Boolean.FALSE))
	((Scheduler)Scheduler.getInstance()).schedule(this);
      else throw new IllegalStateException(this + ".resume()");
    }
    else throw new IllegalStateException(this + ".resume()");
  }	
  

  public String toString() {
    return runnable.toString();
  }
}

