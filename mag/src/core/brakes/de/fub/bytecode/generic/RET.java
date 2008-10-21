package core.brakes.de.fub.bytecode.generic;

import java.io.DataOutputStream;
import java.io.IOException;

import core.brakes.de.fub.bytecode.util.ByteSequence;

/** 
 * RET - Return from subroutine
 *
 * <PRE>Stack: ..., -&gt; ..., address</PRE>
 *
 * @version $Id: RET.java,v 1.2 2006-03-11 03:19:48 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class RET extends Instruction {
  /**
	 * 
	 */
	private static final long serialVersionUID = -3731404771018988995L;
private boolean wide;
  private int     index; // index to local variable containg the return address

  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  RET() {}  
  public RET(int index) {
	super(RET, (short)2);
	setIndex(index);   // May set wide as side effect
  }  
  /**
   * Dump instruction as byte code to stream out.
   * @param out Output stream
   */
  public void dump(DataOutputStream out) throws IOException {
	if(wide)
	  out.writeByte(WIDE);

	out.writeByte(tag);

	if(wide)
	  out.writeShort(index);
	else
	  out.writeByte(index);
  }  
  /**
   * @return index of local variable containg the return address
   */
  public final int getIndex() { return index; }  
  /**
   * Read needed data (e.g. index) from file.
   */
  protected void initFromFile(ByteSequence bytes, boolean wide) throws IOException
  {
	this.wide = wide;

	if(wide) {
	  index  = bytes.readUnsignedShort();
	  length = 4;
	}
	else {
	  index = bytes.readUnsignedByte();
	  length = 2;
	}
  }  
  /**
   * Set index of local variable containg the return address
   */
  public final void setIndex(int n) { 
	if(n < 0)
	  throw new ClassGenException("Negative index value: " + n);

	index = n;
	setWide();
  }  
  private final void setWide() {
	if(wide = index > MAX_BYTE)
	  length = 4; // Including the wide byte  
	else
	  length = 2;
  }  
  /**
   * @return mnemonic for instruction
   */
  public String toString(boolean verbose) {
	return super.toString(verbose) + " " + index;
  }  
}