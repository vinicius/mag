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
import core.brakes.de.fub.bytecode.generic.InstructionHandle;
import core.brakes.de.fub.bytecode.generic.LocalVariableInstruction;
import core.brakes.de.fub.bytecode.generic.MethodGen;
import core.brakes.de.fub.bytecode.generic.Type;

class Store extends Noop {
	private int size;
	
	Store(Type t) {
		size = t.getSize();
	}
	Vector execute(MethodGen mGen, InstructionHandle ins, Stack s, Frame f) {
		System.out.println("stack: " + s);
		System.out.println("frame: " + f);
		
		// VirtualType v = null;
		if (size > 1) 
			s.pop();
			//v = s.pop();
		
		LocalVariableInstruction lins = (LocalVariableInstruction) ins.getInstruction();
		f.put(s.pop(), lins.getIndex());
		return super.execute(mGen,ins,s,f);
	}
}
