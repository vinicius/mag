package gvc.video;

import gvc.login.User;
import gvc.util.ConfigurationManager;
import gvc.util.SerializableUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author paulocb
 *
 */
public class VideoFileList {
	private static VideoFileList instance;
	private List<VideoFile> videoFileList;
	private ConfigurationManager config;
	private File fileList;
	
	//--- TODO ANALISAR NOVA ESTRUTURA DE DADOS PARA AS LISTAS...
	//--- Idéias:
	//private Map<Video, List<Video>> videoFileList = new HashMap<>();
	//private Map<String, List<Video>> videoFileList = new HashMap<>();
	//put.(original, new List<Video>);
	//get(original).add
	//--------videoFileList
	

	/**
	 * 
	 *
	 */
	private VideoFileList() {
		// TODO tosco, configurationManager não ta legal, talvez o download4J resolva.
		config = ConfigurationManager.getInstance();
		fileList = config.getSerializableList();
		try {
			videoFileList = (List<VideoFile>) SerializableUtil.readObject(fileList);
		} catch (IOException e) {
			System.out.println(
				"Lista de vídeos serializada não encontrada, criando uma nova.");
			videoFileList = new ArrayList<VideoFile>();
			try {
				SerializableUtil.writeObject(videoFileList, fileList);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public static VideoFileList getInstance() {
		if(instance == null) instance = new VideoFileList();
		return instance;
	}	
	
	/**
	 * 
	 * @return
	 */
	public List<VideoFile> getVideoFileList() {
		return videoFileList;
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public List<VideoFile> getVideoFileList(User user) {
		List<VideoFile> list = new ArrayList<VideoFile>();
		for(VideoFile videoFile: videoFileList)
			if(user.getEmail().equals(videoFile.getOwner().getEmail()))
				list.add(videoFile);
		return list;
	}
	
	/**
	 * 
	 * @param videoFile
	 */
	public synchronized void addVideoFile(VideoFile videoFile) {
		videoFileList.add(videoFile);
		try {
			SerializableUtil.writeObject(videoFileList, fileList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param videoFile
	 * @param video
	 */
	public synchronized void addVideoToVideoFile(VideoFile videoFile, Video video) {
		for(VideoFile vf: videoFileList) {
			if(vf.getOriginal().getFile().equals(videoFile.getOriginal().getFile())) {
				vf.addConverted(video);
				try {
					SerializableUtil.writeObject(videoFileList, fileList);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}
	
	private synchronized void removeVideo (VideoFile videoFile, Video video) {
		for(VideoFile vf: videoFileList) {
			if(vf.getOriginal().getFile().equals(videoFile.getOriginal().getFile())) {
				vf.removeConverted(video);				
				break;
			}
		}
	}
		
	public synchronized void removeVideoList (VideoFile vf, List<Video> list) {
		for(Video v: list) {
			removeVideo (vf, v);
		}
		try {
			SerializableUtil.writeObject(videoFileList, fileList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void removeVideoFile (VideoFile vf) {
		vf.getOriginal().getFile().delete();
		videoFileList.remove(vf);
		removeVideoList (vf, vf.getConverted());
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
}
