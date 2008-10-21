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
 * ThreadMapping maintains a link between the current executing thread,
 * 	and its computation/context
 *
 * @version $Id: ThreadMapping.java,v 1.1 2006-03-07 17:45:05 rafaelf Exp $
 * @author  <A HREF="http://www.cs.kuleuven.ac.be/~tim">Tim Coninx</A>
 */
public class ThreadMapping {
    private Hashtable comptable; // hashtable for computation <-> thread

    private Hashtable contable; // hashtable for context <-> thread

    private static ThreadMapping instance = null;

    private ThreadMapping() {
	comptable = new Hashtable();
	contable = new Hashtable();
    }

    public static ThreadMapping getInstance() {
	if (instance == null) {
	    instance = new ThreadMapping();
	}
	return instance;
    }

    public void registerThread(Computation c,Runnable r) {
	Thread t = (Thread)r;
	comptable.put(t,c);
	contable.put(t,c.myContext);
    }

    public void deregisterThread(Runnable r) {
	Thread t = (Thread)r;
	comptable.remove(t);
	contable.remove(t);
    }

    public Computation getComputation() {
	if (!comptable.isEmpty()) {
	  Thread t = Thread.currentThread();
	  return (Computation)comptable.get(t);
	} else {
	  return null;
	}
    }

    public Context getContext() {
	if (!contable.isEmpty()) {
	  Thread t = Thread.currentThread();
	  return (Context)contable.get(t);
	} else {
	  return null;
	}
    }
}
