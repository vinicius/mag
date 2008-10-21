package core.brakes.de.fub.bytecode.generic;

/** 
 * SALOAD - Load short from array
 * <PRE>Stack: ..., arrayref, index -&gt; ..., value</PRE>
 *
 * @version $Id: SALOAD.java,v 1.2 2006-03-11 03:19:48 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class SALOAD extends ArrayInstruction {
  /**
	 * 
	 */
	private static final long serialVersionUID = 681958159654749158L;

public SALOAD() {
	super(SALOAD);
  }  
}