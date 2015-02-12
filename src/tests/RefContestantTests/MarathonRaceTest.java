package tests.RefContestantTests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import register.model.MarathonRace;

public class MarathonRaceTest {
	private MarathonRace race;
	private File outfile = new File("testfiles/MarathonRaceTestResult.txt");
	
	@Before
	public void setUp(){
		race = new MarathonRace();
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
}
