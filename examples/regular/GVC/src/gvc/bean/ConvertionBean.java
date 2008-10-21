package gvc.bean;

import gvc.encoder.FfmpegExecutionException;
import gvc.encoder.IPodSimpleEncoder;
import gvc.encoder.PalmSimpleEncoder;
import gvc.login.User;
import gvc.util.ConfigurationManager;
import gvc.video.Video;
import gvc.video.VideoFile;
import gvc.video.VideoFileList;
import gvc.video.format.MPlayerFormatIdentifier;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * @author Root
 *
 */
public class ConvertionBean {
	private String numberOfNodes;
	private ConfigurationManager config;	
	
	/**
	 * 
	 */
	public ConvertionBean() {
		config = ConfigurationManager.getInstance();
	}
	
	/**
	 * @return
	 */
	public List<VideoFile> getVideoFileList() {
		FacesContext fc = FacesContext.getCurrentInstance();
		User user =	
			(User) fc.getApplication().createValueBinding("#{loginBean.user}").getValue(fc);
		return VideoFileList.getInstance().getVideoFileList(user);
	}
	
	/**
	 * 
	 * @return
	 */
	public VideoFile getVideoFileOfContext() {
		final String parameter = "videoFile";
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext context = fc.getExternalContext(); 
		return (VideoFile) context.getRequestMap().get(parameter);		
	}
	
	/**
	 * 
	 * @param expressionLanguage
	 * @param object
	 */
	public void bindValue(String expressionLanguage, Object object) {
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.getApplication().createValueBinding(expressionLanguage).setValue(fc, object);
	}

	/**
	 * 
	 * @return
	 */
	public String customizeRequest() {
		VideoFile videoFile = getVideoFileOfContext();
		Video videoIn = videoFile.getOriginal();
		bindValue("#{customizeConvertionBean.videoIn}", videoIn);
		Video videoOut = new Video(new File(videoIn.getAbsolutePath()));
		bindValue("#{customizeConvertionBean.videoOut}", videoOut);
		bindValue("#{customizeConvertionBean.videoFile}", videoFile);
		MPlayerFormatIdentifier.getInstance().identifyFormat(videoIn);
		MPlayerFormatIdentifier.getInstance().identifyFormat(videoOut);
		videoOut.setDefaultContainer();
		return "customize";
	}
	
	/**
	 * @return
	 */
	public String iPodConvertRequest() {
		VideoFile videoFile = getVideoFileOfContext();
		try {
			convert(videoFile, "mp4");
		} catch(Exception e) {
			String message = e.getMessage();
			if(message == null) message = "Error in the conversion.";
			addMessage(message);
			e.printStackTrace();
			return "failure";
		}
		return "success";
	}

	/**
	 * @return
	 */
	public String palmConvertRequest() {
		VideoFile videoFile = getVideoFileOfContext();
		try {
			convert(videoFile, "avi");
		} catch(Exception e) {
			String message = e.getMessage();
			if(message == null) message = "Error in the conversion.";
			addMessage(message);
			e.printStackTrace();
			return "failure";
		}
		return "success";
	}
	
	private void addMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_ERROR, message, message));
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
		absolutePath = 
			config.getDownloadFolder().getAbsolutePath() + File.separator + 
			timestamp + "-" + file.getName() + "." + format;
		return absolutePath;
	}	
	
	public void convert(VideoFile videoFile, String format) 
	throws IOException, FfmpegExecutionException {		
		String absolutePath = timeStampedOutputName(videoFile.getOriginal(), format);
		File output = new File(absolutePath);

		//IntegradeEncoder encoder = null;
		if (format == "mp4") {
			IPodSimpleEncoder encoder = new IPodSimpleEncoder();
			encoder.encode(videoFile, output, Integer.parseInt(numberOfNodes));
		}
		else if (format == "avi") {
			PalmSimpleEncoder encoder = new PalmSimpleEncoder();
			encoder.encode(videoFile, output, Integer.parseInt(numberOfNodes));
		}		
	}
	
	/**
	 * @return
	 */
	public int getAmountOfVideoFiles() {
		return config.getUploadFolder().listFiles().length;
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
