package tests.RefContestantTests;

import static org.junit.Assert.*;

import org.junit.*;

import register.model.*;

public class TestLapContestant {
	private LapContestant lapContestant;

	@Before
	public void setUp() {
		RacerInfo racerInfo = new RacerInfo();
		racerInfo.put("StartNbr", "1");
		racerInfo.put("Name", "Lars");
		lapContestant = new LapContestant(racerInfo);
	}

	@After
	public void tearDown() {
		lapContestant = null;
	}

	@Test
	public void testToString() {
		lapContestant.addStartTime(new Time("00.00.00"));
		lapContestant.addFinishTime(new Time("00.10.01"));
		lapContestant.addLapTime(new Time("00.02.00"));
		lapContestant.addLapTime(new Time("00.06.00"));
		
		assertEquals("Lars;1;3;00.10.01;00.02.00;00.04.00;00.00.00;00.02.00;00.06.00;00.10.01", lapContestant.toString());
	}
	
	@Test
	public void testCompareTo() {
	}
	

}
