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
		racerInfo = new RacerInfo();
		racerInfo.put("Name","Hannah");
		racer = new MarathonContestant(racerInfo);
	}
	
	@After
	public void tearDown(){
		racerInfo = null;
		racer = null;
	}
	
	@Test
	public void testTotalTime(){
		racer.addStartTime(new Time("12.00.01"));
		racer.addFinishTime(new Time("12.01.15"));
		assertEquals("00.01.14", racer.getTotalTime().toString());
	}
	
	@Test
	public void testToString(){
		racer.addStartTime(new Time("12.00.01"));
		racer.addFinishTime(new Time("12.01.15"));
		String match = "Hannah;00.01.14;12.00.01;12.01.15";
		assertEquals(match, racer.toString());
	}
	
	@Test
	public void testCompareTo(){
		RacerInfo racerInfo2 = new RacerInfo();
		racerInfo2.put("Name", "Kevin");
		MarathonContestant racerAwesome = new MarathonContestant(racerInfo2);
		
		racer.addStartTime(new Time("12.00.01"));
		racer.addFinishTime(new Time("12.02.15"));
		
		racerAwesome.addStartTime(new Time("12.00.01"));
		racerAwesome.addFinishTime(new Time("12.01.15"));
		
		assertEquals(-1, racer.compareTo(racerAwesome) );
	}

}
