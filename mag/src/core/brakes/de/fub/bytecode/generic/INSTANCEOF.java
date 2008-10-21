package core.brakes.de.fub.bytecode.generic;

/** 
 * INSTANCEOF - Determine if object is of given type
 * <PRE>Stack: ..., objectref -&gt; ..., result</PRE>
 *
 * @version $Id: INSTANCEOF.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class INSTANCEOF extends CPInstruction implements LoadClass {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  INSTANCEOF() {}  
  public INSTANCEOF(int index) {
	super(INSTANCEOF, index);
  }  
}