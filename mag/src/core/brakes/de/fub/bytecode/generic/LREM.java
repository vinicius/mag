package core.brakes.de.fub.bytecode.generic;

/**
 * LREM - Remainder of long
 * <PRE>Stack: ..., value1, value2 -&gt; result</PRE>
 *
 * @version $Id: LREM.java,v 1.2 2006-03-11 03:19:48 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class LREM extends ArithmeticInstruction {
  /**
	 * 
	 */
	private static final long serialVersionUID = 929551200322277550L;

public LREM() {
	super(LREM);
  }  
}