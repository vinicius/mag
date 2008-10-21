package core.brakes.de.fub.bytecode.generic;

import java.io.DataOutputStream;
import java.io.IOException;

import core.brakes.de.fub.bytecode.classfile.ConstantPool;
import core.brakes.de.fub.bytecode.util.ByteSequence;

/** 
 * MULTIANEWARRAY - Create new mutidimensional array of references
 * <PRE>Stack: ..., count1, [count2, ...] -&gt; ..., arrayref</PRE>
 *
 * @version $Id: MULTIANEWARRAY.java,v 1.2 2006-03-11 03:19:48 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class MULTIANEWARRAY extends CPInstruction implements LoadClass, AllocationInstruction {
  /**
	 * 
	 */
	private static final long serialVersionUID = -2333564929419988311L;
private short dimensions;

  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  MULTIANEWARRAY() {}  
  public MULTIANEWARRAY(int index, short dimensions) {
	super(MULTIANEWARRAY, index);

	if(dimensions < 0)
	  throw new ClassGenException("Invalid dimensions value: " + dimensions);

	this.dimensions = dimensions;
	length = 4;
  }  
  /**
   * Also works for instructions whose stack effect depends on the
   * constant pool entry they reference.
   * @return Number of words consumed from stack by this instruction
   */
  public int consumeStack(ConstantPoolGen cpg)
   { return dimensions; }   
  /**
   * Dump instruction as byte code to stream out.
   * @param out Output stream
   */
  public void dump(DataOutputStream out) throws IOException {
	out.writeByte(tag);
	out.writeShort(index);
	out.writeByte(dimensions);
  }  
  /**
   * Read needed data (i.e. no. dimension) from file.
   */
  protected void initFromFile(ByteSequence bytes, boolean wide)
	   throws IOException
  {
	super.initFromFile(bytes, wide);
	dimensions = bytes.readByte();
	length     = 4;
  }  
  /**
   * @return mnemonic for instruction with symbolic references resolved
   */
  public String toString(ConstantPool cp) {
	return super.toString(cp) + " " + dimensions;
  }  
  /**
   * @return mnemonic for instruction
   */
  public String toString(boolean verbose) {
	return super.toString(verbose) + " " + index + " " + dimensions;
  }  
}
