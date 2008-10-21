package core.brakes.de.fub.bytecode.generic;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

import core.brakes.de.fub.bytecode.Constants;
import core.brakes.de.fub.bytecode.classfile.ConstantPool;
import core.brakes.de.fub.bytecode.util.ByteSequence;

/** 
 * This abstract class is the super class for all java byte codes.
 *
 * @version $Id: Instruction.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public abstract class Instruction
  implements Constants, Cloneable, Serializable
{
  protected short length = 1;  // Length of instruction in bytes 
  protected short tag    = -1; // Opcode number

  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  Instruction() {}  
  public Instruction(short tag, short length) {
	this.length = length;
	this.tag    = tag;
  }  
  private static final String className(short tag) {
	String name = OPCODE_NAMES[tag].toUpperCase();

	/* ICONST_0, etc. will be shortened to ICONST, etc., since ICONST_0, ...
	 * are not implemented (directly).
	 */
	try {
	  int  len = name.length();
	  char ch1 = name.charAt(len - 2), ch2 = name.charAt(len - 1);

	  if((ch1 == '_') && (ch2 >= '0')  && (ch2 <= '5'))
	name = name.substring(0, len - 2);
	  
	  if(name.equals("ICONST_M1")) // Special case
	name = "ICONST";
	} catch(StringIndexOutOfBoundsException e) { System.err.println(e); }

	return "core.brakes.de.fub.bytecode.generic." + name;
  }  
  /**
   * @return Number of words consumed from stack by this instruction
   */
  public int consumeStack() { return CONSUME_STACK[tag]; }  
  /**
   * Also works for instructions whose stack effect depends on the
   * constant pool entry they reference.
   * @return Number of words consumed from stack by this instruction
   */
  public int consumeStack(ConstantPoolGen cpg) {
	return consumeStack();
  }  
  /**
   * Use with caution, since `BranchInstruction's have a `target' reference which
   * is not copied correctly (only basic types are). This also applies for 
   * `Select' instructions with their multiple branch targets.
   *
   * Used by BranchHandle.setTarget()
   * @see BranchHandle
   *
   * @return (shallow) copy of an instruction
   */
  Instruction copy() {
	Instruction i = null;

	try {
	  i = (Instruction)clone();
	} catch(CloneNotSupportedException e) {
	  System.err.println(e);
	}

	return i;
  }  
  // Some instructions may be reused, so don't do anything
  void dispose() {  }  
  /**
   * Dump instruction as byte code to stream out.
   * @param out Output stream
   */
  public void dump(DataOutputStream out) throws IOException {
	out.writeByte(tag); // Common for all instructions
  }  
  /**
   * @return length (in bytes) of instruction
   */
  public int getLength()   { return length; }  
  /**
   * @return opcode number
   */
  public short getTag()    { return tag; }  
  /**
   * Read needed data (e.g. index) from file.
   */
  protected void initFromFile(ByteSequence bytes, boolean wide)
	throws IOException
  {}  
  /**
   * @return Number of words produced onto stack by this instruction
   */
  public int produceStack() { return PRODUCE_STACK[tag]; }  
  /**
   * Also works for instructions whose stack effect depends on the
   * constant pool entry they reference.
   * @return Number of words produced onto stack by this instruction
   */
  public int produceStack(ConstantPoolGen cpg) {
	return produceStack();
  }  
  /**
   * Read an instruction from (byte code) input stream and return the
   * appropiate object.
   *
   * @param file file to read from
   * @return instruction object being read
   */
  final static Instruction readInstruction(ByteSequence bytes)
	throws IOException
  {
	boolean     wide = false;
	short       tag  = (short)bytes.readUnsignedByte();
	Instruction obj  = null;

	if(tag == WIDE) { // Read next tag after wide byte
	  wide = true;
	  tag  = (short)bytes.readUnsignedByte();
	}

	if(InstructionConstants.INSTRUCTIONS[tag] != null)
	  return InstructionConstants.INSTRUCTIONS[tag]; // Used predefined immutable object, if available

	/* Find appropiate class, instantiate an (empty) instruction object
	 * and initialize it by hand.
	 */
	try {
	  Class clazz = Class.forName(className(tag));
	  obj = (Instruction)clazz.newInstance();

	  obj.setTag(tag);
	  obj.initFromFile(bytes, wide); // Do further initializations, if any
	  // Byte code offset set in InstructionList
	} catch(Exception e) { throw new ClassGenException(e.toString()); }

	return obj;
  }  
  /**
   * Needed in readInstruction.
   */
  private void setTag(short tag) { this.tag = tag; }  
  /**
   * @return mnemonic for instruction in verbose format
   */
  public String toString() {
	return toString(true);
  }  
  /**
   * @return mnemonic for instruction with sumbolic references resolved
   */
  public String toString(ConstantPool cp) {
	return toString(false);
  }  
  /**
   * Long output format:
   *
   * &lt;name of opcode&gt; "["&lt;opcode number&gt;"]" 
   * "("&lt;length of instruction&gt;")"
   *
   * @param verbose long/short format switch
   * @return mnemonic for instruction
   */
  public String toString(boolean verbose) {
	if(verbose)
	  return OPCODE_NAMES[tag] + "[" + tag + "](" + length + ")";
	else
	  return OPCODE_NAMES[tag];
  }  
}