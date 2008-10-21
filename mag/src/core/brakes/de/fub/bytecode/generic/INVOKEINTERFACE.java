package core.brakes.de.fub.bytecode.generic;


import java.io.DataOutputStream;
import java.io.IOException;

import core.brakes.de.fub.bytecode.Constants;
import core.brakes.de.fub.bytecode.classfile.ConstantPool;
import core.brakes.de.fub.bytecode.util.ByteSequence;

/** 
 * INVOKEINTERFACE - Invoke interface method
 * <PRE>Stack: ..., objectref, [arg1, [arg2 ...]] -&gt; ...</PRE>
 *
 * @version $Id: INVOKEINTERFACE.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public final class INVOKEINTERFACE extends InvokeInstruction {
  private int nargs; // Number of arguments on stack

  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  INVOKEINTERFACE() {}  
  public INVOKEINTERFACE(int index, int nargs) {
	super(Constants.INVOKEINTERFACE, index);
	length = 5;

	if(nargs < 1)
	  throw new ClassGenException("Number of arguments must be > 0 " + nargs);

	this.nargs = nargs;
  }  
  public int consumeStack(ConstantPoolGen cpg)  // nargs is given in byte-code
   {  return nargs; }   // nargs includes this reference   
  /**
   * Dump instruction as byte code to stream out.
   * @param out Output stream
   */
  public void dump(DataOutputStream out) throws IOException {
	out.writeByte(tag);
	out.writeShort(index);
	out.writeByte(nargs);
	out.writeByte(0);
  }  
  public int getNoArguments() { return nargs; }  
  /**
   * Read needed data (i.e. index) from file.
   */
  protected void initFromFile(ByteSequence bytes, boolean wide)
	   throws IOException
  {
	super.initFromFile(bytes, wide);

	length = 5;
	nargs = bytes.readUnsignedByte();
	bytes.readByte(); // Skip 0 byte
  }  
  /**
   * @return mnemonic for instruction with symbolic references resolved
   */
  public String toString(ConstantPool cp) {
	return super.toString(cp) + " " + nargs;
  }  
}