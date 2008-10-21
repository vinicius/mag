package core.brakes.de.fub.bytecode.generic;

import java.util.Vector;

import core.brakes.de.fub.bytecode.Constants;
import core.brakes.de.fub.bytecode.classfile.AccessFlags;
import core.brakes.de.fub.bytecode.classfile.Attribute;
import core.brakes.de.fub.bytecode.classfile.ConstantValue;
import core.brakes.de.fub.bytecode.classfile.Field;

/** 
 * Template class for building up a field.  The only reasonable thing
 * one can do is a constant value attribute to a field which must of
 * course be compatible with to the declared type.
 *
 * @version $Id: FieldGen.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 * @see Field
 */
public class FieldGen extends AccessFlags implements Constants {
  private Type            type;
  private String          name;
  private ConstantPoolGen cp;
  private int             index = -1;
  private Vector          attributes;
  private String          class_name;

  public FieldGen(int access_flags, Type type, String name, ConstantPoolGen cp) {
	this(access_flags, type, name, null, cp);
  }  
  /**
   * Declare a field. If it is a static field (access_flags & ACC_STATIC != 0) it
   * may have an initial value associated with it as defined by setInitValue().
   *
   * @param access_flags access qualifiers
   * @param type  field type
   * @param name field name
   * @param class_name class name containing this method (may be null, if you don't care)
   * @param cp constant pool
   */
  public FieldGen(int access_flags, Type type, String name, String class_name, ConstantPoolGen cp) {
	this.access_flags = access_flags;
	this.type         = type;
	this.name         = name;
	this.class_name   = class_name;
	this.cp           = cp;
  }  
  public void addAttribute(Attribute attr) {
	if(attributes == null)
	  attributes = new Vector();

	attributes.addElement(attr);
  }  
  public Attribute[] getAttributes() {
	if(attributes != null) {
	  Attribute[] attrs = new Attribute[attributes.size()];
	  attributes.copyInto(attrs);
	  return attrs;
	} else
	  return null;
  }  
  public String getClassName()       { return class_name; }  
  public ConstantPoolGen getConstantPool()         { return cp; }  
  /**
   * Get method object.
   */
  public Field getField() {
	String      signature       = type.getSignature();
	int         name_index      = cp.addUtf8(name);
	int         signature_index = cp.addUtf8(signature);

	if(index > 0)
	  if(isStatic())
	addAttribute(new ConstantValue(cp.addUtf8("ConstantValue"),
				       2, index, cp.getConstantPool()));
	  else
	throw new ClassGenException("Only static fields may have an initial value!");

	return new Field(access_flags, name_index, signature_index, getAttributes(),
		     cp.getConstantPool());
  }  
  public String getName()            { return name; }  
  public String getSignature() { return type.getSignature(); }  
  public Type   getType()            { return type; }  
  public void setConstantPool(ConstantPoolGen cp) { this.cp = cp; }  
  public void setInitValue(byte b)     { if(b != 0)      index = cp.addInteger(b); }  
  public void setInitValue(char c)     { if(c != 0)      index = cp.addInteger(c); }  
  public void setInitValue(double d)   { if(d != 0.0)    index = cp.addDouble(d); }  
  public void setInitValue(float f)    { if(f != 0.0)    index = cp.addFloat(f); }  
  public void setInitValue(int i)      { if(i != 0)      index = cp.addInteger(i); }  
  public void setInitValue(long l)     { if(l != 0L)     index = cp.addLong(l); }  
  /**
   * Set (optional) initial value of field, otherwise it will be set to null/0/false
   * by the JVM automatically.
   */
  public void setInitValue(String str) { if(str != null) index = cp.addString(str); }  
  public void setInitValue(short s)    { if(s != 0)      index = cp.addInteger(s); }  
  public void setInitValue(boolean b)  { if(b)           index = cp.addInteger(1); }  
  public void   setName(String name) { this.name = name; }  
  public void   setType(Type type)   { this.type = type; }  
}