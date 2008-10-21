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

package core.brakes.be.ac.kuleuven.cs.ttm.transformer.dataflow;

import core.brakes.de.fub.bytecode.generic.BasicType;
import core.brakes.de.fub.bytecode.generic.ReferenceType;
import core.brakes.de.fub.bytecode.generic.Type;

public abstract class VirtualType {

abstract void accept(FrameVisitor v, int i);

abstract void accept(StackVisitor v);

public static VirtualType create(Type t) {
	if (t == Type.NULL)
		return new VirtualReferenceType();
	if (t instanceof BasicType)
		return new VirtualPrimitiveType((BasicType) t);
	if (t instanceof ReferenceType)
		return new VirtualReferenceType((ReferenceType) t);
	throw new VerifyError();
}

public abstract Type getRealType();

// return the number of entries this type takes on the stack/frame
// 2 for double/long, 1 for the other types
public abstract int getSize();

abstract public boolean merge(VirtualType vt);

}
