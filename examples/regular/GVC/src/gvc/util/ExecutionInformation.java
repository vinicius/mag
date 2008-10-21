package gvc.util;

import asct.shared.*;
import java.io.*;
import gvc.video.*;

public class ExecutionInformation implements Serializable {
	private ExecutionRequestStatus request;
	private File source;
	private File target;
	private String format;
	private String convertion_parameter;
	private VideoFile videoFile;
	private int numberOfTasks;
	private int numberOfCompletedTasks;
	
	/**
	 *
	 */
	public ExecutionInformation() {
		
	}

	/**
	 * @return Returns the convertion_parameter.
	 */
	public String getConvertion_parameter() {
		return convertion_parameter;
	}

	/**
	 * @param convertion_parameter The convertion_parameter to set.
	 */
	public void setConvertion_parameter(String convertion_parameter) {
		this.convertion_parameter = convertion_parameter;
	}

	/**
	 * @return Returns the format.
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format The format to set.
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * @return Returns the request.
	 */
	public ExecutionRequestStatus getRequest() {
		return request;
	}

	/**
	 * @param request The request to set.
	 */
	public void setRequest(ExecutionRequestStatus request) {
		this.request = request;
	}

	/**
	 * @return Returns the source.
	 */
	public File getSource() {
		return source;
	}

	/**
	 * @param source The source to set.
	 */
	public void setSource(File source) {
		this.source = source;
	}

	/**
	 * @return Returns the target.
	 */
	public File getTarget() {
		return target;
	}

	/**
	 * @param target The target to set.
	 */
	public void setTarget(File target) {
		this.target = target;
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

	public int getNumberOfTasks() {
		return numberOfTasks;
	}

	public void setNumberOfTasks(int numberOfTasks) {
		this.numberOfTasks = numberOfTasks;
		this.numberOfCompletedTasks = 0;
	}

	public int getNumberOfCompletedTasks() {
		return numberOfCompletedTasks;
	}

	public void setNumberOfCompletedTasks(int numberOfCompletedTasks) {
		this.numberOfCompletedTasks = numberOfCompletedTasks;
	}	

	public void addCompletedTasks() {
		this.numberOfCompletedTasks += 1;
	}	
}
