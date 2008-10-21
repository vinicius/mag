package core.brakes.de.fub.bytecode.generic;

import java.util.Hashtable;

import core.brakes.de.fub.bytecode.Constants;
import core.brakes.de.fub.bytecode.classfile.Constant;
import core.brakes.de.fub.bytecode.classfile.ConstantCP;
import core.brakes.de.fub.bytecode.classfile.ConstantClass;
import core.brakes.de.fub.bytecode.classfile.ConstantDouble;
import core.brakes.de.fub.bytecode.classfile.ConstantFieldref;
import core.brakes.de.fub.bytecode.classfile.ConstantFloat;
import core.brakes.de.fub.bytecode.classfile.ConstantInteger;
import core.brakes.de.fub.bytecode.classfile.ConstantInterfaceMethodref;
import core.brakes.de.fub.bytecode.classfile.ConstantLong;
import core.brakes.de.fub.bytecode.classfile.ConstantMethodref;
import core.brakes.de.fub.bytecode.classfile.ConstantNameAndType;
import core.brakes.de.fub.bytecode.classfile.ConstantPool;
import core.brakes.de.fub.bytecode.classfile.ConstantString;
import core.brakes.de.fub.bytecode.classfile.ConstantUtf8;

/** 
 * This class is used to build up a constant pool. The user adds
 * constants via `addXXX' methods, `addString', `addClass',
 * etc.. These methods return an index into the constant
 * pool. Finally, `getFinalConstantPool()' returns the constant pool
 * built up. Intermediate versions of the constant pool can be
 * obtained with `getConstantPool()'. A constant pool has capacity for
 * Constants.MAX_SHORT entries. Note that the first (0) is used by the
 * JVM and that Double and Long constants need two slots.
 *
 * @version $Id: ConstantPoolGen.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 * @see Constant
 */
public class ConstantPoolGen implements Constants {
  protected int        size      = 1024; // Inital size, sufficient in most cases
  protected Constant[] constants = new Constant[size];
  protected int        index     = 1; // First entry (0) used by JVM

  private static class Index {
	int index;
	Index(int i) { index = i; }
  }

  private Hashtable string_table = new Hashtable();

  private Hashtable class_table = new Hashtable();

  private Hashtable n_a_t_table = new Hashtable();

  private Hashtable cp_table = new Hashtable();

