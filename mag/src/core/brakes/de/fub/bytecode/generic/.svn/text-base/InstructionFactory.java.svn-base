package core.brakes.de.fub.bytecode.generic;

import core.brakes.de.fub.bytecode.Constants;

/** 
 * Instances of this class may be used, e.g., to generate typed
 * versions of instructions.  Its main purpose is to be used as the
 * byte code generating backend of a compiler. You can subclass it to
 * add your own create methods.
 *
 * @version $Id: InstructionFactory.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class InstructionFactory implements InstructionConstants {
  protected ClassGen        cg;
  protected ConstantPoolGen cp;
  public static final short INVOKESTATIC = Constants.INVOKESTATIC;

  /** Need a ClassGen object to obtain a constant pool, e.g.
   */
  public InstructionFactory(ClassGen cg) {
	this.cg = cg;
	cp = cg.getConstantPool();
  }  
/**
 * This method was created in VisualAge.
 * @param cp de.fub.bytecode.generic.ConstantPoolGen
 */
public InstructionFactory(ConstantPoolGen cp) {
	this.cp = cp;
}

  public InstructionList createPrintln(String s) {
    InstructionList il      = new InstructionList();
    int             out     = cp.addFieldref("java.lang.System", "out",
                                             "Ljava/io/PrintStream;");
    int             println = cp.addMethodref("java.io.PrintStream", "println",
                                              "(Ljava/lang/String;)V");

    il.append(new GETSTATIC(out));
    il.append(new PUSH(cp, s));
    il.append(new INVOKEVIRTUAL(println));

    return il;
  }


  /**
   * @param type type of elements of array, i.e. array.getElementType()
   */
  public static ArrayInstruction createArrayLoad(Type type) {
	switch(type.getType()) {
	case Constants.T_BOOLEAN:
	case Constants.T_BYTE:   return BALOAD;
	case Constants.T_CHAR:   return CALOAD;
	case Constants.T_SHORT:  return SALOAD;
	case Constants.T_INT:    return IALOAD;
	case Constants.T_FLOAT:  return FALOAD;
	case Constants.T_DOUBLE: return DALOAD;
	case Constants.T_LONG:   return LALOAD;
	case Constants.T_ARRAY:
	case Constants.T_OBJECT: return AALOAD;
	default:       throw new RuntimeException("Invalid type " + type);
	}
  }  
  /**
   * @param type type of elements of array, i.e. array.getElementType()
   */
  public static ArrayInstruction createArrayStore(Type type) {
	switch(type.getType()) {
	case Constants.T_BOOLEAN:
	case Constants.T_BYTE:   return BASTORE;
	case Constants.T_CHAR:   return CASTORE;
	case Constants.T_SHORT:  return SASTORE;
	case Constants.T_INT:    return IASTORE;
	case Constants.T_FLOAT:  return FASTORE;
	case Constants.T_DOUBLE: return DASTORE;
	case Constants.T_LONG:   return LASTORE;
	case Constants.T_ARRAY:
	case Constants.T_OBJECT: return AASTORE;
	default:       throw new RuntimeException("Invalid type " + type);
	}
  }  
  private static final ArithmeticInstruction createBinaryDoubleOp(char op) {
	switch(op) {
	case '-' : return DSUB;
	case '+' : return DADD;
	case '*' : return DMUL;
	case '/' : return DDIV;
	default: throw new RuntimeException("Invalid operand " + op);
	}
  }  
  private static final ArithmeticInstruction createBinaryFloatOp(char op) {
	switch(op) {
	case '-' : return FSUB;
	case '+' : return FADD;
	case '*' : return FMUL;
	case '/' : return FDIV;
	default: throw new RuntimeException("Invalid operand " + op);
	}
  }  
  private static final ArithmeticInstruction createBinaryIntOp(char first, String op) {
	switch(first) {
	case '-' : return ISUB;
	case '+' : return IADD;
	case '%' : return IREM;
	case '*' : return IMUL;
	case '/' : return IDIV;
	case '&' : return IAND;
	case '|' : return IOR;
	case '^' : return IXOR;
	case '<' : return ISHL;
	case '>' : return op.equals(">>>")? (ArithmeticInstruction)IUSHR :
	  (ArithmeticInstruction)ISHR;
	default: throw new RuntimeException("Invalid operand " + op);
	}
  }  
  private static final ArithmeticInstruction createBinaryLongOp(char first, String op) {
	switch(first) {
	case '-' : return LSUB;
	case '+' : return LADD;
	case '%' : return LREM;
	case '*' : return LMUL;
	case '/' : return LDIV;
	case '&' : return LAND;
	case '|' : return LOR;
	case '^' : return LXOR;
	case '<' : return LSHL;
	case '>' : return op.equals(">>>")? (ArithmeticInstruction)LUSHR :
	  (ArithmeticInstruction)LSHR;
	default: throw new RuntimeException("Invalid operand " + op);
	}
  }  
  /**
   * Create binary operation for simple basic types, such as int and float.
   *
   * @param op operation, such as "+", "*", "<<", etc.
   */
  public static ArithmeticInstruction createBinaryOperation(String op, Type type) {
	char first = op.toCharArray()[0];

	switch(type.getType()) {
	case Constants.T_BYTE:
	case Constants.T_SHORT:
	case Constants.T_INT:
	case Constants.T_CHAR:    return createBinaryIntOp(first, op);
	case Constants.T_LONG:    return createBinaryLongOp(first, op);
	case Constants.T_FLOAT:   return createBinaryFloatOp(first);
	case Constants.T_DOUBLE:  return createBinaryDoubleOp(first);
	default:        throw new RuntimeException("Invalid type " + type);
	}
  }  
  /** Create conversion operation for two stack operands, this may be an I2C, instruction, e.g.,
   * if the operands are basic types and CHECKCAST if they are reference types.
   */
  public Instruction createCast(Type src_type, Type dest_type) {
	if((src_type instanceof BasicType) && (dest_type instanceof BasicType)) {
	  byte dest = dest_type.getType();
	  byte src  = src_type.getType();

	  if(dest == Constants.T_LONG && (src == Constants.T_CHAR || src == Constants.T_BYTE ||
				      src == Constants.T_SHORT))
	src = Constants.T_INT;

	  String[] short_names = { "C", "F", "D", "B", "S", "I", "L" };

	  String name = "de.fub.bytecode.generic." + short_names[src - Constants.T_CHAR] +
	"2" + short_names[dest - Constants.T_CHAR];
	  
	  Instruction i = null;
	  try {
	i = (Instruction)java.lang.Class.forName(name).newInstance();
	  } catch(Exception e) {
	throw new RuntimeException("Could not find instruction: " + name);
	  }

	  return i;
	} else if((src_type instanceof ReferenceType) && (dest_type instanceof ReferenceType)) {
	  if(dest_type instanceof ArrayType)
	return new CHECKCAST(cp.addArrayClass((ArrayType)dest_type));
	  else
	return new CHECKCAST(cp.addClass(((ObjectType)dest_type).getClassName()));
	}
	else
	  throw new RuntimeException("Can not cast " + src_type + " to " + dest_type);
  }  
  public CHECKCAST createCheckCast(ReferenceType t) {
	if(t instanceof ArrayType)
	  return new CHECKCAST(cp.addArrayClass((ArrayType)t));
	else
	  return new CHECKCAST(cp.addClass((ObjectType)t));
  }  
  /**
   * @param size size of operand, either 1 (int, e.g.) or 2 (double)
   */
  public static StackInstruction createDup(int size) {
	return (size == 2)? (StackInstruction)DUP2 :
	  (StackInstruction)DUP;
  }  
  /**
   * @param size size of operand, either 1 (int, e.g.) or 2 (double)
   */
  public static StackInstruction createDup_1(int size) {
	return (size == 2)? (StackInstruction)DUP2_X1 :
	  (StackInstruction)DUP_X1;
  }  
  /**
   * @param size size of operand, either 1 (int, e.g.) or 2 (double)
   */
  public static StackInstruction createDup_2(int size) {
	return (size == 2)? (StackInstruction)DUP2_X2 :
	  (StackInstruction)DUP_X2;
  }  
  public GETFIELD createGetField(String class_name, String name, Type t) {
	return new GETFIELD(cp.addFieldref(class_name, name, t.getSignature()));
  }  
  public GETSTATIC createGetStatic(String class_name, String name, Type t) {
	return new GETSTATIC(cp.addFieldref(class_name, name, t.getSignature()));
  }  
  /** Create an invoke instruction.
   *
   * @param class_name name of the called class
   * @param name name of the called method
   * @param ret_type return type of method
   * @param arg_types argument types of method
   * @param kind how to invoke, i.e. INVOKEINTERFACE, INVOKESTATIC, INVOKEVIRTUAL,
   * or INVOKESPECIAL
   */
  public InvokeInstruction createInvoke(String class_name, String name, Type ret_type,
					Type[] arg_types, short kind) {
	int    index;
	int    nargs      = 0;
	String signature  = Type.getMethodSignature(ret_type, arg_types);

	for(int i=0; i < arg_types.length; i++) // Count size of arguments
	  nargs += arg_types[i].getSize();

	if(kind == Constants.INVOKEINTERFACE)
	  index = cp.addInterfaceMethodref(class_name, name, signature);
	else
	  index = cp.addMethodref(class_name, name, signature);

	switch(kind) {
	case Constants.INVOKESPECIAL:   return new INVOKESPECIAL(index);
	case Constants.INVOKEVIRTUAL:   return new INVOKEVIRTUAL(index);
	case Constants.INVOKESTATIC:    return new INVOKESTATIC(index);
	case Constants.INVOKEINTERFACE: return new INVOKEINTERFACE(index, nargs + 1);
	default:
	  throw new RuntimeException("Oops: Unknown invoke kind:" + kind);
	}
  }  
  /**
   * @param index index of local variable
   */
  public static LocalVariableInstruction createLoad(Type type, int index) {
	switch(type.getType()) {
	case Constants.T_BOOLEAN:
	case Constants.T_CHAR:
	case Constants.T_BYTE:
	case Constants.T_SHORT:
	case Constants.T_INT:    return new ILOAD(index);
	case Constants.T_FLOAT:  return new FLOAD(index);
	case Constants.T_DOUBLE: return new DLOAD(index);
	case Constants.T_LONG:   return new LLOAD(index);
	case Constants.T_ARRAY:
	case Constants.T_OBJECT: return new ALOAD(index);
	default:       throw new RuntimeException("Invalid type " + type);
	}
  }  
  public NEW createNew(ObjectType t) {
	return new NEW(cp.addClass(t));
  }  
  /** Create new  array.
   */
  public CPInstruction createNewArray(ArrayType t, short dim) {
	if(dim == 1)
	  return new ANEWARRAY(cp.addArrayClass(t));
	else
	  return new MULTIANEWARRAY(cp.addArrayClass(t), dim);
  } 

 /** Create new array of given size and type.
   */
  public Instruction createNewArray(Type t, short dim) {
    if(dim == 1) {
      if(t instanceof ObjectType)
        return new ANEWARRAY(cp.addClass((ObjectType)t));
      else if(t instanceof ArrayType)
        return new ANEWARRAY(cp.addArrayClass((ArrayType)t));
      else
        return new NEWARRAY(((BasicType)t).getType());
    } else {
      ArrayType at;

      if(t instanceof ArrayType)
        at = (ArrayType)t;
      else
        at = new ArrayType(t, dim);

      return new MULTIANEWARRAY(cp.addArrayClass(at), dim);
    }
  }

  /**
   * @param size size of operand, either 1 (int, e.g.) or 2 (double)
   */
  public static StackInstruction createPop(int size) {
	return (size == 2)? (StackInstruction)POP2 :
	  (StackInstruction)POP;
  }  
  public PUTFIELD createPutField(String class_name, String name, Type t) {
	return new PUTFIELD(cp.addFieldref(class_name, name, t.getSignature()));
  }  
  public PUTSTATIC createPutStatic(String class_name, String name, Type t) {
	return new PUTSTATIC(cp.addFieldref(class_name, name, t.getSignature()));
  }  
  /** Create typed return
   */
  public static ReturnInstruction createReturn(Type type) {
	switch(type.getType()) {
	case Constants.T_ARRAY:
	case Constants.T_OBJECT:  return ARETURN;
	case Constants.T_INT:
	case Constants.T_SHORT:
	case Constants.T_BOOLEAN:
	case Constants.T_CHAR: 
	case Constants.T_BYTE:    return IRETURN;
	case Constants.T_FLOAT:   return FRETURN;
	case Constants.T_DOUBLE:  return DRETURN;
	case Constants.T_LONG:    return LRETURN;
	case Constants.T_VOID:    return RETURN;

	default:
	  throw new RuntimeException("Invalid type: " + type);
	}
  }  
  /**
   * @param index index of local variable
   */
  public static LocalVariableInstruction createStore(Type type, int index) {
	switch(type.getType()) {
	case Constants.T_BOOLEAN:
	case Constants.T_CHAR:
	case Constants.T_BYTE:
	case Constants.T_SHORT:
	case Constants.T_INT:    return new ISTORE(index);
	case Constants.T_FLOAT:  return new FSTORE(index);
	case Constants.T_DOUBLE: return new DSTORE(index);
	case Constants.T_LONG:   return new LSTORE(index);
	case Constants.T_ARRAY:
	case Constants.T_OBJECT: return new ASTORE(index);
	default:       throw new RuntimeException("Invalid type " + type);
	}
  }  
  /** Create reference to `this'
   */
  public static Instruction createThis() {
	return new ALOAD(0);
  }  
}
