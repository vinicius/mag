package core.brakes.de.fub.bytecode.generic;

/** 
 * LSUB - Substract longs
 * <PRE>Stack: ..., value1.word1, value1.word2, value2.word1, value2.word2 -&gt;</PRE>
 *        ..., result.word1, result.word2
 *
 * @version $Id: LSUB.java,v 1.2 2006-03-11 03:19:48 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class LSUB extends ArithmeticInstruction {
  /**
	 * 
	 */
	private static final long serialVersionUID = -5119499786493229381L;

public LSUB() {
	super(LSUB);
  }  
}