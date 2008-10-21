package core.brakes.de.fub.bytecode.generic;

/** 
 * ANEWARRAY -  Create new array of references
 * <PRE>Stack: ..., count -&gt; ..., arrayref</PRE>
 *
 * @version $Id: ANEWARRAY.java,v 1.2 2006-03-11 03:19:48 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class ANEWARRAY extends CPInstruction
implements LoadClass, AllocationInstruction {
	private static final long serialVersionUID = -6681107971135479965L;
	
	/**
	 * Empty constructor needed for the Class.newInstance() statement in
	 * Instruction.readInstruction(). Not to be used otherwise.
	 */
	ANEWARRAY() {}  
	public ANEWARRAY(int index) {
		super(ANEWARRAY, index);
	}  
}