  /**
   * Create empty constant pool.
   */
  public ConstantPoolGen() {}  
  /**
   * Initialize with given array of constants.
   *
   * @param c array of given constants, new ones will be appended
   */
  public ConstantPoolGen(Constant[] cs) {
	if(cs.length > size) {
	  size      = cs.length;
	  constants = new Constant[size];
	}

	System.arraycopy(cs, 0, constants, 0, cs.length);

	if(cs.length > 0)
	  index = cs.length;

	for(int i=1; i < index; i++) {
	  Constant c = constants[i];

	  if(c instanceof ConstantString) {
	ConstantString s  = (ConstantString)c;
	ConstantUtf8   u8 = (ConstantUtf8)constants[s.getStringIndex()];

	string_table.put(u8.getBytes(), new Index(i));
	  } else if(c instanceof ConstantClass) {
	ConstantClass s  = (ConstantClass)c;
	ConstantUtf8  u8 = (ConstantUtf8)constants[s.getNameIndex()];

	class_table.put(u8.getBytes(), new Index(i));
	  } else if(c instanceof ConstantNameAndType) {
	ConstantNameAndType n    = (ConstantNameAndType)c;
	ConstantUtf8        u8   = (ConstantUtf8)constants[n.getNameIndex()];
	ConstantUtf8        u8_2 = (ConstantUtf8)constants[n.getSignatureIndex()];

	n_a_t_table.put(u8.getBytes() + ":" + u8_2.getBytes(), new Index(i));
	  } else if(c instanceof ConstantCP) {
	ConstantCP          m     = (ConstantCP)c;
	ConstantClass       clazz = (ConstantClass)constants[m.getClassIndex()];
	ConstantNameAndType n     = (ConstantNameAndType)constants[m.getNameAndTypeIndex()];
	
		ConstantUtf8 u8         = (ConstantUtf8)constants[clazz.getNameIndex()];
		String       class_name = u8.getBytes().replace('/', '.');

	u8 = (ConstantUtf8)constants[n.getNameIndex()];
	String method_name = u8.getBytes();

	u8 = (ConstantUtf8)constants[n.getSignatureIndex()];
	String signature = u8.getBytes();

	cp_table.put(class_name + ":" + method_name + ":" + signature, new Index(i));
	  }
	}
  }  
  /**
   * Initialize with given constant pool.
   */
  public ConstantPoolGen(ConstantPool cp) {
	this(cp.getConstantPool());
  }  
  /**
   * Add a reference to an array class (e.g. String[][]) as needed by MULTIANEWARRAY
   * instruction, e.g. to the ConstantPool.
   *
   * @param type type of array class
   * @return index of entry
   */
  public final int addArrayClass(ArrayType type) {
	return addClass_(type.getSignature());
  }  
  /**
   * Add a new Class reference to the ConstantPool for a given type.
   *
   * @param str Class to add
   * @return index of entry
   */
  public final int addClass(ObjectType type) {
	return addClass(type.getClassName());
  }  
  /**
   * Add a new Class reference to the ConstantPool, if it is not already in there.
   *
   * @param str Class to add
   * @return index of entry
   */
  public final int addClass(String str) {
	return addClass_(str.replace('.', '/'));
  }  
  private final int addClass_(String clazz) {
	int    ret;

	if((ret = lookupClass(clazz)) != -1)
	  return ret; // Already in CP

	adjustSize();

	ConstantUtf8  u8 = new ConstantUtf8(clazz);
	ConstantClass c  = new ConstantClass(index);

	constants[index++] = u8;
	ret = index;
	constants[index++] = c;

	class_table.put(clazz, new Index(ret));

	return ret;
  }  
  /**
   * Add a new double constant to the ConstantPool, if it is not already in there.
   *
   * @param n Double number to add
   * @return index of entry
   */
  public final int addDouble(double n) {
	int  ret;

	if((ret = lookupDouble(n)) != -1)
	  return ret; // Already in CP

	adjustSize();

	ret = index;
	constants[index] = new ConstantDouble(n);
	index += 2;   // Wastes one entry according to spec

	return ret;
  }  
  public final int addFieldref(FieldGen field) {
	return addFieldref(field.getClassName(), field.getName(), field.getSignature());
  }  
  /**
   * Add a new Fieldref constant to the ConstantPool, if it is not already 
   * in there.
   *
   * @param n Fieldref string to add
   * @return index of entry
   */
  public final int addFieldref(String class_name, String field_name,
			       String signature) {
	int  ret;
	int  class_index, name_and_type_index;

	if((ret = lookupFieldref(class_name, field_name, signature)) != -1)
	  return ret; // Already in CP

	adjustSize();

	class_index         = addClass(class_name);
	name_and_type_index = addNameAndType(field_name, signature);
	ret = index;
	constants[index++] = new ConstantFieldref(class_index, name_and_type_index);

	cp_table.put(class_name + ":" + field_name + ":" + signature, new Index(ret));

	return ret;
  }  
  /**
   * Add a new Float constant to the ConstantPool, if it is not already in there.
   *
   * @param n Float number to add
   * @return index of entry
   */
  public final int addFloat(float n) {
	int  ret;

	if((ret = lookupFloat(n)) != -1)
	  return ret; // Already in CP

	adjustSize();

	ret = index;
	constants[index++] = new ConstantFloat(n);

	return ret;
  }  
  /* Suggested ...
	 public final int addConstant(Constant c)
  {
	int  ret;

	adjustSize();

	ret = index;
	constants[index++] = c;

	return ret;
  }
  */

