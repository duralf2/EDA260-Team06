package tests.RefContestantTests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import register.model.Database;
import register.model.MarathonContestant;
import register.model.MarathonCompetition;
import register.model.RacerInfo;
import register.model.Time;

public class MarathonRaceTest {
	private MarathonCompetition race;
	private Database db;
	private File outfile = new File("testfiles/MarathonRaceTestResult.txt");
	
	@Before
	public void setUp(){
		db = new Database();
		race = new MarathonCompetition(db);
	}
	
	@After
	public void tearDown(){
		outfile.delete();
	}
	
	@Test
	public void testPrintColumnNames() {
		race.print(outfile);
		Scanner scan = null;
		try {
			scan = new Scanner(outfile);
		} catch(IOException e) {
			e.printStackTrace();
		}
		assertEquals("StartNr;Namn;TotalTid;Starttider;Måltider",scan.nextLine());
		scan.close();
	}
	
	@Test
	public void testPrintResults() {
		
		RacerInfo ri = new RacerInfo(new String[] { "StartNr", "Namn" });
		ri.put("StartNr", "1");
		db.addContestantEntry("1", new MarathonContestant(ri));
		ri = new RacerInfo(new String[] { "StartNr", "Namn" });
		ri.put("StartNr", "2");
		MarathonContestant contestant = new MarathonContestant(ri);
		contestant.addStartTime(new Time("10.00.00"));
		contestant.addFinishTime(new Time("12.00.00"));
		db.addContestantEntry("2", contestant);
		
		race.print(outfile);
		Scanner scan = null;
		try {
			scan = new Scanner(outfile);
		} catch(IOException e) {
			e.printStackTrace();
		}
		assertEquals("StartNr;Namn;TotalTid;Starttider;Måltider",scan.nextLine());
		assertEquals("1;;00.00.00;;;Omöjlig totaltid?",scan.nextLine());
		assertEquals("2;;02.00.00;10.00.00;12.00.00",scan.nextLine());
		scan.close();
	}
}
