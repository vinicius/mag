package core.brakes.de.fub.bytecode;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Responsible for loading (class) files from CLASSPATH. Inspired by
 * sun.tools.ClassPath.
 *
 * @version $Id: ClassPath.java,v 1.1 2006-03-07 17:45:48 rafaelf Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~dahm">M. Dahm</A>
 */
public class ClassPath {
  private PathEntry[] paths;

  private abstract class PathEntry {
	abstract InputStream getInputStream(String name, String suffix) throws IOException;
  }
  
  private class Dir extends PathEntry {
	private String dir;

	Dir(String d) { dir = d; }

	InputStream getInputStream(String name, String suffix) throws IOException {
	  File f = new File(dir + File.separatorChar +
			name.replace('.', File.separatorChar) + suffix);

	  return f.exists()? new FileInputStream(f) : null;
	}

	public String toString() { return dir; }
  }

  private class Zip extends PathEntry {
	private ZipFile zip;

	Zip(ZipFile z) { zip = z; }

	InputStream getInputStream(String name, String suffix) throws IOException {
	  ZipEntry entry = zip.getEntry(name.replace('.', '/') + suffix);

	  if(entry == null)
	return null;

	  return zip.getInputStream(entry);
	}
  }
  /**
   * Search for classes in CLASSPATH.
   */
  public ClassPath() {
	this(getClassPath());
  }  
  /**
   * Search for classes in given path.
   */
  public ClassPath(String class_path) {
	Vector vec = new Vector();

	for(StringTokenizer tok=new StringTokenizer(class_path,
						System.getProperty("path.separator"));
	tok.hasMoreTokens();)
	{
	  String path = tok.nextToken();
	  
	  if(!path.equals("")) {
	File file = new File(path);

	try {
	  if(file.exists()) {
	    if(file.isDirectory())
	      vec.addElement(new Dir(path));
	    else
	      vec.addElement(new Zip(new ZipFile(file)));
	  }
	} catch(IOException e) {
	  System.err.println("CLASSPATH component " + file + ": " + e);
	}
	  }
	}

	paths = new PathEntry[vec.size()];
	vec.copyInto(paths);
  }  
  /**
   * @return byte array for class
   */
  public byte[] getBytes(String name) throws IOException {
	return getBytes(name, ".class");
  }  
  /**
   * @param name fully qualified file name, e.g. java/lang/String
   * @param suffix file name ends with suff, e.g. .java
   * @return byte array for file on class path
   */
  public byte[] getBytes(String name, String suffix) throws IOException {
	InputStream is = getInputStream(name);
	
	if(is == null)
	  throw new IOException("Couldn't find: " + name + suffix);

	DataInputStream dis   = new DataInputStream(is);
	byte[]          bytes = new byte[is.available()];
	dis.readFully(bytes);
	dis.close(); is.close();

	return bytes;
  }  
  private static final String getClassPath() {
	String class_path = System.getProperty("java.class.path");

	if(class_path == null)
	  class_path = "";

	String boot_path = System.getProperty("sun.boot.class.path");

	if(boot_path == null)
	  boot_path = "";
	
	return class_path + System.getProperty("path.separator") + boot_path;
  }  
  /**
   * @param name fully qualified class name, e.g. java.lang.String
   * @return input stream for class
   */
   public InputStream getInputStream(String name) throws IOException {
	 return getInputStream(name, ".class");
   }   
  /**
   * @param name fully qualified file name, e.g. java/lang/String
   * @param suffix file name ends with suff, e.g. .java
   * @return input stream for file on class path
   */
  public InputStream getInputStream(String name, String suffix) throws IOException {
	for(int i=0; i < paths.length; i++) {
	  InputStream is;

	  if((is = paths[i].getInputStream(name, suffix)) != null)
	return is;
	}

	throw new IOException("Couldn't find: " + name + suffix);
  }  
  /**
   * @param name name of file to search for, e.g. java/lang/String.java
   * @return full (canonical) path for file
   */
  public String getPath(String name) {
	int    index  = name.lastIndexOf('.');
	String suffix = "";

	if(index > 0) {
	  suffix = name.substring(index);
	  name   = name.substring(0, index);
	}
	
	return getPath(name, suffix);
  }  
  /**
   * @param name name of file to search for, e.g. java/lang/String
   * @param suffix file name suffix, e.g. .java
   * @return full (canonical) path for file, if it exists
   */
  public String getPath(String name, String suffix) {
	name = name.replace('.', File.separatorChar);
	if('/' !=  File.separatorChar)
	  name = name.replace('/', File.separatorChar);

	for(int i=0; i < paths.length; i++) {
	  if(paths[i] instanceof Zip)
	continue;

	  File file = new File(paths[i] + (File.separatorChar + name) + suffix);

	  try {
	if(file.exists())
	  return file.getCanonicalPath();
	  } catch(IOException e) { return null; }
	}
	
	return null;
  }  
}