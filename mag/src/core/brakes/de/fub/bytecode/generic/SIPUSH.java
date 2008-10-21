package core.brakes.de.fub.bytecode.generic;

import java.io.DataOutputStream;
import java.io.IOException;

import core.brakes.de.fub.bytecode.util.ByteSequence;

/**
 * SIPUSH - Push short
 *
 * <PRE>Stack: ... -&gt; ..., value</PRE>
 *
 * @version $Id: SIPUSH.java,v 1.2 2006-03-11 03:19:48 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class SIPUSH extends Instruction implements ConstantPushInstruction {
	private static final long serialVersionUID = 130301540475396667L;
	
	private short b;
	
	/**
	 * Empty constructor needed for the Class.newInstance() statement in
	 * Instruction.readInstruction(). Not to be used otherwise.
	 */
	SIPUSH() {}  
	public SIPUSH(short b) {
		super(SIPUSH, (short)3);
		this.b = b;
	}  
	/**
	 * Dump instruction as short code to stream out.
	 */
	public void dump(DataOutputStream out) throws IOException {
		super.dump(out);
		out.writeShort(b);
	}  
	public Number getValue() { return new Integer(b); }  
	/**
	 * Read needed data (e.g. index) from file.
	 */
	protected void initFromFile(ByteSequence bytes, boolean wide) throws IOException
	{
		length = 3;
		b      = bytes.readShort();
	}  
	/**
	 * @return mnemonic for instruction
	 */
	public String toString(boolean verbose) {
		return super.toString(verbose) + " " + b;
	}  
}