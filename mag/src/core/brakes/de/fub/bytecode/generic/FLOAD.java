package core.brakes.de.fub.bytecode.generic;

/** 
 * FLOAD - Load float from local variable
 * Stack ... -> ..., result
 *
 * @version $Id: FLOAD.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class FLOAD extends LocalVariableInstruction implements PushInstruction {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  FLOAD() {
	super(FLOAD, FLOAD_0);
  }  
  public FLOAD(int n) {
	super(FLOAD, FLOAD_0, n);
  }  
}