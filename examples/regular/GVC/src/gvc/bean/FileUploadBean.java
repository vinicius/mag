package gvc.bean;

import gvc.login.User;
import gvc.util.ConfigurationManager;
import gvc.video.Video;
import gvc.video.VideoFile;
import gvc.video.VideoFileList;
import gvc.video.format.MPlayerFormatIdentifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.faces.context.FacesContext;

import org.apache.myfaces.custom.fileupload.UploadedFile;


/**
 * @author  (latest modification by $Author: paulocheque $)
 * @version $Revision: 1.5 $ $Date: 2006-06-28 02:40:59 $
 */
public class FileUploadBean {
	private ConfigurationManager config;
	private int bufferSize;
	private UploadedFile upFile;
	private String errorStatus;
	
	/**
	 * 
	 */
	public FileUploadBean() {
		config = ConfigurationManager.getInstance();
	}
	
	/**
	 * @return
	 * @throws IOException
	 */
	public String upload() throws IOException {
		try {
			FacesContext fc = FacesContext.getCurrentInstance();
			User user =	
				(User) fc.getApplication().createValueBinding("#{loginBean.user}").getValue(fc);
			
			String timestamp = new Long((new Date()).getTime()).toString();
			String outputFilename = timestamp + "-" + upFile.getName();
			String absolutePath;
			absolutePath = config.getUploadFolder().getAbsolutePath() +
							File.separator + outputFilename;
			File originalFile = new File(absolutePath);

			saveUploadedFile(upFile.getInputStream(), originalFile);
			Video video = new Video(originalFile);
			MPlayerFormatIdentifier.getInstance().identifyFormat(video);
			VideoFileList videoFilesList = VideoFileList.getInstance();
			videoFilesList.addVideoFile(new VideoFile (video, user));
		} catch(IOException e) {
			e.printStackTrace();
			errorStatus = e.getMessage();
			return "failure";
		}
		return "success";
	}
	
	/**
	 * @param inputStream
	 * @param outputFilename
	 * @throws IOException
	 */
	public void saveUploadedFile(InputStream inputStream, File outputFile)
	throws IOException {

		OutputStream outputStream = new FileOutputStream(outputFile);
		byte[] buffer = new byte[bufferSize];
		int bytesRead;
		
		while (true) {
			bytesRead = inputStream.read(buffer);
			if (bytesRead == -1)
				break;
			outputStream.write(buffer, 0, bytesRead);
		}		
		
		outputStream.flush();
		inputStream.close();
		outputStream.close();
	}
	
	/**
	 * @return Returns the upFile.
	 */
	public UploadedFile getUpFile() {
		return upFile;
	}
	
	/**
	 * @param upFile The upFile to set.
	 */
	public void setUpFile(UploadedFile upFile) {
		this.upFile = upFile;
	}
	
	/**
	 * @return Returns true if there was an error.
	 */
	public boolean isWrong() {
		return errorStatus != null;
	}
	
	/**
	 * @return Returns the error status.
	 */
	public String getErrorStatus(){
		return errorStatus;
	}

	/**
	 * @return Returns the config.
	 */
	public ConfigurationManager getConfig() {
		return config;
	}

	/**
	 * @param config The config to set.
	 */
	public void setConfig(ConfigurationManager config) {
		this.config = config;
	}

	/**
	 * @return Returns the bufferSize.
	 */
	public int getBufferSize() {
		return bufferSize;
	}

	/**
	 * @param bufferSize The bufferSize to set.
	 */
	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
	
}
