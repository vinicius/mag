package core.brakes.de.fub.bytecode.generic;

/** 
 * IASTORE -  Store into int array
 * <PRE>Stack: ..., arrayref, index, value -&gt; ...</PRE>
 *
 * @version $Id: IASTORE.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class IASTORE extends ArrayInstruction {
  public IASTORE() {
	super(IASTORE);
  }  
}