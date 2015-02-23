package tests;

import static org.junit.Assert.*;

import java.io.IOException;

import gui.model.FormatErrorHandler;

import org.junit.Before;
import org.junit.Test;

import sorter.model.Configuration;

public class FormatErrorHandlerTest {
	private Configuration conf;
	
	@Before
	public void setUp() {
		try {
			conf = new Configuration();
		} catch (IOException e) {
			fail("Could not read config file.");
		}
	}
	
	@Test
	public void testAddErrors() {
		FormatErrorHandler.addError(FormatErrorHandler.TIME, 1);
		assertEquals("1st error.", 1, FormatErrorHandler.getErrorCount());
		FormatErrorHandler.addError(FormatErrorHandler.TIME, 1);
		assertEquals("2nd error.", 1, FormatErrorHandler.getErrorCount());
		FormatErrorHandler.addError(FormatErrorHandler.TIME, 2);
		assertEquals("3rd error.", 1, FormatErrorHandler.getErrorCount());
		FormatErrorHandler.addError(FormatErrorHandler.NAME, 1);
		assertEquals("4th error.", 2, FormatErrorHandler.getErrorCount());
		FormatErrorHandler.addError(FormatErrorHandler.NAME, 1);
		assertEquals("5th error.", 2, FormatErrorHandler.getErrorCount());
		FormatErrorHandler.addError(FormatErrorHandler.NAME, 2);
		assertEquals("6th error.", 3, FormatErrorHandler.getErrorCount());
	}
	
	@Test
	public void testPrintOneError() {
		FormatErrorHandler.addError(FormatErrorHandler.TIME, 1);
		assertEquals("Format error in " + conf.getProperty(Configuration.KEY_TIME_FILE_PATH), FormatErrorHandler.errorToString());
	}
}
