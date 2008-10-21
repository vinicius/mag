package gvc.video.format;

import gvc.video.Video;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author mario
 *
 */
public class MPlayerFormatIdentifier implements FormatIdentifier {
	private static final long serialVersionUID = 5L;
	private static MPlayerFormatIdentifier instance;
	private static File script;
	
	private MPlayerFormatIdentifier() {
		
	}
	
	public static MPlayerFormatIdentifier getInstance() {
		if(instance ==  null) {
			instance = new MPlayerFormatIdentifier();
		}
		return instance;
	}
	
	/* (non-Javadoc)
	 * @see gvc.video.format.FormatIdentifier#identifyFormat(gvc.video.VideoFile)
	 */
	public void identifyFormat(Video video) {
		String commandLine[] = {script.getAbsolutePath(), video.getAbsolutePath() };
		try {
			Process identify = Runtime.getRuntime().exec("/bin/sh " + commandLine[0] + " " + commandLine[1]);
			identify.waitFor();
			storeToVideoFile(identify.getInputStream(), video);
		} catch(IOException e) {
			e.printStackTrace();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void setContainer(Video video) {
		int i = video.getFilename().lastIndexOf('.');
		int n = video.getFilename().length();
		String extension = video.getFilename().substring(i+1, n).toLowerCase();
		video.setContainer(extension);
	}
	
	private void setFormat(Video video) {
		String extension = video.getContainer();
		if(isPalmFormat(video))	extension = extension.concat("Palm");
		else if(isIPodFormat(video)) extension = extension.concat("IPod");
		video.getVideoFormat().setFormat(extension);
	}	

	/**
	 * 
	 * @param video Audio codec: mp2 / Video Codec: mp4 / Container: avi / Resolution: 320x240
	 * @return
	 */
	public boolean isPalmFormat(Video video) {
		String audioCodec = video.getAudioFormat().getCodec();
		String videoCode = video.getVideoFormat().getCodec();
		String width = video.getVideoFormat().getWidth();
		String height = video.getVideoFormat().getHeight();
		String container = video.getContainer();
		return ((audioCodec.equals("mad") || audioCodec.equals("mp2")) &&
				(width.equals("320") && height.equals("240")) &&
				videoCode.matches(".*MPEG-4.*") &&
				container.equals("avi"));
	}

	/**
	 * 
	 * @param video Audio codec: aac / Container: mp4 / Video Codec: mp4 / Resolution: 320x240
	 * @return
	 */
	public boolean isIPodFormat(Video video) {
		String videoCodec = video.getVideoFormat().getCodec();
		String audioCodec = video.getAudioFormat().getCodec();
		String width = video.getVideoFormat().getWidth();
		String height = video.getVideoFormat().getHeight();
		String container = video.getContainer();
		return ((audioCodec.equals("faad") || audioCodec.equals("aac")) &&
				(width.equals("320") && height.equals("240")) &&
				videoCodec.matches(".*MPEG-4.*") && container.equals("mp4"));	
	}
	
	public void storeToVideoFile(InputStream stream, Video video) {
		String line = "";
		
		try {
			InputStreamReader r = new InputStreamReader(stream);
			BufferedReader bufferedReader = new BufferedReader(r);
			while((line = bufferedReader.readLine()) != null) {			 
				if(line.startsWith("Selected video codec: ")) {
					line = line.replaceFirst("Selected video codec: ", "");
					video.getVideoFormat().setCodec(line);
				} else if(line.startsWith("ID_VIDEO_WIDTH=")) {
					line = line.replaceFirst("ID_VIDEO_WIDTH=", "");
					video.getVideoFormat().setWidth(line);
				} else if(line.startsWith("ID_VIDEO_HEIGHT=")) {
					line = line.replaceFirst("ID_VIDEO_HEIGHT=", "");
					video.getVideoFormat().setHeight(line);
				} else if(line.startsWith("ID_VIDEO_FPS=")) {
					line = line.replaceFirst("ID_VIDEO_FPS=", "");
					video.getVideoFormat().setFramesPerSecond(line);
				} else if(line.startsWith("ID_VIDEO_BITRATE=")) {
					line = line.replaceFirst("ID_VIDEO_BITRATE=", "");
					video.getVideoFormat().setBitrate(line);
				} else if(line.startsWith("ID_AUDIO_CODEC=")) {
					line = line.replaceFirst("ID_AUDIO_CODEC=", "");
					video.getAudioFormat().setCodec(line);
				} else if(line.startsWith("ID_AUDIO_BITRATE=")) {
					line = line.replaceFirst("ID_AUDIO_BITRATE=", "");
					video.getAudioFormat().setBitrate(line);
				} else if(line.startsWith("ID_AUDIO_NCH=")) {
					line = line.replaceFirst("ID_AUDIO_NCH=", "");
					video.getAudioFormat().setSoundSystem(line);
				}
				System.out.println("Linha MPLAYER " + line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();	
		}
		setContainer(video);
		setFormat(video);
	}
	
	/**
	 * @return Returns the script.
	 */
	public File getScript() {
		return script;
	}

	/**
	 * @param script The script to set.
	 */
	public void setScript(File script) {
		MPlayerFormatIdentifier.script = script;
	}
	
}
