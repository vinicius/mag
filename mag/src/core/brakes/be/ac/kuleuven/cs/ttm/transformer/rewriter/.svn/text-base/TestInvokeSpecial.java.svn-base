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

/**
 * This type was created in VisualAge.
 */
import core.brakes.de.fub.bytecode.generic.BranchInstruction;
import core.brakes.de.fub.bytecode.generic.ClassGen;
import core.brakes.de.fub.bytecode.generic.GOTO;
import core.brakes.de.fub.bytecode.generic.IFEQ;
import core.brakes.de.fub.bytecode.generic.Instruction;
import core.brakes.de.fub.bytecode.generic.InstructionConstants;
import core.brakes.de.fub.bytecode.generic.InstructionFactory;
import core.brakes.de.fub.bytecode.generic.InstructionList;
import core.brakes.de.fub.bytecode.generic.MethodGen;
import core.brakes.de.fub.bytecode.generic.ObjectType;
import core.brakes.de.fub.bytecode.generic.Type;

public class TestInvokeSpecial {
/**
 * Test constructor comment.
 */
public TestInvokeSpecial() {
	super();
}
public static void main(String[] args) {
	String[] empty = new String[] {};
	ClassGen cg = new ClassGen("tools.Test", "java.lang.Object", "c:\\tmp\\tools\\Test.class", Instruction.ACC_PUBLIC, empty);
	InstructionList insList = new InstructionList();
	MethodGen mg = new MethodGen(0, Type.VOID, Type.NO_ARGS, empty, "test", "tools.Test", insList, cg.getConstantPool());
	InstructionFactory insFactory = new InstructionFactory(cg.getConstantPool());
	Instruction invIns = insFactory.createInvoke("tools.Test", "<init>", Type.VOID, Type.NO_ARGS, Instruction.INVOKESPECIAL);
	insList.append(InstructionConstants.ICONST_2);
	BranchInstruction ifIns = new IFEQ(insList.getStart());
	insList.append(ifIns);
	Instruction newIns = insFactory.createNew((ObjectType) Type.getType("Ltools/Test;"));
	insList.append(newIns);
	// insList.append(invIns); // patch
	BranchInstruction gotoIns = new GOTO(insList.getStart());
	insList.append(gotoIns);
	insList.append(newIns);
	ifIns.setTarget(insList.getEnd());
	insList.append(invIns);
	/* patch */ gotoIns.setTarget(insList.getEnd());
	insList.append(InstructionConstants.RETURN);
	// gotoIns.setTarget(insList.getEnd()); // patch 
	mg.setMaxStack(5);
	cg.addMethod(mg.getMethod());
	cg.addEmptyConstructor(Instruction.ACC_PUBLIC);
	try {
		cg.getJavaClass().dump("c:\\tmp\\tools\\Test.class");
	} catch (java.io.IOException e) {
		System.err.println(e);
	}
}
}
