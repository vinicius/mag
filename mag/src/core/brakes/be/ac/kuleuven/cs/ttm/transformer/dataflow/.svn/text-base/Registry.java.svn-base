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

package core.brakes.be.ac.kuleuven.cs.ttm.transformer.dataflow;

import java.util.Hashtable;

import core.brakes.de.fub.bytecode.generic.InstructionHandle;

public class Registry {
  private Hashtable sh; //Hashtable for instructionhandle <-> Stack
  private Hashtable fh; //Hashtable for instructionhandle <-> Frame
  private Hashtable nh; //Hashtable for invokespecialinstructionh. <-> Newinstr.

/**
 * Registry constructor comment.
 */
  public Registry() {
	super();
	sh = new Hashtable();
	fh = new Hashtable();
  }

  public Frame getFrame(InstructionHandle ins) {
	return (Frame) fh.get(ins);
  }

  public InstructionHandle getInstructionHandle(InstructionHandle hInvspec) {
	return (InstructionHandle) nh.get(hInvspec);
  }

  public Stack getStack(InstructionHandle ins) {
	return (Stack) sh.get(ins);
  }

  // hInvspec is a handle of an invokespecial instruction that
  // initializes the object created by the new instruction referred to by hNew
  public void register(InstructionHandle hInvspec, InstructionHandle hNew) {
	nh.put(hInvspec, hNew);
  }

  public void register(InstructionHandle ins, Frame f) {
	fh.put(ins, f);
  }
  
  public void register(InstructionHandle ins, Stack s) {
	sh.put(ins, s);
  }
}
