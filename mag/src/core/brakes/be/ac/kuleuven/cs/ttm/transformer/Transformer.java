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

package core.brakes.be.ac.kuleuven.cs.ttm.transformer;

import core.brakes.be.ac.kuleuven.cs.ttm.transformer.dataflow.Registry;
import core.brakes.be.ac.kuleuven.cs.ttm.transformer.dataflow.analyzer.Analyzer;
import core.brakes.be.ac.kuleuven.cs.ttm.transformer.rewriter.InvokeRewriter;
import core.brakes.be.ac.kuleuven.cs.ttm.transformer.rewriter.InvokespecialRewriter;
import core.brakes.be.ac.kuleuven.cs.ttm.transformer.rewriter.Rewriter;
import core.brakes.be.ac.kuleuven.cs.ttm.transformer.rewriter.StripDebugRewriter;
import core.brakes.de.fub.bytecode.classfile.ClassParser;
import core.brakes.de.fub.bytecode.classfile.JavaClass;
import core.brakes.de.fub.bytecode.classfile.Method;
import core.brakes.de.fub.bytecode.generic.ConstantPoolGen;
import core.brakes.de.fub.bytecode.generic.MethodGen;


public class Transformer {
    public static boolean currentMethodStatic;

    public static boolean manualInstrumentation = false;
    
    public static final String contextAttributeName = "tmpContext";
    
    public static void main(String arg[]) {
	for (int i = 0; i < arg.length; i++) {
	    if (arg[i].equals("-manual")) {
		manualInstrumentation = true;

		break;
	    }
	}
	
	for (int i = 0; i < arg.length; i++) {
	    if (!arg[i].equals("-manual")) {
		try {
		    JavaClass jClass = new ClassParser(arg[i]).parse();
		    ConstantPoolGen cpGen = new ConstantPoolGen(jClass.getConstantPool());
		    Rewriter r1 = new StripDebugRewriter();
		    Rewriter r2 = new InvokespecialRewriter();
		    Rewriter r3 = new InvokeRewriter(jClass.getClassName(), cpGen);
		    Analyzer a = new Analyzer();
		    
		    Method[] method = jClass.getMethods();
		    for (int j = 0; j < method.length; j++) {
			currentMethodStatic = method[j].isStatic();
			MethodGen mGen = new MethodGen(method[j], jClass.getClassName(), cpGen);
			if (!mGen.isAbstract() && !mGen.isStatic()) {
			    Registry reg = a.analyze(mGen);
			    r1.rewrite(mGen, reg);
			    r2.rewrite(mGen, reg);
			    r3.rewrite(mGen, reg);
			    
			    mGen.setMaxStack();
			    mGen.setMaxLocals();
			    method[j] = mGen.getMethod();
			}
		    }
		    
		    /*//Reanalyze each method
		      for (int j = 0; j < method.length; j++) {
		      currentMethodStatic = method[j].isStatic();
		      MethodGen mGen = new MethodGen(method[j], jClass.getClassName(), cpGen);
		      if (!mGen.isAbstract()) {
		      Registry reg = a.analyze(mGen);
		      r3.rewrite(mGen, reg);
		      mGen.setMaxStack();
		      mGen.setMaxLocals();
		      method[j] = mGen.getMethod();
		      }
		      }*/	      
		    
		    jClass.setConstantPool(cpGen.getFinalConstantPool());
		    jClass.dump(arg[i]); // + ".rewritten");
		} catch (java.io.IOException e) {
		    e.printStackTrace();
		}
	    }
	}
    }
    
    public void register(MethodVisitor m) {
    }
    
    public void translate(JavaClass jc) {
    }
}
