package gvc.util;

import gvc.video.VideoFileList;
import gvc.video.format.MPlayerFormatIdentifier;

import java.io.File;

/**
 * @author
 *
 */
public class ConfigurationManager {
	private static ConfigurationManager instance = null;
	private String uploadFolderName;
	private String downloadFolderName;
	private String convertScriptName;
	private String mplayerScriptName;
	private String serializableListName;
	private String integradeRequestListName;
	private String userListName;
	
	/**
	 * 
	 */
	private ConfigurationManager() {
		String basedir = System.getenv("GVC_HOME");
		System.out.println("\nGVC_HOME = " + basedir + "\n");
		uploadFolderName = basedir + "/files/upload";
		downloadFolderName = basedir + "/files/download";
		convertScriptName = basedir + "/shellScript/convert.sh";
		mplayerScriptName = basedir + "/shellScript/mplayer.sh";
		serializableListName = basedir + "/serializable/videoList.serializable";
		integradeRequestListName =
			basedir + "/serializable/integradeRequestList.serializable";
		userListName = basedir + "/serializable/userListName.serializable";
		MPlayerFormatIdentifier.getInstance().setScript(new File(mplayerScriptName));
	}
	
	public static ConfigurationManager getInstance() {
		if(instance == null) instance = new ConfigurationManager();
		return instance;
	}

	/**
	 * @param folder
	 * @throws InvalidParameterException
	 * @throws NullPointerException
	 */
	public void verifyDirectory(File folder) 
		throws InvalidParameterException, NullPointerException {
		String errorMessage;
		if(!folder.exists()) {
			if(!folder.mkdir()) {
				errorMessage = "Impossible to create the directory: "
					+ folder.getName() + ".";
				throw new InvalidParameterException(errorMessage);
			}
		} else {
			if(!folder.isDirectory()) {
				errorMessage = folder.getName() + "is not a directory.";
				throw new InvalidParameterException(errorMessage);
			}
		}
		if(!folder.canWrite()) {
			errorMessage = folder.getName() + "don't have permission to write" + ".";
			throw new InvalidParameterException(errorMessage);
		}
	}
	
	public void verifyFile(File file) throws InvalidParameterException {
		String errorMessage;
		if(!file.exists()) {
			errorMessage = "File don't exist.";
			throw new InvalidParameterException(errorMessage);
		}
		if(!file.isFile()) {
			errorMessage = "It is not a valid file.";
			throw new InvalidParameterException(errorMessage);
		}
	}

	/**
	 * @return
	 */
	public File getDownloadFolder() {
		return new File(downloadFolderName);
	}
	
	/**
	 * @return
	 */
	public File getUploadFolder() {
		return new File(uploadFolderName);
	}
	
	/**
	 * @return
	 */
	public File getConvertScript() {
		return new File(convertScriptName);
	}
	
	/**
	 * @return
	 */
	public File getMplayerScript() {
		return new File(mplayerScriptName);
	}
	
	/**
	 * @return
	 */
	public File getSerializableList() {
		return new File(serializableListName);
	}
	
	/**
	 * @return
	 */
	public File getIntegradeRequestList() {
		return new File(integradeRequestListName);
	}
	
	/**
	 * @return Returns the userListName.
	 */
	public File getUserList() {
		return new File(userListName);
	}
	
	/*************************************************/
	
	/**
	 * @return Returns the downloadFolderName.
	 */
	public String getDownloadFolderName() {
		return downloadFolderName;
	}

	/**
	 * @param downloadFolderName The downloadFolderName to set.
	 */
	public void setDownloadFolderName(String downloadFolderName)
	throws InvalidParameterException, NullPointerException {
		
		verifyDirectory(new File(downloadFolderName));
		this.downloadFolderName = downloadFolderName;
	}

	/**
	 * @return Returns the convertScriptName.
	 */
	public String getConvertScriptName() {
		return convertScriptName;
	}

	/**
	 * @param convertScriptName The convertScriptName to set.
	 */
	public void setConvertScriptName(String convertScriptName)
	throws InvalidParameterException, NullPointerException {
		
		verifyFile(new File(convertScriptName));
		this.convertScriptName = convertScriptName;
	}

	/**
	 * @return Returns the mplayerScriptName.
	 */
	public String getMplayerScriptName() {
		return mplayerScriptName;
	}

	/**
	 * @param mplayerScriptName The mplayerScriptName to set.
	 */
	public void setMplayerScriptName(String mplayerScriptName)
	throws InvalidParameterException, NullPointerException {
		
		verifyFile(new File(mplayerScriptName));
		MPlayerFormatIdentifier.getInstance().setScript(new File(mplayerScriptName));
		this.mplayerScriptName = mplayerScriptName;
	}	
	
	/**
	 * @return Returns the uploadFolderName.
	 */
	public String getUploadFolderName() {
		return uploadFolderName;
	}

	/**
	 * @param uploadFolderName The uploadFolderName to set.
	 */
	public void setUploadFolderName(String uploadFolderName)
	throws InvalidParameterException, NullPointerException {
		
		verifyDirectory(new File(uploadFolderName));
		this.uploadFolderName = uploadFolderName;
	}

	/**
	 * @return Returns the serializableListName.
	 */
	public String getSerializableListName() {
		return serializableListName;
	}

	/**
	 * @param serializableListName The serializableListName to set.
	 */
	public void setSerializableListName(String serializableListName) { 
		VideoFileList.getInstance().setConfig(this);
		this.serializableListName = serializableListName;
	}

	/**
	 * @return Returns the integradeRequestListName.
	 */
	public String getIntegradeRequestListName() {
		return integradeRequestListName;
	}

	/**
	 * @param integradeRequestListName The integradeRequestListName to set.
	 */
	public void setIntegradeRequestListName(String integradeRequestListName) {
		this.integradeRequestListName = integradeRequestListName;
	}

	/**
	 * @return Returns the userListName.
	 */
	public String getUserListName() {
		return userListName;
	}

	/**
	 * @param userListName The userListName to set.
	 */
	public void setUserListName(String userListName) {
		this.userListName = userListName;
	}

}
