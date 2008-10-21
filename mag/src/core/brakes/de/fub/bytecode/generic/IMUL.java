package core.brakes.de.fub.bytecode.generic;

/** 
 * IMUL - Multiply ints
 * <PRE>Stack: ..., value1, value2 -&gt; result</PRE>
 *
 * @version $Id: IMUL.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class IMUL extends ArithmeticInstruction {
  public IMUL() {
	super(IMUL);
  }  
}