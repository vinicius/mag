package core.brakes.de.fub.bytecode.generic;

import core.brakes.de.fub.bytecode.Constants;
import core.brakes.de.fub.bytecode.classfile.LocalVariable;

/** 
 * This class represents a local variable within a method. It contains its
 * scope, name and type. The generated LocalVariable object can be obtained
 * with getLocalVariable which needs the instruction list and the constant
 * pool as parameters.
 *
 * @version $Id: LocalVariableGen.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 * @see     LocalVariable
 * @see     MethodGen
 */
public class LocalVariableGen implements Constants, InstructionTargeter {
  private int         index;
  private String      name;
  private Type        type;
  private InstructionHandle start, end;

  /**
   * Generate a local variable that with index `index'. Note that double and long
   * variables need two indexs. Index indices have to be provided by the user.
   *
   * @param index index of local variable
   * @param name its name
   * @param type its type
   * @param start from where the instruction is valid (null means from the start)
   * @param end until where the instruction is valid (null means to the end)
   */
  public LocalVariableGen(int index, String name, Type type,
			  InstructionHandle start, InstructionHandle end) {
	if((index < 0) || (index > MAX_SHORT))
	  throw new ClassGenException("Invalid index index: " + index);
	
	this.name  = name;
	this.type  = type;
	this.index  = index;
	setStart(start);
	setStart(end);
  }  
  /**
   * @return true, if ih is target of this variable
   */
  public boolean containsTarget(InstructionHandle ih) {
	return (start == ih) || (end == ih);
  }  
  /**
   * We consider to local variables to be equal, if the use the same index and
   * are valid in the same range.
   */
  public boolean equals(Object o) {
	if(!(o instanceof LocalVariableGen))
	  return false;

	LocalVariableGen l = (LocalVariableGen)o;
	return (l.index == index) && (l.start == start) && (l.end == end);
  }  
  public InstructionHandle getEnd()                    { return end; }  
  public int         getIndex()                   { return index; }  
  /**
   * Get LocalVariable object.
   *
   * This relies on that the instruction list has already been dumped to byte code or
   * or that the `setPositions' methods has been called for the instruction list.
   *
   * @param il instruction list (byte code) which this variable belongs to
   * @param cp constant pool
   */
  public LocalVariable getLocalVariable(ConstantPoolGen cp) {
	int start_pc        = start.getPosition();
	int length          = end.getPosition() - start_pc;
	int name_index      = cp.addUtf8(name);
	int signature_index = cp.addUtf8(type.getSignature());

	return new LocalVariable(start_pc, length, name_index,
			     signature_index, index, cp.getConstantPool());
  }  
  public String      getName()                   { return name; }  
  /** @deprecated
   */
  public int         getSlot()                   { return index; }  
  public InstructionHandle getStart()                  { return start; }  
  public Type        getType()                   { return type; }  
  public void setEnd(InstructionHandle end) {
	BranchInstruction.notifyTarget(this.end, end, this);
	this.end = end;
  }  
  public void        setIndex(int index)           { this.index = index; }  
  public void        setName(String name)        { this.name = name; }  
  /** @deprecated
   */
  public void        setSlot(int index)           { this.index = index; }  
  public void setStart(InstructionHandle start) {
	BranchInstruction.notifyTarget(this.start, start, this);
	this.start = start;
  }  
  public void        setType(Type type)          { this.type = type; }  
  public String toString() {
	return "LocalvariableGen(" + name +  ", " + type +  ", " + start + ", " + end + ")";
  }  
  /**
   * @param old_ih old target, either start or end
   * @param new_ih new target
   */
  public void updateTarget(InstructionHandle old_ih, InstructionHandle new_ih) {
	if(start == old_ih)
	  setStart(new_ih);
	else if(end == old_ih)
	  setEnd(new_ih);
	else
	  throw new ClassGenException("Not targeting " + old_ih + ", but {" + start + ", " +
				  end + "}");
  }  
}