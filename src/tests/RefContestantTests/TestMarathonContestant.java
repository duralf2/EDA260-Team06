package tests.RefContestantTests;

import static org.junit.Assert.*;

import org.junit.*;

import register.model.RacerInfo;

public class TestMarathonContestant {
	private RacerInfo racerInfo;
	
	@Before
	public void setUp() {
		racerInfo = new RacerInfo("Hannah");
	}
	
	@After
	public void tearDown(){
		
	}

}
