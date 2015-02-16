package tests.RefContestantTests;

import static org.junit.Assert.assertEquals;
import io.FileWriter;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import register.model.Database;
import register.model.LapContestant;
import register.model.LapCompetition;
import register.model.ContestantProperties;
import register.model.Time;

public class LapCompetitionTest {
	private LapCompetition race;
	private Database db;
	private File outfile = new File("testfiles/LapRaceTestResult.txt");
	private FileWriter fw;

	@Before
	public void setUp() {
		db = new Database();
		race = new LapCompetition(db);
		fw = new FileWriter(outfile);
	}

	@After
	public void tearDown() {
		outfile.delete();
	}

	@Test
	public void testPrintColumnNames() throws IOException {
		fw.printString(race.print());
		Scanner scan = null;
		try {
			scan = new Scanner(outfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals("StartNr;Namn;#Varv;TotalTid;Start;Mål", scan.nextLine());
		scan.close();
	}

	@Test
	public void testManyLaps() throws IOException {
		ContestantProperties ri = new ContestantProperties(new String[] { "StartNr", "Namn" });
		ri.put("StartNr", "1");
		db.addContestantEntry("1", new LapContestant(ri));
		ContestantProperties ri2 = new ContestantProperties(new String[] { "StartNr", "Namn" });
		ri2.put("StartNr", "2");
		LapContestant lc = new LapContestant(ri2);
		lc.addStartTime(new Time("15.05.55"));
		lc.addLapTime(new Time("16.05.55"));
		lc.addFinishTime(new Time("17.05.55"));
		db.addContestantEntry("2", lc);
		fw.printString(race.print());
		Scanner scan = null;
		try {
			scan = new Scanner(outfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(
				"StartNr;Namn;#Varv;TotalTid;Varv1;Varv2;Start;Varvning1;Mål",
				scan.nextLine());
		assertEquals("1;;0;00.00.00;;;;;", scan.nextLine());
		assertEquals(
				"2;;2;02.00.00;01.00.00;01.00.00;15.05.55;16.05.55;17.05.55",
				scan.nextLine());
		scan.close();
	}

}
