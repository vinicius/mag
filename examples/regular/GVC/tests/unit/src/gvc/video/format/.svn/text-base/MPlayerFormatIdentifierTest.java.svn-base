package gvc.video.format;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import gvc.video.Video;

import java.io.File;
import java.io.IOException;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MPlayerFormatIdentifierTest {
	private static MPlayerFormatIdentifier formatIdentifier;
	private Video videoMock;
	private File scriptFileMock;
	
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(MPlayerFormatIdentifierTest.class);
	}
	
	@BeforeClass public static void setUp() {
		formatIdentifier = MPlayerFormatIdentifier.getInstance();		
	}
	
	@AfterClass public static void tearDown() {
		formatIdentifier = null;
	}
	
	@Before public void runBeforeEachTest() {
		videoMock = createMock(Video.class);
		scriptFileMock = createMock(File.class);
	}

	@After public void runAfterEachTest() {
		videoMock = null;
		scriptFileMock = null;
	}
	
	@Test
	public void identifyFormat0() throws IOException {
		expect(videoMock.getAbsolutePath()).andReturn("/auto/home/pos/mario/workspace/GVC/web/files/upload/video4Test.avi");
		expect(videoMock.getFilename()).andReturn("video4Test.avi").times(3);
		expect(videoMock.getVideoFormat()).andReturn(new VideoFormat()).times(12);
		expect(videoMock.getAudioFormat()).andReturn(new AudioFormat()).times(6);
		expect(scriptFileMock.getAbsolutePath()).andReturn("/auto/home/pos/mario/workspace/GVC/web/shellScript/mplayer.sh");
		replay(videoMock);
		replay(scriptFileMock);
		formatIdentifier.setScript(scriptFileMock);
		formatIdentifier.identifyFormat(videoMock);
		assertEquals(videoMock.getVideoFormat().getFormat(),"avi");
		assertEquals(videoMock.getVideoFormat().getCodec(),"[ffmsrle] vfm:ffmpeg (Microsoft RLE)");
		assertEquals(videoMock.getVideoFormat().getWidth(),"400");
		assertEquals(videoMock.getVideoFormat().getHeight(),"448");
		assertEquals(videoMock.getVideoFormat().getFramesPerSecond(),"10.000");
		assertEquals(videoMock.getVideoFormat().getBitrate(),"401088");
		assertEquals(videoMock.getAudioFormat().getCodec(),"pcm");
		assertEquals(videoMock.getAudioFormat().getBitrate(),"176400");
		assertEquals(videoMock.getAudioFormat().getSoundSystem(),"1");
		verify(videoMock);
		verify(scriptFileMock);
	}

	
}

