package core.brakes.de.fub.bytecode.generic;

/** 
 * DUP2_X2 - Duplicate two top operand stack words and put four down
 * <PRE>Stack: ..., word4, word3, word2, word1 -&gt; ..., word2, word1, word4, word3, word2, word1</PRE>
 *
 * @version $Id: DUP2_X2.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class DUP2_X2 extends StackInstruction {
  public DUP2_X2() {
	super(DUP2_X2);
  }  
}