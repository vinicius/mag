package gvc.video.format;

import java.io.Serializable;

/**
 * @author 
 *
 */
public class AudioFormat implements Serializable {
	private static final long serialVersionUID = 4L;
	String codec;
	String bitrate;
	String soundSystem;
		
	/**
	 * 
	 */
	public AudioFormat() {
		 codec = "unknown";
		 bitrate = "unknown";
		 soundSystem = "unknown";
	}	
	
	/**
	 * @param bitrate
	 * @param codec
	 * @param system
	 */
	public AudioFormat(String bitrate, String codec, String system) {
		this.bitrate = bitrate;
		this.codec = codec;
		soundSystem = system;
	}
	
	/**
	 * 
	 * @param audioFormat
	 * @return
	 */
	public boolean equals(Object obj) {
		if(!(obj instanceof AudioFormat)) return false;
		AudioFormat audioFormat = (AudioFormat) obj;
		return (codec.equals(audioFormat.getCodec()) &&
				bitrate.equals(audioFormat.getBitrate()) &&
				soundSystem.equals(audioFormat.getSoundSystem()));
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
	 * @return Returns the soundSystem.
	 */
	public String getSoundSystem() {
		return soundSystem;
	}

	/**
	 * @param soundSystem The soundSystem to set.
	 */
	public void setSoundSystem(String soundSystem) {
		this.soundSystem = soundSystem;
	}

}
