package core.brakes.de.fub.bytecode.util;

import java.util.Vector;

import core.brakes.de.fub.bytecode.classfile.JavaClass;

/** 
 * Utility class implementing a (typesafe) queue of JavaClass
 * objects.
 *
 * @version $Id: ClassQueue.java,v 1.1 2006-03-07 17:45:05 rafaelf Exp $
 * @author <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A> 
 * @see Vector
*/
public class ClassQueue {
  protected int    left = 0;
  private   Vector vec  = new Vector();

  public JavaClass dequeue()                { return (JavaClass)vec.elementAt(left++); }  
  public boolean   empty()                  { return vec.size() <= left; }  
  public void      enqueue(JavaClass clazz) { vec.addElement(clazz); }  
}