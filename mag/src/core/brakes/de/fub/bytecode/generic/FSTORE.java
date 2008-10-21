package core.brakes.de.fub.bytecode.generic;

/** 
 * FSTORE - Store float into local variable
 * Stack ..., value -> ... 
 *
 * @version $Id: FSTORE.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class FSTORE extends LocalVariableInstruction {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  FSTORE() {
	super(FSTORE, FSTORE_0);
  }  
  public FSTORE(int n) {
	super(FSTORE, FSTORE_0, n);
  }  
}