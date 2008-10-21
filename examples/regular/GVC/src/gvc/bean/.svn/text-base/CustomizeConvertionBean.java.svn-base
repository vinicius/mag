/**
 * 
 */
package gvc.bean;

import gvc.encoder.CustomizedEncoder;
import gvc.util.ConfigurationManager;
import gvc.video.Video;
import gvc.video.VideoFile;
import gvc.video.format.VideoFormat;

import java.awt.Dimension;
import java.io.File;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * @author paulocb
 *
 */
public class CustomizeConvertionBean {
	private String numberOfNodes;
	private ConfigurationManager config;
	private Video videoIn;
	private Video videoOut;
	private VideoFile videoFile;
	
	public CustomizeConvertionBean() {
		config = ConfigurationManager.getInstance();
	}

	/**
	 * 
	 * @param file
	 * @param format
	 * @return
	 */
	private String timeStampedOutputName(Video file, String format) {
		String timestamp = new Long((new Date()).getTime()).toString();
		String absolutePath;
		absolutePath = timestamp + "-" + file.getName() + "." + format;			
		return absolutePath;
	}
	
	/**
	 * 
	 * @return
	 */
	public String customConvertRequest() {
		CustomizedEncoder encoder = new CustomizedEncoder();

		videoOut.setFilename(config.getDownloadFolder().getAbsolutePath() + File.separator
				+ timeStampedOutputName(videoOut, videoOut.getVideoFormat().getFormat()));
		videoOut.setFile(new File(config.getDownloadFolder().getAbsolutePath() + File.separator +
				timeStampedOutputName(videoOut, videoOut.getVideoFormat().getFormat())));
		videoOut.setIdFilename(timeStampedOutputName(videoOut, videoOut.getVideoFormat().getFormat()));
		
		VideoFormat vf = videoOut.getVideoFormat();
		
		encoder.setResolution(new Dimension(
				Integer.parseInt(vf.getWidth()), Integer.parseInt(vf.getHeight())));
		encoder.setFps(vf.getFramesPerSecond());
		encoder.setContainer(vf.getFormat());
		encoder.setAudioCodec(videoOut.getAudioFormat().getCodec());
		encoder.setVideoCodec(vf.getCodec());
		videoOut.setFilename(videoOut.getName() + vf.getFormat());
	
		try {
			encoder.encode(videoFile, new File(videoOut.getAbsolutePath()), Integer.parseInt(numberOfNodes));		
		} catch(Exception e) {
			String message = e.getMessage();
			if(message == null) message = "Error in the conversion.";
			addMessage(message);
			return "failure";
		}
		return "success";
	}
	
	private void addMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_ERROR, message, message));		
	}
	
	public String help() {
		return "help";
	}
	
	/**
	 * @return Returns the videoIn.
	 */
	public Video getVideoIn() {
		return videoIn;
	}

	/**
	 * @param videoIn The videoIn to set.
	 */
	public void setVideoIn(Video videoIn) {
		this.videoIn = videoIn;
	}

	/**
	 * @return Returns the videoOut.
	 */
	public Video getVideoOut() {
		return videoOut;
	}

	/**
	 * @param videoOut The videoOut to set.
	 */
	public void setVideoOut(Video videoOut) {
		this.videoOut = videoOut;
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
	 * @return Returns the videoFile.
	 */
	public VideoFile getVideoFile() {
		return videoFile;
	}

	/**
	 * @param videoFile The videoFile to set.
	 */
	public void setVideoFile(VideoFile videoFile) {
		this.videoFile = videoFile;
	}

	/**
	 * @return Returns the numberOfNodes.
	 */
	public String getNumberOfNodes() {
		return numberOfNodes;
	}

	/**
	 * @param numberOfNodes The numberOfNodes to set.
	 */
	public void setNumberOfNodes(String numberOfNodes) {
		this.numberOfNodes = numberOfNodes;
	}	
	
}
