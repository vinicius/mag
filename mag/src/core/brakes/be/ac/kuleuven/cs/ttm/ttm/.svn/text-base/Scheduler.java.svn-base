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

package core.brakes.be.ac.kuleuven.cs.ttm.ttm;

/**
  * This is the toplevel interface for the Scheduler classes
  * @version $Id: Scheduler.java,v 0.1
  * @author <A HREF="http://www.cs.kuleuven.ac.be/~tim">T.Coninx</A>
  */
public abstract class Scheduler {

  private static Scheduler instance = null;

  public static Scheduler getInstance() {
    if (instance == null) {
      Factory f = Factory.getInstance();
      try {
        instance = (Scheduler)f.getScheduler().newInstance();
      } catch (Exception e) {
        e.printStackTrace();
	System.exit(1);
      }
    }
    return instance;
  }

  public abstract Computation currentComputation();

  public abstract void start();
}
