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

package core.brakes.be.ac.kuleuven.cs.ttm.transformer.rewriter;





import java.util.Vector;

import core.brakes.be.ac.kuleuven.cs.ttm.transformer.Transformer;
import core.brakes.be.ac.kuleuven.cs.ttm.transformer.Util;
import core.brakes.be.ac.kuleuven.cs.ttm.transformer.dataflow.Registry;
import core.brakes.be.ac.kuleuven.cs.ttm.transformer.dataflow.Stack;
import core.brakes.de.fub.bytecode.Constants;
import core.brakes.de.fub.bytecode.generic.ConstantPoolGen;
import core.brakes.de.fub.bytecode.generic.GETSTATIC;
import core.brakes.de.fub.bytecode.generic.GOTO;
import core.brakes.de.fub.bytecode.generic.ICONST;
import core.brakes.de.fub.bytecode.generic.IFEQ;
import core.brakes.de.fub.bytecode.generic.IFNE;
import core.brakes.de.fub.bytecode.generic.INVOKESTATIC;
import core.brakes.de.fub.bytecode.generic.INVOKEVIRTUAL;
import core.brakes.de.fub.bytecode.generic.InstructionConstants;
import core.brakes.de.fub.bytecode.generic.InstructionFactory;
import core.brakes.de.fub.bytecode.generic.InstructionHandle;
import core.brakes.de.fub.bytecode.generic.InstructionList;
import core.brakes.de.fub.bytecode.generic.InvokeInstruction;
import core.brakes.de.fub.bytecode.generic.MethodGen;
import core.brakes.de.fub.bytecode.generic.ObjectType;
import core.brakes.de.fub.bytecode.generic.POP;
import core.brakes.de.fub.bytecode.generic.PUSH;
import core.brakes.de.fub.bytecode.generic.TABLESWITCH;
import core.brakes.de.fub.bytecode.generic.Type;


public class InvokeRewriter implements Rewriter {
	private MethodGen mGen;
	
	private Registry registry;
	
	private InstructionFactory insFactory;
	
	private String className = "";
	
	private ConstantPoolGen cp;
	
	
	public InvokeRewriter(String className_, ConstantPoolGen cp_) {
		className = className_;
		cp = cp_;
	}  
	
	private boolean isValid(MethodGen m) {
		if (m.getMethodName().equals("<init>")) return false;
		if (m.getMethodName().equals("<clinit>")) return false;
		if (m.isNative() || m.isAbstract()) return false;
		return true;
	}
	
	private Stack removeReturnType(Stack s, InstructionHandle ins) {
		InvokeInstruction inv = (InvokeInstruction) ins.getInstruction();
		Type t = Util.getReturnType(mGen.getConstantPool().getConstantPool(), inv.getIndex());
		if (!t.equals(Type.VOID)) {
			s = new Stack(s);
			s.pop();
			if (t.getSize() == 2) {
				s.pop();
			}
		}
		return s;
	}
	
	private InstructionList restoreContext(InstructionHandle ins) {
		RewriteFactory f = RewriteFactory.getInstance();
		InstructionList insList = new InstructionList();
		StackRestorer ss = new StackRestorer(className, insFactory);
		FrameRestorer fs = new FrameRestorer(className, insFactory);
		
		removeReturnType(registry.getStack(ins.getNext()),ins).accept(ss);
		registry.getFrame(ins.getNext()).accept(fs);
		insList.append(fs.getInstructionList());
		insList.append(ss.getInstructionList());
		
		InvokeInstruction inv = (InvokeInstruction) ins.getInstruction();
		
		if (!(inv instanceof INVOKESTATIC)) {
			insList.append(InstructionFactory.createLoad (new ObjectType(className),0));
			insList.append (insFactory.createGetField(className, Transformer.contextAttributeName, new ObjectType(f.getContextClass())));
			
			insList.append(insFactory.createInvoke(f.getContextClass(), f.getPopMethod() + "This", Type.OBJECT, Type.NO_ARGS, Constants.INVOKEVIRTUAL));
			
			insList.append(insFactory.createCast(Type.OBJECT, Util.getObjectType(mGen.getConstantPool().getConstantPool(), inv.getIndex())));
			
		}
		
		InstructionHandle newIns = ins;
		
		Type[] pTypes = Util.getParamTypes(mGen.getConstantPool().getConstantPool(), inv.getIndex());
		
		int j = 0;
		
		for (j = 0; j < pTypes.length - 1; j++) {
			insList.append(new NULLCONST(pTypes[j]));
		}
		
		if (newIns.getPrev().getInstruction().toString().startsWith("iload") ||
				newIns.getPrev().getInstruction().toString().startsWith("fload") ||
				newIns.getPrev().getInstruction().toString().startsWith("dload") ||
				newIns.getPrev().getInstruction().toString().startsWith("lload") ) {
			
			newIns = newIns.getPrev();
		} else {
			if (pTypes.length > 0) {
				insList.append (new NULLCONST(pTypes[pTypes.length - 1]));
			}
		}

		InstructionHandle ih = insList.append(new GOTO(newIns));

		InstructionList il = new InstructionList();

		il.insert (insFactory.createPutField(className, "restoring", Type.BOOLEAN));
		il.insert (new ICONST(0));
		
		il.insert (InstructionFactory.createLoad (new ObjectType(className),0));
		
		il.insert (new IFNE(ih));
		
		il.insert (insFactory.createInvoke(className, "notLastStackInContext", Type.BOOLEAN, Type.NO_ARGS, Constants.INVOKEVIRTUAL));


		il.insert (InstructionFactory.createLoad (new ObjectType(className),0));
		int currentTime = cp.addMethodref("java.lang.System","currentTimeMillis", "()J");
		
		il.insert (insFactory.createPutField(className, "lastCheckpointTimestamp", Type.LONG));

		il.insert(new INVOKESTATIC(currentTime));
		
		il.insert (InstructionFactory.createLoad (new ObjectType(className),0));

		il.insert (insFactory.createPutField(className, "switching", Type.BOOLEAN));
		il.insert (new ICONST(0));
		
		il.insert (InstructionFactory.createLoad (new ObjectType(className),0));
		
		il.insert (insFactory.createPutField(className, "stopping", Type.BOOLEAN));
		il.insert (new ICONST(0));
		
		il.insert (InstructionFactory.createLoad (new ObjectType(className),0));

		insList.insert (ih, il);
		
		return insList;
	}
	
