package core.brakes.de.fub.bytecode.generic;

import core.brakes.de.fub.bytecode.Constants;
import core.brakes.de.fub.bytecode.classfile.CodeException;

/** 
 * This class represents an exception handler, i.e. specifies the  region where
 * a handler is active and an instruction where the actual handling is done.
 * pool as parameters.
 *
 * @version $Id: CodeExceptionGen.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 * @see     MethodGen
 * @see     CodeException
 */
public final class CodeExceptionGen implements Constants, InstructionTargeter {
  private InstructionHandle start_pc;
  private InstructionHandle end_pc;
  private InstructionHandle handler_pc;
  private ObjectType        catch_type;
  
  /**
   * Add an exception handler, i.e. specify region where a handler is active and an
   * instruction where the actual handling is done.
   *
   * @param start_pc Start of region
   * @param end_pc End of region
   * @param handler_pc Where handling is done
   * @param catch_type which exception is handled
   */
  public CodeExceptionGen(InstructionHandle start_pc, InstructionHandle end_pc,
			  InstructionHandle handler_pc, ObjectType catch_type) {
	setStartPC(start_pc);
	setEndPC(end_pc);
	setHandlerPC(handler_pc);
	this.catch_type = catch_type;
  }  
  /**
   * @return true, if ih is target of this handler
   */
  public boolean containsTarget(InstructionHandle ih) {
	return (start_pc == ih) || (end_pc == ih) || (handler_pc == ih);
  }  
  public ObjectType        getCatchType()                             { return catch_type; }  
  /**
   * Get CodeException object.
   *
   * This relies on that the instruction list has already been dumped to byte code or
   * or that the `setPositions' methods has been called for the instruction list.
   *
   * @param cp constant pool
   */
  public CodeException getCodeException(ConstantPoolGen cp) {
	return new CodeException(start_pc.getPosition(),
			     end_pc.getPosition(),
			     handler_pc.getPosition(),
			     (catch_type == null)? 0 : cp.addClass(catch_type));
  }  
  public InstructionHandle getEndPC()                                 { return end_pc; }  
  public InstructionHandle getHandlerPC()                             { return handler_pc; }  
  public InstructionHandle getStartPC()                               { return start_pc; }  
  public void              setCatchType(ObjectType catch_type)        { this.catch_type = catch_type; }  
  public void setEndPC(InstructionHandle end_pc) {
	BranchInstruction.notifyTarget(this.end_pc, end_pc, this);
	this.end_pc = end_pc;
  }  
  public void setHandlerPC(InstructionHandle handler_pc) {
	BranchInstruction.notifyTarget(this.handler_pc, handler_pc, this);
	this.handler_pc = handler_pc;
  }  
  public void setStartPC(InstructionHandle start_pc) {
	BranchInstruction.notifyTarget(this.start_pc, start_pc, this);
	this.start_pc = start_pc; 
  }  
  public String toString() {
	return "CodeExceptionGen(" + start_pc + ", " + end_pc + ", " + handler_pc + ")";
  }  
  /**
   * @param old_ih old target, either start or end
   * @param new_ih new target
   */
  public void updateTarget(InstructionHandle old_ih, InstructionHandle new_ih) {
	if(start_pc == old_ih)
	  setStartPC(new_ih);
	else if(end_pc == old_ih)
	  setEndPC(new_ih);
	else if(handler_pc == old_ih)
	  setHandlerPC(new_ih);
	else
	  throw new ClassGenException("Not targeting " + old_ih + ", but {" + start_pc + ", " +
				  end_pc + ", " + handler_pc + "}");
  }  
}