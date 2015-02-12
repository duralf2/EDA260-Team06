package tests;

import static org.junit.Assert.*;
import io.FileWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import register.model.AbstractContestant;
import register.model.Database;
import register.model.MarathonContestant;
import register.model.Time;

public class OutputTest {

	private PrintWriter pw;
	private Database db;
	private File f;
	private Scanner sc;
	
	@Before
	public void setUp() throws FileNotFoundException
	{
		f = new File("output.txt");
		FileOutputStream fos = new FileOutputStream(f);
		pw = new PrintWriter(fos);
		db = new Database();

		AbstractContestant contestant = new MarathonContestant();
		contestant.putInformation("Namn", "Göran-Victor Hansson");
		contestant.addStartTime(new Time("12.00.00"));
		contestant.addFinishTime(new Time("13.23.34"));
		db.addContestantEntry("1", contestant);
		sc = new Scanner(f);
	}
	
	@After
	public void tearDown()
	{
		f.delete();
	}

//	TODO: Gör om när vi har en sortmetod!!
//	@Test
//	public void testWriteTwoResults() {
//		ds.addEntry("2", new Time("13.37.00", "13.37.01"));
//		IOHandler.writeResult(pw, ds);
//		assert(f.exists());
//		assertEquals("StartNr; TotalTid; Starttid; Måltid", sc.nextLine());
//		assertEquals("1;", sc.next()ran-Victor );
//		assertEquals("--.--.--;",  sc.next());
//		assertEquals("12.00.00;",  sc.next());
//		assertEquals("13.23.34",  sc.next());
//		assertEquals("2;", sc.next());
//		assertEquals("--.--.--;",  sc.next());
//		assertEquals("13.37.00;",  sc.next());
//		assertEquals("13.37.01",  sc.next());
//	}
	
	
	/*
	 * Tests have been removed since the methods have been removed (not necessary) TODO
	 * 
	 * @Test
	public void testWriteStartTimes() {
		FileWriter.writeStartTimes(pw, ds);
		String[] line = sc.nextLine().split(";");
		assertTrue(f.exists());
		assertEquals("1", line[0]);
		assertEquals(" 12.00.00",  line[1]);
	}

	@Test
	public void testWriteFinishTimes() {
		FileWriter.writeFinishTimes(pw, ds);
		String[] line = sc.nextLine().split(";");
		assertTrue(f.exists());
		assertEquals("1", line[0]);
		assertEquals(" 13.23.34",  line[1]);
	}
	 */
	
	
	
	@Test
	public void testWriteResults() {
		FileWriter.writeResult(pw, db);
		assertTrue(f.exists());
		assertEquals("StartNr; Namn; TotalTid; Starttider; Måltider", sc.nextLine());
		String[] line = sc.nextLine().split(";");
		assertEquals("1", line[0]);
		assertEquals(" Göran-Victor Hansson", line[1]);
		assertEquals(" 01.23.34",  line[2]);
		assertEquals(" 12.00.00",  line[3]);
		assertEquals(" 13.23.34", line[4]);
	}
}
