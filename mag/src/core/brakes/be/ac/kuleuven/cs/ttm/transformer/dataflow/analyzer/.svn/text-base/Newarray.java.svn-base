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
import core.brakes.be.ac.kuleuven.cs.ttm.transformer.dataflow.VirtualReferenceType;
import core.brakes.de.fub.bytecode.generic.ArrayType;
import core.brakes.de.fub.bytecode.generic.BasicType;
import core.brakes.de.fub.bytecode.generic.InstructionHandle;
import core.brakes.de.fub.bytecode.generic.MethodGen;
import core.brakes.de.fub.bytecode.generic.NEWARRAY;

class Newarray extends Noop {
	Newarray() {
	}
	Vector execute(MethodGen mGen, InstructionHandle ins, Stack s, Frame f) {
		//ConstantPool cp = mGen.getConstantPool().getConstantPool();
		NEWARRAY naIns = (NEWARRAY) ins.getInstruction();
		for (int i = 0; i < naIns.consumeStack(); i++)
			s.pop(); // pop array size
		s.push(new VirtualReferenceType(new ArrayType(new BasicType(naIns.getTypeTag()), naIns.consumeStack())));
		return super.execute(mGen, ins, s, f);
	}
}
