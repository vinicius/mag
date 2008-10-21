package core.brakes.de.fub.bytecode.generic;

/**
 * FMUL - Multiply floats
 * <PRE>Stack: ..., value1, value2 -&gt; result</PRE>
 *
 * @version $Id: FMUL.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class FMUL extends ArithmeticInstruction {
  public FMUL() {
	super(FMUL);
  }  
}