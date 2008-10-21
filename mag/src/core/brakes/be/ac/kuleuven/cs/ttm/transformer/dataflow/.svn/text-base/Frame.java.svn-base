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

// The content of the location following a type with size 2
// (DOUBLE or LONG) is ignored.
public class Frame {

  VirtualType[] vmType;

  public class FrameEnumeration implements Enumeration {
	int i;
	public FrameEnumeration() {
	  i = 0;
	  while ((i < vmType.length) && (vmType[i] == null)) i++;
	}
	public boolean hasMoreElements() {
	  return i < vmType.length;
	}
	public Object nextElement() {
	  VirtualType r = vmType[i];
	  i += r.getSize();
	  while ((i < vmType.length) && (vmType[i] == null)) i++;
	  return r;
	}
	public int getPosition() {
	  return i;
	}
  }

  public Frame() {
	vmType = new VirtualType[10];
  }  

  public Frame(Frame f) {
	vmType = new VirtualType[f.vmType.length];
	System.arraycopy(f.vmType, 0, vmType, 0, f.vmType.length);
  }  
  
  public void accept(FrameVisitor v) {
	for (int i = 0; i < vmType.length;) {
		if (vmType[i] != null) {
			vmType[i].accept(v,i);
			i += vmType[i].getSize();
		} else {
			i++;
		}
	}
  }
  
  public VirtualType get(int location) {
	return vmType[location];
  }  
  
  public FrameEnumeration getElements() {
	return new FrameEnumeration();
  }  
  
  public int getSize() {
	int size = 0;
	Enumeration fe = getElements();
	while (fe.hasMoreElements()) {
	  size++;
	  fe.nextElement();
	}
	return size;
  }  
  
  void grow() {
	VirtualType[] newT = new VirtualType[vmType.length+10];
	System.arraycopy(vmType, 0, newT, 0, vmType.length);
	vmType = newT;
  }  
  
  boolean isEmpty(int pos) {
	while (pos >= vmType.length) {
	  grow();
	}
	return vmType[pos] == null;
  }  
  
  public boolean merge(Frame f) {
	boolean hasChanged = false;
	FrameEnumeration myEnum = getElements();
	try {
		while (myEnum.hasMoreElements()) {
			int pos = myEnum.getPosition();
			VirtualType myVt = (VirtualType) myEnum.nextElement();
			VirtualType fVt = f.get(pos);
			try {
				hasChanged |= myVt.merge(fVt);
			} catch (Throwable e) {
			// means that f has no valid element at this position
				hasChanged = true;
				put(null, pos);
				if (myVt.getSize() > 1)
					put(null, pos + 1);
			}
		}
	} catch (java.util.NoSuchElementException e) {
		throw new VerifyError();
	}
	return hasChanged;
  }
  
  /**
   * This method was created in VisualAge.
   * @return int
   */
  public int nextFreeRegister() {
	FrameEnumeration fe = getElements();
	if (fe.hasMoreElements()) { 
		int pos=0;
		while (fe.hasMoreElements()) {
	 	 	pos = fe.getPosition();
	 	 	fe.nextElement();
		}
		VirtualType t = vmType[pos];
		if (t.getSize() > 1) return pos + 2;
		else return pos+1;
	}
	else return 0;
  }
  
  public void put(VirtualType vt, int location) {
	while (location >= vmType.length) {
	  grow();
	}
	vmType[location] = vt;
  }    
  
  public int store(VirtualType vt) {
	int i = 0;
	if (vt.getSize() == 2) {
	  while (true) {
		if (isEmpty(i)) {
		  if (isEmpty(i+1)) break;
		  i++;
		} else {
		  i += vmType[i].getSize();
		}
	  }
	} else {
	  for (i = 0; !isEmpty(i); i += vmType[i].getSize());
	}
	put(vt, i);
	return i;
  }  
  
  public String toString() {
	FrameEnumeration fe = getElements();
	String s = "[";
	while (fe.hasMoreElements()) s += "(" + fe.getPosition() +
	  "," + fe.nextElement() +")";
	return s + "]";
  }  
}
