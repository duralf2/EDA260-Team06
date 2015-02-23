package tests.RefContestantTests;

import static org.junit.Assert.*;
import io.FileWriter;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sorter.model.ContestantProperties;
import sorter.model.Database;
import sorter.model.MarathonCompetition;
import sorter.model.MarathonContestant;
import sorter.model.Time;

public class MarathonCompetitionTest {
	private MarathonCompetition race;
	private Database db;
	private File outfile = new File("testfiles/MarathonRaceTestResult.txt");
	private FileWriter fw;
	
	@Before
	public void setUp(){
		db = new Database();
		db.setContestantColumnNames(new String[] { "StartNr", "Namn" });
		race = new MarathonCompetition(db);
		fw = new FileWriter(outfile);
	}
	
	@After
	public void tearDown(){
		outfile.delete();
	}
	
	@Test
	public void testPrintColumnNames() throws IOException {
		fw.writeString(race.toResultString(false));
		Scanner scan = null;
		try {
			scan = new Scanner(outfile);
		} catch(IOException e) {
			e.printStackTrace();
		}
		assertEquals("StartNr; Namn; TotalTid; Starttider; Måltider",scan.nextLine());
		scan.close();
	}
	
	@Test
	public void testPrintResults() throws IOException {
		
		ContestantProperties ri = new ContestantProperties(new String[] { "StartNr", "Namn" });
		ri.put("StartNr", "1");
		db.addContestantEntry("1", new MarathonContestant(ri));
		ri = new ContestantProperties(new String[] { "StartNr", "Namn" });
		ri.put("StartNr", "2");
		MarathonContestant contestant = new MarathonContestant(ri);
		contestant.addStartTime(new Time("10.00.00"));
		contestant.addFinishTime(new Time("12.00.00"));
		contestant.putInformation("Namn", "Bertil");
		db.addContestantEntry("2", contestant);
		
		fw.writeString(race.toResultString(false));
		Scanner scan = null;
		try {
			scan = new Scanner(outfile);
		} catch(IOException e) {
			e.printStackTrace();
		}
		assertEquals("StartNr; Namn; TotalTid; Starttider; Måltider",scan.nextLine());
		assertEquals("2; Bertil; 02.00.00; 10.00.00; 12.00.00",scan.nextLine());
		assertEquals("Icke existerande startnummer", scan.nextLine());
		assertEquals("StartNr; Namn; TotalTid; Starttider; Måltider", scan.nextLine());
		assertEquals("1; ; --.--.--; Start?; Slut?",scan.nextLine());
		scan.close();
	}
}
