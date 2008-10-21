package gvc.util;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;

import java.io.File;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConfigurationManagerTest {
	private static ConfigurationManager configurationManager;
	private File fileMock;

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(ConfigurationManagerTest.class);
	}
	
	@BeforeClass public static void setUp() {
		configurationManager = ConfigurationManager.getInstance();
	}
	
	@AfterClass public static void tearDown() {
		configurationManager = null;
	}
	
	@Before public void runBeforeEachTest() {
		fileMock = createMock(File.class);
	}

	@After public void runAfterEachTest() {
		fileMock = null;
	}
	
	@Test(expected=NullPointerException.class)
	public void verifyDirectory0() throws InvalidParameterException {
		// null		
		configurationManager.verifyDirectory(null);
	}
	
	@Test
	public void verifyDirectory1() throws InvalidParameterException {
		// Exists true - isDirectory true - canWrite true
		expect(fileMock.exists()).andReturn(true);		
		expect(fileMock.isDirectory()).andReturn(true);
		expect(fileMock.canWrite()).andReturn(true);
		replay(fileMock);
		configurationManager.verifyDirectory(fileMock);
		verify(fileMock);
	}


	@Test(expected=InvalidParameterException.class)
	public void verifyDirectory2() throws InvalidParameterException {
		// Exists true - isDirectory true - canWrite false
		expect(fileMock.exists()).andReturn(true);
		expect(fileMock.isDirectory()).andReturn(true);
		expect(fileMock.canWrite()).andReturn(false);
		expect(fileMock.getName()).andReturn("teste").times(3);
		replay(fileMock);
		configurationManager.verifyDirectory(fileMock);
		verify(fileMock);
	}

	@Test(expected=InvalidParameterException.class)
	public void verifyDirectory3() throws InvalidParameterException {
		// Exists true - isDirectory false
		expect(fileMock.exists()).andReturn(true);
		expect(fileMock.isDirectory()).andReturn(false);
		expect(fileMock.getName()).andReturn("teste").times(3);
		replay(fileMock);
		configurationManager.verifyDirectory(fileMock);
		verify(fileMock);
		
	}
	
	@Test
	public void verifyDirectory4() throws InvalidParameterException {
		// Exists false - mkDir true - canWrite true
		expect(fileMock.exists()).andReturn(false);
		expect(fileMock.mkdir()).andReturn(true);
		expect(fileMock.canWrite()).andReturn(true);
		replay(fileMock);
		configurationManager.verifyDirectory(fileMock);
		verify(fileMock);
	}
	
	@Test(expected=InvalidParameterException.class)
	public void verifyDirectory5() throws InvalidParameterException {
		// Exists false - mkDir true - canWrite false
		expect(fileMock.exists()).andReturn(false);
		expect(fileMock.mkdir()).andReturn(true);
		expect(fileMock.canWrite()).andReturn(false);
		expect(fileMock.getName()).andReturn("teste").times(3);
		replay(fileMock);
		configurationManager.verifyDirectory(fileMock);
		verify(fileMock);
	}
	
	@Test(expected=InvalidParameterException.class)
	public void verifyDirectory6() throws InvalidParameterException {
		// Exists false - mkDir false
		expect(fileMock.exists()).andReturn(false);
		expect(fileMock.mkdir()).andReturn(false);
		expect(fileMock.getName()).andReturn("teste").times(3);
		replay(fileMock);
		configurationManager.verifyDirectory(fileMock);
		verify(fileMock);
	}

	@Test
	public void verifyFile1() throws InvalidParameterException {
		// exist and is File
		expect(fileMock.exists()).andReturn(true);
		expect(fileMock.isFile()).andReturn(true);
		replay(fileMock);
		configurationManager.verifyFile(fileMock);
		verify(fileMock);
	}
	
	@Test(expected=InvalidParameterException.class)
	public void verifyFile2() throws InvalidParameterException {
		// exist and is not File
		expect(fileMock.exists()).andReturn(true);
		expect(fileMock.isFile()).andReturn(false);
		replay(fileMock);
		configurationManager.verifyFile(fileMock);
		verify(fileMock);
	}
	
	@Test(expected=InvalidParameterException.class)
	public void verifyFile3() throws InvalidParameterException {
		// don't exist
		expect(fileMock.exists()).andReturn(false);
		replay(fileMock);
		configurationManager.verifyFile(fileMock);
		verify(fileMock);
	}
	
}
