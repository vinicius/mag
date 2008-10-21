package core.brakes.de.fub.bytecode.generic;

/** 
 * LSHL - Arithmetic shift left long
 * <PRE>Stack: ..., value1.word1, value1.word2, value2 -&gt; ..., result.word1, result.word2</PRE>
 *
 * @version $Id: LSHL.java,v 1.2 2006-03-11 03:19:48 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class LSHL extends ArithmeticInstruction {
  /**
	 * 
	 */
	private static final long serialVersionUID = 4124746630499130458L;

public LSHL() {
	super(LSHL);
  }  
}