	public void rewrite(MethodGen m, Registry reg) {
		RewriteFactory f = RewriteFactory.getInstance();
		if (!isValid(m)) return;
		insFactory = new InstructionFactory(m.getConstantPool());
		Vector invokeIns = new Vector();
		int count = 0;
		mGen = m;
		registry = reg;
		InstructionList insList = m.getInstructionList();
		InstructionHandle firstIns = insList.getStart();
		InstructionHandle ins = firstIns;
		
		InstructionList rList = insList;
		while (ins != null) {
			InstructionHandle next = ins.getNext();
			
			if (rewriteable(ins) && !Transformer.manualInstrumentation) {
				rList = restoreContext(ins);
				
				InstructionHandle savedIns = ins.getNext();
				
				insList.append(ins, saveContext(ins, count++));
				
				if (savedIns != null && savedIns.getInstruction().toString().indexOf("store") > 0) {
					next = next.getNext();
					insList.move (savedIns, ins);
				}
				
				invokeIns.addElement(rList.getStart());
				insList.insert(firstIns, rList);
			}
			ins = next;
		}
		
		if (count > 0) {
			InstructionHandle[] tableTargets = new InstructionHandle[count];
			int[] match = new int[count];
			for (int i = 0; i < count; i++)
				match[i] = i;
			invokeIns.copyInto(tableTargets);
			
			insList.insert(InstructionFactory.createLoad (new ObjectType(className),0));
			
			insList.insert(new TABLESWITCH(match, tableTargets, firstIns));
			
			insList.insert(insFactory.createInvoke(f.getContextClass(), f.getPopMethod() + "Int", Type.INT, Type.NO_ARGS, Constants.INVOKEVIRTUAL));
			insList.insert(insFactory.createGetField(className, Transformer.contextAttributeName, new ObjectType(f.getContextClass())));
			
			insList.insert(InstructionFactory.createLoad (new ObjectType(className),0));
						
 			insList.insert(new IFEQ(firstIns));
			
			insList.insert(insFactory.createInvoke(className, f.getRestoring(), Type.BOOLEAN, Type.NO_ARGS, Constants.INVOKEVIRTUAL));
			insList.insert(InstructionFactory.createThis());
			
			m.setMaxLocals(m.getMaxLocals() + 10);
		}
	}
	
	private boolean rewriteable(InstructionHandle ins) {
		int t = ins.getInstruction().getTag();
		boolean invokeSpecialSuper = false;
		if (t == InstructionList.INVOKESPECIAL) {
			InvokeInstruction ivs = (InvokeInstruction) ins.getInstruction();
			String mName = ivs.getMethodName(mGen.getConstantPool());
			invokeSpecialSuper = !mName.equals("<init>");
		}
		
		if ((t == InstructionList.INVOKEVIRTUAL) || (t == InstructionList.INVOKESTATIC) || (t == InstructionList.INVOKEINTERFACE) || invokeSpecialSuper ) {
			int index = ((InvokeInstruction) ins.getInstruction()).getIndex();
			String cName = core.brakes.be.ac.kuleuven.cs.ttm.transformer.Util.getObjectType(mGen.getConstantPool().getConstantPool(), index).getClassName();
			return !cName.startsWith("java.");
		}
		return false;
	}
	
