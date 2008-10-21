package core.brakes.de.fub.bytecode.generic;

import java.io.DataOutputStream;
import java.io.IOException;

import core.brakes.de.fub.bytecode.util.ByteSequence;

/** 
 * BIPUSH - Push byte
 *
 * <PRE>Stack: ... -&gt; ..., value</PRE>
 *
 * @version $Id: BIPUSH.java,v 1.2 2006-03-11 03:19:48 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class BIPUSH extends Instruction implements ConstantPushInstruction {
	private static final long serialVersionUID = -5121828555701374677L;
	
	private byte b;
	
	/**
	 * Empty constructor needed for the Class.newInstance() statement in
	 * Instruction.readInstruction(). Not to be used otherwise.
	 */
	BIPUSH() {}  
	public BIPUSH(byte b) {
		super(BIPUSH, (short)2);
		this.b = b;
	}  
	/**
	 * Dump instruction as byte code to stream out.
	 */
	public void dump(DataOutputStream out) throws IOException {
		super.dump(out);
		out.writeByte(b);
	}  
	public Number getValue() { return new Integer(b); }  
	/**
	 * Read needed data (e.g. index) from file.
	 */
	protected void initFromFile(ByteSequence bytes, boolean wide) throws IOException
	{
		length = 2;
		b      = bytes.readByte();
	}  
	/**
	 * @return mnemonic for instruction
	 */
	public String toString(boolean verbose) {
		return super.toString(verbose) + " " + b;
	}  
}