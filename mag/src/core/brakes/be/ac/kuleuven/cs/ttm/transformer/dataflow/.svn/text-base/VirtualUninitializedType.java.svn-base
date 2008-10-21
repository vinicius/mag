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

import core.brakes.de.fub.bytecode.generic.InstructionHandle;
import core.brakes.de.fub.bytecode.generic.ObjectType;
import core.brakes.de.fub.bytecode.generic.Type;

public class VirtualUninitializedType extends VirtualType {
  ObjectType myType;
  
  InstructionHandle newIns; // newIns is null if myType refers 
  			    // to this in a constructor

  boolean next; // if true a clone of this is on the next stack frame

  public VirtualUninitializedType(ObjectType t, InstructionHandle ins) {
    myType = t;
    newIns = ins;
    next = false;
  }
  
  void accept(FrameVisitor v, int i) {
    throw new VerifyError("Local variables can not be uninitialized.");
  }
  
  void accept(StackVisitor v) {
	v.visitUninitializedType((ObjectType)getRealType(),newIns);
  }
  
  /**
   * getRealType method comment.
   */
  public Type getRealType() {
    return myType;
  }
  
  /**
   * getSize method comment.
   */
  public int getSize() {
    return 1;
  }
  
  public void checkNext() {
    next=true;
  }
  
  /**
   * returs true when a clone of this virtualtype is on the current stack
   */
  
  public boolean next() {
    return next;
  }
 
  /**
   * Niet heel correct omdat we eigenlijk ook moeten checken op gelijkheid
   * tussen de overeenkomstige gelijke uninit types op de stack.
   */
  public boolean merge(VirtualType vt) {
    if (vt instanceof VirtualUninitializedType) {
      if (vt.getRealType().equals(myType))
	return false;
      else return true;
    }
    throw new VerifyError("Cannot merge with UninitializedType " + this);
  }
 
  public String toString() {
    return "<uninit " + myType.getClassName() + ">";
  }
}












