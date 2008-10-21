package core.brakes.de.fub.bytecode.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * This class represents a (PC offset, line number) pair, i.e. a line number in
 * the source that corresponds to a relative address in the byte code. This
 * is used for debugging purposes.
 *
 * @version $Id: LineNumber.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 * @see     LineNumberTable
 */
public final class LineNumber implements Cloneable {
  private int start_pc;    // Program Counter (PC) corresponds to line
  private int line_number; // number in source file

  /**
   * @param start_pc Program Counter (PC) corresponds to
   * @param line_number line number in source file
   */
  public LineNumber(int start_pc, int line_number)
  {
	this.start_pc    = start_pc;
	this.line_number = line_number;
  }  
  /**
   * Initialize from another object.
   */
  public LineNumber(LineNumber c) {
	this(c.getStartPC(), c.getLineNumber());
  }  
  /**
   * Construct object from file stream.
   * @param file Input stream
   * @throw IOException
   */
  LineNumber(DataInputStream file) throws IOException
  {
	this(file.readUnsignedShort(), file.readUnsignedShort());
  }  
  /**
   * Called by objects that are traversing the nodes of the tree implicitely
   * defined by the contents of a Java class. I.e., the hierarchy of methods,
   * fields, attributes, etc. spawns a tree of objects.
   *
   * @param v Visitor object
   */
  public void accept(Visitor v) {
	v.visitLineNumber(this);
  }  
  /**
   * @return deep copy of this object
   */
  public LineNumber copy() {
	try {
	  return (LineNumber)clone();
	} catch(CloneNotSupportedException e) {}

	return null;
  }  
  /**
   * Dump line number/pc pair to file stream in binary format.
   *
   * @param file Output file stream
   * @throw IOException
   */ 
  public final void dump(DataOutputStream file) throws IOException
  {
	file.writeShort(start_pc);
	file.writeShort(line_number);
  }  
  /**
   * @return Corresponding source line
   */
  public final int getLineNumber() { return line_number; }  
  /**
   * @return PC in code
   */  
  public final int getStartPC() { return start_pc; }  
  /**
   * @param line_number.
   */
  public final void setLineNumber(int line_number) {
	this.line_number = line_number;
  }  
  /**
   * @param start_pc.
   */
  public final void setStartPC(int start_pc) {
	this.start_pc = start_pc;
  }  
  /**
   * @return String representation
   */ 
  public final String toString()
  {
	return "LineNumber(" + start_pc + ", " + line_number + ")";
  }  
}