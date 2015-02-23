package tests.RefContestantTests;

import static org.junit.Assert.assertEquals;
import io.FileWriter;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sorter.model.ContestantProperties;
import sorter.model.Database;
import sorter.model.LapCompetition;
import sorter.model.LapContestant;
import sorter.model.Time;

public class LapCompetitionTest {
	private LapCompetition race;
	private Database db;
	private File outfile = new File("testfiles/LapRaceTestResult.txt");
	private FileWriter fw;

	@Before
	public void setUp() {
		db = new Database();
		db.setContestantColumnNames(new String[] { "StartNr", "Namn" });
		race = new LapCompetition(db);
		fw = new FileWriter(outfile);
	}

	@After
	public void tearDown() {
		outfile.delete();
	}

	@Test
	public void testPrintColumnNames() throws IOException {
		fw.writeString(race.toResultString(false));
		Scanner scan = null;
		try {
			scan = new Scanner(outfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals("StartNr; Namn; #Varv; TotalTid; Start; Mål", scan.nextLine());
		scan.close();
	}

	@Test
	public void testManyLaps() throws IOException {
		ContestantProperties ri = new ContestantProperties(new String[] { "StartNr", "Namn" });
		ri.put("StartNr", "1");
		db.addContestantEntry("1", new LapContestant(ri));
		ContestantProperties ri2 = new ContestantProperties(new String[] { "StartNr", "Namn" });
		ri2.put("StartNr", "2");
		LapContestant contestant = new LapContestant(ri2);
		contestant.addStartTime(new Time("15.05.55"));
		contestant.addLapTime(new Time("16.05.55"));
		contestant.addFinishTime(new Time("17.05.55"));
		contestant.putInformation("Namn", "Bertil");
		db.addContestantEntry("2", contestant);
		fw.writeString(race.toResultString(false));
		Scanner scan = null;
		try {
			scan = new Scanner(outfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals("StartNr; Namn; #Varv; TotalTid; Varv1; Varv2; Start; Varvning1; Mål", scan.nextLine());
		assertEquals("2; Bertil; 2; 02.00.00; 01.00.00; 01.00.00; 15.05.55; 16.05.55; 17.05.55", scan.nextLine());
		assertEquals("Icke existerande startnummer", scan.nextLine());

		assertEquals("StartNr; Namn; #Varv; TotalTid; Start; Mål", scan.nextLine());
        assertEquals("1; ; 0; --.--.--; Start?; Slut?", scan.nextLine());

		scan.close();
	}

}
