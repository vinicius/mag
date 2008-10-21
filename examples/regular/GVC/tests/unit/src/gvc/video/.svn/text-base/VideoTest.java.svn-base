/**
 * 
 */
package gvc.video;

import java.io.File;
import static org.junit.Assert.*;
import org.junit.Test;

import junit.framework.JUnit4TestAdapter;

/**
 * @author Root
 *
 */
public class VideoTest {
	
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(VideoTest.class);
	}
	
	@Test public void equals() throws Exception {
		File file = File.createTempFile("gvc-file4Test", "tmp");
		Video video1 = new Video(file);
		Video video2 = new Video(File.createTempFile("gvc-file4Test", "tmp"));
		Video video3 = new Video(file);
		assertFalse(video1.equals(video2));
		assertTrue(video1.equals(video3));
		video1.setSize(123);
		assertFalse(video1.equals(video3));
	}
}
