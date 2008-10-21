package applicationRepository;

import java.io.File;

import clusterManagement.DirectoryCreationException;
import clusterManagement.DirectoryNotEmptyException;
import clusterManagement.DirectoryNotFoundException;
import clusterManagement.InvalidPathNameException;
import dataTypes.ContentDescription;
import dataTypes.kindOfItens;

public class FileUtils {

	private static String applicationDescriptionName = 	"AppDescription";
	private static String binarySuffixName = "_binaryFile";
	private static String publicDirectory = "Public";
	
	public static String getPublicDirectory() {
		return publicDirectory;
	}

	public static void setPublicDirectory(String publicDirectory) {
		FileUtils.publicDirectory = publicDirectory;
	}

	public static String getBinarySuffixName() {
		return binarySuffixName;
	}

	public static String getApplicationDescriptionName() {
		return applicationDescriptionName;
	}

	/**
	 * Verify if pathName is not null and does not includes the repository_root
	 * @throws InvalidPathNameException 
	 */
	public static void checkPathName(String pathName) throws InvalidPathNameException {
	
		if(pathName == null) {
			InvalidPathNameException ipne = new InvalidPathNameException("null parameter.");
		}
		if(pathName.indexOf("..") != -1){
			InvalidPathNameException ipne = new InvalidPathNameException("`"+pathName+"' is invalid. `..' is not allowed.");
			throw ipne;
		}
		if(pathName.equals(""))
		{
			pathName=File.separator;
		}
	}
	
	public static void createDirectory(String directoryName) throws DirectoryCreationException
	{
		File dir = new File(directoryName);
		if (dir.exists() && dir.isDirectory()) {
			DirectoryCreationException dce = 
				new DirectoryCreationException("directory exists !");
			throw  dce;
		}
		if ( !dir.mkdirs() ) {
			DirectoryCreationException dce = 
				new DirectoryCreationException("couldn't create dir.mkdirs() "+ directoryName+ " !");
			throw dce;
		}
	}

	public static void removeDirectory(String directoryName) throws DirectoryNotFoundException, DirectoryNotEmptyException, InvalidPathNameException 
	{
		File dirFile = new File(directoryName) ;
		if ( ! dirFile.exists()) {
			DirectoryNotFoundException dnfe = 
				new DirectoryNotFoundException();
			throw dnfe;			
		}
		if (dirFile.list().length > 0) {
			DirectoryNotEmptyException dnee = 
				new  DirectoryNotEmptyException("Directory not empty !");
			throw dnee;
		}

		/* deletes the directory */
		if (! dirFile.delete()) {
			DirectoryNotFoundException dnfe = 
				new DirectoryNotFoundException("deleting the directory !");
			throw dnfe;
		}
	}
	/**
	 * 
	 * @param dir
	 * @return
	 */
	public static ContentDescription[] readDirectoryContents(File dir) {
		File[] contentDir = dir.listFiles();
		int size = contentDir.length;
		ContentDescription[] contents = new ContentDescription[size];
		
		for (int i = 0; i < size; i++) {
			contents[i]=new ContentDescription();
			if(contentDir[i].isDirectory())
			{
				File ApplicationDescription = new File(contentDir[i].getPath()+ File.separator + FileUtils.getApplicationDescriptionName());
				if(ApplicationDescription.exists())
				{
					contents[i].kind = kindOfItens.applicationDirectory;
				}else
				{
					contents[i].kind = kindOfItens.commonDirectory;
				}
			}else
			{
				if(contentDir[i].getName().equals(FileUtils.applicationDescriptionName))
				{
					contents[i].kind = kindOfItens.applicationDescriptionFile;
				}
				else
				{
					contents[i].kind = kindOfItens.binaryFile;
				}
					
			}
			contents[i].fileName = removeRootPathName(contentDir[i].getPath());
		}
		return contents;
	}// readDirectoryContents method
	private static String removeRootPathName(String fullPathName)
	{
		return fullPathName.substring(ApplicationRepositoryImpl.rootName.length());
	}

}
