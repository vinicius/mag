/**
 * 
 */
package gvc.util;

import static org.junit.Assert.assertEquals;

import gvc.login.User;
import gvc.video.Video;
import gvc.video.VideoFile;

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
public class SerializableUtilTest {
	private static VideoFile videoFile;
	private static List<VideoFile> list;
	private static File file; 
	
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(SerializableUtilTest.class);
	}
	
	@BeforeClass public static void setUp() {
		list = new ArrayList<VideoFile>();
		User user = new User("", "", "");
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
		file = null;
		list = null;
	}
	
	@Before public void runBeforeEachTest() {

	}

	@After public void runAfterEachTest() {

	}
	
	@Test public void testReadAndWriteObject1() throws Exception {
		List<String> list = new ArrayList<String>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		SerializableUtil.writeObject(list, file);
		List<String> saida = (List<String>) SerializableUtil.readObject(file);
		assertEquals(list, saida);
	}
	
	@Test public void testReadAndWriteObject2() throws Exception {
		SerializableUtil.writeObject(list, file);
		List<VideoFile> saida = (ArrayList<VideoFile>) SerializableUtil.readObject(file);
		assertEquals(list, saida);
	}	
}
