package core.brakes.de.fub.bytecode.generic;

/** 
 * LASTORE -  Store into long array
 * <PRE>Stack: ..., arrayref, index, value.word1, value.word2 -&gt; ...</PRE>
 *
 * @version $Id: LASTORE.java,v 1.2 2006-03-11 03:19:48 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class LASTORE extends ArrayInstruction {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1149784719458074236L;

public LASTORE() {
	super(LASTORE);
  }  
}