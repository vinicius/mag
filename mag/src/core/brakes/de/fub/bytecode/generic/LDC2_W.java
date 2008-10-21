package core.brakes.de.fub.bytecode.generic;

/** 
 * LDC2_W - Push long or double from constant pool
 *
 * <PRE>Stack: ... -&gt; ..., item.word1, item.word2</PRE>
 *
 * @version $Id: LDC2_W.java,v 1.2 2006-03-11 03:19:48 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class LDC2_W extends CPInstruction implements PushInstruction {
  /**
	 * 
	 */
	private static final long serialVersionUID = -2119409666194805734L;
/**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  LDC2_W() {}  
  public LDC2_W(int index) {
	super(LDC2_W, index);
  }  
  public Type getType(ConstantPoolGen cpg) {
	switch(cpg.getConstantPool().getConstant(index).getTag()) {
	case CONSTANT_Long:    return Type.LONG;
	case CONSTANT_Integer: return Type.DOUBLE;
	default: // Never reached
	  throw new RuntimeException("Unknown constant type " + tag);
	}
  }  
}