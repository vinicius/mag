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

import core.brakes.be.ac.kuleuven.cs.ttm.transformer.dataflow.Registry;
import core.brakes.de.fub.bytecode.generic.LineNumberGen;
import core.brakes.de.fub.bytecode.generic.LocalVariableGen;
import core.brakes.de.fub.bytecode.generic.MethodGen;

public class StripDebugRewriter implements Rewriter {

  public void rewrite(MethodGen m, Registry reg) {
	LineNumberGen[] l = m.getLineNumbers();
	for (int i = 0; i < l.length; i++) {
		m.removeLineNumber(l[i]);
	}
	LocalVariableGen[] v = m.getLocalVariables();
	for (int i = 0; i < v.length; i++) {
		m.removeLocalVariable(v[i]);
	}
  }
}
