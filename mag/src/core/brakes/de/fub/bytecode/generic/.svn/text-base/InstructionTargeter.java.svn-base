package core.brakes.de.fub.bytecode.generic;

/**
 * Denote that a class targets InstructionHandles within an InstructionList. Namely
 * the following implementers:
 *
 * @see BranchHandle
 * @see LocalVariableGen
 * @see CodeExceptionGen
 * @version $Id: InstructionTargeter.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public interface InstructionTargeter {
  public boolean containsTarget(InstructionHandle ih);  
  public void updateTarget(InstructionHandle old_ih, InstructionHandle new_ih);  
}