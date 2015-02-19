package tests;

import static org.junit.Assert.assertEquals;
import io.FileWriter;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sorter.model.AbstractContestant;
import sorter.model.Configuration;
import sorter.model.ContestantProperties;
import sorter.model.Database;
import sorter.model.MarathonContestant;
import sorter.model.Time;

public class InvalidInputMarathonTest {

	private FileWriter fileWriter;
	private Database db;
	private File f;
	private Scanner sc;
	private AbstractContestant contestant;
	private Configuration conf;

	@Before
	public void setUp() throws IOException {
		conf = new Configuration(new File(
				"testfiles/config/marathonContestant.ini"));
		AbstractContestant.setConfiguration(conf);

		f = new File("testfiles/outputOddInputsTest.txt");
		fileWriter = new FileWriter(f);
		db = new Database();
		db.setContestantColumnNames(new String[] { "StartNr", "Namn" });
		contestant = new MarathonContestant(new ContestantProperties(
				new String[] { "StartNr", "Namn" }));
		contestant.putInformation("Namn", "Göran");
		contestant.putInformation("StartNr", "1");
	}

	@After
	public void tearDown() throws IOException {
		AbstractContestant.setConfiguration(new Configuration());
		f.delete();
	}

	@Test
	public void testNoStartTime() throws IOException {
		db.addContestantEntry("1", contestant);

		contestant.addFinishTime(new Time("13.23.34"));
		fileWriter.writeResults(conf, db);

		sc = new Scanner(f);
		assertEquals("StartNr; Namn; TotalTid; Starttider; Måltider", sc.nextLine());
		assertEquals("1; Göran; --.--.--; Start?; 13.23.34", sc.nextLine());
	}

	@Test
	public void testNoFinishTime() throws IOException {
		db.addContestantEntry("1", contestant);
		contestant.addStartTime(new Time("13.23.34"));

		fileWriter.writeResults(conf, db);

		sc = new Scanner(f);
		assertEquals("StartNr; Namn; TotalTid; Starttider; Måltider", sc.nextLine());
		assertEquals("1; Göran; --.--.--; 13.23.34; Slut?", sc.nextLine());
	}

	@Test
	public void testTooFast() throws IOException {
		db.addContestantEntry("1", contestant);

		contestant.addStartTime(new Time("13.23.34"));
		contestant.addFinishTime(new Time("13.30.34"));
		fileWriter.writeResults(conf, db);

		sc = new Scanner(f);
		assertEquals("StartNr; Namn; TotalTid; Starttider; Måltider", sc.nextLine());
		assertEquals("1; Göran; 00.07.00; 13.23.34; 13.30.34; Omöjlig totaltid?",
				sc.nextLine());
	}

	@Test
	public void testMultipleStart() throws IOException {
		contestant.addStartTime(new Time("13.23.34"));
		contestant.addStartTime(new Time("13.24.35"));
		contestant.addFinishTime(new Time("14.30.34"));
		db.addContestantEntry("1", contestant);

		fileWriter.writeResults(conf, db);

		sc = new Scanner(f);
		assertEquals("StartNr; Namn; TotalTid; Starttider; Måltider", sc.nextLine());
		assertEquals(
				"1; Göran; 01.07.00; 13.23.34; 14.30.34; Flera starttider? 13.24.35",
				sc.nextLine());
	}

	@Test
	public void testMultipleFinish() throws IOException {
		contestant.addStartTime(new Time("13.23.34"));
		contestant.addFinishTime(new Time("14.30.34"));
		contestant.addFinishTime(new Time("14.31.34"));
		db.addContestantEntry("1", contestant);
		
		fileWriter.writeResults(conf, db);

		sc = new Scanner(f);
		assertEquals("StartNr; Namn; TotalTid; Starttider; Måltider", sc.nextLine());
		
		assertEquals(
				"1; Göran; 01.07.00; 13.23.34; 14.30.34; Flera måltider? 14.31.34",
				sc.nextLine());
	}

}
