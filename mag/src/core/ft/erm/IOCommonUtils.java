package core.ft.erm;

import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;

public class IOCommonUtils {

	public static byte[] readFile(File file) throws IOException{
		FileInputStream fis = new FileInputStream(file);
		byte[] serializedFile = new byte[(int) file.length()];// FIXME:
		// check
		// if
		// cast
		// is
		// acceptable
		fis.read(serializedFile);
		fis.close();
		return serializedFile;
	}
	
	public static  void saveFile(String fileName, byte[] content,
			String destinationPath) throws IOException {
		if (fileName != null && content != null) {
			File dir = new File(destinationPath);
			dir.mkdirs();
			FileOutputStream fos = new FileOutputStream(new File(
					destinationPath + "/" + fileName), true);
			fos.write(content);
			fos.close();
		}
	}
	/**
	 * Used by the cleanup method to remove a directory recursively
	 * 
	 * @param directory -
	 *            Directory to be removed
	 */
	public static void removeDirectory(File directory) {
		File fileList[] = directory.listFiles();

		for (int i = 0; i < fileList.length; i++) {
			File f = fileList[i];

			if (f.getName().equals(".") || f.getName().equals("..")) {
				continue;
			}

			if (f.isDirectory()) {
				removeDirectory(f);
			}

			f.delete();
		}

		directory.delete();
	}
}


