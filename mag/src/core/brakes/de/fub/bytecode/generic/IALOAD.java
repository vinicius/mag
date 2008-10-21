package core.brakes.de.fub.bytecode.generic;

/** 
 * IALOAD - Load int from array
 * <PRE>Stack: ..., arrayref, index -&gt; ..., value</PRE>
 *
 * @version $Id: IALOAD.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class IALOAD extends ArrayInstruction {
  public IALOAD() {
	super(IALOAD);
  }  
}