package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import gui.model.FormatErrorHandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sorter.model.Configuration;

public class FormatErrorHandlerTest {
	private Properties conf;

	@Before
	public void setUp() {
        try {
            conf = new Properties();
            conf.load(new FileReader(new File("testfiles/custom/FormatErrorHandler/registration.properties")));
        } catch (IOException e) {
            fail("Could not read config file.");
        }
        FormatErrorHandler.setConf(conf);
	}
    
    @After
    public void tearDown() {
        conf = null;
    }

	@Test
	public void testAddErrors() {
		assertEquals("0th error.", 0, FormatErrorHandler.getErrorCount());
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
		String s = FormatErrorHandler.errorToString();
		assertEquals("Format error in " + conf.getProperty(Configuration.KEY_TIME_FILE_PATH), s);
	}

	@Test
	public void testPrintMultipleErrors() {
        FormatErrorHandler.addError(FormatErrorHandler.NAME, 2);
		FormatErrorHandler.addError(FormatErrorHandler.TIME, 3);
        String[] error = FormatErrorHandler.errorToString().split("\n");
        for(String s : error) {
            if(s.contains(conf.getProperty(Configuration.KEY_NAME_FILE_PATH))) {
                assertEquals("Format error in " + conf.getProperty(Configuration.KEY_NAME_FILE_PATH) + " at line 2", s);
            } else if(s.contains(conf.getProperty(Configuration.KEY_TIME_FILE_PATH))) {
                assertEquals("Format error in " + conf.getProperty(Configuration.KEY_TIME_FILE_PATH), s);
            } else {
                fail("Invalid error message for paths '" + conf.getProperty(Configuration.KEY_TIME_FILE_PATH) +
                     "' and '" + conf.getProperty(Configuration.KEY_NAME_FILE_PATH) + "': " + s);
            }
        }
	}
}
