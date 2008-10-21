package gvc.bean;

import gvc.video.Video;
import gvc.video.VideoFile;
import gvc.video.VideoFileList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

public class FileManagementBean {
	private VideoFileList videoFileList;
	private List<String> convertedSelected;
	private String originalSelected;
	private List<VideoFile> listVideoFile;	
	private VideoFile videoFile;
	
	public FileManagementBean () {
		videoFileList = VideoFileList.getInstance();
		FacesContext fc = FacesContext.getCurrentInstance();
		ConvertionBean bean = 
			(ConvertionBean) fc.getApplication().createValueBinding("#{convertionBean}").getValue(fc);
		listVideoFile = bean.getVideoFileList();
		if(listVideoFile.size() > 0) 
			videoFile = listVideoFile.get(0);
	}

	
	public void selectOriginalVideoChangeListener(ValueChangeEvent event) {
		videoFile = listVideoFile.get(Integer.parseInt((String) event.getNewValue())); 
	}
	
	public Collection<SelectItem> getConvertedVideoList() {
		Collection<SelectItem> list = new ArrayList<SelectItem>();
		if(videoFile == null) return list;
		int i = 0;
		for(Video video: videoFile.getConverted())
			list.add(new SelectItem(Integer.toString(i++), video.getIdFilename()));
		return list;
	}
	
	public Collection<SelectItem> getOriginalVideos() {
		Collection<SelectItem> list = new ArrayList<SelectItem>();
		int i = 0;
		for(VideoFile videoFile: listVideoFile)
			list.add(new SelectItem(
					Integer.toString(i++),
					videoFile.getOriginal().getIdFilename()));
		return list;
	}
	
	private List<Video> getConvertedSelected2() {
		List<Video> list = new ArrayList<Video>();
		for(String select: convertedSelected)
			list.add(videoFile.getConverted().get(Integer.parseInt(select)));
		return list;
	}	
	
	private void addMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_WARN, message, message));
	}
	
	public String removeOriginalVideo() {
		try {
			videoFile = listVideoFile.get(Integer.parseInt(originalSelected));
			videoFileList.removeVideoFile(videoFile);
			listVideoFile.remove(videoFile);
			videoFile = null;
			if(listVideoFile.size() > 0) 
				videoFile = listVideoFile.get(0);
		} catch (Exception e) {
			addMessage(e.getMessage());
		}
		return "done";
	}
	
	public String removeConvertedVideos() {
		try {
			videoFileList.removeVideoList(videoFile, getConvertedSelected2());
			videoFile.getConverted().removeAll(getConvertedSelected2());
		} catch (RuntimeException e) {
			addMessage(e.getMessage());
		}
		return "done";
	}

	/**
	 * @return Returns the convertedSelected.
	 */
	public List<String> getConvertedSelected() {
		return convertedSelected;
	}

	/**
	 * @param convertedSelected The convertedSelected to set.
	 */
	public void setConvertedSelected(List<String> convertedSelected) {
		this.convertedSelected = convertedSelected;
	}

	/**
	 * @return Returns the listVideoFile.
	 */
	public List<VideoFile> getListVideoFile() {
		return listVideoFile;
	}

	/**
	 * @param listVideoFile The listVideoFile to set.
	 */
	public void setListVideoFile(List<VideoFile> listVideoFile) {
		this.listVideoFile = listVideoFile;
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
	 * @return Returns the videoFileList.
	 */
	public VideoFileList getVideoFileList() {
		return videoFileList;
	}

	/**
	 * @param videoFileList The videoFileList to set.
	 */
	public void setVideoFileList(VideoFileList videoFileList) {
		this.videoFileList = videoFileList;
	}


	/**
	 * @return Returns the originalSelected.
	 */
	public String getOriginalSelected() {
		return originalSelected;
	}


	/**
	 * @param originalSelected The originalSelected to set.
	 */
	public void setOriginalSelected(String originalSelected) {
		this.originalSelected = originalSelected;
	}

}
