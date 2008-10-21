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

import core.brakes.be.ac.kuleuven.cs.ttm.transformer.Transformer;
import core.brakes.be.ac.kuleuven.cs.ttm.transformer.dataflow.FrameVisitor;
import core.brakes.de.fub.bytecode.generic.BasicType;
import core.brakes.de.fub.bytecode.generic.InstructionFactory;
import core.brakes.de.fub.bytecode.generic.InstructionHandle;
import core.brakes.de.fub.bytecode.generic.InstructionList;
import core.brakes.de.fub.bytecode.generic.ObjectType;
import core.brakes.de.fub.bytecode.generic.ReferenceType;
import core.brakes.de.fub.bytecode.generic.Type;

class FrameSaver implements FrameVisitor, Constants {
  private InstructionList insList;
  private InstructionFactory insFactory;
  private String className;
  
  /**
   * StackSaver constructor comment.
   */
  public FrameSaver(String className_, InstructionFactory f) {
	super();
	insFactory = f;
	insList = new InstructionList();
	className = className_;
  }

  InstructionList getInstructionList() {
	return insList;
  }
    
  public void visitBasicType(BasicType t, int pos) {
	RewriteFactory f = RewriteFactory.getInstance();
	insList.append(InstructionFactory.createLoad (new ObjectType(className),0));	

	insList.append (insFactory.createGetField(className, Transformer.contextAttributeName, new ObjectType(f.getContextClass())));

	insList.append(InstructionFactory.createLoad(t, pos));
	if ((t.getSize() < 2) && (!t.equals(Type.FLOAT)))
		t = (BasicType) Type.INT;
	String mName = f.getPushMethod() + TYPE_NAMES[t.getType()];
	
	insList.append(insFactory.createInvoke(f.getContextClass(), mName, Type.VOID, new Type[] { t }, core.brakes.de.fub.bytecode.Constants.INVOKEVIRTUAL));
  }
  
  public void visitNull(int pos) {
	// no need to save null
  }
  
  public void visitReferenceType(ReferenceType t, int pos) {
	RewriteFactory f = RewriteFactory.getInstance();

	if (pos == 0 && !Transformer.currentMethodStatic) {
	    insList.append(InstructionFactory.createLoad (new ObjectType(className),0));
	    insList.append (insFactory.createGetField(className, Transformer.contextAttributeName, new ObjectType(f.getContextClass())));
	
	    insList.append(InstructionFactory.createLoad(t, pos));
	  insList.append(insFactory.createInvoke(f.getContextClass(), f.getPushMethod() + "This", Type.VOID, new Type[] { Type.OBJECT }, core.brakes.de.fub.bytecode.Constants.INVOKEVIRTUAL));
	}

      if ((t.toString().indexOf("BufferedImage") > 0) || (t.toString().indexOf("ImageReader") > 0) ||
	  (t.toString().indexOf("Iterator") > 0) || (t.toString().indexOf("ImageInputStream") > 0) ||
	  (t.toString().indexOf("FileOutputStream") > 0) || (t.toString().indexOf("ObjectOutputStream") > 0)) {
      } else {
	    insList.append(InstructionFactory.createLoad (new ObjectType(f.getContextClass()),0));
	    insList.append (insFactory.createGetField(className, Transformer.contextAttributeName, new ObjectType(f.getContextClass())));
	    insList.append(InstructionFactory.createLoad(t, pos));
	    
	    insList.append(insFactory.createInvoke(f.getContextClass(), f.getPushMethod() + "Object", Type.VOID, new Type[] { Type.OBJECT }, core.brakes.de.fub.bytecode.Constants.INVOKEVIRTUAL));
	}
  }
  
  public void visitUninitializedType(ObjectType t, InstructionHandle ins, int pos) {
  }
}
