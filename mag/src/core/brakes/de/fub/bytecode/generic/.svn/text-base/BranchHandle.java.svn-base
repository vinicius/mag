package core.brakes.de.fub.bytecode.generic;

/**
 * BranchHandle is returned by specialized InstructionList.append() whenever a
 * BranchInstruction is appended. This is useful when the target of this
 * instruction is not known at time of creation and must be set later
 * via setTarget().
 *
 * @see InstructionHandle
 * @see Instruction
 * @see InstructionList
 * @version $Id: BranchHandle.java,v 1.2 2006-03-11 03:19:48 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public final class BranchHandle extends InstructionHandle {
	private static final long serialVersionUID = -6702902359788161038L;
	
	private BranchInstruction bi; // An alias in fact, but saves lots of casts
	
	/** Factory methods.
	 */
	private static BranchHandle bh_list = null; // List of reusable handles
	
	private BranchHandle(BranchInstruction i) {
		super(i);
		bi = i;
	}  
	/** Handle adds itself to the list of resuable handles.
	 */
	protected void addHandle() {
		next    = bh_list;
		bh_list = this;
	}  
	static final BranchHandle getBranchHandle(BranchInstruction i) {
		if(bh_list == null)
			return new BranchHandle(i);
		else {
			BranchHandle bh = bh_list;
			bh_list = (BranchHandle)bh.next;
			
			bh.setInstruction(i);
			
			return bh;
		}
	}  
	/* Overload InstructionHandle methods: delegate to branch instruction.
	 * Through this overloading all access to the private i_position field should
	 * be prevented.
	 */
	int getPosition() { return bi.position; }  
	/**
	 * @return target of instruction.
	 */
	public InstructionHandle getTarget() {
		return bi.getTarget();
	}  
	/** 
	 * Set new contents. Old instruction is disposed and may not be used anymore.
	 */
	public void setInstruction(Instruction i) {
		super.setInstruction(i);
		
		if(!(i instanceof BranchInstruction))
			throw new ClassGenException(i + " is not a BranchInstruction");
		
		bi = (BranchInstruction)i;
	}  
	void setPosition(int pos) { bi.position = pos; }  
	/**
	 * Pass new target to instruction.
	 */
	public void setTarget(InstructionHandle ih) {
		bi.setTarget(ih);
	}  
	protected int updatePosition(int offset, int max_offset) {
		return bi.updatePosition(offset, max_offset);
	}  
	/**
	 * Update target of instruction.
	 */
	public void updateTarget(InstructionHandle old_ih, InstructionHandle new_ih) {
		bi.updateTarget(old_ih, new_ih);
	}  
}