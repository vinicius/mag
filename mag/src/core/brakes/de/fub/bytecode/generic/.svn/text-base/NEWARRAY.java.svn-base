package core.brakes.de.fub.bytecode.generic;

import java.io.DataOutputStream;
import java.io.IOException;

import core.brakes.de.fub.bytecode.util.ByteSequence;

/** 
 * NEWARRAY -  Create new array of basic type (int, short, ...)
 * <PRE>Stack: ..., count -&gt; ..., arrayref</PRE>
 * type mus be one of T_INT, T_SHORT, ...
 * 
 * @version $Id: NEWARRAY.java,v 1.2 2006-03-11 03:19:48 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class NEWARRAY extends Instruction implements AllocationInstruction{
  /**
	 * 
	 */
	private static final long serialVersionUID = 2374618689505910113L;
private byte type;

  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  NEWARRAY() {}  
  public NEWARRAY(byte type) {
	super(NEWARRAY, (short)2);
	this.type = type;
  }  
  /**
   * Dump instruction as byte code to stream out.
   * @param out Output stream
   */
  public void dump(DataOutputStream out) throws IOException {
	out.writeByte(tag);
	out.writeByte(type);
  }  
  public byte getTypeTag() {
	  return type;
  }      
  /**
   * Read needed data (e.g. index) from file.
   */
  protected void initFromFile(ByteSequence bytes, boolean wide) throws IOException
  {
	type   = bytes.readByte();
	length = 2;
  }  
  /**
   * @return mnemonic for instruction
   */
  public String toString(boolean verbose) {
	return super.toString(verbose) + " " + TYPE_NAMES[type];
  }  
}