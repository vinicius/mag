package core.brakes.de.fub.bytecode.generic;

/** 
 * DDIV -  Divide doubles
 * <PRE>Stack: ..., value1.word1, value1.word2, value2.word1, value2.word2 -&gt;</PRE>
 *        ..., result.word1, result.word2
 *
 * @version $Id: DDIV.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class DDIV extends ArithmeticInstruction {
  public DDIV() {
	super(DDIV);
  }  
}