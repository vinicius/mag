package core.brakes.de.fub.bytecode.generic;

/**
 * I2L - Convert int to long
 * <PRE>Stack: ..., value -&gt; ..., result.word1, result.word2</PRE>
 *
 * @version $Id: I2L.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class I2L extends ConversionInstruction {
  public I2L() {
	super(I2L);
  }  
}