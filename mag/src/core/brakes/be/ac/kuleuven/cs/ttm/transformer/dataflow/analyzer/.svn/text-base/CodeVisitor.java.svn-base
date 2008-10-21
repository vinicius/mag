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

import core.brakes.de.fub.bytecode.generic.InstructionHandle;

class CodeVisitor {

  static public CodeVisitor instance;

  Opcode[] opcodes;
  public CodeVisitor(Opcode[] opc) {
	opcodes = opc;
	instance = this;
  }  
public void compute(Environment e) {
	InstructionHandle ins = e.getChangedInstruction();
	while (ins != null) {
	        System.out.println(ins);
		System.out.println(ins.getInstruction().getTag());
		opcodes[ins.getInstruction().getTag()].compute(ins, e);
                System.out.println("1");
		ins = e.getChangedInstruction();
		System.out.println("2");
	}
}
}
