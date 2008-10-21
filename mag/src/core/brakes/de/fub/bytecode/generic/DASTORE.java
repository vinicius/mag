package core.brakes.de.fub.bytecode.generic;

/** 
 * DASTORE -  Store into double array
 * <PRE>Stack: ..., arrayref, index, value.word1, value.word2 -&gt; ...</PRE>
 *
 * @version $Id: DASTORE.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class DASTORE extends ArrayInstruction {
  public DASTORE() {
	super(DASTORE);
  }  
}