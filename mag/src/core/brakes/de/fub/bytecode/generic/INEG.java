package core.brakes.de.fub.bytecode.generic;

/** 
 * INEG - Negate int
 * <PRE>Stack: ..., value -&gt; ..., result</PRE>
 *
 * @version $Id: INEG.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class INEG extends ArithmeticInstruction {
  public INEG() {
	super(INEG);
  }  
}