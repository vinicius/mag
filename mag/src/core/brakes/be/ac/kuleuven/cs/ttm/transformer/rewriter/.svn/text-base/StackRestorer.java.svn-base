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
import core.brakes.de.fub.bytecode.generic.ACONST_NULL;
import core.brakes.de.fub.bytecode.generic.BasicType;
import core.brakes.de.fub.bytecode.generic.InstructionFactory;
import core.brakes.de.fub.bytecode.generic.InstructionHandle;
import core.brakes.de.fub.bytecode.generic.InstructionList;
import core.brakes.de.fub.bytecode.generic.ObjectType;
import core.brakes.de.fub.bytecode.generic.ReferenceType;
import core.brakes.de.fub.bytecode.generic.Type;

class StackRestorer implements StackVisitor, Constants {
  private InstructionList insList;
  private InstructionFactory insFactory;
  private String className;
  
  /**
   * StackSaver constructor comment.
   */
  public StackRestorer(String className_, InstructionFactory f) {
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
	String mName = f.getPopMethod() + TYPE_NAMES[t.getType()];

	insList.insert(insFactory.createInvoke(f.getContextClass(), mName, t, Type.NO_ARGS, core.brakes.de.fub.bytecode.Constants.INVOKEVIRTUAL));
	insList.insert (insFactory.createGetField(className, Transformer.contextAttributeName, new ObjectType(f.getContextClass())));
	insList.insert(InstructionFactory.createLoad (new ObjectType(className),0));
  }
  
  public void visitNull() {
	insList.insert(new ACONST_NULL());
  }
  
  public void visitReferenceType(ReferenceType t) {
      RewriteFactory f = RewriteFactory.getInstance();
	if (!t.equals(Type.OBJECT)) {
		insList.insert(insFactory.createCast(Type.OBJECT, t));
	}
	insList.insert(insFactory.createInvoke(f.getContextClass(), f.getPopMethod() + "Object", Type.OBJECT, Type.NO_ARGS, core.brakes.de.fub.bytecode.Constants.INVOKEVIRTUAL));
	insList.insert (insFactory.createGetField(className, Transformer.contextAttributeName, new ObjectType(f.getContextClass())));
	insList.insert(InstructionFactory.createLoad (new ObjectType(className),0));
  }
  
  public void visitUninitializedType(ObjectType t, InstructionHandle ins) {
	// ofwel new ofwel dup
	/* already taken care of
	if (previousType == t)
		insList.append(insList.getStart(), new DUP());
	else {
		insList.insert(insFactory.createNew(t));
		// dit is niet helemaal juist; eigenlijk moet ik alle mogelijkheden onderzoeken !
		System.out.println("New inserted !");
	}
	previousType = t;
	*/
  }
}
