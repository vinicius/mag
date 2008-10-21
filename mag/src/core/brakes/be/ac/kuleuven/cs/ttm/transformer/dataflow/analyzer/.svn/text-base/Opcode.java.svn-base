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

package core.brakes.be.ac.kuleuven.cs.ttm.transformer.dataflow.analyzer;


import java.util.Enumeration;
import java.util.Vector;

import core.brakes.be.ac.kuleuven.cs.ttm.transformer.dataflow.Frame;
import core.brakes.be.ac.kuleuven.cs.ttm.transformer.dataflow.Stack;
import core.brakes.de.fub.bytecode.generic.InstructionHandle;
import core.brakes.de.fub.bytecode.generic.MethodGen;
public abstract class Opcode {
final public void compute(InstructionHandle ins, Environment e) {
	Stack s = new Stack(e.getStack(ins));
	Frame f = new Frame(e.getFrame(ins));
	MethodGen m = e.getMethodGen();
	Enumeration succ = execute(m,ins,s,f).elements();
	while (succ.hasMoreElements()) {
		InstructionHandle succIns = (InstructionHandle) succ.nextElement();
		boolean hasChanged;
		hasChanged = e.merge(succIns,s);
		hasChanged |= e.merge(succIns,f);
		if (hasChanged) e.addChanged(succIns);
	}
}
abstract Vector execute(MethodGen m, InstructionHandle ins, Stack s, Frame f);
}
