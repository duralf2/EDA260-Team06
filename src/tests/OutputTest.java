package tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import register.logic.IOHandler;
import register.model.DataStructure;

public class OutputTest {

	private PrintWriter pw;
	private DataStructure ds;
	private File f;
	
	@Before
	public void setUp() throws FileNotFoundException
	{
		f = new File("output.txt");
		FileOutputStream fos = new FileOutputStream(f);
		pw = new PrintWriter(fos);
		ds = new DataStructure();
	}
	
	@After
	public void tearDown()
	{
		f.delete();
	}
	
	@Test
	public void testWriteResults() {
		IOHandler.writeResult(pw, ds);
		assert(f.exists());
	}

	@Test
	public void testWriteStartTimes() {
		IOHandler.writeStartTimes(pw, ds);
		assert(f.exists());
	}

	@Test
	public void testWriteGoalTimes() {
		IOHandler.writeGoalTimes(pw, ds);
		assert(f.exists());
	}

	
}
