package core.brakes.de.fub.bytecode.generic;

import java.util.Enumeration;
import java.util.Vector;

import core.brakes.de.fub.bytecode.Constants;
import core.brakes.de.fub.bytecode.classfile.AccessFlags;
import core.brakes.de.fub.bytecode.classfile.Attribute;
import core.brakes.de.fub.bytecode.classfile.Field;
import core.brakes.de.fub.bytecode.classfile.JavaClass;
import core.brakes.de.fub.bytecode.classfile.Method;
import core.brakes.de.fub.bytecode.classfile.SourceFile;

/** 
 * Template class for building up a java class. May be initialized by an
 * existing java class (file).
 *
 * @see JavaClass
 * @version $Id: ClassGen.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class ClassGen extends AccessFlags implements Constants {
  /* Corresponds to the fields found in a JavaClass object.
   */
  private String   class_name, super_class_name, file_name;
  private int      class_name_index, superclass_name_index;

  private ConstantPoolGen cp; // Template for building up constant pool

  // Vectors instead of arrays to gather fields, methods, etc.
  private Vector   field_vec     = new Vector();
  private Vector   method_vec    = new Vector();
  private Vector   attribute_vec = new Vector();
  private Vector   interface_vec = new Vector();

  private static final class Int {
	Int(int i) { value = i; }
	int value;

	public boolean equals(Object o) { return value == ((Int)o).value; }
  }
  /**
   * Initialize with existing class.
   * @param clazz JavaClass object (e.g. read from file)
   */
  public ClassGen(JavaClass clazz) {
	class_name_index      = clazz.getClassNameIndex();
	superclass_name_index = clazz.getSuperclassNameIndex();
	class_name            = clazz.getClassName();
	super_class_name      = clazz.getSuperclassName();
	file_name             = clazz.getSourceFileName();
	access_flags          = clazz.getAccessFlags();
	cp                    = new ConstantPoolGen(clazz.getConstantPool());

	Attribute[] attributes = clazz.getAttributes();
	Method[]    methods    = clazz.getMethods();
	Field[]     fields     = clazz.getFields();
	int[]       interfaces = clazz.getInterfaces();
	
	for(int i=0; i < interfaces.length; i++)
	  addInterface(interfaces[i]);

	for(int i=0; i < attributes.length; i++)
	  addAttribute(attributes[i]);

	for(int i=0; i < methods.length; i++)
	  addMethod(methods[i]);

	for(int i=0; i < fields.length; i++)
	  addField(fields[i]);
  }  
  /**
   * @param class_name fully qualified class name
   * @param super_class_name fully qualified superclass name
   * @param file_name source file name
   * @param access_flags access qualifiers
   * @param interfaces implemented interfaces
   */
  public ClassGen(String class_name, String super_class_name, String file_name,
		  int access_flags, String[] interfaces) {
	this.class_name       = class_name;
	this.super_class_name = super_class_name;
	this.file_name        = file_name;
	this.access_flags     = access_flags;
	cp = new ConstantPoolGen(); // Create empty constant pool

	// Put everything needed by default into the constant pool and the vectors
	addAttribute(new SourceFile(cp.addUtf8("SourceFile"), 2,
				cp.addUtf8(file_name), cp.getConstantPool()));
	class_name_index      = cp.addClass(class_name);
	superclass_name_index = cp.addClass(super_class_name);

	if(interfaces != null)
	  for(int i=0; i < interfaces.length; i++)
	addInterface(interfaces[i]);
  }  
  /**
   * Add an attribute to this class.
   * @param a attribute to add
   */
  public final void addAttribute(Attribute a)    { attribute_vec.addElement(a); }  
  /**
   * Convenience method.
   *
   * Add an empty constructor to this class that does nothing but calling super().
   * @param access rights for constructor
   */
  public final void addEmptyConstructor(int access_flags) {
	InstructionList il = new InstructionList();
	il.append(InstructionConstants.THIS); // Push `this'
	il.append(new INVOKESPECIAL(cp.addMethodref(super_class_name,
						"<init>", "()V")));
	il.append(InstructionConstants.RETURN);

	MethodGen mg = new MethodGen(access_flags, Type.VOID, Type.NO_ARGS, null,
		       "<init>", class_name, il, cp);
	mg.setMaxStack(1);
	addMethod(mg.getMethod());
  }  
  /**
   * Add a field to this class.
   * @param f field to add
   */
  public final void addField(Field f)            { field_vec.addElement(f); }  
  /**
   * Add an interface to this class, i.e. this class has to implement it.
   * @param i interface to implement (must be index in constant pool pointing to a ConstantClass)
   */
  public final void addInterface(int i) {
	interface_vec.addElement(new Int(i)); // Wrapped in Int object
  }  
  /**
   * Add an interface to this class, i.e. this class has to implement it.
   * @param i interface to implement (fully qualified class name)
   */
  public final void addInterface(String i) {
	addInterface(cp.addClass(i));
  }  
  /**
   * Add a method to this class.
   * @param m method to add
   */
  public final void addMethod(Method m)          { method_vec.addElement(m); }  
  public final boolean containsField(Field f)    { return field_vec.contains(f); }  
  public final Field containsField(String name) {
	for(Enumeration e=field_vec.elements(); e.hasMoreElements();) {
	  Field f = (Field)e.nextElement();
	  if(f.getName().equals(name))
	return f;
	}

	return null;
  }  
  public final Method containsMethod(String name, String signature) {
	for(Enumeration e=method_vec.elements(); e.hasMoreElements();) {
	  Method m = (Method)e.nextElement();
	  if(m.getName().equals(name) && m.getSignature().equals(signature))
	return m;
	}

	return null;
  }  
  public Attribute[] getAttributes() {
	Attribute[] attributes = new Attribute[attribute_vec.size()];
	attribute_vec.copyInto(attributes);
	return attributes;
  }  
  public String getClassName()      { return class_name; }  
  public final int getClassNameIndex()   { return class_name_index; }  
  public final ConstantPoolGen getConstantPool() { return cp; }  
  public Field[] getFields() {
	Field[] fields = new Field[field_vec.size()];
	field_vec.copyInto(fields);
	return fields;
  }  
  public String getFileName()       { return file_name; }  
  public int[] getInterfaces() {
	int   size       = interface_vec.size();
	int[] interfaces = new int[size];

	for(int i=0; i < size; i++)
	  interfaces[i] = ((Int)interface_vec.elementAt(i)).value;

	return interfaces;
  }  
  /**
   * @return the (finally) built up Java class object.
   */
  public JavaClass getJavaClass() {
	return new JavaClass(class_name_index, superclass_name_index,
			 file_name, MAJOR_1_1, MINOR_1_1, access_flags,
			 cp.getFinalConstantPool(),
			 getInterfaces(), getFields(), getMethods(), getAttributes());
  }  
  public Method[] getMethods() {
	Method[] methods = new Method[method_vec.size()];
	method_vec.copyInto(methods);
	return methods;
  }  
  public String getSuperclassName() { return super_class_name; }  
  public final int getSuperclassNameIndex() { return superclass_name_index; }  
  /**
   * Remove an attribute from this class.
   * @param a attribute to remove
   */
  public final void removeAttribute(Attribute a) { attribute_vec.removeElement(a); }  
  /**
   * Remove a field to this class.
   * @param f field to remove
   */
  public final void removeField(Field f)         { field_vec.removeElement(f); }  
  /**
   * Remove an interface from this class.
   * @param i interface to remove (index in constant pool)
   */
  public final void removeInterface(int i) {
	i = interface_vec.indexOf(new Int(i));
	if(i >= 0)
	  interface_vec.removeElementAt(i);
  }  
  /**
   * Remove a method from this class.
   * @param m method to remove
   */
  public final void removeMethod(Method m)       { method_vec.removeElement(m); }  
  public final void setClassNameIndex(int class_name_index) {
	this.class_name_index = class_name_index;
  }  
  public final void setConstantPool(ConstantPoolGen constant_pool) {
	cp = constant_pool;
  }  
  public void setMethods(Method[] methods) {
	method_vec.removeAllElements();
	for (int m=0; m<methods.length; m++)
	  addMethod(methods[m]);
  }  
  public final void setSuperclassNameIndex(int superclass_name_index) {
	this.superclass_name_index = superclass_name_index;
  }  
}