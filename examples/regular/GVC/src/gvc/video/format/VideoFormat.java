package gvc.video.format;

import java.io.Serializable;


/**
 * @author 
 *
 */
public class VideoFormat implements Serializable {
	private static final long serialVersionUID = 3L;
	String format;
	String codec;
	String framesPerSecond;
	String bitrate;
	String width;
	String height;

	/**
	 * 
	 */
	public VideoFormat() {
		format = "unknown";
		codec = "unknown";
		framesPerSecond = "unknown";
		bitrate = "unknown";
		width = "unknown";
		height = "unknown";
	}

	/**
	 * 
	 * @param videoFormat
	 * @return
	 */
	public boolean equals(Object obj) {
		if(!(obj instanceof VideoFormat)) return false;
		VideoFormat videoFormat = (VideoFormat) obj;
		return (format.equals(videoFormat.getFormat()) &&
				codec.equals(videoFormat.getCodec()) &&
				framesPerSecond.equals(videoFormat.getFramesPerSecond()) &&
				bitrate.equals(videoFormat.getBitrate()) &&
				width.equals(videoFormat.getWidth()) &&
				height.equals(videoFormat.getHeight()));
	}
	
	/**
	 * @return Returns the bitrate.
	 */
	public String getBitrate() {
		return bitrate;
	}

	/**
	 * @param bitrate The bitrate to set.
	 */
	public void setBitrate(String bitrate) {
		this.bitrate = bitrate;
	}

	/**
	 * @return Returns the codec.
	 */
	public String getCodec() {
		return codec;
	}

	/**
	 * @param codec The codec to set.
	 */
	public void setCodec(String codec) {
		this.codec = codec;
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
	 * @return Returns the framesPerSecond.
	 */
	public String getFramesPerSecond() {
		return framesPerSecond;
	}

	/**
	 * @param framesPerSecond The framesPerSecond to set.
	 */
	public void setFramesPerSecond(String framesPerSecond) {
		this.framesPerSecond = framesPerSecond;
	}

	/**
	 * @return Returns the height.
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * @param height The height to set.
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * @return Returns the width.
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * @param width The width to set.
	 */
	public void setWidth(String width) {
		this.width = width;
	}

}
