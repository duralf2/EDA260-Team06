package tests;

import static org.junit.Assert.*;
import io.IOHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import register.model.DataStructure;

public class OutputTest {

	private PrintWriter pw;
	private DataStructure ds;
	private File f;
	private Scanner sc;
	
	@Before
	public void setUp() throws FileNotFoundException
	{
		f = new File("output.txt");
		FileOutputStream fos = new FileOutputStream(f);
		pw = new PrintWriter(fos);
		ds = new DataStructure();
		sc = new Scanner(f);
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
		assertEquals(sc.nextLine(), "StartNr; TotalTid; Starttid; Måltid");
		assertEquals(sc.next(), "1;");
		assertEquals(sc.next(), "--.--.--;");
		assertEquals(sc.next(), "12.00.00;");
		assertEquals(sc.next(), "13.23.34");
	}

	@Test
	public void testWriteStartTimes() {
		IOHandler.writeStartTimes(pw, ds);
		assert(f.exists());
		assertEquals(sc.next(), "1;");
		assertEquals(sc.next(), "12.00.00");
	}

	@Test
	public void testWriteGoalTimes() {
		IOHandler.writeGoalTimes(pw, ds);
		assert(f.exists());
		assertEquals(sc.next(), "1;");
		assertEquals(sc.next(), "13.23.34");
	}

	
}
