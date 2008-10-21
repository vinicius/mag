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

/**
 * Running Computation is mainly a wrapper for a computation object
 * 	to execute in his own thread. In this way, the scheduler can
 * 	leave this computation running, and turn to other computations.
 *
 * @version $Id: RunningComp.java,v 1.1 2006-03-07 17:45:05 rafaelf Exp $
 * @author  <A HREF="http://www.cs.kuleuven.ac.be/~tim">Tim Coninx</A>
 */
public class RunningComp implements Runnable {

    private Computation thiscomp;

    public RunningComp(Computation comp) {
	thiscomp = comp;
    }

    public void run() {
	thiscomp.schedule();
    }
}
