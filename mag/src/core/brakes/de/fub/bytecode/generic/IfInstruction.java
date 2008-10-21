package core.brakes.de.fub.bytecode.generic;

/**
 * Super class for the IFxxx  family of instructions.
 *
 * @version $Id: IfInstruction.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public abstract class IfInstruction extends BranchInstruction {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  IfInstruction() {}  
  /**
   * @param instruction Target instruction to branch to
   */
  protected IfInstruction(short tag, InstructionHandle target) {
	super(tag, target);
  }  
  /**
   * @return negation of instruction, e.g. IFEQ.negate() == IFNE
   */
  public abstract IfInstruction negate();  
}