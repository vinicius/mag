package gvc.video;


import gvc.video.format.AudioFormat;
import gvc.video.format.VideoFormat;

import java.io.File;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.StringTokenizer;

/**
 * @author 
 *
 */
public class Video implements Serializable {
	private static final long serialVersionUID = 2L;
	private File file; // getAbsolutePath() = dir/dir/.../timestamp-filename.extension
	private String idFilename; // timestamp-filename.extension
	private String filename; // filename.extension
	private String name; // filename
	private long size;
	private VideoFormat videoFormat;
	private AudioFormat audioFormat;
	private String container;

	/**
	 * 
	 * @param filename
	 * @param formatIdentifier
	 */
	public Video(File file) {
		this.file = file;
		this.size = file.length() / 1024;
		this.idFilename = file.getAbsolutePath().replaceAll(".*" + File.separator, "");
		this.filename = idFilename.replaceAll("^\\d+-?", "");		
		StringTokenizer aux = new StringTokenizer(filename, ".");
		name = aux.nextToken();
		videoFormat = new VideoFormat();
		audioFormat = new AudioFormat();
	}

	/**
	 * @return Returns the audioFormat.
	 */
	public AudioFormat getAudioFormat() {
		return audioFormat;
	}

	/**
	 * @param audioFormat The audioFormat to set.
	 */
	public void setAudioFormat(AudioFormat audioFormat) {
		this.audioFormat = audioFormat;
	}

	/**
	 * @return Returns the filename.
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename The filename to set.
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the videoFormat.
	 */
	public VideoFormat getVideoFormat() {
		return videoFormat;
	}

	/**
	 * @param videoFormat The videoFormat to set.
	 */
	public void setVideoFormat(VideoFormat videoFormat) {
		this.videoFormat = videoFormat;
	}

	/**
	 * @return Returns the size.
	 */
	public long getSize() {
		return size;
	}
	
	public String getSizeMB() {
		NumberFormat d = NumberFormat.getInstance();
		d.setMaximumFractionDigits(2);
		return d.format((double)size / 1024); 
	}

	/**
	 * @param size The size to set.
	 */
	public void setSize(long size) {
		this.size = size;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getAbsolutePath() {
		return file.getAbsolutePath();		
	}	

	/**
	 * 
	 * @return
	 */
	public boolean exists() {
		return file.exists();
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean canRead() {
		return file.canRead();
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean equals(Object obj) {
		if(!(obj instanceof Video)) return false;
		Video video = (Video) obj;
		return (file.equals(video.getFile()) &&
				idFilename.equals(video.getIdFilename()) &&
				filename.equals(video.getFilename()) &&
				name.equals(video.getName()) &&
				(size == video.getSize()) &&
				videoFormat.equals(video.getVideoFormat()) &&
				audioFormat.equals(video.getAudioFormat()));
	}
	
	/**
	 * @return Returns the file.
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file The file to set.
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @return Returns the idFilename.
	 */
	public String getIdFilename() {
		return idFilename;
	}

	/**
	 * @param idFilename The idFilename to set.
	 */
	public void setIdFilename(String idFilename) {
		this.idFilename = idFilename;
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
		int i = getFilename().lastIndexOf('.');
		this.setFilename(getFilename().substring(0, i+1).toLowerCase() + container);
		this.container = container;
}
	
	public void setDefaultContainer() {
		setContainer("mp4");
	}
		
}
