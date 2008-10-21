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
import core.brakes.de.fub.bytecode.generic.ASTORE;
import core.brakes.de.fub.bytecode.generic.BranchInstruction;
import core.brakes.de.fub.bytecode.generic.ClassGen;
import core.brakes.de.fub.bytecode.generic.FieldGen;
import core.brakes.de.fub.bytecode.generic.GETSTATIC;
import core.brakes.de.fub.bytecode.generic.IFLT;
import core.brakes.de.fub.bytecode.generic.ILOAD;
import core.brakes.de.fub.bytecode.generic.Instruction;
import core.brakes.de.fub.bytecode.generic.InstructionConstants;
import core.brakes.de.fub.bytecode.generic.InstructionFactory;
import core.brakes.de.fub.bytecode.generic.InstructionList;
import core.brakes.de.fub.bytecode.generic.MethodGen;
import core.brakes.de.fub.bytecode.generic.PUTFIELD;
import core.brakes.de.fub.bytecode.generic.Type;

public class TestAssignThis {
/**
 * Test constructor comment.
 */
public TestAssignThis() {
	super();
}
public static void main(String[] args) {
	String[] empty = new String[] {};
	ClassGen cg = new ClassGen("tools.AssignThis", "java.lang.Object", "c:\\tmp\\tools\\AssignThis.class", Instruction.ACC_PUBLIC, empty);
	InstructionFactory insFactory = new InstructionFactory(cg.getConstantPool());
	// add fields
	FieldGen fg = new FieldGen(Instruction.ACC_PUBLIC, Type.INT, "i", cg.getConstantPool());
	cg.addField(fg.getField());
	fg = new FieldGen(Instruction.ACC_STATIC, Type.getType("Ltools/AssignThis;"), "at", cg.getConstantPool());
	cg.addField(fg.getField());
	{ // add method
		InstructionList insList = new InstructionList();
		MethodGen mg = new MethodGen(0, Type.VOID, Type.NO_ARGS, empty, "test", "tools.AssignThis", insList, cg.getConstantPool());
		int index = cg.getConstantPool().addFieldref("tools/AssignThis", "at", "Ltools/AssignThis;");
		insList.append(InstructionConstants.ICONST_2);
		insList.append(new GETSTATIC(index));
		insList.append(new ASTORE(0));
		insList.append(InstructionConstants.ICONST_2);
		insList.append(InstructionConstants.IADD);
		insList.append(InstructionConstants.RETURN);
		mg.setMaxStack(5);
		cg.addMethod(mg.getMethod());
	}
	{ // add constructor
		InstructionList insList = new InstructionList();
		MethodGen mg = new MethodGen(0, Type.VOID, new Type[] {Type.INT}, new String[] {"i"}, "<init>", "tools.AssignThis", insList, cg.getConstantPool());
		insList.append(new ILOAD(1));
		BranchInstruction bIns = new IFLT(insList.getStart());
		insList.append(bIns);
		insList.append(InstructionFactory.createThis());
		insList.append(insFactory.createInvoke("java.lang.Object", "<init>", Type.VOID, Type.NO_ARGS, Instruction.INVOKESPECIAL));
		insList.append(InstructionConstants.RETURN);
		insList.append(InstructionFactory.createThis());
		bIns.setTarget(insList.getEnd());
		insList.append(insFactory.createInvoke("tools.AssignThis", "<init>", Type.VOID, Type.NO_ARGS, Instruction.INVOKESPECIAL));
		insList.append(InstructionConstants.RETURN);
		mg.setMaxStack(5);
		cg.addMethod(mg.getMethod());
	}
	{
		// add default constructor
		InstructionList insList = new InstructionList();
		MethodGen mg = new MethodGen(0, Type.VOID, Type.NO_ARGS, empty, "<init>", "tools.AssignThis", insList, cg.getConstantPool());
		int index = cg.getConstantPool().addFieldref("tools/AssignThis", "i", "I");
		insList.append(InstructionFactory.createThis());
		insList.append(insFactory.createInvoke("java.lang.Object", "<init>", Type.VOID, Type.NO_ARGS, Instruction.INVOKESPECIAL));
		insList.append(InstructionFactory.createThis());
		insList.append(InstructionConstants.ICONST_5);
		insList.append(new PUTFIELD(index));
		insList.append(InstructionConstants.RETURN);
		mg.setMaxStack(5);
		cg.addMethod(mg.getMethod());
	} // write out classfile
	try {
		cg.getJavaClass().dump("c:\\tmp\\tools\\AssignThis.class");
	} catch (java.io.IOException e) {
		System.err.println(e);
	}
}
}
