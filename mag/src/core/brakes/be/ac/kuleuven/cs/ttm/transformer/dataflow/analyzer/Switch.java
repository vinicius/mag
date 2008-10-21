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
import core.brakes.de.fub.bytecode.generic.MethodGen;
import core.brakes.de.fub.bytecode.generic.Select;
import core.brakes.de.fub.bytecode.generic.Type;

class Switch extends Branch {
	Switch() {
		super(new Type[] { Type.INT } );
	}
Vector execute(MethodGen mGen, InstructionHandle ins, Stack s, Frame f) {
        System.out.println("&&&&&&&& "  + ins);
        System.out.println(s);
        System.out.println(f);
	Vector v = super.execute(mGen,ins,s,f);
	Select swIns = (Select) ins.getInstruction();
	InstructionHandle[] targets = swIns.getTargets();
	for (int i = 0; i < targets.length; i++)
		v.addElement(targets[i]);
	return v;
}
}





