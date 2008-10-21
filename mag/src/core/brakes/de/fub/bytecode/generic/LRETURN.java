package core.brakes.de.fub.bytecode.generic;

/** 
 * LRETURN -  Return long from method
 * <PRE>Stack: ..., value.word1, value.word2 -&gt; &lt;empty&gt;</PRE>
 *
 * @version $Id: LRETURN.java,v 1.2 2006-03-11 03:19:48 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class LRETURN extends ReturnInstruction {
  /**
	 * 
	 */
	private static final long serialVersionUID = -1684706969285033227L;

public LRETURN() {
	super(LRETURN);
  }  
}