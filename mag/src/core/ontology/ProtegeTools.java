package core.ontology;

/* This is file is generated by the ontology bean generator.  
DO NOT EDIT, UNLESS YOU ARE REALLY REALLY SURE WHAT YOU ARE DOING! */

/** file: ProtegeTools
 * @author ontology bean generator (Acklin BV) 
 * @version 2007/02/20, 11:28:12
 */

import jade.util.leap.Collection;
import jade.util.leap.Iterator;

public class ProtegeTools {

  public static void firstDown(StringBuffer buff) {
    buff.setCharAt(0, Character.toLowerCase(buff.charAt(0)));
  }

  public static String firstDown(String str) {
    StringBuffer buff = new StringBuffer(str);
    firstDown(buff);
    return buff.toString();
  }

  public static void firstUpper(StringBuffer buff) {
    buff.setCharAt(0, Character.toUpperCase(buff.charAt(0)));
  }

  public static String firstUpper(String str) {
    StringBuffer buff = new StringBuffer(str);
    firstUpper(buff);
    return buff.toString();
  }
  /**
   *  Convert Strings to a valid Java identifiers by replacing ' ' and '-' with
   *  an underscore '_'
   *
   *@param  buff  Description of the Parameter
   */
  public static void toJavaString(StringBuffer buff) {
    for (int i = 0; i < buff.length(); i++) {
      char c = buff.charAt(i);
      switch (c) {
          case ' ':
            buff.setCharAt(i, '_');
            break;
          case '-':
            buff.setCharAt(i, '_');
            break;
          default:
      }
    }
  }

  public static String toJavaString(String key) {
    StringBuffer buff = new StringBuffer(key);
    toJavaString(buff);
    return buff.toString();
  }

  public static void toJavaStyleString(StringBuffer buff) {
    // remove spaces, dashes etc
    toJavaString(buff);
    // capitalise letters after a '_'


/*    for (int i = 0; i < buff.length(); i++) {
      char c = buff.charAt(i);
      switch (c) {
          case '_':
            buff.deleteCharAt(i);
            // after deletion, char i+1 is at position i
            buff.setCharAt(i, Character.toUpperCase(buff.charAt(i)));
            break;
          default:
      }
    }
*/
  }

  public static String toJavaStyleString(String key) {
    StringBuffer buff = new StringBuffer(key);
    toJavaStyleString(buff);
    return buff.toString();
  }

  public static String toJavaVariableStyleString(String var) {
    StringBuffer buff = new StringBuffer(var);
    toJavaVariableStyleString(buff);
    return buff.toString();
  }

  public static void toJavaVariableStyleString(StringBuffer buff) {
    // remove incompatible characters
    toJavaStyleString(buff);

    // conform to Java variable style:
    // MyHAP->myHAP, AIDSchema -> aidSchema, Dir->dir, HomeDir->homeDir

    // put down 1st char
    buff.setCharAt(0, Character.toLowerCase(buff.charAt(0)));
    // walk along and put down i char if i+1 is uppercase
    boolean pastFirstWork = false;
    for (int i = 0; i < buff.length(); i++) {
      // i+1 doesn't exist
      if (i == buff.length() - 1) {
        break;
      }
      // i and i+1 are lowercase so we should stop processing now
      if (Character.isLowerCase(buff.charAt(i)) && Character.isLowerCase(buff.charAt(i + 1))) {
        break;
      }

      if (Character.isUpperCase(buff.charAt(i + 1))) {
        buff.setCharAt(i, Character.toLowerCase(buff.charAt(i)));
      }
    }

  }

  public interface ProtegeOntology {
    //        public String getJADENameFromSlotName(SlotHolder slot);
    public SlotHolder getSlotNameFromJADEName(SlotHolder jadeSlot);
    //        public String getClassNameFromJADEName(String jadeName);
    //        public String getJADENameFromClassName(String className);
  }

  public static void copyCollection(final Collection from, final Collection to) {
    for (Iterator i = from.iterator(); i.hasNext(); ) {
      to.add(i.next());
     }
    }
}
