package core.brakes.de.fub.bytecode.generic;

/** 
 * ARETURN -  Return reference from method
 * <PRE>Stack: ..., objectref -&gt; &lt;empty&gt;</PRE>
 *
 * @version $Id: ARETURN.java,v 1.2 2006-03-11 03:19:48 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class ARETURN extends ReturnInstruction {
	private static final long serialVersionUID = 9051276880538787001L;

	public ARETURN() {
		super(ARETURN);
	}  
}