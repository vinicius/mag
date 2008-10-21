package core.brakes.de.fub.bytecode.generic;

/** 
 * LNEG - Negate long
 * <PRE>Stack: ..., value.word1, value.word2 -&gt; ..., result.word1, result.word2</PRE>
 *
 * @version $Id: LNEG.java,v 1.2 2006-03-11 03:19:48 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class LNEG extends ArithmeticInstruction {
  /**
	 * 
	 */
	private static final long serialVersionUID = 7332301479172549743L;

public LNEG() {
	super(LNEG);
  }  
}