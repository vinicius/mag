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

import core.brakes.be.ac.kuleuven.cs.ttm.transformer.dataflow.Frame;
import core.brakes.be.ac.kuleuven.cs.ttm.transformer.dataflow.Registry;
import core.brakes.be.ac.kuleuven.cs.ttm.transformer.dataflow.Stack;
import core.brakes.de.fub.bytecode.generic.BranchInstruction;
import core.brakes.de.fub.bytecode.generic.InstructionConstants;
import core.brakes.de.fub.bytecode.generic.InstructionFactory;
import core.brakes.de.fub.bytecode.generic.InstructionHandle;
import core.brakes.de.fub.bytecode.generic.InstructionList;
import core.brakes.de.fub.bytecode.generic.InstructionTargeter;
import core.brakes.de.fub.bytecode.generic.InvokeInstruction;
import core.brakes.de.fub.bytecode.generic.MethodGen;
import core.brakes.de.fub.bytecode.generic.TargetLostException;



public class InvokespecialRewriter implements Rewriter {
  public InvokespecialRewriter() {
  }  
  private boolean isValid(MethodGen m) {
    if (m.getMethodName().equals("<init>")) return false;
    if (m.getMethodName().equals("<clinit>")) return false;
    if (m.isNative() || m.isAbstract()) return false;
    return true;
  }
  public void rewrite(MethodGen m, Registry reg) {
    if (!isValid(m))
		return;
    InstructionFactory insFactory = new InstructionFactory(m.getConstantPool());
    InstructionList insList = m.getInstructionList();
    InstructionHandle firstIns = insList.getStart();
    InstructionHandle ins = firstIns;
    while (ins != null) {
      if (ins.getInstruction().getTag() == InstructionList.INVOKESPECIAL) {
	InvokeInstruction ivs = (InvokeInstruction) ins.getInstruction();
	String mName = ivs.getMethodName(m.getConstantPool());
	int nrOfArguments = (ivs.getArgumentTypes(m.getConstantPool())).length;
	if (reg.getFrame(ins) != null && mName.equals("<init>") && (nrOfArguments > 0) ) {
	  StackDuplicator sd = new StackDuplicator(insFactory, reg.getFrame(ins).nextFreeRegister());
	  reg.getStack(ins).accept(sd);
          InstructionHandle endPossibleGotos = sd.getInstructionList().getStart();

	  /* De code vanaf hier past adresverwijzingen, stack informatie aan 
             de nieuwe situatie:
             -creeer stack en frame voor eerste instructie van stackduplicator lijst: kopieer stack en frame van invokespecial zonder uninitialized values
             -instructions met target oude new -> verwijzen naar nieuwe new
             -branchinstruction naar invokespecial in berekenen van argumenten moet springen naar begin van stackduplicator lijst
             -pas stack aan van instructies die argumenten berekenen: verwijder uninitialized types van stack
             

	  */
	  
	  updateRegistry(reg, sd.getInstructionList(), ins);
	  insList.insert(ins, sd.getInstructionList());
	  InstructionHandle oldNew = sd.getNewInstruction();
	  InstructionHandle newTarget = oldNew.getNext();	  
	  while (true) {
	    if (!newTarget.getInstruction().equals(InstructionConstants.DUP))
	      break;
	    newTarget = newTarget.getNext();
	  }
	  InstructionHandle possibleGoto = newTarget;
	  while (true) {
	    if (possibleGoto == endPossibleGotos)
	      break;
	    Stack s = reg.getStack(possibleGoto);
	    if (s != null) { 
	      // if s == null => instruction is an stackduplicator-instruction
	      // from nested invokespecial 
	     
	      s.removeFirstUninitializedValues();
	    }
	    // update stack, new is placed after this instruction 
            // instead in front of it

	    
	    /*update target branchinstruction if target was invokespecial*/
            if (possibleGoto.getInstruction() instanceof core.brakes.de.fub.bytecode.generic.BranchInstruction) {
  	      BranchInstruction branch = (BranchInstruction)possibleGoto.getInstruction();
	      if (branch.getTarget() == ins) {
	         branch.setTarget(endPossibleGotos);
              }
            }
            possibleGoto = possibleGoto.getNext();
          }   
	  while (true) {
	    oldNew = oldNew.getNext();
	    try {
	      insList.delete(oldNew.getPrev());
	    } catch (TargetLostException e) {
	      InstructionHandle[] targets = e.getTargets();
	      for (int i = 0; i < targets.length; i++) {
		InstructionTargeter[] targeters = targets[i].getTargeters();
		for (int j = 0; j < targeters.length; j++) {
		  targeters[j].updateTarget(targets[i], newTarget);
		}
	      }
	    }
	    if (!oldNew.getInstruction().equals(InstructionConstants.DUP))
	      break;
	  }
	}
      }
      ins = ins.getNext();
    }
  }
  /**
   * This method was created in VisualAge.
   * @param reg be.ac.kuleuven.cs.ttm.transformer.dataflow.Registry
   * @param insList de.fub.bytecode.generic.InstructionList
   * Create stack and frame for first instruction of the list, generated
   * by the stackduplicator.
   */
  protected void updateRegistry(Registry reg, InstructionList insList, InstructionHandle referIns) {
    InstructionHandle first = (insList).getStart();
    Stack s = new Stack(reg.getStack(referIns));
    s.removeFirstUninitializedValues();
    Frame f = new Frame(reg.getFrame(referIns));
    reg.register(first, s);
    reg.register(first, f);
  }
  
  
}








