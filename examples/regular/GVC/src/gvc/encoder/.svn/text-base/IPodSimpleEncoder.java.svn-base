package gvc.encoder;

import gvc.video.VideoFile;

import java.io.File;
import java.io.IOException;

public class IPodSimpleEncoder extends IntegradeEncoder{
	//private static final String CONVERSION_PARAMETERS = 
	//	"-y -r 30 -vcodec mpeg4 -maxrate 1000 -b 700 -qmin 3 " +
	//	"-qmax 5 -bufsize 4096 -g 300 -acodec aac -ar 44100 -ab 192 " +
	//	"-s 320�240 -aspect 4:3";
	
		private String CONVERSION_PARAMETERS = 
			"30 mpeg4 1000 700 3 5 4096 300 aac 44100 192 320x240 4:3";	

		
	/**
	 * 
	 */
	public IPodSimpleEncoder() {

	}
	
	public void encode(VideoFile videoFile, File target, int numberOfNodes) throws  IOException, FfmpegExecutionException {

		super.encode(videoFile,target,CONVERSION_PARAMETERS,"IPod", numberOfNodes);

}

}
