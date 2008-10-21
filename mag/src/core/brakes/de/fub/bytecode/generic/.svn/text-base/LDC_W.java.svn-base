package core.brakes.de.fub.bytecode.generic;

import java.io.IOException;

import core.brakes.de.fub.bytecode.util.ByteSequence;

/** 
 * LDC_W - Push item from constant pool (wide index)
 *
 * <PRE>Stack: ... -&gt; ..., item.word1, item.word2</PRE>
 *
 * @version $Id: LDC_W.java,v 1.2 2006-03-11 03:19:48 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class LDC_W extends LDC {
  /**
	 * 
	 */
	private static final long serialVersionUID = 3382933656923468215L;
/**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  LDC_W() {}  
  public LDC_W(int index) {
	super(index);
  }  
  /**
   * Read needed data (i.e. index) from file.
   */
  protected void initFromFile(ByteSequence bytes, boolean wide)
	   throws IOException
  {
	setIndex(bytes.readUnsignedShort());
	length = 3;
  }  
}