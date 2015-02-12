package tests.RefContestantTests;

import static org.junit.Assert.*;

import org.junit.*;

import register.model.*;

public class TestStageContestant {
	private StageContestant stageContestant;

	@Before
	public void setUp() {
//		RacerInfo racerInfo = new RacerInfo(new String[0]);
//		racerInfo.put("StartNbr", "1");
//		racerInfo.put("Name", "Lars");
//		stageContestant = new StageContestant(racerInfo);
//		stageContestant.addStartTime(new Time("00.00.00"));
//		stageContestant.addFinishTime(new Time("00.02.01"));
//		stageContestant.addStartTime(new Time("00.05.11"));
//		stageContestant.addFinishTime(new Time("00.10.01"));
	}
	
	@After
	public void tearDown(){
		stageContestant = null;
	}

	@Test
	public void testToString() {
		assertTrue(true);
	}
	
	

}
