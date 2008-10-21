package core.brakes.de.fub.bytecode.generic;

/** 
 * ACONST_NULL -  Push null
 * <PRE>Stack: ... -&gt; ..., null</PRE>
 *
 * @version $Id: ACONST_NULL.java,v 1.2 2006-03-11 03:19:48 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class ACONST_NULL extends Instruction implements PushInstruction {
	private static final long serialVersionUID = -6134710645249508881L;

	public ACONST_NULL() {
		super(ACONST_NULL, (short)1);
	}      
}