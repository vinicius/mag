package core.brakes.de.fub.bytecode.util;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

/**
 * Utility class that implements a sequence of bytes which can be read
 * via the `readByte()' method. This is used to implement a wrapper for the 
 * Java byte code stream to gain some more readability.
 *
 * @version $Id: ByteSequence.java,v 1.1 2006-03-07 17:45:05 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public final class ByteSequence extends DataInputStream {
  private ByteArrayStream byte_stream;

  private static final class ByteArrayStream extends ByteArrayInputStream {
	ByteArrayStream(byte[] bytes) { super(bytes); }
	final int  getPosition() { return pos; } // is protected in ByteArrayInputStream
	final void unreadByte()  { if(pos > 0) pos--; }
  }
  public ByteSequence(byte[] bytes) { 
	super(new ByteArrayStream(bytes));
	byte_stream = (ByteArrayStream)in;
  }  
  public final int getIndex()   { return byte_stream.getPosition(); }  
  final  void      unreadByte() { byte_stream.unreadByte(); }  
}