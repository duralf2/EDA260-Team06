package tests.RefContestantTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import register.model.Database;
import register.model.LapContestant;
import register.model.LapCompetition;
import register.model.MarathonContestant;
import register.model.ContestantProperties;
import register.model.Time;

public class LapContestantTest {
	private LapContestant lapContestant;
	private ContestantProperties racerInfo;
	private Database db;

	@Before
	public void setUp() {
		db = new Database();
		
		racerInfo = new ContestantProperties(new String[]{"StartNr","Namn"});
		racerInfo.put("StartNr", "1");
		racerInfo.put("Namn", "Lars");
		
		lapContestant = new LapContestant(racerInfo);
		lapContestant.addStartTime(new Time("00.00.00"));
		lapContestant.addLapTime(new Time("00.02.00"));
		lapContestant.addLapTime(new Time("00.06.00"));
		lapContestant.addFinishTime(new Time("00.10.01"));
		
		db.addContestantEntry("1", lapContestant);
	}

	@After
	public void tearDown() {
		lapContestant = null;
	}

	@Test
	public void testToString() {
		assertEquals("1;Lars;3;00.10.01;00.02.00;00.04.00;00.04.01;00.00.00;00.02.00;00.06.00;00.10.01", lapContestant.toString(new LapCompetition(db)));
	}

	@Test
	public void testToStringMissingData() {
		lapContestant = new LapContestant(racerInfo);
		assertEquals("1;Lars;0;00.00.00;;;;;;;", lapContestant.toString(new LapCompetition(db)));
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
		
		assertTrue( lapContestant.compareTo(lapContestant2) < 0);
	}
	
	@Test
	public void testCompareToNbrOfLaps() {
		racerInfo.put("StartNbr", "2");
		racerInfo.put("Name", "Hampus");
		
		LapContestant lapContestant2 = new LapContestant(racerInfo);
		lapContestant2.addStartTime(new Time("00.00.00"));
		lapContestant2.addFinishTime(new Time("00.08.01"));
		lapContestant2.addLapTime(new Time("00.04.00"));
		
		assertTrue( lapContestant.compareTo(lapContestant2) > 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidObjectToCompareTo() {
		lapContestant.compareTo(new MarathonContestant(racerInfo));
	}
}