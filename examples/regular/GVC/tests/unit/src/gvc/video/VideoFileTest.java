package gvc.video;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import gvc.login.User;

import java.io.File;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class VideoFileTest {
	private static User user;
	
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(VideoFileTest.class);
	}
	
	@BeforeClass public static void setUp() {
		user = new User("", "", "");
	}
	
	@AfterClass public static void tearDown() {
		user = null;
	}
	
	@Before public void runBeforeEachTest() {

	}

	@After public void runAfterEachTest() {

	}
	
	@Test public void testAddConverted() throws Exception {
		VideoFile videoFile = new VideoFile(new Video(
				File.createTempFile("gvc-file4Test", "tmp")), user);
		Video aux = new Video(File.createTempFile("gvc-file4Test", "tmp"));
		
		List list1 = videoFile.getConverted();
		list1.add(aux);
		
		videoFile.addConverted(aux);
		List list2 = videoFile.getConverted();
		
		assertEquals(list1, list2);
	}
	
	@Test public void testRemoveConverted() throws Exception {
		
	}
	
	@Test public void testEquals() throws Exception {
		Video aux = new Video(File.createTempFile("gvc-file4Test", "tmp"));
		VideoFile video1 = new VideoFile(aux, user);
		VideoFile video2 = new VideoFile(new Video(
				File.createTempFile("gvc-file4Test", "tmp")), user);
		VideoFile video3 = new VideoFile(aux, user);
		assertFalse(video1.equals(video2));
		assertTrue(video1.equals(video3));
		video1.getOriginal().setFilename("aaa");
		assertFalse(video1.equals(video2));
	}
}
