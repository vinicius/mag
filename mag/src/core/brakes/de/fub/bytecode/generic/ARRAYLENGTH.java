package core.brakes.de.fub.bytecode.generic;

/** 
 * ARRAYLENGTH -  Get length of array
 * <PRE>Stack: ..., arrayref -&gt; ..., length</PRE>
 *
 * @version $Id: ARRAYLENGTH.java,v 1.2 2006-03-11 03:19:48 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class ARRAYLENGTH extends Instruction {
	private static final long serialVersionUID = -2160184082188318639L;

	public ARRAYLENGTH() {
		super(ARRAYLENGTH, (short)1);
	}  
}