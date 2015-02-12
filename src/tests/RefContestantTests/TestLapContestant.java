package tests.RefContestantTests;

import static org.junit.Assert.*;

import org.junit.*;

import register.model.*;

public class TestLapContestant {
	private LapContestant lapContestant;
	private RacerInfo racerInfo;
	private Database db;

	@Before
	public void setUp() {
		db = new Database();
		
		racerInfo = new RacerInfo(new String[]{"StartNr","Namn"});
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
		assertEquals("1;Lars;3;00.10.01;00.02.00;00.04.00;00.04.01;00.00.00;00.02.00;00.06.00;00.10.01", lapContestant.toString(new LapRace(db)));
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
	

}
