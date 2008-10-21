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

import java.util.Enumeration;
import java.util.Vector;

import core.brakes.de.fub.bytecode.generic.ArrayType;
import core.brakes.de.fub.bytecode.generic.BasicType;
import core.brakes.de.fub.bytecode.generic.ObjectType;
import core.brakes.de.fub.bytecode.generic.ReferenceType;
import core.brakes.de.fub.bytecode.generic.Type;

public class VirtualReferenceType extends VirtualType {
	Vector commonSuperClasses;
	
	// create null type
	public VirtualReferenceType() {
		commonSuperClasses = new Vector();
	}
	
	/**
	 * ReferenceType constructor comment.
	 */
	public VirtualReferenceType(ReferenceType t) {
		commonSuperClasses = new Vector();
		addToSuperClasses(typeToClass(t));
	}
	
	void accept(FrameVisitor v, int i) {
		if (commonSuperClasses.isEmpty()) {
			v.visitNull(i);
		} else {
			v.visitReferenceType((ReferenceType) getRealType(), i);
		}
	}
	
	void accept(StackVisitor v) {
		if (commonSuperClasses.isEmpty()) {
			v.visitNull();
		} else {
			v.visitReferenceType((ReferenceType) getRealType());
		}
	}
	
	private void addToSuperClasses(Class c) {
		if (c == null)
			return;
		if (!commonSuperClasses.contains(c))
			commonSuperClasses.addElement(c);
	}
	
	/**
	 * Returns whether the reference type has changed as a result of the merge.
	 */
	public void assignComponent(VirtualType vt) {
		if (vt instanceof VirtualReferenceType) {
			VirtualReferenceType t = (VirtualReferenceType) vt;
			Enumeration e = t.commonSuperClasses.elements();
			while (e.hasMoreElements()) {
				narrow(getArrayClass((Class) e.nextElement()));
			}
		} else {
			// for well-typed programs, nothing has to be done in this case
			// VirtualPrimitiveType t = (VirtualPrimitiveType) vt;
			// narrow(getArrayClass(typeToClass(t.getBasicType())));
		}
	}
	
	private Class getArrayClass(Class component) {
		try {
			String cName;
			if (component.isPrimitive()) {
				throw new VerifyError("Should not happen.");
			}
			if (component.isArray()) {
				cName = "[" + component.getName();
			} else {
				cName = "[L" + component.getName() + ";";
			}
			return Class.forName(cName);
		} catch (ClassNotFoundException e) {
			throw new VerifyError(e.getMessage());
		}
	}
	
	public VirtualType getComponentType() {
		VirtualReferenceType t = new VirtualReferenceType();
		for (int i = 0; i < commonSuperClasses.size(); i++) {
			Class c = (Class) commonSuperClasses.elementAt(i);
			if (c.isArray()) {
				Class compClass = c.getComponentType();
				if (compClass.isPrimitive()) {
					return new VirtualPrimitiveType(compClass);
				}
				t.addToSuperClasses(compClass);
			}
		}
		if (t.commonSuperClasses.isEmpty())
			throw new VerifyError(this +" is not an ArrayType !");
		return t;
	}
	
	public Type getRealType() {
		Class c = (Class) commonSuperClasses.firstElement();
		//int dim = 0;
		if(c.isArray()) {
			return Type.getType(c.getName().replace('.', '/'));
		} else {
			return new ObjectType(c.getName());
		}
	}
	
	public int getSize() {
		return 1;
	}
	
	/**
	 * Returns whether the reference type has changed as a result of the merge.
	 */
	public boolean merge(VirtualType vt) {
		if (commonSuperClasses.isEmpty()) {
			commonSuperClasses = (Vector) (((VirtualReferenceType) vt).commonSuperClasses).clone();
			return !commonSuperClasses.isEmpty();
		}
		if (vt instanceof VirtualReferenceType) {
			VirtualReferenceType t = (VirtualReferenceType) vt;
			boolean hasChanged = false;
			Enumeration e = t.commonSuperClasses.elements();
			while (e.hasMoreElements()) {
				hasChanged |= narrow((Class) e.nextElement());
			}
			return hasChanged;
		}
		throw new VerifyError("Cannot merge " + vt + " with " + this);
	}
	
	private boolean narrow(Class c) {
		boolean hasChanged = false;
		for (int i = 0; i < commonSuperClasses.size(); i++) {
			Class superClass = (Class) commonSuperClasses.elementAt(i);
			if (!superClass.isAssignableFrom(c)) {
				hasChanged = true;
				commonSuperClasses.removeElementAt(i);
				addToSuperClasses(superClass.getSuperclass());
				Class[] interfaces = superClass.getInterfaces();
				for (int j = 0; j < interfaces.length; j++)
					addToSuperClasses(interfaces[j]);
				if (superClass.isArray()) {
					Class component = superClass.getComponentType();
					if (component.getSuperclass() != null) {
						addToSuperClasses(getArrayClass(component.getSuperclass()));
						interfaces = component.getInterfaces();
						for (int j = 0; j < interfaces.length; j++)
							addToSuperClasses(getArrayClass(interfaces[j]));
					}
				}
			}
		}
		return hasChanged;
	}
	
	public String toString() {
		return commonSuperClasses.toString();
	}
	
	private Class typeToClass(ReferenceType t) {
		try {
			if (t instanceof ObjectType) {
				return Class.forName(((ObjectType) t).getClassName());
			} else {
				ArrayType at = (ArrayType) t;
				int dimensions = at.getDimensions();
				String str = "";
				for (int i = 1; i <= dimensions; i++) {
					str += "[";
				}
				Type bt = at.getBasicType();
				if (bt instanceof BasicType) {
					str += bt.getSignature();
				} else {
					ObjectType ot = (ObjectType) bt;
					str += "L" + ot.getClassName() + ";";
				}
				return Class.forName(str);
			}
		} catch (ClassNotFoundException e) {
			//System.out.println("Thougt that would happen");
			throw new VerifyError(e.getMessage());
		}
	}
}
