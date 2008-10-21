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
import core.brakes.be.ac.kuleuven.cs.ttm.transformer.dataflow.StackVisitor;
import core.brakes.de.fub.bytecode.generic.ArrayType;
import core.brakes.de.fub.bytecode.generic.BasicType;
import core.brakes.de.fub.bytecode.generic.InstructionConstants;
import core.brakes.de.fub.bytecode.generic.InstructionFactory;
import core.brakes.de.fub.bytecode.generic.InstructionHandle;
import core.brakes.de.fub.bytecode.generic.InstructionList;
import core.brakes.de.fub.bytecode.generic.ObjectType;
import core.brakes.de.fub.bytecode.generic.ReferenceType;
import core.brakes.de.fub.bytecode.generic.Type;

class StackSaver implements StackVisitor, Constants {
  private InstructionList insList;
  private InstructionFactory insFactory;
  private String className;
  
  /**
   * StackSaver constructor comment.
   */
  StackSaver(String className_, InstructionFactory f) {
	super();
	insFactory = f;
	insList = new InstructionList();
	className = className_;
  }
  
  InstructionList getInstructionList() {
	return insList;
  }
  
  public void visitBasicType(BasicType t) {
      RewriteFactory f = RewriteFactory.getInstance();
      if ((t.getSize() < 2) && (!t.equals(Type.FLOAT)))
	  t = (BasicType) Type.INT;
      String mName = f.getPushMethod() + TYPE_NAMES[t.getType()];
      
      insList.append(InstructionFactory.createLoad (new ObjectType(className),0));
      
      insList.append (insFactory.createGetField(className, Transformer.contextAttributeName, new ObjectType(f.getContextClass())));
      
      insList.append (new NULLCONST(t));
      
      insList.append(insFactory.createInvoke(f.getContextClass(), mName, Type.VOID, new Type[] { t }, core.brakes.de.fub.bytecode.Constants.INVOKEVIRTUAL));
  }
    
  public void visitNull() {
      insList.append(InstructionConstants.POP);
  }
  
  public void visitReferenceType(ReferenceType t) {
      RewriteFactory f = RewriteFactory.getInstance();

      if ((t.toString().indexOf("BufferedImage") > 0) || (t.toString().indexOf("ImageReader") > 0) ||
	  (t.toString().indexOf("Iterator") > 0) || (t.toString().indexOf("ImageInputStream") > 0) ||
	  (t.toString().indexOf("FileOutputStream") > 0) || (t.toString().indexOf("ObjectOutputStream") > 0)) {
      } else {

	  insList.append(InstructionFactory.createLoad (new ObjectType(className),0));
	  
	  insList.append (insFactory.createGetField(className, Transformer.contextAttributeName, new ObjectType(f.getContextClass())));
	  
	  insList.append(InstructionConstants.ICONST_1);
	  insList.append(insFactory.createNewArray ((new ArrayType(Type.getType(t.getSignature()), 0)).getBasicType(), (short)1));
	  
	  insList.append(insFactory.createInvoke(f.getContextClass(), f.getPushMethod() + "Object", Type.VOID, new Type[] { Type.OBJECT }, core.brakes.de.fub.bytecode.Constants.INVOKEVIRTUAL));
      }
  }
  
  public void visitUninitializedType(ObjectType t, InstructionHandle ins) {
      // insList.append(InstructionConstants.POP);
  }
}
