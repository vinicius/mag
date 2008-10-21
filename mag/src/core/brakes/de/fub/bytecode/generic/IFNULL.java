package core.brakes.de.fub.bytecode.generic;

/** 
 * IFNULL - Branch if reference is not null
 *
 * <PRE>Stack: ..., reference -&gt; ...</PRE>
 *
 * @version $Id: IFNULL.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class IFNULL extends IfInstruction {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  IFNULL() {}  
  public IFNULL(InstructionHandle target) {
	super(IFNULL, target);
  }  
  /**
   * @return negation of instruction
   */
  public IfInstruction negate() {
	return new IFNONNULL(target);
  }  
}