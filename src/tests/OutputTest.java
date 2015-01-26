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

import register.model.Contestant;
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
		Contestant contestant = new Contestant("Göran");
		contestant.setStartTime(new Time("12.00.00"));
		contestant.setFinishTime(new Time("13.23.34"));
		ds.addContestantEntry("1", contestant);
		sc = new Scanner(f);
	}
	
	@After
	public void tearDown()
	{
		f.delete();
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
		assertTrue(f.exists());
		assertEquals("1;", sc.next());
		assertEquals("12.00.00",  sc.next());
	}

	@Test
	public void testWriteGoalTimes() {
		IOHandler.writeGoalTimes(pw, ds);
		assertTrue(f.exists());
		assertEquals("1;", sc.next());
		assertEquals("13.23.34", sc.next());
	}
	
	@Test
	public void testWriteResults() {
		IOHandler.writeResult(pw, ds);
		assertTrue(f.exists());
		assertEquals("StartNr; TotalTid; Starttid; Måltid", sc.nextLine());
		assertEquals("1;", sc.next());
		assertEquals("--.--.--;",  sc.next());
		assertEquals("12.00.00;",  sc.next());
		assertEquals("13.23.34",  sc.next());
	}
	
}
