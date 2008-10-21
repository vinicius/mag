package core.brakes.de.fub.bytecode.generic;

/** 
 * ATHROW -  Throw exception
 * <PRE>Stack: ..., objectref -&gt; objectref</PRE>
 *
 * @version $Id: ATHROW.java,v 1.2 2006-03-11 03:19:48 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class ATHROW extends Instruction implements UnconditionalBranch {
	private static final long serialVersionUID = -7867558232420987364L;

	public ATHROW() {
		super(ATHROW, (short)1);
	}  
}