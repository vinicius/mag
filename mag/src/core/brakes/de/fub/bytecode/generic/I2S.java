package core.brakes.de.fub.bytecode.generic;

/**
 * I2S - Convert int to short
 * <PRE>Stack: ..., value -&gt; ..., result</PRE>
 *
 * @version $Id: I2S.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class I2S extends ConversionInstruction {
  public I2S() {
	super(I2S);
  }  
}