	private InstructionList saveContext(InstructionHandle ins, int pc) {
		RewriteFactory f = RewriteFactory.getInstance();
		InstructionList insList;
		StackSaver ss = new StackSaver(className, insFactory);
		FrameSaver fs = new FrameSaver(className, insFactory);
		
		removeReturnType(registry.getStack(ins.getNext()), ins).accept(ss);
		registry.getFrame(ins.getNext()).accept(fs);
		// save stack
		insList = ss.getInstructionList();

		String methodName = "";
		
		try {
		    methodName = ((InvokeInstruction) ins.getInstruction()).getName(cp);
		} catch (Exception e) { }
		
		if (Transformer.manualInstrumentation) {
		    if (!methodName.equals (f.getCheckpoint())) {
			return insList;
		    }
		}
		
		//Indien het een niet-void methode betreft, moet de return-waarde eruit gegooid worden.
		// InvokeInstruction ivs = (InvokeInstruction) ins.getInstruction();
		//     Type t = Util.getReturnType(mGen.getConstantPool().getConstantPool(), ivs.getIndex());
		//     if (!t.equals(Type.VOID)) {
		//       if (t.getSize() == 1) insList.insert(new POP());
		//       else insList.insert(new POP2());
		//     }

		// InstructionHandle saved = ins;

		// save local variables
		insList.append(fs.getInstructionList());
		// save programcounter
		insList.append(InstructionFactory.createLoad (new ObjectType(className),0));

		InstructionHandle ih = ins.getNext();
		if (ih.getInstruction().toString().indexOf("store") > 0) {
			ih = ih.getNext();
		}

		insList.append (insFactory.createGetField(className, Transformer.contextAttributeName, new ObjectType(f.getContextClass())));
		insList.append(new PUSH(mGen.getConstantPool(), pc));
		insList.append(insFactory.createInvoke(f.getContextClass(), f.getPushMethod() + "Int", Type.VOID, new Type[] {Type.INT}, Constants.INVOKEVIRTUAL));
		
		// assigns the application's checkpoint in the 'myContext' attribute
		insList.append (InstructionConstants.THIS);
		insList.append (insFactory.createInvoke(className, "copyStableCheckpoint", Type.VOID, Type.NO_ARGS, Constants.INVOKEVIRTUAL));


		insList.append(InstructionConstants.THIS);

		InstructionList il          = new InstructionList();
		int             currentTime = cp.addMethodref("java.lang.System",
							      "currentTimeMillis", "()J");
		
		insList.append(new INVOKESTATIC(currentTime));

		insList.append (insFactory.createPutField(className, "lastCheckpointTimestamp", Type.LONG));
		
		
		// Flush `out` in file...
		il      = new InstructionList();
		int             out     = cp.addFieldref("java.lang.System", "out",
							 "Ljava/io/PrintStream;");
		int             println = cp.addMethodref("java.io.PrintStream", "flush",
							  "()V");
		
		il.append(new GETSTATIC(out));
		il.append(new INVOKEVIRTUAL(println));
		
		insList.append(il);

		// Flush `err` in file...
		il      = new InstructionList();
		out     = cp.addFieldref("java.lang.System", "err",
							 "Ljava/io/PrintStream;");
		println = cp.addMethodref("java.io.PrintStream", "flush",
							  "()V");
		
		il.append(new GETSTATIC(out));
		il.append(new INVOKEVIRTUAL(println));
		
		insList.append(il);

		// add isStopping test
		insList.append(InstructionConstants.THIS);
		insList.append(insFactory.createInvoke(className, f.getStopping(), Type.BOOLEAN, Type.NO_ARGS, Constants.INVOKEVIRTUAL));
		

		insList.append (new IFEQ(ih));

		// return NULL result
		insList.append(new NULLCONST(mGen.getReturnType()));
		insList.append(InstructionFactory.createReturn(mGen.getReturnType()));

		//if (!methodName.equals (f.getCheckpoint())) {
		    // These were necessary "to mark" the last position in the switching block
		    InstructionHandle switchLastIns = insList.append(new NULLCONST(mGen.getReturnType())); insList.append (new POP());
		    
		    // add isSwitching test
		    insList.insert (new IFEQ(switchLastIns));
		    
		    insList.insert(insFactory.createInvoke(className, f.getSwitching(), Type.BOOLEAN, Type.NO_ARGS, Constants.INVOKEVIRTUAL));
		    
		    insList.insert(InstructionConstants.THIS);
		//}
		
		return insList;
	}
}
