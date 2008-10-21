package core.brakes.de.fub.bytecode.generic;

/** 
 * Thrown on internal errors. Extends RuntimeException so it hasn't to be declared
 * in the throws clause every time.
 *
 * @version $Id: ClassGenException.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class ClassGenException extends RuntimeException {
  public ClassGenException() { super(); }  
  public ClassGenException(String s) { super(s); }  
}