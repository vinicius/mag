package core.brakes.de.fub.bytecode.generic;

/** 
 * IXOR - Bitwise XOR int
 * <PRE>Stack: ..., value1, value2 -&gt; ..., result</PRE>
 *
 * @version $Id: IXOR.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @authXOR  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class IXOR extends ArithmeticInstruction {
  public IXOR() {
	super(IXOR);
  }  
}