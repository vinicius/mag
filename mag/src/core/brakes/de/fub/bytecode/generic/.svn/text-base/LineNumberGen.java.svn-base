package core.brakes.de.fub.bytecode.generic;

import core.brakes.de.fub.bytecode.classfile.LineNumber;

/** 
 * This class represents a line number within a method, i.e. give an instruction
 * a line number corresponding to the source code line.
 *
 * @version $Id: LineNumberGen.java,v 1.1 2006-03-07 17:45:02 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 * @see     LineNumber
 * @see     MethodGen
 */
public class LineNumberGen {
  private InstructionHandle ih;
  private int               src_line;

  /**
   * Create a line number.
   *
   * @param ih instruction to tag
   * @return new line number object
   */
  public LineNumberGen(InstructionHandle ih, int src_line) {
	this.ih       = ih;
	this.src_line = src_line;
  }  
  public InstructionHandle getInstruction()                     { return ih; }  
  /**
   * Get LineNumber object.
   *
   * This relies on that the instruction list has already been dumped to byte code or
   * or that the `setPositions' methods has been called for the instruction list.
   *
   * @param cp constant pool
   */
  public LineNumber getLineNumber(ConstantPoolGen cp) {
	return new LineNumber(ih.getPosition(), src_line);
  }  
  public int               getSourceLine()                { return src_line; }  
  public void              setInstruction(InstructionHandle ih) { this.ih = ih; }  
  public void              setSourceLine(int src_line)    { this.src_line = src_line; }  
}