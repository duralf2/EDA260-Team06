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
import register.model.Time;

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
		ds.addEntry("1", new Time("12.00.00", "13.23.34"));
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
		assertEquals("StartNr; TotalTid; Starttid; Måltid", sc.nextLine());
		assertEquals("1;", sc.next());
		assertEquals("--.--.--;",  sc.next());
		assertEquals("12.00.00;",  sc.next());
		assertEquals("13.23.34",  sc.next());
	}

//	Gör om när vi har en sortmetod!!
//	@Test
//	public void testWriteTwoResults() {
//		ds.addEntry("2", new Time("13.37.00", "13.37.01"));
//		IOHandler.writeResult(pw, ds);
//		assert(f.exists());
//		assertEquals("StartNr; TotalTid; Starttid; Måltid", sc.nextLine());
//		assertEquals("1;", sc.next());
//		assertEquals("--.--.--;",  sc.next());
//		assertEquals("12.00.00;",  sc.next());
//		assertEquals("13.23.34",  sc.next());
//		assertEquals("2;", sc.next());
//		assertEquals("--.--.--;",  sc.next());
//		assertEquals("13.37.00;",  sc.next());
//		assertEquals("13.37.01",  sc.next());
//	}
	
	
	@Test
	public void testWriteStartTimes() {
		IOHandler.writeStartTimes(pw, ds);
		assert(f.exists());
		assertEquals("1;", sc.next());
		assertEquals("12.00.00",  sc.next());
	}

	@Test
	public void testWriteGoalTimes() {
		IOHandler.writeGoalTimes(pw, ds);
		assert(f.exists());
		assertEquals("1;", sc.next());
		assertEquals("13.23.34", sc.next());
	}

	
}
