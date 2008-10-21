package core.brakes.de.fub.bytecode.generic;

/** 
 * FRETURN -  Return float from method
 * <PRE>Stack: ..., value -&gt; &lt;empty&gt;</PRE>
 *
 * @version $Id: FRETURN.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class FRETURN extends ReturnInstruction {
  public FRETURN() {
	super(FRETURN);
  }  
}