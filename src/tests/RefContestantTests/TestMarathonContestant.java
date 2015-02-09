package tests.RefContestantTests;

import static org.junit.Assert.*;

import org.junit.*;

import register.model.MarathonContestant;
import register.model.RacerInfo;

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

}