  /**
   * Add a new Integer constant to the ConstantPool, if it is not already in there.
   *
   * @param n integer number to add
   * @return index of entry
   */
  public final int addInteger(int n) {
	int  ret;

	if((ret = lookupInteger(n)) != -1)
	  return ret; // Already in CP

	adjustSize();

	ret = index;
	constants[index++] = new ConstantInteger(n);

	return ret;
  }  
  public final int addInterfaceMethodref(MethodGen method) {
	return addInterfaceMethodref(method.getClassName(), method.getMethodName(),
				 method.getMethodSignature());
  }  
  /**
   * Add a new InterfaceMethodref constant to the ConstantPool, if it is not already 
   * in there.
   *
   * @param n InterfaceMethodref string to add
   * @return index of entry
   */
  public final int addInterfaceMethodref(String class_name,
					 String method_name,
					 String signature) {
	int  ret, class_index, name_and_type_index;

	if((ret = lookupInterfaceMethodref(class_name, method_name, signature)) != -1)
	  return ret; // Already in CP

	adjustSize();

	class_index         = addClass(class_name);
	name_and_type_index = addNameAndType(method_name, signature);
	ret = index;
	constants[index++] = new ConstantInterfaceMethodref(class_index, name_and_type_index);

	cp_table.put(class_name + ":" + method_name + ":" + signature, new Index(ret));

	return ret;
  }  
  /**
   * Add a new long constant to the ConstantPool, if it is not already in there.
   *
   * @param n Long number to add
   * @return index of entry
   */
  public final int addLong(long n) {
	int  ret;

	if((ret = lookupLong(n)) != -1)
	  return ret; // Already in CP

	adjustSize();

	ret = index;
	constants[index] = new ConstantLong(n);
	index += 2;   // Wastes one entry according to spec

	return ret;
  }  
  public final int addMethodref(MethodGen method) {
	return addMethodref(method.getClassName(), method.getMethodName(),
			method.getMethodSignature());
  }  
  /**
   * Add a new Methodref constant to the ConstantPool, if it is not already 
   * in there.
   *
   * @param n Methodref string to add
   * @return index of entry
   */
  public final int addMethodref(String class_name, String method_name,
				String signature) {
	int  ret, class_index, name_and_type_index;

	if((ret = lookupMethodref(class_name, method_name, signature)) != -1)
	  return ret; // Already in CP

	adjustSize();

	name_and_type_index = addNameAndType(method_name, signature);
	class_index         = addClass(class_name);
	ret = index;
	constants[index++] = new ConstantMethodref(class_index, name_and_type_index);

	cp_table.put(class_name + ":" + method_name + ":" + signature, new Index(ret));

	return ret;
  }  
  /**
   * Add a new NameAndType constant to the ConstantPool if it is not already 
   * in there.
   *
   * @param n NameAndType string to add
   * @return index of entry
   */
  public final int addNameAndType(String name, String signature) {
	int  ret;
	int  name_index, signature_index;

	if((ret = lookupNameAndType(name, signature)) != -1)
	  return ret; // Already in CP

	adjustSize();

	name_index      = addUtf8(name);
	signature_index = addUtf8(signature);
	ret = index;
	constants[index++] = new ConstantNameAndType(name_index, signature_index);

	n_a_t_table.put(name + ":" + signature, new Index(ret));
	return ret;
  }  
  /**
   * Add a new String constant to the ConstantPool, if it is not already in there.
   *
   * @param str String to add
   * @return index of entry
   */
  public final int addString(String str) {
	int ret;
	
	if((ret = lookupString(str)) != -1)
	  return ret; // Already in CP

	adjustSize();

	ConstantUtf8   u8 = new ConstantUtf8(str);
	ConstantString s  = new ConstantString(index);
	   
	constants[index++] = u8;
	ret = index;
	constants[index++] = s;

	string_table.put(str, new Index(ret));

	return ret;
  }  
  /**
   * Add a new Utf8 constant to the ConstantPool, if it is not already in there.
   *
   * @param n Utf8 string to add
   * @return index of entry
   */
  public final int addUtf8(String n) {
	int  ret;

	if((ret = lookupUtf8(n)) != -1)
	  return ret; // Already in CP

	adjustSize();

	ret = index;
	constants[index++] = new ConstantUtf8(n);

	return ret;
  }  
  /** Resize internal array of constants.
   */
  protected void adjustSize() {
	if(index + 3 >= size) {
	  Constant[] cs = constants;

	  size      *= 2;
	  constants  = new Constant[size];
	  System.arraycopy(cs, 0, constants, 0, index);
	}
  }  
  /**
   * @param i index in constant pool
   * @return constant pool entry at index i
   */
  public Constant getConstant(int i) { return constants[i]; }  
  /**
   * @return intermediate constant pool
   */
  public ConstantPool getConstantPool() {
	return new ConstantPool(constants);
  }  
  /**
   * @return constant pool with proper length
   */
  public ConstantPool getFinalConstantPool() { 
	Constant[] cs = new Constant[index];
	
	System.arraycopy(constants, 0, cs, 0, index);

	return new ConstantPool(cs); 
  }  
  /**
   * @return current size of constant pool
   */
  public int getSize() {
	return index;
  }  
  /**
   * Look for ConstantClass in ConstantPool named `str'.
   *
   * @param str String to search for
   * @return index on success, -1 otherwise
   */
  public final int lookupClass(String str) {
	Index index = (Index)class_table.get(str);
	return (index != null)? index.index : -1;
  }  
  /** 
   * Look for ConstantDouble in ConstantPool.
   *
   * @param n Double number to look for
   * @return index on success, -1 otherwise
   */
  public final int lookupDouble(double n) {
	for(int i=1; i < index; i++) {
	  if(constants[i] instanceof ConstantDouble) {
	ConstantDouble c = (ConstantDouble)constants[i];

	if(c.getBytes() == n)
	  return i;
	  }
	}

	return -1;
  }  
  public final int lookupFieldref(FieldGen field) {
	return lookupFieldref(field.getClassName(), field.getName(), field.getSignature());
  }  
  /** 
   * Look for ConstantFieldref in ConstantPool.
   *
   * @param class_name Where to find method
   * @param field_name Guess what
   * @param signature return and argument types
   * @return index on success, -1 otherwise
   */
  public final int lookupFieldref(String class_name, String field_name,
				  String signature) {
	Index index = (Index)cp_table.get(class_name + ":" + field_name + ":" + signature);
	return (index != null)? index.index : -1;
  }  
  /** 
   * Look for ConstantFloat in ConstantPool.
   *
   * @param n Float number to look for
   * @return index on success, -1 otherwise
   */
  public final int lookupFloat(float n) {
	for(int i=1; i < index; i++) {
	  if(constants[i] instanceof ConstantFloat) {
	ConstantFloat c = (ConstantFloat)constants[i];

	if(c.getBytes() == n)
	  return i;
	  }
	}

	return -1;
  }  
  /** 
   * Look for ConstantInteger in ConstantPool.
   *
   * @param n integer number to look for
   * @return index on success, -1 otherwise
   */
  public final int lookupInteger(int n) {
	for(int i=1; i < index; i++) {
	  if(constants[i] instanceof ConstantInteger) {
	ConstantInteger c = (ConstantInteger)constants[i];

	if(c.getBytes() == n)
	  return i;
	  }
	}

	return -1;
  }  
  public final int lookupInterfaceMethodref(MethodGen method) {
	return lookupInterfaceMethodref(method.getClassName(), method.getMethodName(),
				    method.getMethodSignature());
  }  
  /** 
   * Look for ConstantInterfaceMethodref in ConstantPool.
   *
   * @param class_name Where to find method
   * @param method_name Guess what
   * @param signature return and argument types
   * @return index on success, -1 otherwise
   */
  public final int lookupInterfaceMethodref(String class_name,
					    String method_name,
					    String signature) {
	Index index = (Index)cp_table.get(class_name + ":" + method_name + ":" + signature);
	return (index != null)? index.index : -1;
  }  
  /** 
   * Look for ConstantLong in ConstantPool.
   *
   * @param n Long number to look for
   * @return index on success, -1 otherwise
   */
  public final int lookupLong(long n) {
	for(int i=1; i < index; i++) {
	  if(constants[i] instanceof ConstantLong) {
	ConstantLong c = (ConstantLong)constants[i];

	if(c.getBytes() == n)
	  return i;
	  }
	}

	return -1;
  }  
  public final int lookupMethodref(MethodGen method) {
	return lookupMethodref(method.getClassName(), method.getMethodName(),
			  method.getMethodSignature());
  }  
  /** 
   * Look for ConstantMethodref in ConstantPool.
   *
   * @param class_name Where to find method
   * @param method_name Guess what
   * @param signature return and argument types
   * @return index on success, -1 otherwise
   */
  public final int lookupMethodref(String class_name, String method_name,
				   String signature) {
	Index index = (Index)cp_table.get(class_name + ":" + method_name + ":" + signature);
	return (index != null)? index.index : -1;
  }  
  /** 
   * Look for ConstantNameAndType in ConstantPool.
   *
   * @param name of variable/method
   * @param signature of variable/method
   * @return index on success, -1 otherwise
   */
  public final int lookupNameAndType(String name, String signature) {
	Index index = (Index)n_a_t_table.get(name + ":" + signature);
	return (index != null)? index.index : -1;
  }  
  /** 
   * Look for ConstantString in ConstantPool containing String `str'.
   *
   * @param str String to search for
   * @return index on success, -1 otherwise
   */
  public final int lookupString(String str) {
	Index index = (Index)string_table.get(str);
	return (index != null)? index.index : -1;
  }  
  /** 
   * Look for ConstantUtf8 in ConstantPool.
   *
   * @param n Utf8 string to look for
   * @return index on success, -1 otherwise
   */
  public final int lookupUtf8(String n) {
	for(int i=1; i < index; i++) {
	  if(constants[i] instanceof ConstantUtf8) {
	ConstantUtf8 c = (ConstantUtf8)constants[i];

	if(c.getBytes().equals(n))
	  return i;
	  }
	}

	return -1;
  }  
  /**
   * Use with care!
   *
   * @param i index in constant pool
   * @param c new constant pool entry at index i
   */
  public void setConstant(int i, Constant c) { constants[i] = c; }  
  /**
   * @return String representation.
   */
  public final String toString() {
	StringBuffer buf = new StringBuffer();

	for(int i=1; i < index; i++)
	  buf.append(i + ")" + constants[i] + "\n");

	return buf.toString();
  }  
}