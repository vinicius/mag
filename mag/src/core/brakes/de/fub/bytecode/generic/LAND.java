package core.brakes.de.fub.bytecode.generic;

/** 
 * LAND - Bitwise AND longs
 * <PRE>Stack: ..., value1.word1, value1.word2, value2.word1, value2.word2 -&gt;</PRE>
 *        ..., result.word1, result.word2
 *
 * @version $Id: LAND.java,v 1.2 2006-03-11 03:19:48 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class LAND extends ArithmeticInstruction {
  /**
	 * 
	 */
	private static final long serialVersionUID = 5461587810914843265L;

public LAND() {
	super(LAND);
  }  
}