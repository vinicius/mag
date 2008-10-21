package core.brakes.de.fub.bytecode.generic;

/** 
 * MONITORENTER - Enter monitor for object
 * <PRE>Stack: ..., objectref -&gt; ...</PRE>
 *
 * @version $Id: MONITORENTER.java,v 1.2 2006-03-11 03:19:48 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class MONITORENTER extends Instruction {
  /**
	 * 
	 */
	private static final long serialVersionUID = 4213028203879410372L;

public MONITORENTER() {
	super(MONITORENTER, (short)1);
  }  
}