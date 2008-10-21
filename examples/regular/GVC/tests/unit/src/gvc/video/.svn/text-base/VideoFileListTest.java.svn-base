/**
 * 
 */
package gvc.video;

import static org.junit.Assert.assertEquals;

import gvc.login.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author paulocb
 *
 */
public class VideoFileListTest {
	private static VideoFileList videoFileList;
	private static VideoFile videoFile;
	private static List<VideoFile> list;
	private static File file;
	private static User user;
	
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(VideoFileListTest.class);
	}
	
	@BeforeClass public static void setUp() {
		videoFileList = VideoFileList.getInstance();
		list = new ArrayList<VideoFile>();
		user = new User("", "", "");

		try {
			file = File.createTempFile("gvc-file4Test", "tmp");
			videoFile = new VideoFile(new Video(
					File.createTempFile("uploadedVideo", "tmp")), user);
			videoFile.addConverted(new Video(
					File.createTempFile("convertedVideo", "tmp")));
			list.add(videoFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@AfterClass public static void tearDown() {
		user = null;
		videoFileList = null;
		file = null;
		list = null;
	}
	
	@Before public void runBeforeEachTest() {

	}

	@After public void runAfterEachTest() {

	}
	
	@Test public void testAddVideoFile() throws Exception {
		VideoFile aux = new VideoFile(new Video(
				File.createTempFile("uploadedVideo2", "tmp")), user);

		List<VideoFile> list1 = videoFileList.getVideoFileList();
		list1.add(aux);
		
		videoFileList.addVideoFile(aux);
		List list2 = videoFileList.getVideoFileList();
		
		assertEquals(list1, list2);
	}

	@Test public void testAddVideoToVideoFile() throws Exception {
		Video aux = new Video(File.createTempFile("uploadedVideo3", "tmp"));
		List<VideoFile> list1 = videoFileList.getVideoFileList();
		list1.get(0).getConverted().add(aux);
		
		videoFileList.addVideoToVideoFile(videoFileList.getVideoFileList().get(0), aux);
		List list2 = videoFileList.getVideoFileList();
		assertEquals(list1, list2);
	}	

}
