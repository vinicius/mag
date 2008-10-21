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

// This stack stores types of size 1. If a type of size 2 (read long or double)
// is stored on the stack, it is the responsibility of the user to ensure that
// this type is followed by a dummy type.
// The reason why a stack doesn't hide this complexity is because several JVM
// instructions (pop, dup, ...) consider stackelements to be of size 1.
public class Stack {

  VirtualType[] vmType;

  int top;

  // enumerates elements in the same order as if they would have been popped
  class StackEnumeration implements Enumeration {
	int i;
	StackEnumeration() {
		i = top;
	}
	public boolean hasMoreElements() {
		return i > 0;
	}
	public Object nextElement() {
		if ((i > 1) && (vmType[i - 2].getSize() == 2))
			i--;
		return vmType[--i];
	}
  }
  
  public Stack() {
	vmType = new VirtualType[10];
	top = 0;
  }  
  
  public Stack(Stack s) {
	vmType = new VirtualType[s.vmType.length];
	System.arraycopy(s.vmType, 0, vmType, 0, s.vmType.length);
	top = s.top;
  }  
  
  public void accept(StackVisitor v) {
	for (int i = top - 1; i >= 0; i--) {
		if ((i > 0) && (vmType[i - 1].getSize() == 2))
			i--;
		vmType[i].accept(v);
	}
  }
  
  public Enumeration getElements() {
	return new StackEnumeration();
  }  
  
  // this is the number of elements on the stack but does not include
  // VMType.UNKNOWN elements
  public int getRealSize() {
	int size = 0;
	Enumeration e = getElements();
	while (e.hasMoreElements()) {
	  size++;
	  e.nextElement();
	}
	return size;
  }  
  
  public boolean merge(Stack s) {
	boolean hasChanged = false;
	if (s.top != top)
		throw new VerifyError();
	for (int i = 0; i < top; i++) {
		hasChanged |= vmType[i].merge(s.vmType[i]);
		if (vmType[i].getSize() == 2)
			i++;
	}
	return hasChanged;
  }
  
  public VirtualType peek() {
	return vmType[top-1];
  }  
  
  public VirtualType pop() {
	return vmType[--top];
  }  
  
  public void push(VirtualType vt) {
	if (top == vmType.length) {
	  VirtualType[] newT = new VirtualType[vmType.length+10];
	  System.arraycopy(vmType, 0, newT, 0, vmType.length);
	  vmType = newT;
	}
	vmType[top++] = vt;
  }      
  
  public void replace(VirtualType vOld, VirtualType vNew) {
	for (int i = 0; i < top; i++) {
		if (vmType[i] == vOld) {
			vmType[i] = vNew;
		}
	}
  }
  
  public String toString() {
	String s = "[ ";
	Enumeration e = getElements();
	while (e.hasMoreElements()) {
	  Object obj = e.nextElement();
	  if (e!=null)
	  s += obj + " ";
	  else
	  s += "NULL" + " ";
	}
	return s + "]";
  }

  public void removeFirstUninitializedValues() {
    /*if (vmType[top-1] instanceof VirtualUninitializedType) {
	if (((VirtualUninitializedType)vmType[top-1]).next()) {
	  pop();
	  pop();
	} else {
          pop();
	}
      }
      else {*/
    int value = top - 1;
    boolean stop = false;
    while (value >= 0 && !stop) {
      if (vmType[value] instanceof VirtualUninitializedType) {
	stop = true;
	int count;
	if (((VirtualUninitializedType)vmType[value]).next())
	  count=2;
	else
	  count=1;
	for (int i=value - count + 1; i <= top - count - 1; i++)
	  vmType[i] = vmType[i+count];
	top= top-count;
      }
      value--;
    }
  }

}
