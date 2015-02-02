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

		Contestant contestant = new Contestant("Göran-Victor Hansson");
		contestant.addStartTime(new Time("12.00.00"));
		contestant.addFinishTime(new Time("13.23.34"));
		ds.addContestantEntry("1", contestant);
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
		FileWriter.writeStartTimes(pw, ds);
		String[] line = sc.nextLine().split(";");
		assertTrue(f.exists());
		assertEquals("1", line[0]);
		assertEquals("12.00.00",  line[1]);
	}

	@Test
	public void testWriteFinishTimes() {
		FileWriter.writeFinishTimes(pw, ds);
		String[] line = sc.nextLine().split(";");
		assertTrue(f.exists());
		assertEquals("1", line[0]);
		assertEquals("13.23.34",  line[1]);
	}
	
	
	
	@Test
	public void testWriteResults() {
		FileWriter.writeResult(pw, ds);
		assertTrue(f.exists());
		assertEquals("StartNr; Namn; TotalTid; Starttid; Måltid", sc.nextLine());
		String[] line = sc.nextLine().split(";");
		assertEquals("1", line[0]);
		assertEquals("Göran-Victor Hansson", line[1]);
		assertEquals("01.23.34",  line[2]);
		assertEquals("12.00.00",  line[3]);
		assertEquals("13.23.34", line[4]);
	}
}
