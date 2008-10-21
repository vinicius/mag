package gvc.encoder;

import gvc.video.VideoFile;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

public class CustomizedEncoder extends IntegradeEncoder {
	
	private Dimension resolution;
	private String fps;
	private String audioCodec;
	private String videoCodec;
	private String container;
		
	/**
	 * 
	 */
	public CustomizedEncoder() {

	}

	
	/* (non-Javadoc)
	* @see gvc.encoder.IEncoder#encode(java.io.File, java.io.File, java.lang.String)
	*/
	public void encode(VideoFile videoFile, File target, int numberOfNodes) throws  IOException, FfmpegExecutionException {

		String CONVERSION_PARAMETERS = " " + getConversionParameters() + " "	+ videoFile.getOriginal().getFile().getAbsolutePath() + " " + target.getAbsolutePath();
		super.encode(videoFile,target,CONVERSION_PARAMETERS,"Custom", numberOfNodes);
		
			
	}

	private String getConversionParameters() {
		
		return " " + getFps() + " " + getVideoCodec() + " 1000 700 3 5 4096 300 " + getAudioCodec() + " 44100 192 " +getResolution().width + "x" + getResolution().height + " 4:3";
		
			
	}

	/**
	 * @return Returns the audioCodec.
	 */
	public String getAudioCodec() {
		return audioCodec;
	}

	/**
	 * @param audioCodec The audioCodec to set.
	 */
	public void setAudioCodec(String audioCodec) {
		this.audioCodec = audioCodec;
	}

	/**
	 * @return Returns the container.
	 */
	public String getContainer() {
		return container;
	}

	/**
	 * @param container The container to set.
	 */
	public void setContainer(String container) {
		this.container = container;
	}

	/**
	 * @return Returns the fps.
	 */
	public String getFps() {
		return fps;
	}

	/**
	 * @param fps The fps to set.
	 */
	public void setFps(String fps) {
		this.fps = fps;
	}

	/**
	 * @return Returns the resolution.
	 */
	public Dimension getResolution() {
		return resolution;
	}

	/**
	 * @param resolution The resolution to set.
	 */
	public void setResolution(Dimension resolution) {
		this.resolution = resolution;
	}

	/**
	 * @return Returns the videoCodec.
	 */
	public String getVideoCodec() {
		return videoCodec;
	}

	/**
	 * @param videoCodec The videoCodec to set.
	 */
	public void setVideoCodec(String videoCodec) {
		this.videoCodec = videoCodec;
	}
}
