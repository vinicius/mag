package core.brakes.de.fub.bytecode.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import core.brakes.de.fub.bytecode.Constants;

/**
 * Abstract superclass for classes to represent the different constant types
 * in the constant pool of a class file. The classes keep closely to
 * the JVM specification.
 *
 * @version $Id: Constant.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 * @see     ConstantClass
 * @see     ConstantFieldref
 * @see     ConstantMethodref
 * @see     ConstantInterfaceMethodref
 * @see     ConstantString
 * @see     ConstantInteger
 * @see     ConstantFloat
 * @see     ConstantLong
 * @see     ConstantDouble
 * @see     ConstantNameAndType
 * @see     ConstantUtf8
 */
public abstract class Constant implements Constants, Cloneable {
  /* In fact this tag is redundant since we can distinguish different
   * `Constant' objects by their type, i.e. via `instanceof'. In some
   * places we will use the tag for switch()es anyway.
   *
   * First, we want match the specification as closely as possible. Second we
   * need the tag as an index to select the corresponding class name from the 
   * `CONSTANT_NAMES' array.
   */
  protected byte tag;

  Constant(byte tag) { this.tag = tag; }  
  /**
   * Called by objects that are traversing the nodes of the tree implicitely
   * defined by the contents of a Java class. I.e., the hierarchy of methods,
   * fields, attributes, etc. spawns a tree of objects.
   *
   * @param v Visitor object
   */
  public abstract void accept(Visitor v);  
  /**
   * @return deep copy of this constant
   */
  public Constant copy() {
	try {
	  return (Constant)clone();
	} catch(CloneNotSupportedException e) {}

	return null;
  }  
  public abstract void dump(DataOutputStream file) throws IOException;  
  /**
   * @return Tag of constant, i.e. its type. No setTag() method to avoid
   * confusion.
   */
  public final byte getTag() { return tag; }  
  /**
   * Read one constant from the given file, the type depends on a tag byte.
   *
   * @param file Input stream
   * @return Constant object
   */
  static final Constant readConstant(DataInputStream file)
	throws IOException, ClassFormatError
  {
	byte b = file.readByte(); // Read tag byte

	switch(b) {
	case CONSTANT_Class:              return new ConstantClass(file);
	case CONSTANT_Fieldref:           return new ConstantFieldref(file);
	case CONSTANT_Methodref:          return new ConstantMethodref(file);
	case CONSTANT_InterfaceMethodref: return new 
					ConstantInterfaceMethodref(file);
	case CONSTANT_String:             return new ConstantString(file);
	case CONSTANT_Integer:            return new ConstantInteger(file);
	case CONSTANT_Float:              return new ConstantFloat(file);
	case CONSTANT_Long:               return new ConstantLong(file);
	case CONSTANT_Double:             return new ConstantDouble(file);
	case CONSTANT_NameAndType:        return new ConstantNameAndType(file);
	case CONSTANT_Utf8:               return new ConstantUtf8(file);
	default:                          
	  throw new ClassFormatError("Invalid byte tag in constant pool: " + b);
	}
  }  
  /**
   * @return String representation.
   */  
  public String toString() {
	return CONSTANT_NAMES[tag] + "[" + tag + "]";
  }  
}