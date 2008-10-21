package core.brakes.de.fub.bytecode.generic;

/**
 * Denotes an unparameterized instruction to produce a value on top of the stack,
 * such as ILOAD, LDC, SIPUSH, DUP, ICONST, etc.
 *
 * @version $Id: PushInstruction.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>

 * @see ILOAD
 * @see ICONST
 * @see LDC
 * @see DUP
 * @see SIPUSH
 * @see GETSTATIC
 */
public interface PushInstruction {}