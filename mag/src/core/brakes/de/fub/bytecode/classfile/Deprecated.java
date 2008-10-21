package core.brakes.de.fub.bytecode.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * This class is derived from <em>Attribute</em> and denotes that this is a
 * deprecated method.
 * It is instantiated from the <em>Attribute.readAttribute()</em> method.
 *
 * @version $Id: Deprecated.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 * @see     Attribute
 */
public final class Deprecated extends Attribute {
  private byte[] bytes;

  /**
   * @param name_index Index in constant pool to CONSTANT_Utf8
   * @param length Content length in bytes
   * @param bytes Attribute contents
   * @param constant_pool Array of constants
   * @param sourcefile_index Index in constant pool to CONSTANT_Utf8
   */
  public Deprecated(int name_index, int length, byte[] bytes,
		    ConstantPool constant_pool)
  {
	super(ATTR_DEPRECATED, name_index, length, constant_pool);
	this.bytes = bytes;
  }  
  /**
   * Construct object from file stream.
   * @param name_index Index in constant pool to CONSTANT_Utf8
   * @param length Content length in bytes
   * @param file Input stream
   * @param constant_pool Array of constants
   * @throw IOException
   */
  Deprecated(int name_index, int length, DataInputStream file,
	     ConstantPool constant_pool) throws IOException
  {
	this(name_index, length, (byte [])null, constant_pool);

	if(length > 0) {
	  bytes = new byte[length];
	  file.readFully(bytes);
	  System.err.println("Deprecated attribute with length > 0");
	}
  }  
  /**
   * Initialize from another object. Note that both objects use the same
   * references (shallow copy). Use clone() for a physical copy.
   */
  public Deprecated(Deprecated c) {
	this(c.getNameIndex(), c.getLength(), c.getBytes(), c.getConstantPool());
  }  
  /**
   * Called by objects that are traversing the nodes of the tree implicitely
   * defined by the contents of a Java class. I.e., the hierarchy of methods,
   * fields, attributes, etc. spawns a tree of objects.
   *
   * @param v Visitor object
   */
  public void accept(Visitor v) {
	v.visitDeprecated(this);
  }  
  /**
   * @return deep copy of this attribute
   */
  public Attribute copy(ConstantPool constant_pool) {
	Deprecated c = (Deprecated)clone();

	if(bytes != null)
	  c.bytes = (byte[])bytes.clone();

	c.constant_pool = constant_pool;
	return c;
  }  
  /**
   * Dump source file attribute to file stream in binary format.
   *
   * @param file Output file stream
   * @throw IOException
   */ 
  public final void dump(DataOutputStream file) throws IOException
  {
	super.dump(file);

	if(length > 0)
	  file.write(bytes, 0, length);
  }  
  /**
   * @return data bytes.
   */  
  public final byte[] getBytes() { return bytes; }  
  /**
   * @param bytes.
   */
  public final void setBytes(byte[] bytes) {
	this.bytes = bytes;
  }  
  /**
   * @return attribute name
   */ 
  public final String toString() {
	return ATTRIBUTE_NAMES[ATTR_DEPRECATED];
  }  
}