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

public class OddInputsTest {

	private PrintWriter pw;
	private Database db;
	private File f;
	private Scanner sc;
	private AbstractContestant contestant;

	@Before
	public void setUp() throws FileNotFoundException {
		f = new File("output.txt");
		FileOutputStream fos = new FileOutputStream(f);
		pw = new PrintWriter(fos);
		db = new Database();
		sc = new Scanner(f);
		contestant = new MarathonContestant();
		contestant.putInformation("Namn", "Göran");
		contestant.addStartTime(new Time("13.23.34"));
	}

	@After
	public void tearDown() {
		f.delete();
	}

	@Test
	public void testNoStartTime() throws FileNotFoundException {
		db.addContestantEntry("1", contestant);
		FileWriter.writeResult(pw, db);
		assertEquals("StartNr; Namn; TotalTid; Starttider; Måltider",
				sc.nextLine());
		assertEquals("1; Göran; --.--.--; Start?; 13.23.34", sc.nextLine());
	}

	@Test
	public void testNoFinishTime() {
		db.addContestantEntry("1", contestant);
		FileWriter.writeResult(pw, db);
		assertEquals("StartNr; Namn; TotalTid; Starttider; Måltider",
				sc.nextLine());
		assertEquals("1; Göran; --.--.--; 13.23.34; Slut?", sc.nextLine());
	}

	@Test
	public void testTooFast() {
		contestant.addFinishTime(new Time("13.30.34"));
		db.addContestantEntry("1", contestant);
		FileWriter.writeResult(pw, db);
		assertEquals("StartNr; Namn; TotalTid; Starttider; Måltider",
				sc.nextLine());
		assertEquals(
				"1; Göran; 00.07.00; 13.23.34; 13.30.34; Omöjlig totaltid?",
				sc.nextLine());
	}

	@Test
	public void testMultipleStart() {
		contestant.addStartTime(new Time("13.24.35"));
		contestant.addFinishTime(new Time("14.30.34"));
		db.addContestantEntry("1", contestant);
		FileWriter.writeResult(pw, db);
		assertEquals("StartNr; Namn; TotalTid; Starttider; Måltider",
				sc.nextLine());
		assertEquals(
				"1; Göran; 01.07.00; 13.23.34; 14.30.34; Flera starttider? 13.24.35",
				sc.nextLine());
	}

	@Test
	public void testMultipleFinish() {
		contestant.addFinishTime(new Time("14.30.34"));
		contestant.addFinishTime(new Time("14.31.34"));
		db.addContestantEntry("1", contestant);
		FileWriter.writeResult(pw, db);
		assertEquals("StartNr; Namn; TotalTid; Starttider; Måltider",
				sc.nextLine());
		assertEquals(
				"1; Göran; 01.07.00; 13.23.34; 14.30.34; Flera måltider? 14.31.34",
				sc.nextLine());
	}

}
