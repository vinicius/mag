/**
 * 
 */
package gvc.encoder;

import gvc.video.VideoFile;

import java.io.File;
import java.io.IOException;

/**
 * @author lbarion
 *
 */
public class PalmSimpleEncoder extends IntegradeEncoder{

	//private static final String CONVERSION_PARAMETERS = 
	//	"-y -r 30 -vcodec mpeg4 -maxrate 1000 -b 700 -qmin 3 " +
	//	"-qmax 5 -bufsize 4096 -g 300 -acodec mp2 -ar 44100 " +
	//	"-s 320�240 -aspect 4:3";

	private String CONVERSION_PARAMETERS = 
        "30 mpeg4 1000 700 3 5 4096 300 mp2 44100 192 320x240 4:3";

	/**
	 * 
	 */
	public PalmSimpleEncoder() {

	}

	public void encode(VideoFile videoFile, File target, int numberOfNodes) throws  IOException, FfmpegExecutionException {
		
		super.encode(videoFile,target,CONVERSION_PARAMETERS,"Palm", numberOfNodes);
		
	}

}
