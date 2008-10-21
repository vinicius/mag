package core.brakes.de.fub.bytecode.generic;

/** 
 * CALOAD - Load char from array
 * <PRE>Stack: ..., arrayref, index -&gt; ..., value</PRE>
 *
 * @version $Id: CALOAD.java,v 1.2 2006-03-11 03:19:48 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class CALOAD extends ArrayInstruction {
	private static final long serialVersionUID = 6598096980344557154L;

	public CALOAD() {
		super(CALOAD);
	}  
}