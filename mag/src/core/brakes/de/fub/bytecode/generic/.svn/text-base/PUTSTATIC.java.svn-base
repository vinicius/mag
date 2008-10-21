package core.brakes.de.fub.bytecode.generic;

import core.brakes.de.fub.bytecode.Constants;

/** 
 * PUTSTATIC - Put static field in class
 * <PRE>Stack: ..., objectref, value -&gt; ...</PRE>
 * OR
 * <PRE>Stack: ..., objectref, value.word1, value.word2 -&gt; ...</PRE>
 *
 * @version $Id: PUTSTATIC.java,v 1.2 2006-03-11 03:19:48 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class PUTSTATIC extends FieldInstruction {
  /**
	 * 
	 */
	private static final long serialVersionUID = 4878933408142037431L;
/**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  PUTSTATIC() {}  
  public PUTSTATIC(int index) {
	super(Constants.PUTSTATIC, index);
  }  
  public int consumeStack(ConstantPoolGen cpg)
   { return getFieldSize(cpg); }   
}