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

package core.brakes.be.ac.kuleuven.cs.ttm.transformer.rewriter;

import core.brakes.de.fub.bytecode.generic.ACONST_NULL;
import core.brakes.de.fub.bytecode.generic.CompoundInstruction;
import core.brakes.de.fub.bytecode.generic.DCONST;
import core.brakes.de.fub.bytecode.generic.FCONST;
import core.brakes.de.fub.bytecode.generic.ICONST;
import core.brakes.de.fub.bytecode.generic.Instruction;
import core.brakes.de.fub.bytecode.generic.InstructionList;
import core.brakes.de.fub.bytecode.generic.LCONST;
import core.brakes.de.fub.bytecode.generic.ReferenceType;
import core.brakes.de.fub.bytecode.generic.Type;

public class NULLCONST implements CompoundInstruction {
	private Instruction ins;
public NULLCONST(Type t) {
	if (t.equals(Type.LONG)) {
		ins = new LCONST(0);
		return;
	}
	if (t.equals(Type.DOUBLE)) {
		ins = new DCONST(0);
		return;
	}
	if (t.equals(Type.FLOAT)) {
		ins = new FCONST(0);
		return;
	}
	if (t instanceof ReferenceType) {
		ins = new ACONST_NULL();
		return;
	}
	ins = new ICONST(0);
}
public Instruction getInstruction() {
	return ins;
}
/**
 * getInstructionList method comment.
 */
public InstructionList getInstructionList() {
	return new InstructionList(ins);
}
}
