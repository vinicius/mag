/** This file is part of the BRAKES framework v0.3
  * Developed by: 
  *   Distributed Systems and Computer Networks Group (DistriNet)
  *   Katholieke Universiteit Leuven  
  *   Department of Computer Science
  *   Celestijnenlaan 200A
  *   3001 Leuven (Heverlee)
  *   Belgium
  * Project Manager and Principal Investigator: 
  *                        Pierre Verbaeten(pv@cs.kuleuven.ac.be)
  * Licensed under the Academic Free License version 1.1 (see COPYRIGHT)
  */

package core.brakes.be.ac.kuleuven.cs.ttm.transformer.dataflow.analyzer;


import core.brakes.be.ac.kuleuven.cs.ttm.transformer.dataflow.Registry;
import core.brakes.de.fub.bytecode.classfile.JavaClass;
import core.brakes.de.fub.bytecode.classfile.Method;
import core.brakes.de.fub.bytecode.generic.MethodGen;
import core.brakes.de.fub.bytecode.generic.Type;

public class Analyzer {

  JavaClass jClass;
  CodeVisitor codeVisitor;
public Analyzer() {
	codeVisitor = new CodeVisitor(createOpcodeTable());
}
public Registry analyze(MethodGen m) {
	System.out.println("Analyzing " + m.getClassName() + "." + m.getMethodName());
	Environment env = new Environment(m);
	codeVisitor.compute(env);
	return env.getRegistry();
}
Opcode[] createOpcodeTable() {
	Opcode[] codes = new Opcode[256];
	// commmon type combinations
	Type[] objint = {Type.OBJECT, Type.INT};
	Type[] objintint = {Type.OBJECT, Type.INT, Type.INT};
	Type[] objintlong = {Type.OBJECT, Type.INT, Type.LONG, Type.VOID};
	Type[] objintfloat = {Type.OBJECT, Type.INT, Type.FLOAT};
	Type[] objintdouble = {Type.OBJECT, Type.INT, Type.DOUBLE, Type.VOID};
	Type[] objintobj = {Type.OBJECT, Type.INT, Type.OBJECT};
	Type[] doubledouble = {Type.DOUBLE, Type.VOID, Type.DOUBLE, Type.VOID};
	Type[] longlong = {Type.LONG, Type.VOID, Type.LONG, Type.VOID};
	Type[] floatfloat = {Type.FLOAT, Type.FLOAT};
	Type[] empty = {};
	Type[] type_int = {Type.INT};
	Type[] type_long = {Type.LONG, Type.VOID};
	Type[] type_float = {Type.FLOAT};
	Type[] type_double = {Type.DOUBLE, Type.VOID};
	Type[] type_obj = {Type.OBJECT};
	Type[] objobj = {Type.OBJECT, Type.OBJECT};
	Type[] intint = {Type.INT, Type.INT};
	Type[] longint = {Type.LONG, Type.INT};
	codes[50] = new ArrayLoad(); // objint, type_obj; aaload
	codes[83] = new ArrayStore(Type.OBJECT); // objintobj, empty; aastore
	codes[1] = new SimpleOpcode(empty, new Type [] { Type.NULL }); // aconst_null
	codes[25] = new Load(Type.OBJECT); // aload
	codes[42] = codes[25]; // aload_0
	codes[43] = codes[25]; // aload_1
	codes[44] = codes[25]; // aload_2
	codes[45] = codes[25]; // aload_3
	codes[189] = new Anewarray(); // anewarray
	codes[176] = new Return(Type.OBJECT); // areturn
	codes[190] = new SimpleOpcode(type_obj, type_int); // arraylength
	codes[58] = new Store(Type.OBJECT); // astore
	codes[75] = new Store(Type.OBJECT); // astore_0
	codes[76] = new Store(Type.OBJECT); // astore_1
	codes[77] = new Store(Type.OBJECT); // astore_2
	codes[78] = new Store(Type.OBJECT); // astore_3
	codes[191] = new SimpleOpcode(type_obj, empty); // athrow
	codes[51] = new ArrayLoad(); // baload
	codes[84] = new ArrayStore(Type.BOOLEAN); // bastore
	codes[16] = new SimpleOpcode(empty, type_int); // bipush
	codes[52] = new ArrayLoad(); // caload
	codes[85] = new ArrayStore(Type.CHAR); // castore
	codes[192] = new Checkcast(); // checkcast
	codes[193] = new SimpleOpcode(type_obj, type_int); // CLASSREF,type_obj, type_int, 2);
	codes[144] = new SimpleOpcode(type_double, type_float); // d2f
	codes[142] = new SimpleOpcode(type_double, type_int); // d2i
	codes[143] = new SimpleOpcode(type_double, type_long); // d2l
	codes[99] = new SimpleOpcode(doubledouble, type_double); // dadd
	codes[49] = new ArrayLoad(); // SimpleOpcode(objint, type_double); // daload
	codes[82] = new ArrayStore(Type.DOUBLE); // SimpleOpcode(objintdouble, empty); // dastore
	codes[152] = new SimpleOpcode(doubledouble, type_int); // dcmpg
	codes[151] = new SimpleOpcode(doubledouble, type_int); // dcmpl
	codes[14] = new SimpleOpcode(empty, type_double); // dconst_0
	codes[15] = new SimpleOpcode(empty, type_double); // dconst_1
	codes[111] = new SimpleOpcode(doubledouble, type_double); // ddiv
	codes[24] = new Load(Type.DOUBLE); // dload
	codes[38] = new Load(Type.DOUBLE); // dload_0
	codes[39] = new Load(Type.DOUBLE); // dload_1
	codes[40] = new Load(Type.DOUBLE); // dload_2
	codes[41] = new Load(Type.DOUBLE); // dload_3
	codes[107] = new SimpleOpcode(doubledouble, type_double); // dmul
	codes[119] = new SimpleOpcode(type_double, type_double); // dneg
	codes[115] = new SimpleOpcode(doubledouble, type_double); // drem
	codes[175] = new Return(Type.DOUBLE); // dreturn
	codes[57] = new Store(Type.DOUBLE); // dstore
	codes[71] = new Store(Type.DOUBLE); // dstore_0
	codes[72] = new Store(Type.DOUBLE); // dstore_1
	codes[73] = new Store(Type.DOUBLE); // dstore_2
	codes[74] = new Store(Type.DOUBLE); // dstore_3
	codes[103] = new SimpleOpcode(doubledouble, type_double); // dsub
	codes[89] = new Dup(); 
	codes[90] = new Dup_x1();
	codes[91] = new Dup_x2();
	codes[92] = new Dup2(); 
	codes[93] = new Dup2_x1();
	codes[94] = new Dup2_x2();
	codes[141] = new SimpleOpcode(type_float, type_double); // f2d
	codes[139] = new SimpleOpcode(type_float, type_int); // f2i
	codes[140] = new SimpleOpcode(type_float, type_long); // f2l
	codes[98] = new SimpleOpcode(floatfloat, type_float); // fadd
	codes[48] = new ArrayLoad(); // SimpleOpcode(objint, type_float); // faload
	codes[81] = new ArrayStore(Type.FLOAT); // SimpleOpcode(objintfloat, empty); // fastore
	codes[150] = new SimpleOpcode(floatfloat, type_int); // fcmpg
	codes[149] = new SimpleOpcode(floatfloat, type_int); // fcmpl
	codes[11] = new SimpleOpcode(empty, type_float); // fconst_0
	codes[12] = new SimpleOpcode(empty, type_float); // fconst_1
	codes[13] = new SimpleOpcode(empty, type_float); // fconst_2
	codes[110] = new SimpleOpcode(floatfloat, type_float); // fdiv
	codes[23] = new Load(Type.FLOAT); // fload
	codes[34] = new Load(Type.FLOAT); // fload_0
	codes[35] = new Load(Type.FLOAT); // fload_1
	codes[36] = new Load(Type.FLOAT); // fload_2 
	codes[37] = new Load(Type.FLOAT); // fload_3
	codes[106] = new SimpleOpcode(floatfloat, type_float); // fmul
	codes[118] = new SimpleOpcode(type_float, type_float); // fneg
	codes[114] = new SimpleOpcode(floatfloat, type_float); // frem
	codes[174] = new Return(Type.FLOAT); // freturn 
	codes[56] = new Store(Type.FLOAT); // fstore
	codes[67] = new Store(Type.FLOAT); // fstore_0
	codes[68] = new Store(Type.FLOAT); // fstore_1
	codes[69] = new Store(Type.FLOAT); // fstore_2
	codes[70] = new Store(Type.FLOAT); // fstore_3
	codes[102] = new SimpleOpcode(floatfloat, type_float); // fsub
	codes[180] = new Field(false,false); // getfield
	codes[178] = new Field(false,true);  // getstatic
	codes[167] = new Goto(); // goto 
	codes[200] = new Goto(); // goto_w 
	codes[145] = new SimpleOpcode(type_int, type_int); // i2b
	codes[146] = new SimpleOpcode(type_int, type_int); // i2c
	codes[135] = new SimpleOpcode(type_int, type_double); // i2d
	codes[134] = new SimpleOpcode(type_int, type_float); // i2f
	codes[133] = new SimpleOpcode(type_int, type_long); // i2l
	codes[147] = new SimpleOpcode(type_int, type_int); // i2s
	codes[96] = new SimpleOpcode(intint, type_int); // iadd
	codes[46] = new ArrayLoad(); // SimpleOpcode(objint, type_int); // iaload
	codes[126] = new SimpleOpcode(intint, type_int); // iand
	codes[79] = new ArrayStore(Type.INT); // SimpleOpcode(objintint, empty); // iastore
	codes[2] = new SimpleOpcode(empty, type_int); // iconst_m1
	codes[3] = new SimpleOpcode(empty, type_int); // iconst_0
	codes[4] = new SimpleOpcode(empty, type_int); // iconst_1
	codes[5] = new SimpleOpcode(empty, type_int); // iconst_2
	codes[6] = new SimpleOpcode(empty, type_int); // iconst_3
	codes[7] = new SimpleOpcode(empty, type_int); // iconst_4
	codes[8] = new SimpleOpcode(empty, type_int); // iconst_5
	codes[108] = new SimpleOpcode(intint, type_int); // idiv
	codes[165] = new Branch(objobj); // if_acmpeq
	codes[166] = new Branch(objobj); // if_acmpne
	codes[159] = new Branch(intint); // if_icmpeq
	codes[160] = new Branch(intint); // if_icmpne
	codes[161] = new Branch(intint); // if_icmplt
	codes[162] = new Branch(intint); // if_icmpge
	codes[163] = new Branch(intint); // if_icmpgt
	codes[164] = new Branch(intint); // if_icmple
	codes[153] = new Branch(type_int); // ifeq
	codes[154] = new Branch(type_int); // ifne
	codes[155] = new Branch(type_int); // iflt
	codes[156] = new Branch(type_int); // ifge
	codes[157] = new Branch(type_int); // ifgt
	codes[158] = new Branch(type_int); // ifle
	codes[199] = new Branch(type_obj); // ifnonnull
	codes[198] = new Branch(type_obj); // ifnull
	codes[132] = new SimpleOpcode(empty, empty); // iinc
	codes[21] = new Load(Type.INT); // iload
	codes[26] = new Load(Type.INT); // iload_0
	codes[27] = new Load(Type.INT); // iload_1
	codes[28] = new Load(Type.INT); // iload_2
	codes[29] = new Load(Type.INT); // iload_3
	codes[104] = new SimpleOpcode(intint, type_int); // imul
	codes[116] = new SimpleOpcode(type_int, type_int); // ineg
	codes[193] = new SimpleOpcode(type_obj, type_int); // CLASSREF, type_obj, type_int, 2); // instanceof
	codes[185] = new Invoke(); // invokeinterface
	codes[183] = new Invoke(); // invokespecial
	codes[184] = new Invoke(); // invokestatic
	codes[182] = new Invoke(); // invokevirtual
	codes[128] = new SimpleOpcode(intint, type_int); // ior
	codes[112] = new SimpleOpcode(intint, type_int); // irem
	codes[172] = new Return(Type.INT); // ireturn
	codes[120] = new SimpleOpcode(intint, type_int); // ishl
	codes[122] = new SimpleOpcode(intint, type_int); // ishr
	codes[54] = new Store(Type.INT); // istore 
	codes[59] = new Store(Type.INT); // istore_0
	codes[60] = new Store(Type.INT); // istore_1
	codes[61] = new Store(Type.INT); // istore_2
	codes[62] = new Store(Type.INT); // istore_3
	codes[100] = new SimpleOpcode(intint, type_int); // isub
	codes[124] = new SimpleOpcode(intint, type_int); // iushr
	codes[130] = new SimpleOpcode(intint, type_int); // ixor
	// codes[168] = new Jsr(); // SimpleOpcode(null, null); // jsr0, 1, 2);
	// codes[201] = new Jsr(); // SimpleOpcode(jsr_w0, 1, 4); // 
	codes[138] = new SimpleOpcode(type_long, type_double); // l2d
	codes[137] = new SimpleOpcode(type_long, type_float); // l2f
	codes[136] = new SimpleOpcode(type_long, type_int); // l2i
	codes[97] = new SimpleOpcode(longlong, type_long); // ladd
	codes[47] = new ArrayLoad(); // SimpleOpcode(objint, type_long); // laload
	codes[127] = new SimpleOpcode(longlong, type_long); // land
	codes[80] = new ArrayStore(Type.LONG); // objintlong); // lastore
	codes[148] = new SimpleOpcode(longlong, type_int); // lcmp
	codes[9] = new SimpleOpcode(empty, type_long); // lconst_0
	codes[10] = new SimpleOpcode(empty, type_long); // lconst_1
	codes[18] = new Ldc(); // SimpleOpcode(null, null); // ldcLDC, 1); // 
	codes[19] = new Ldc(); // SimpleOpcode(ldc_wLDC, 2); // 
	codes[20] = new Ldc(); // SimpleOpcode(ldc2_wLDC, 2); // 
	codes[109] = new SimpleOpcode(longlong, type_long); // ldiv
	codes[22] = new Load(Type.LONG); // lload
	codes[30] = new Load(Type.LONG); // lload_0
	codes[31] = new Load(Type.LONG); // lload_1
	codes[32] = new Load(Type.LONG); // lload_2
	codes[33] = new Load(Type.LONG); // lload_3
	codes[105] = new SimpleOpcode(longlong, type_long); // lmul
	codes[117] = new SimpleOpcode(type_long, type_long); // lneg
	codes[171] = new Switch(); // impleCode(lookupswitchSWITCH, type_int, empty, INST_LOOKUP); // 
	codes[129] = new SimpleOpcode(longlong, type_long); // lor
	codes[113] = new SimpleOpcode(longlong, type_long); // lrem
	codes[173] = new Return(Type.LONG); // lreturn
	codes[121] = new SimpleOpcode(longint, type_long); // lshl
	codes[123] = new SimpleOpcode(longint, type_long); // lshr
	codes[55] = new Store(Type.LONG); // lstore
	codes[63] = new Store(Type.LONG); // lstore_0
	codes[64] = new Store(Type.LONG); // lstore_1
	codes[65] = new Store(Type.LONG); // lstore_2
	codes[66] = new Store(Type.LONG); // lstore_3
	codes[101] = new SimpleOpcode(longlong, type_long); // lsub
	codes[125] = new SimpleOpcode(longint, type_long); // lushr
	codes[131] = new SimpleOpcode(longlong, type_long); // lxor
	codes[194] = new SimpleOpcode(type_obj, empty); // monitorenter
	codes[195] = new SimpleOpcode(type_obj, empty); // monitorexit
	codes[197] = new Multianewarray(); // multianewarrayNEW, -1, 1, 3); // 
	codes[187] = new New(); // new 
	codes[188] = new Newarray(); // newarrayNEW, 1, 1, 1); // 
	codes[0] = new SimpleOpcode(empty, empty); // nop
	codes[87] = new Pop(); // pop
	codes[88] = new Pop2(); // pop2
	codes[181] = new Field(true,false); // putfield
	codes[179] = new Field(true,true);  // putstatic 
	// codes[169] = new SimpleOpcode(retRET, 0, 0, 1); // 
	codes[177] = new Return(); // return
	codes[53] = new ArrayLoad(); // SimpleOpcode(objint, type_int); // saload
	codes[86] = new ArrayStore(Type.SHORT); // SimpleOpcode(objintint, empty); // sastore
	codes[17] = new SimpleOpcode(empty, type_int); // sipush
	codes[95] = new Swap(); // swap
	codes[170] = new Switch(); // impleCode(tableswitchSWITCH, type_int, empty, INST_TABLE); // 
	// codes[196] = new SimpleOpcode(wideWIDE, 1, 0, INST_WIDE); // 
	return codes;
}
boolean isValid(Method m) {
	if (m.getName().equals("<init>")) return false;
	if (m.getName().equals("<clinit>")) return false;
	if (m.isNative() || m.isAbstract()) return false;
	return true;
}
}
