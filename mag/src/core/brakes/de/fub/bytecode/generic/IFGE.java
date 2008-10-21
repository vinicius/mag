package core.brakes.de.fub.bytecode.generic;

/** 
 * IFGE - Branch if int comparison with zero succeeds
 *
 * <PRE>Stack: ..., value -&gt; ...</PRE>
 *
 * @version $Id: IFGE.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class IFGE extends IfInstruction {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  IFGE() {}  
  public IFGE(InstructionHandle target) {
	super(IFGE, target);
  }  
  /**
   * @return negation of instruction
   */
  public IfInstruction negate() {
	return new IFLT(target);
  }  
}