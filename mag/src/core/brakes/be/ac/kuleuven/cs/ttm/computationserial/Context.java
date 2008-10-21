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

package core.brakes.be.ac.kuleuven.cs.ttm.computationserial;

public class Context implements java.io.Serializable {

  private int[] istack;
  private float[] fstack;
  private double[] dstack;
  private long[] lstack;
  private Object[] astack;
  private Object[] tstack; // contains the this variables
  private int iTop, fTop, dTop, lTop, aTop, tTop;

  static Context curContext;
public Context() {
	istack = new int[10];
	lstack = new long[5];
	dstack = new double[5];
	fstack = new float[5];
	astack = new Object[10];
	tstack = new Object[5];
}
  static public double curPopDouble() {
	return curContext.popDouble();
  }  
static public float curPopFloat() {
	return curContext.popFloat();
}
static public int curPopInt() {
	int i = curContext.popInt();
//	System.out.println("  curPopInt() " + i);
	return i;
}
static public long curPopLong() {
	return curContext.popLong();
}
static public Object curPopObject() {
	Object o = curContext.popObject();
//	System.out.println("  curPopObject " + o);
	return o;
}
static public Object curPopThis() {
	Object o = curContext.popThis();
//	System.out.println("  curPopThis " + o);
	return o;
}
static public void curPushDouble(double d) {
	curContext.pushDouble(d);
}
static public void curPushFloat(float f) {
	curContext.pushFloat(f);
}
static public void curPushInt(int i) {
//	System.out.println("   curPushInt " + i);
	curContext.pushInt(i);
}
static public void curPushLong(long l) {
	curContext.pushLong(l);
}
static public void curPushObject(Object o) {
//	System.out.println("   curPushObject " + o);
	curContext.pushObject(o);
}
static public void curPushThis(Object o) {
//	System.out.println("   curPushThis " + o);
	curContext.pushThis(o);
}
  static public Context getCurrentContext() {
	return curContext;
  }  
  public double popDouble() {
	return dstack[--dTop];
  }  
  public float popFloat() {
	return fstack[--fTop];
  }  
  public int popInt() {
	return istack[--iTop];
  }  
public long popLong() {
	return lstack[--lTop];
}
  public Object popObject() {
	return astack[--aTop];
  }  
  public Object popThis() {
	return tstack[--tTop];
  }  
  public void pushDouble(double d) {
	dstack[dTop++] = d;
	if (dTop == dstack.length) {
	  double[] hlp = new double[dstack.length+10];
	  System.arraycopy(dstack, 0, hlp, 0, dstack.length);
	  dstack = hlp;
	}
  }  
  public void pushFloat(float f) {
	fstack[fTop++] = f;
	if (fTop == fstack.length) {
	  float[] hlp = new float[fstack.length+10];
	  System.arraycopy(fstack, 0, hlp, 0, fstack.length);
	  fstack = hlp;
	}
  }  
  public void pushInt(int i) {
	istack[iTop++] = i;
	if (iTop == istack.length) {
	  int[] hlp = new int[istack.length+10];
	  System.arraycopy(istack, 0, hlp, 0, istack.length);
	  istack = hlp;
	}
  }  
  public void pushLong(long l) {
	lstack[lTop++] = l;
	if (lTop == lstack.length) {
	  long[] hlp = new long[lstack.length+10];
	  System.arraycopy(lstack, 0, hlp, 0, lstack.length);
	  lstack = hlp;
	}
  }  
  public void pushObject(Object o) {
	astack[aTop++] = o;
	if (aTop == astack.length) {
	  Object[] hlp = new Object[astack.length+10];
	  System.arraycopy(astack, 0, hlp, 0, astack.length);
	  astack = hlp;
	}
  }  
public void pushThis(Object o) {
	tstack[tTop++] = o;
	if (tTop == tstack.length) {
		Object[] hlp = new Object[tstack.length + 10];
		System.arraycopy(tstack, 0, hlp, 0, tstack.length);
		tstack = hlp;
	}
}
  static public void setCurrentContext(Context c) {
	curContext = c;
  }  
}
