package core.brakes.de.fub.bytecode.generic;

import java.util.Vector;

import core.brakes.de.fub.bytecode.classfile.Utility;

/**
 * Instances of this class give users a handle to the instructions contained in
 * an InstructionList. Instruction objects may be uesd more than once within a
 * list, this is useful because it saves memory and may be much faster.
 *
 * Within an InstructionList an InstructionHandle object is wrapped
 * around all instructions, i.e. it implements a cell in a
 * doubly-linked list. From the outside only the next and the
 * previous instruction (handle) are accessible. One
 * can traverse the list via an Enumeration returned by
 * InstructionList.elements().
 *
 * @version $Id: InstructionHandle.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 * @see java.util.Enumeration
 * @see Instruction
 * @see BranchHandle
 * @see InstructionList 
 */
public class InstructionHandle implements java.io.Serializable {
  InstructionHandle next, prev;  // Will be set from the outside
  Instruction       instruction;
  private int       i_position = -1; // byte code offset of instruction
  private Vector    targeters;

  /** Factory methods.
   */
  private static InstructionHandle ih_list = null; // List of reusable handles

  /*private*/ protected InstructionHandle(Instruction i) {
	setInstruction(i);
  }  
  /** Overridden in BranchHandle
   */
  protected void addHandle() {
	next    = ih_list;
	ih_list = this;
  }  
  /**
   * Denote this handle is being referenced by t.
   */
  public void addTargeter(InstructionTargeter t) {
	if(targeters == null)
	  targeters = new Vector();

	if(!targeters.contains(t))
	  targeters.addElement(t);
  }  
  /**
   * Delete contents, i.e. remove user access and make handle reusable.
   */
  void dispose() {
	next = prev = null;
	instruction.dispose();
	instruction = null;
	i_position = -1;
	removeAllTargeters();
	addHandle();
  }  
  public final Instruction       getInstruction() { return instruction; }  
  static final InstructionHandle getInstructionHandle(Instruction i) {
	if(ih_list == null)
	  return new InstructionHandle(i);
	else {
	  InstructionHandle ih = ih_list;
	  ih_list = ih.next;

	  ih.setInstruction(i);

	  return ih;
	}
  }  
  public final InstructionHandle getNext()        { return next; }  
  int getPosition() { return i_position; }  
  public final InstructionHandle getPrev()        { return prev; }  
  /**
   * @return null, if there are no targeters
   */
  public InstructionTargeter[] getTargeters() {
	if(!hasTargeters())
	  return null;
	
	InstructionTargeter[] t = new InstructionTargeter[targeters.size()];
	targeters.copyInto(t);
	return t;
  }  
  public boolean hasTargeters() {
	return (targeters != null) && (targeters.size() > 0);
  }  
  /** Remove all targeters, if any.
   */
  public void removeAllTargeters() {
	if(targeters != null)
	  targeters.removeAllElements();
  }  
  /**
   * Denote this handle isn't referenced anymore by t.
   */
  public void removeTargeter(InstructionTargeter t) {
	targeters.removeElement(t);
  }  
  /**
   * Replace current instruction contained in this handle.
   * Old instruction is disposed and may not be used anymore.
   */
  public void setInstruction(Instruction i) {
	if(i == null)
	  throw new ClassGenException("Appending null Instruction");

	if(instruction != null)
	  instruction.dispose();

	instruction = i;
  }  
  void setPosition(int pos) { i_position = pos; }  
  public String toString() {
	return toString(true);
  }  
  public String toString(boolean verbose) {
	return Utility.format(i_position, 4, false, ' ') + ": " + instruction.toString(verbose);
  }  
  /**
   * Called by InstructionList.setPositions when setting the position for every
   * instruction. In the presence of variable length instructions `setPositions'
   * performs multiple passes over the instruction list to calculate the
   * correct (byte) positions and offsets by calling this function.
   *
   * @param offset additional offset caused by preceding (variable length) instructions
   * @param max_offset the maximum offset that may be caused by these instructions
   * @return additional offset caused by possible change of this instruction's length
   */
  protected int updatePosition(int offset, int max_offset) {
	i_position += offset;
	return 0;
  }  
}
