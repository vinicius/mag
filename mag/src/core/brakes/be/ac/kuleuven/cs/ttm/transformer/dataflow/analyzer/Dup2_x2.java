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

import java.util.Vector;

import core.brakes.be.ac.kuleuven.cs.ttm.transformer.dataflow.Frame;
import core.brakes.be.ac.kuleuven.cs.ttm.transformer.dataflow.Stack;
import core.brakes.be.ac.kuleuven.cs.ttm.transformer.dataflow.VirtualType;
import core.brakes.de.fub.bytecode.generic.InstructionHandle;
import core.brakes.de.fub.bytecode.generic.MethodGen;


class Dup2_x2 extends Noop {
Vector execute(MethodGen mGen, InstructionHandle ins, Stack s, Frame f) {
	VirtualType tp1 = s.pop();
	VirtualType tp2 = s.pop();
	VirtualType tp3 = s.pop();
	VirtualType tp4 = s.pop();
	s.push(tp2);
	s.push(tp1);
	s.push(tp4);
	s.push(tp3);
	s.push(tp2);
	s.push(tp1);
	return super.execute(mGen, ins, s, f);
}
}
