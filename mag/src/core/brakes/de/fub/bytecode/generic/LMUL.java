package core.brakes.de.fub.bytecode.generic;

/** 
 * LMUL - Multiply longs
 * <PRE>Stack: ..., value1.word1, value1.word2, value2.word1, value2.word2 -&gt;</PRE>
 *        ..., result.word1, result.word2
 *
 * @version $Id: LMUL.java,v 1.2 2006-03-11 03:19:48 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class LMUL extends ArithmeticInstruction {
  /**
	 * 
	 */
	private static final long serialVersionUID = 197545584766067337L;

public LMUL() {
	super(LMUL);
  }  
}