package core.brakes.de.fub.bytecode.generic;

import core.brakes.de.fub.bytecode.classfile.ConstantCP;
import core.brakes.de.fub.bytecode.classfile.ConstantNameAndType;
import core.brakes.de.fub.bytecode.classfile.ConstantPool;
import core.brakes.de.fub.bytecode.classfile.ConstantUtf8;

/**
 * Super class for InvokeInstruction and FieldInstruction, since they have
 * some methods in common!
 *
 * @version $Id: FieldOrMethod.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public abstract class FieldOrMethod extends CPInstruction implements LoadClass {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  FieldOrMethod() {}  
  /**
   * @param index to constant pool
   */
  protected FieldOrMethod(short tag, int index) {
	super(tag, index);
  }  
  /** @return name of the referenced class/interface
   */
  public String getClassName(ConstantPoolGen cpg) {
	ConstantPool cp  = cpg.getConstantPool();
	ConstantCP   cmr = (ConstantCP)cp.getConstant(index);
	return cp.getConstantString(cmr.getClassIndex(), CONSTANT_Class).replace('/', '.');
  }  
  /** @return type of the referenced class/interface
   */
  public ObjectType getClassType(ConstantPoolGen cpg) {
	return new ObjectType(getClassName(cpg));
  }  
  /** @return name of referenced method/field.
   */
  public String getName(ConstantPoolGen cpg) {
	ConstantPool        cp   = cpg.getConstantPool();
	ConstantCP          cmr  = (ConstantCP)cp.getConstant(index);
	ConstantNameAndType cnat = (ConstantNameAndType)cp.getConstant(cmr.getNameAndTypeIndex());
	return ((ConstantUtf8)cp.getConstant(cnat.getNameIndex())).getBytes();
  }  
  /** @return signature of referenced method/field.
   */
  public String getSignature(ConstantPoolGen cpg) {
	ConstantPool        cp   = cpg.getConstantPool();
	ConstantCP          cmr  = (ConstantCP)cp.getConstant(index);
	ConstantNameAndType cnat = (ConstantNameAndType)cp.getConstant(cmr.getNameAndTypeIndex());

	return ((ConstantUtf8)cp.getConstant(cnat.getSignatureIndex())).getBytes();
  }  
}