package tests.RefContestantTests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import register.model.Database;
import register.model.LapContestant;
import register.model.LapRace;
import register.model.RacerInfo;
import register.model.Time;

public class LapRaceTest {
	private LapRace lr;
	private Database db;
	private File f = new File("whatever");
	
	@Before
	public void setUp(){
		db = new Database();
		lr = new LapRace(db);
	}
	
	@After
	public void tearDown(){
		f.delete();
	}
	
	@Test
	public void testPrintColumnNames() {
		lr.print(f);
		Scanner scan = null;
		try {
			scan = new Scanner(f);
		} catch(IOException e) {
			e.printStackTrace();
		}
		assertTrue("StartNr;Namn;#Varv;TotalTid;Start;Mål".equalsIgnoreCase(scan.nextLine()));
	}
	
	@Test
	public void testManyLaps(){
		RacerInfo ri = new RacerInfo(new String[]{"StartNr","Namn"});
		ri.put("StartNr", "1");
		db.addContestantEntry("1", new LapContestant(ri));
		RacerInfo ri2 = new RacerInfo(new String[]{"StartNr","Namn"});
		ri2.put("StartNr", "2");
		LapContestant lc = new LapContestant(ri2);
		lc.addStartTime(new Time("15.05.55"));
		lc.addLapTime(new Time("16.05.55"));
		lc.addFinishTime(new Time("17.05.55"));
		db.addContestantEntry("2", lc);
		lr.print(f);
		Scanner scan = null;
		try {
			scan = new Scanner(f);
		} catch(IOException e) {
			e.printStackTrace();
		}
		assertEquals("StartNr;Namn;#Varv;TotalTid;Varv1;Varv2;Start;Varvning1;Mål", scan.nextLine());
		assertEquals("1;;0;00.00.00;;;;;", scan.nextLine());
		assertEquals("2;;2;02.00.00;01.00.00;01.00.00;15.05.55;16.05.55;17.05.55", scan.nextLine());
	}

}
