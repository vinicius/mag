package core.appexec.agenthandler;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URLClassLoader;
import java.util.HashMap;

/**
 Class ThreadAwareOutputStream - Represents the output controller
 
 Each application must write its output in its own files.
 This class is responsible for redirect the output of applications to
 their correct output files.
 
 @author Rafael Fernandes Lopes
 */
public class ThreadAwareOutputStream extends OutputStream implements Serializable {
	private static final long serialVersionUID = 8345806801944537855L;
	
	protected HashMap map = null;
	protected OutputStream defaultOutput = null;
	
	/**
	 Class constructor
	 
	 @param aDefaultOutputStream - If no files are associated to
	 a thread, the write will be done in the aDefaultOutputStream
	 */
	public ThreadAwareOutputStream(OutputStream aDefaultOutputStream) {
		map = new HashMap();
		defaultOutput = aDefaultOutputStream;
	}
	
	/**
	 Writes a character in this OutputStream
	 
	 @param w - The character to be written in this OutputStream
	 */
	public void write(int w) throws IOException {
		getOutputStream(Thread.currentThread()).write(w);
	}
	
	/**
	 Writes a character in this OutputStream
	 
	 @param w - The character to be written in this OutputStream
	 */
	public void write(byte[] b, int offset, int length) throws IOException {
		getOutputStream(Thread.currentThread()).write(b, offset, length);
	}
	
	/**
	 Associates an object classloader to a file
	 
	 @param thread - Object which the classloader will be associated to the file
	 @param file - File where 'th' will write its output
	 */
	public synchronized void attachFile (Object th, File file) throws IOException {
		if (th != null) {
			ClassLoader cl = th.getClass().getClassLoader();
			
			if (cl != null) {
				map.put (((URLClassLoader) cl).getURLs()[0] , new FileOutputStream (file, true));
			} else {
				map.put (cl, new FileOutputStream (file, true) );
			}
		}
	}
	
	/**
	 Removes the association of a thread to a file
	 
	 @param th - Thread to have the association removed
	 */
	public synchronized void removeFile (Object th) throws IOException {
		if (th != null) {
			ClassLoader cl = th.getClass().getClassLoader();
			OutputStream b = null;
			
			if (cl != null) {
				b = (OutputStream) map.remove (((URLClassLoader) cl).getURLs()[0] );
			} else {
				b = (OutputStream) map.remove (cl);
			}
			
			if (b != null) {
				b.flush();
				b.close();
			}
		}
	}
	
	public void flush () throws IOException {
		this.flush (Thread.currentThread());
	}
	
	public void flush (Object th) throws IOException {
		super.flush();
		
		this.getOutputStream (th).flush();
	}
	
	/**
	 Returns the OutputStream associated to a thread th
	 
	 @param th
	 */
	protected OutputStream getOutputStream (Object th) throws IOException {
		ClassLoader cl = th.getClass().getClassLoader();
		OutputStream b = null;
		
		if (cl != null) {
			b = (OutputStream) map.get(((URLClassLoader) cl).getURLs()[0] );
		} else {
			b = (OutputStream) map.get(cl);
		}
		
		if (b == null) {
			return this.defaultOutput;
		}
		
		return b;
	}
}
