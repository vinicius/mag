package gvc.encoder;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;

import java.io.File;
import java.io.IOException;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class IPodSimpleEncoderTest {

	private static IPodSimpleEncoder encoder;
	private File inputFileMock;
	private File outputFileMock;
	//private File scriptFileMock;
	
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(IPodSimpleEncoderTest.class);
	}

	@BeforeClass public static void setUp() {
		encoder = new IPodSimpleEncoder();
	}

	
	@AfterClass public static void tearDown() {
		encoder = null;
	}

	@Before public void runBeforeEachTest() {
		inputFileMock = createMock(File.class);
		outputFileMock = createMock(File.class);
		//scriptFileMock = createMock(File.class);
	}

	@After public void runAfterEachTest() {
		inputFileMock = null;
		outputFileMock = null;
		//scriptFileMock = null;
	}

	@Test (expected=IOException.class)
	public void encodeInexistentFile() throws IOException, FfmpegExecutionException{
		// The input file does not exists
		expect(inputFileMock.exists()).andReturn(false);
		replay(inputFileMock);
		//encoder.encode(inputFileMock, outputFileMock, "teste");
		verify(inputFileMock);
	}

	@Test (expected=IOException.class)
	public void encodeUnreadableFile() throws IOException, FfmpegExecutionException {
		expect(inputFileMock.exists()).andReturn(true);
		expect(inputFileMock.canRead()).andReturn(false);
		replay(inputFileMock);
		//encoder.encode(inputFileMock, outputFileMock, "teste");
		verify(inputFileMock);
	}

	@Test (expected=IOException.class)
	public void encodeInputEqualToOutput() throws IOException, FfmpegExecutionException {
		expect(inputFileMock.exists()).andReturn(true);
		expect(inputFileMock.canRead()).andReturn(true);
		expect(inputFileMock.getAbsolutePath()).andReturn("/tmp/tst1.in");
		expect(outputFileMock.getAbsolutePath()).andReturn("/tmp/tst1.in");
		replay(inputFileMock);
		replay(outputFileMock);
		//encoder.encode(inputFileMock, outputFileMock, "teste");
		verify(inputFileMock);
		verify(outputFileMock);
	}
	
	@Test (expected=FfmpegExecutionException.class)
	public void encodeWithInvalidScript() throws IOException, FfmpegExecutionException {
		expect(inputFileMock.exists()).andReturn(true);
		expect(inputFileMock.canRead()).andReturn(true);
		//expect(scriptFileMock.getAbsolutePath()).andReturn("");
		expect(inputFileMock.getAbsolutePath()).andReturn("/tmp/tst1.in").times(2);
		expect(outputFileMock.getAbsolutePath()).andReturn("/tmp/tst2.out").times(2);
		//replay(scriptFileMock);
		replay(inputFileMock);
		replay(outputFileMock);
		//encoder.encode(inputFileMock, outputFileMock, "teste");
		verify(inputFileMock);
		verify(outputFileMock);
		//verify(scriptFileMock);
	}

	@Test (expected=FfmpegExecutionException.class)
	public void encodeWithError1() throws IOException, FfmpegExecutionException {
		expect(inputFileMock.exists()).andReturn(true);
		expect(inputFileMock.canRead()).andReturn(true);
		//expect(scriptFileMock.getAbsolutePath()).andReturn("/auto/home/pos/lbarion/Workspace/GVC/web/shellScript/convert.sh");
		expect(inputFileMock.getAbsolutePath()).andReturn("/tmp/tst1.in").times(2);
		expect(outputFileMock.getAbsolutePath()).andReturn("/tmp/tst2.out").times(2);
		expect(outputFileMock.exists()).andReturn(false);
		//replay(scriptFileMock);
		replay(inputFileMock);
		replay(outputFileMock);
		//encoder.encode(inputFileMock, outputFileMock, "teste");
		verify(inputFileMock);
		//verify(scriptFileMock);
		verify(outputFileMock);
	}

	@Test (expected=FfmpegExecutionException.class)
	public void encodeWithError2() throws IOException, FfmpegExecutionException {
		expect(inputFileMock.exists()).andReturn(true);
		expect(inputFileMock.canRead()).andReturn(true);
		//expect(scriptFileMock.getAbsolutePath()).andReturn("/auto/home/pos/lbarion/Workspace/GVC/web/shellScript/convert.sh");
		expect(inputFileMock.getAbsolutePath()).andReturn("/tmp/tst1.in").times(2);
		expect(outputFileMock.getAbsolutePath()).andReturn("/tmp/tst2.out").times(2);
		expect(outputFileMock.exists()).andReturn(true);
		expect(outputFileMock.length()).andReturn(new Long(0));
		//replay(scriptFileMock);
		replay(inputFileMock);
		replay(outputFileMock);
		//encoder.encode(inputFileMock, outputFileMock, "teste");
		verify(inputFileMock);
		//verify(scriptFileMock);
		verify(outputFileMock);
	}	
}
