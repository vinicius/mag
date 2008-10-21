package core.brakes.de.fub.bytecode.generic;

import core.brakes.de.fub.bytecode.Constants;

/** 
 * PUTFIELD - Put field in object
 * <PRE>Stack: ..., objectref, value -&gt; ...</PRE>
 * OR
 * <PRE>Stack: ..., objectref, value.word1, value.word2 -&gt; ...</PRE>
 *
 * @version $Id: PUTFIELD.java,v 1.2 2006-03-11 03:19:48 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class PUTFIELD extends FieldInstruction {
  /**
	 * 
	 */
	private static final long serialVersionUID = 3322609421692456273L;
/**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  PUTFIELD() {}  
  public PUTFIELD(int index) {
	super(Constants.PUTFIELD, index);
  }  
  public int consumeStack(ConstantPoolGen cpg)
   { return getFieldSize(cpg) + 1; }   
}