/**
 * 
 */
package gvc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Root
 *
 */
public class SerializableUtil {
	
	/**
	 *
	 */
	public SerializableUtil() {
		
	}
	
	/**
	 * 
	 * @param object
	 * @param file
	 * @throws IOException
	 */
	public static void writeObject(Object object, File file) throws IOException {		
		FileOutputStream fileIn = new FileOutputStream(file);
		ObjectOutputStream outputStream = new ObjectOutputStream(fileIn);
		outputStream.writeObject(object);
	}

	/**
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object readObject(File file) throws IOException, ClassNotFoundException {
		FileInputStream fileIn = new FileInputStream(file);
		ObjectInputStream inputStream = new ObjectInputStream(fileIn);
		return inputStream.readObject();
	}
}
