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

public class Test {
/**
 * Starts the application.
 * @param args an array of command-line arguments
 */
public static void main(java.lang.String[] args) {
	int[] ia = new int[1];
	int[][] iaa = new int[1][1];
	Object[] oa = new Object[1];
	Object[][] oaa = new Object[1][1];
	System.out.println("int => " + int.class.getName());
	System.out.println("int[] => " + ia.getClass().getName());
	System.out.println("int[][] => " + iaa.getClass().getName());
	System.out.println("Object => " + Object.class.getName());
	System.out.println("Object[] => " + oa.getClass().getName());
	System.out.println("Object[][] => " + oaa.getClass().getName());
	try {
		Class c = Class.forName("[Ljava.lang.String;");
		System.out.println(c.getName());
	} catch (Exception e) {
		e.printStackTrace();
	}
}
}
