package tests.RefContestantTests;

import static org.junit.Assert.*;

import org.junit.*;

import register.model.MarathonContestant;
import register.model.RacerInfo;
import register.model.Time;

public class TestMarathonContestant {
	private RacerInfo racerInfo;
	private MarathonContestant racer;
	
	@Before
	public void setUp() {
		racerInfo = new RacerInfo("Hannah");
		racer = new MarathonContestant(racerInfo);
	}
	
	@After
	public void tearDown(){
		
	}
	
	@Test
	public void testTime(){
		racer.addStartTime(new Time("12.00.01"));
		racer.addFinishTime(new Time("12.01.15"));
		assertEquals("00.01.14", racer.getTotalTime().toString());
	}
	
	@Test
	public void testToString(){
		
	}

}
