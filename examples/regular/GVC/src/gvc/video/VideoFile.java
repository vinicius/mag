package gvc.video;

import gvc.login.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 
 *
 */
public class VideoFile implements Serializable {
	private static final long serialVersionUID = 1L;
	private User owner;
	private Video original;
	private List<Video> converted;
	
	/**
	 * 
	 */
	public VideoFile(Video original, User owner) {
		this.owner = owner;
		this.original = original;
		converted = new ArrayList<Video>();
	}
	

	/**
	 * 
	 * @param videoFile
	 */
	public void addConverted(Video video) {
		converted.add(video);
	}

	public void removeConverted(Video video) {
		if (video.getFile().delete())
			converted.remove(video);	
	}
		
	/**
	 * 
	 * @param videoFile
	 * @return
	 */
	public boolean equals(Object obj) {
		if(!(obj instanceof VideoFile)) return false;
		VideoFile videoFile = (VideoFile) obj;
		return (original.equals(videoFile.getOriginal()) &&
				converted.equals(videoFile.getConverted()));
	}
	
	/**
	 * @return Returns the converted.
	 */
	public List<Video> getConverted() {
		return converted;
	}
	
	/**
	 * @param converted The converted to set.
	 */
	public void setConverted(List<Video> converted) {
		this.converted = converted;
	}
	
	/**
	 * @return Returns the original.
	 */
	public Video getOriginal() {
		return original;
	}
	
	/**
	 * @param original The original to set.
	 */
	public void setOriginal(Video original) {
		this.original = original;
	}	
	
	/**
	 * Auxiliar funcion to print only one row in the table.
	 * @return
	 */
	public List<String> getAux() {
		List<String> list = new ArrayList<String>();
		list.add("");
		return list;
	}


	/**
	 * @return Returns the owner.
	 */
	public User getOwner() {
		return owner;
	}


	/**
	 * @param owner The owner to set.
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
}
