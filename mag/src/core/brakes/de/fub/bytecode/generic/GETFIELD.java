package core.brakes.de.fub.bytecode.generic;

import core.brakes.de.fub.bytecode.Constants;

/** 
 * GETFIELD - Fetch field from object
 * <PRE>Stack: ..., objectref -&gt; ..., value</PRE>
 * OR
 * <PRE>Stack: ..., objectref -&gt; ..., value.word1, value.word2</PRE>
 *
 * @version $Id: GETFIELD.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class GETFIELD extends FieldInstruction {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  GETFIELD() {}  
  public GETFIELD(int index) {
	super(Constants.GETFIELD, index);
  }  
  public int produceStack(ConstantPoolGen cpg)
   { return getFieldSize(cpg); }   
}