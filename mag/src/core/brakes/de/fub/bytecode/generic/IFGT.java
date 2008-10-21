package core.brakes.de.fub.bytecode.generic;

/** 
 * IFGT - Branch if int comparison with zero succeeds
 *
 * <PRE>Stack: ..., value -&gt; ...</PRE>
 *
 * @version $Id: IFGT.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class IFGT extends IfInstruction {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  IFGT() {}  
  public IFGT(InstructionHandle target) {
	super(IFGT, target);
  }  
  /**
   * @return negation of instruction
   */
  public IfInstruction negate() {
	return new IFLE(target);
  }  
}