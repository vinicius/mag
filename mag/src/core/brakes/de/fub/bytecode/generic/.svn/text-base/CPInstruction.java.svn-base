package core.brakes.de.fub.bytecode.generic;

import java.io.DataOutputStream;
import java.io.IOException;

import core.brakes.de.fub.bytecode.Constants;
import core.brakes.de.fub.bytecode.classfile.Constant;
import core.brakes.de.fub.bytecode.classfile.ConstantClass;
import core.brakes.de.fub.bytecode.classfile.ConstantPool;
import core.brakes.de.fub.bytecode.util.ByteSequence;

/** 
 * Abstract super class for instructions that use an index into the 
 * constant pool such as LDC, INVOKEVIRTUAL, etc.
 *
 * @see ConstantPoolGen
 * @see LDC
 * @see INVOKEVIRTUAL
 *
 * @version $Id: CPInstruction.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public abstract class CPInstruction extends Instruction implements Constants {
  protected int index; // index to constant pool

  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  CPInstruction() {}  
  /**
   * @param index to constant pool
   */
  protected CPInstruction(short tag, int index) {
	super(tag, (short)3);
	setIndex(index);
  }  
  /**
   * Dump instruction as byte code to stream out.
   * @param out Output stream
   */
  public void dump(DataOutputStream out) throws IOException {
	out.writeByte(tag);
	out.writeShort(index);
  }  
  /**
   * @return index in constant pool referred by this instruction.
   */
  public final int getIndex() { return index; }  
  /** @return type related with this instruction.
   */
  public Type getType(ConstantPoolGen cpg) {
	ConstantPool cp   = cpg.getConstantPool();
	String       name = cp.getConstantString(index, CONSTANT_Class);

	if(!name.startsWith("["))
	  name = "L" + name + ";";

	return Type.getType(name);
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
  /**
   * Set the index to constant pool.
   */
  public void setIndex(int index) { 
	if(index < 0)
	  throw new ClassGenException("Negative index value: " + index);

	this.index = index;
  }  
  /**
   * @return mnemonic for instruction with symbolic references resolved
   */
  public String toString(ConstantPool cp) {
	Constant c   = cp.getConstant(index);
	String   str = cp.constantToString(c);

	if(c instanceof ConstantClass)
	  str = str.replace('.', '/');

	return OPCODE_NAMES[tag] + " " + str;
  }  
  /**
   * Long output format:
   *
   * &lt;name of opcode&gt; "["&lt;opcode number&gt;"]" 
   * "("&lt;length of instruction&gt;")" "&lt;"&lt; constant pool index&gt;"&gt;"
   *
   * @param verbose long/short format switch
   * @return mnemonic for instruction
   */
  public String toString(boolean verbose) {
	return super.toString(verbose) + " " + index;
  }  
}