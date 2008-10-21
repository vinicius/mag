package core.brakes.de.fub.bytecode.generic;

/** 
 * DLOAD - Load double from local variable
 * Stack ... -> ..., result.word1, result.word2
 *
 * @version $Id: DLOAD.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class DLOAD extends LocalVariableInstruction implements PushInstruction {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  DLOAD() {
	super(DLOAD, DLOAD_0);
  }  
  public DLOAD(int n) {
	super(DLOAD, DLOAD_0, n);
  }  
}