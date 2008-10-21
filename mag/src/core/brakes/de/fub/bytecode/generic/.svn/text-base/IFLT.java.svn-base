package core.brakes.de.fub.bytecode.generic;

/** 
 * IFLT - Branch if int comparison with zero succeeds
 *
 * <PRE>Stack: ..., value -&gt; ...</PRE>
 *
 * @version $Id: IFLT.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class IFLT extends IfInstruction {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  IFLT() {}  
  public IFLT(InstructionHandle target) {
	super(IFLT, target);
  }  
  /**
   * @return negation of instruction
   */
  public IfInstruction negate() {
	return new IFGE(target);
  }  
}