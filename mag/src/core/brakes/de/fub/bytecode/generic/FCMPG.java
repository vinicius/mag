package core.brakes.de.fub.bytecode.generic;

/** 
 * FCMPG - Compare floats: value1 > value2
 * <PRE>Stack: ..., value1, value2 -&gt; ..., result</PRE>
 *
 * @version $Id: FCMPG.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class FCMPG extends Instruction {
  public FCMPG() {
	super(FCMPG, (short)1);
  }  
}