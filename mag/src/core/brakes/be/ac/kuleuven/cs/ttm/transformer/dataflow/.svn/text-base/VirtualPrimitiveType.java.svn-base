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
import core.brakes.de.fub.bytecode.generic.Type;

class VirtualPrimitiveType extends VirtualType {

BasicType myType;

VirtualPrimitiveType(BasicType t) {
	myType = t;
}

// Here we can change some orders by doing a statistical analysis
// on the population of primitive types
VirtualPrimitiveType(Class c) {
	if (c == double.class)
		myType = (BasicType) Type.DOUBLE;
	else
		if (c == float.class)
			myType = (BasicType) Type.FLOAT;
		else
			if (c == long.class)
				myType = (BasicType) Type.LONG;
			else
				myType = (BasicType) Type.INT;
}

void accept(FrameVisitor v, int i) {
	v.visitBasicType(myType, i);
}

void accept(StackVisitor v) {
	v.visitBasicType(myType);
}

public BasicType getBasicType() {
	return myType;
}

public Type getRealType() {
	return myType;
}

public int getSize() {
	return myType.getSize();
}  

public boolean merge(VirtualType vt) {
	if (vt.getRealType().equals(myType))
		return false;
	throw new VerifyError("Cannot merge " + vt + " with " + this);
}

public String toString() {
	return myType.toString();
}  
}
