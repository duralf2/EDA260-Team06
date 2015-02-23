package tests.RefContestantTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sorter.model.AbstractContestant;
import sorter.model.Configuration;
import sorter.model.ContestantProperties;
import sorter.model.Database;
import sorter.model.LapCompetition;
import sorter.model.LapContestant;
import sorter.model.MarathonContestant;
import sorter.model.Time;

public class LapContestantTest {
	private LapContestant lapContestant;
	private ContestantProperties racerInfo;
	private Database db;
	private Configuration config;

	@Before
	public void setUp() throws IOException {
		db = new Database();
		
		File file = new File("testfiles/config/lapContestant.ini");
		config = new Configuration(file);
		AbstractContestant.setConfiguration(config);

		racerInfo = new ContestantProperties(new String[] { "StartNr", "Namn" });
		racerInfo.put("StartNr", "1");
		racerInfo.put("Namn", "Lars");

		lapContestant = new LapContestant(racerInfo);
		lapContestant.addStartTime(new Time("00.00.00"));
		lapContestant.addLapTime(new Time("00.02.00"));
		lapContestant.addLapTime(new Time("00.06.00"));
		lapContestant.addFinishTime(new Time("01.00.01"));

		db.addContestantEntry("1", lapContestant);
	}

	@After
	public void tearDown() throws IOException {
		lapContestant = null;
		AbstractContestant.setConfiguration(new Configuration());
	}

	@Test
	public void testToString() {
		assertEquals(
				"1;Lars;3;01.00.01;00.02.00;00.04.00;00.54.01;00.00.00;00.02.00;00.06.00;01.00.01",
				lapContestant.toString(new LapCompetition(db)));
	}
//	"StartNr;Namn;#Varv;TotalTid;Varv1;Varv2;Start;Varvning1;Mål",
	
//	lapContestant.addStartTime(new Time("00.00.00"));
//	lapContestant.addLapTime(new Time("00.02.00"));
//	lapContestant.addLapTime(new Time("00.06.00"));
//	lapContestant.addFinishTime(new Time("00.10.01"));
//
//	db.addContestantEntry("1", lapContestant);
//	TODO: Doesnt work as story 15.
	@Test
	public void testToStringMissingData() {
		lapContestant = new LapContestant(racerInfo);
		assertEquals("1;Lars;0;--.--.--;;;;;;;",
				lapContestant.toString(new LapCompetition(db)));
	}

	@Test
	public void testCompareToTotalTime() {
		racerInfo.put("StartNbr", "2");
		racerInfo.put("Name", "Hampus");

		LapContestant lapContestant2 = new LapContestant(racerInfo);
		lapContestant2.addStartTime(new Time("00.00.00"));
		lapContestant2.addFinishTime(new Time("00.08.01"));
		lapContestant2.addLapTime(new Time("00.03.00"));
		lapContestant2.addLapTime(new Time("00.04.00"));

		assertTrue(lapContestant.compareTo(lapContestant2) > 0);
	}

	@Test
	public void testCompareToNbrOfLaps() {
		racerInfo.put("StartNbr", "2");
		racerInfo.put("Name", "Hampus");

		LapContestant lapContestant2 = new LapContestant(racerInfo);
		lapContestant2.addStartTime(new Time("00.00.00"));
		lapContestant2.addFinishTime(new Time("00.08.01"));
		lapContestant2.addLapTime(new Time("00.04.00"));

		assertTrue(lapContestant.compareTo(lapContestant2) > 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidObjectToCompareTo() {
		lapContestant.compareTo(new MarathonContestant(racerInfo));
	}

	@Test
	public void testAddFinishTime() {
		config.setProperty(Configuration.KEY_SHORTEST_POSSIBLE_TIME, "13.00.00");
		
		LapContestant lapContestant2 = new LapContestant(racerInfo);
		lapContestant2.addStartTime(new Time("00.00.00"));
		lapContestant2.addFinishTime(new Time("00.08.01"));
		lapContestant2.addFinishTime(new Time("13.04.00"));

		assertEquals(2, lapContestant2.getLapsCompleted());
		assertEquals(new Time("13.04.00"), lapContestant2.getFinishTime());
	}
}
