package core.brakes.de.fub.bytecode.generic;

/** 
 * LLOAD - Load long from local variable
 * Stack ... -> ..., result.word1, result.word2
 *
 * @version $Id: LLOAD.java,v 1.2 2006-03-11 03:19:48 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class LLOAD extends LocalVariableInstruction implements PushInstruction {
	private static final long serialVersionUID = 6361487571843440036L;
	
	/**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  LLOAD() {
	super(LLOAD, LLOAD_0);
  }  
  public LLOAD(int n) {
	super(LLOAD, LLOAD_0, n);
  }  
}