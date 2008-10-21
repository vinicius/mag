package core.brakes.de.fub.bytecode.generic;

/** 
 * SWAP - Swa top operand stack word
 * <PRE>Stack: ..., word2, word1 -&gt; ..., word1, word2</PRE>
 *
 * @version $Id: SWAP.java,v 1.2 2006-03-11 03:19:48 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class SWAP extends StackInstruction {
	private static final long serialVersionUID = 7905150109535986384L;

	public SWAP() {
		super(SWAP);
	}  
}